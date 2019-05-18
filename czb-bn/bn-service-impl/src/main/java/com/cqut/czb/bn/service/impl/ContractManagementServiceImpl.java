package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.ContractModelMapperExtra;
import com.cqut.czb.bn.dao.mapper.ContractRecordsMapperExtra;
import com.cqut.czb.bn.dao.mapper.FileMapper;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.contractManagement.ContractDTO;
import com.cqut.czb.bn.entity.dto.contractManagement.ContractInputDTO;
import com.cqut.czb.bn.entity.dto.contractManagement.PersonalContractDetailDTO;
import com.cqut.czb.bn.entity.entity.ContractModel;
import com.cqut.czb.bn.entity.entity.File;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.service.IContractService;
import com.cqut.czb.bn.util.file.FileUploadUtil;
import com.cqut.czb.bn.util.method.HttpClient4;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class ContractManagementServiceImpl implements IContractService {

    private ContractModelMapperExtra contractModelMapperExtra;

    private FileMapper fileMapper;

    private ContractRecordsMapperExtra contractRecordsMapperExtra;

    @Autowired
    public ContractManagementServiceImpl(ContractModelMapperExtra contractModelMapperExtra, FileMapper fileMapper, ContractRecordsMapperExtra contractRecordsMapperExtra) {
        this.contractModelMapperExtra = contractModelMapperExtra;
        this.fileMapper = fileMapper;
        this.contractRecordsMapperExtra = contractRecordsMapperExtra;
    }

    /**
     * 更新token的方法
     * @return
     */
    public static String checkToken(){
        JSONObject request = new JSONObject();
        request.put("appId", "2019042516271800110");
        request.put("appKey", "uDCFes85C3OwDQ");
        String response = HttpClient4.doPost("https://api.yunhetong.com/api/auth/login", request, 0);
        int indexMax = response.length();
        response = response.substring(7, indexMax); // 删除token前的一些无关字符

        return response;
    }

    /**
     * 合同管理-上传合同模板
     * */
    @Override
    public boolean uploadContractTemplate(String templateName, MultipartFile file, User user) throws IOException {
        String address = "";
        if (file != null || !file.isEmpty()) {
            address = FileUploadUtil.putObject(file.getOriginalFilename(), file.getInputStream());
        }
        System.out.println(file.getOriginalFilename());
        String token = checkToken();
        OkHttpClient client = new OkHttpClient();

        RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file.getBytes());//将file转换成RequestBody文件
        RequestBody body=new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("multipartFile",file.getOriginalFilename(), fileBody)
                .addFormDataPart("templateName",templateName)
                .build();
        Request request = new Request.Builder()
                .url("https://api.yunhetong.com/api/template/upload")
                .post(body)
                .addHeader("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
                .addHeader("charset", "UTF-8")
                .addHeader("token", token)
                .addHeader("cache-control", "no-cache")
                .build();

        Response response = client.newCall(request).execute();

        JSONObject json = new JSONObject();
        json.put("res", response.body().string());
        String code = json.getJSONObject("res").get("code").toString();
        String templateId = "";
        if("200".equals(code)) {
            templateId = json.getJSONObject("res").getJSONObject("data").get("templateId").toString();
        }
        if(templateId.equals("")) {
            return false;
        }
        File file1 = setFile(file.getOriginalFilename(), address, user.getUserId(), new Date());
        fileMapper.insertSelective(file1);
        ContractModel contractModel = new ContractModel();
        contractModel.setModelId(StringUtil.createId());
        contractModel.setModelName(templateName);
        contractModel.setYunModelId(templateId);
        contractModel.setFileId(file1.getFileId());

        return contractModelMapperExtra.insertContractModel(contractModel) > 0;
    }

    @Override
    public PageInfo<ContractModel> selectContractModelList(PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        List<ContractModel> contractModelList = contractModelMapperExtra.selectContractModelList();
        return new PageInfo<>(contractModelList);
    }

    @Override
    public PageInfo<ContractDTO> selectEnterpriseContractList(ContractInputDTO contractInputDTO, PageDTO pageDTO) {
        contractInputDTO.setContractType(0);
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        List<ContractDTO> contractDTOList = contractRecordsMapperExtra.selectContractList(contractInputDTO);
        return new PageInfo<>(contractDTOList);
    }

    @Override
    public PageInfo<ContractDTO> selectPersonalContractList(ContractInputDTO contractInputDTO, PageDTO pageDTO) {
        contractInputDTO.setContractType(1);
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        List<ContractDTO> contractDTOList = contractRecordsMapperExtra.selectContractList(contractInputDTO);
        return new PageInfo<>(contractDTOList);
    }

    @Override
    public PageInfo<ContractDTO> selectEnterpriseContract(String contractId, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        List<ContractDTO> contractDTOList = contractRecordsMapperExtra.selectEnterpriseContract(contractId);
        return new PageInfo<>(contractDTOList);
    }

    @Override
    public PageInfo<ContractDTO> selectPersonalContract(String contractId, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        List<ContractDTO> contractDTOList = contractRecordsMapperExtra.selectPersonalContract(contractId);
        return new PageInfo<>(contractDTOList);
    }

    @Override
    public PersonalContractDetailDTO selectPersonalContractDetail(String contractId) {
        return contractRecordsMapperExtra.selectPersonalContractDetail(contractId);
    }

    @Override
    public boolean changeContractState(ContractInputDTO contractInputDTO) {

        return contractRecordsMapperExtra.changeContractState(contractInputDTO) > 0;
    }

    /**
     * 设置File对象内容
     */
    public File setFile(String name,String path,String user,Date create) {
        File file = new File();
        file.setFileId(StringUtil.createId());
        file.setFileName(name);
        file.setSavePath(path);
        file.setUploader(user);
        if (create == null) {
            file.setCreateAt(new Date());
        } else {
            file.setCreateAt(create);
        }
        file.setUpdateAt(new Date());
        return file;
    }

}
