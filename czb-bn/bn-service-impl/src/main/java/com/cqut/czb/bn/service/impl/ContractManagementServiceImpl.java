package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.contractManagement.ContractDTO;
import com.cqut.czb.bn.entity.dto.contractManagement.ContractInputDTO;
import com.cqut.czb.bn.entity.dto.contractManagement.PersonalContractDetailDTO;
import com.cqut.czb.bn.entity.entity.*;
import com.cqut.czb.bn.service.IContractService;
import com.cqut.czb.bn.util.date.DateUtil;
import com.cqut.czb.bn.util.file.FileUploadUtil;
import com.cqut.czb.bn.util.method.HttpClient4;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import okhttp3.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class ContractManagementServiceImpl implements IContractService {

    private ContractModelMapperExtra contractModelMapperExtra;

    private FileMapper fileMapper;

    private ContractRecordsMapperExtra contractRecordsMapperExtra;

    private ContractRecordsMapper contractRecordsMapper;

    @Autowired
    public ContractManagementServiceImpl(ContractModelMapperExtra contractModelMapperExtra, FileMapper fileMapper, ContractRecordsMapperExtra contractRecordsMapperExtra, ContractRecordsMapper contractRecordsMapper) {
        this.contractModelMapperExtra = contractModelMapperExtra;
        this.fileMapper = fileMapper;
        this.contractRecordsMapperExtra = contractRecordsMapperExtra;
        this.contractRecordsMapper = contractRecordsMapper;
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
        if(contractInputDTO.getSignBeginTime() != null) {
            contractInputDTO.setSignBeginTime(DateUtil.getDateStart(contractInputDTO.getSignBeginTime()));
        }
        if(contractInputDTO.getSignEndTime() != null) {
            contractInputDTO.setSignEndTime(DateUtil.getDateEnd(contractInputDTO.getSignEndTime()));
        }
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        List<ContractDTO> contractDTOList = contractRecordsMapperExtra.selectContractList(contractInputDTO);
        return new PageInfo<>(contractDTOList);
    }

    @Override
    public PageInfo<ContractDTO> selectPersonalContractList(ContractInputDTO contractInputDTO, PageDTO pageDTO) {
        contractInputDTO.setContractType(1);
        if(contractInputDTO.getSignBeginTime() != null) {
            contractInputDTO.setSignBeginTime(DateUtil.getDateStart(contractInputDTO.getSignBeginTime()));
        }
        if(contractInputDTO.getSignEndTime() != null) {
            contractInputDTO.setSignEndTime(DateUtil.getDateEnd(contractInputDTO.getSignEndTime()));
        }
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

    @Override
    public boolean downloadContract(String contractId, HttpServletResponse response) {
        ContractRecords contractRecords = contractRecordsMapper.selectByPrimaryKey(contractId);
        String token = checkToken();
        JSONObject paramMap = new JSONObject();
        paramMap.put("idType", "0"); // id类型：0合同ID，1合同自定义编号
        paramMap.put("idContent", contractRecords.getThirdContractNum()); // ID内容

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        // 创建httpClient实例
        httpClient = HttpClients.createDefault();
        // 创建httpPost远程连接实例
        HttpPost httpPost = new HttpPost("https://api.yunhetong.com/api/download/contract");
        // 配置请求参数实例
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 设置连接主机服务超时时间
                .setConnectionRequestTimeout(35000)// 设置连接请求超时时间
                .setSocketTimeout(60000)// 设置读取数据连接超时时间
                .build();
        /**
         *
         * 为httpPost实例设置配置
         */
        httpPost.setConfig(requestConfig);
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.addHeader("charset", "UTF-8");
        httpPost.addHeader("token", token);

        /**
         * 文件下载请求头
         * */
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type", "application/x-download");

        StringEntity requestEntity = new StringEntity(paramMap.toString(), ContentType.APPLICATION_JSON);
        httpPost.setEntity(requestEntity);
        HttpEntity entity;
        try {
            // httpClient对象执行post请求,并返回响应参数对象
            httpResponse = httpClient.execute(httpPost);
            // 从响应对象中获取响应内容(调用相应的响应信息处理方法)
            entity = httpResponse.getEntity();
            entity.writeTo(response.getOutputStream());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            // 关闭资源
            if (null != httpResponse) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
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
