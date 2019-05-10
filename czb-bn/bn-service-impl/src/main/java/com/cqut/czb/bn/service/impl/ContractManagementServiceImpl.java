package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.ContractModelMapperExtra;
import com.cqut.czb.bn.dao.mapper.FileMapper;
import com.cqut.czb.bn.entity.dto.PageDTO;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class ContractManagementServiceImpl implements IContractService {

    @Autowired
    private ContractModelMapperExtra contractModelMapperExtra;

    @Autowired
    private FileMapper fileMapper;

    public String getContractToken(){
        JSONObject request = new JSONObject();
        request.put("appId", "2019042516271800110");
        request.put("appKey", "uDCFes85C3OwDQ");
        String response = HttpClient4.doPost("https://api.yunhetong.com/api/auth/login", request, 0);

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
        File file1 = setFile(file.getOriginalFilename(), address, user.getUserId(), new Date());
        fileMapper.insertSelective(file1);

        String token = getContractToken();
        JSONObject requestJson = new JSONObject();
        requestJson.put("templateName", templateName);
        requestJson.put("multipartFile ", file);
        requestJson.put("token", token);
        String templateId = HttpClient4.doPost("https://api.yunhetong.com/api/template/upload", requestJson, 1);
        if(templateId == null || templateId.equals("") || !templateId.startsWith("templateId")) {
            return false;
        }
        templateId = templateId.replace("templateId: ", "");
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
        }
        else {
            file.setCreateAt(create);
        }
        file.setUpdateAt(new Date());
        return file;
    }
}
