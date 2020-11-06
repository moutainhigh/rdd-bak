package com.cqut.czb.bn.api.controller;

import com.alibaba.fastjson.JSON;
import com.cqut.czb.auth.interceptor.PermissionCheck;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.petrolDeliveryRecords.DeliveryInput;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.PetrolDeliveryRecordsService;
import com.cqut.czb.bn.util.constants.ResponseCodeConstants;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**   PetrolDeliveryRecordsController  油卡快递模块
 *
 */
@RequestMapping("/api/petrolDelivery")
@RestController
public class PetrolDeliveryRecordsController {
    @Autowired
    PetrolDeliveryRecordsService petrolDeliveryRecordsService;

    @Autowired
    RedisUtils redisUtils;

    @Value("${register.common.petrolType}")
    private Integer commonPetrolType;

    @Value("${register.special.petrolType}")
    private Integer specialPetrolType;


    /**
     * 获取寄送数据&查询
     * @param deliveryInput
     * @param pageDTO
     * @return
     */
    @GetMapping("/selectRecords")
    public JSONResult selectRecords(DeliveryInput deliveryInput, PageDTO pageDTO, Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        if (user.getIsSpecial() == 0){
            deliveryInput.setIsSpecialPetrol(commonPetrolType);
        }
        else if (user.getIsSpecial() == 1){
            deliveryInput.setIsSpecialPetrol(specialPetrolType);
        }
        return new JSONResult(petrolDeliveryRecordsService.selectPetrolDelivery(deliveryInput,pageDTO));
    }

    @PostMapping("/deleteRecord")
    @PermissionCheck(role = "管理员")
    public JSONResult deleteRecord(@RequestBody DeliveryInput deliveryInput){
        return new JSONResult(petrolDeliveryRecordsService.deleteByPrimaryKey(deliveryInput.getRecordId()));
    }

    /**
     * 确认收货（批量）
     * @param deliveryInput
     * @return
     */
    @PostMapping("/receivePetrolDelivery")
//    @PermissionCheck(role = "管理员")
    public JSONResult receivePetrolDelivery(@RequestBody DeliveryInput deliveryInput){
        return new JSONResult(petrolDeliveryRecordsService.receivePetrolDelivery(deliveryInput.getIds()));
    }

    /**
     * 修改寄送信息（快递单号/公司）
     * @param deliveryInput
     * @return
     */
    @PostMapping("/updateRecords")
    @PermissionCheck(role = "管理员")
    public JSONResult updateRecords(@RequestBody DeliveryInput deliveryInput, Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        if (user.getIsSpecial() == 0){
            deliveryInput.setIsSpecialPetrol(commonPetrolType);
        }
        else if (user.getIsSpecial() == 1){
            deliveryInput.setIsSpecialPetrol(specialPetrolType);
        }
        return new JSONResult(petrolDeliveryRecordsService.updatePetrolDelivery(deliveryInput));
    }

    /**
     * 导出excel表（根据时间段筛选）
     * @param response
     * @param request
     * @param deliveryInput
     * @return
     */
    @PostMapping("/exportRecords")
    @PermissionCheck(role = "管理员")
    public JSONResult exportPertrolRecord(HttpServletResponse response, HttpServletRequest request,
                                                        DeliveryInput deliveryInput, Principal principal) {
        User user = (User)redisUtils.get(principal.getName());
        if (user.getIsSpecial() == 0){
            deliveryInput.setIsSpecialPetrol(commonPetrolType);
        }
        else if (user.getIsSpecial() == 1){
            deliveryInput.setIsSpecialPetrol(specialPetrolType);
        }

        Map<String, Object> result = new HashMap<>();
        String message = null;
        Workbook workbook = null;
        try {
            workbook = petrolDeliveryRecordsService.exportDeliveryRecords(deliveryInput);
            System.out.println("wwww"+workbook);
            if(workbook == null) {
     //           workbook = new SXSSFWorkbook();
                message = "当前月没有未导出的数据啦";
                result.put("message", message);
                return new JSONResult(result);
            }
            //设置对客户端请求的编码格式
            request.setCharacterEncoding("utf-8");
            //指定服务器返回给浏览器的编码格式
            response.setCharacterEncoding("utf-8");
            //点击下载之后出现下载对话框
            response.setContentType("application/x-download");
            String fileName = null;
            fileName = "油卡寄送记录.xlsx";
            //System.out.println(fileName);
            //将中文转换为16进制
            fileName = URLEncoder.encode(fileName,"utf-8");
            //确保浏览器弹出对应文件的对话框
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            OutputStream out = response.getOutputStream();
            workbook.write(out);
            //System.out.println("====="+out.toString());
            out.close();
        } catch (IOException e) {
            message = "导出Excel数据失败，请稍后再试";
        } catch (Exception e1) {
            e1.printStackTrace();
            message = "Excel数据量过大，请缩短导出文件的时间间隔";
        }
        result.put("message", message);
        return new JSONResult(result);

    }

    /**
     * 导入excel表（更新快递单号/公司 状态）
     * @param file
     * @return
     */
    @PostMapping("importRecords")
    @PermissionCheck(role = "管理员")
    public JSONResult importRecords(MultipartFile file){
        try {
            petrolDeliveryRecordsService.ImportDeliveryRecords(file);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new JSONResult("success");
    }

    /**
     * 物流查询
     * @return
     */
    @PostMapping("/selectLogistics")
//    @PermissionCheck(role = "管理员")
    public JSONResult selectLogisticsOnPC(@RequestBody DeliveryInput deliveryInput){
        String logistics = petrolDeliveryRecordsService.selectLogistics(deliveryInput);
        if (logistics==null||logistics.equals("")){
            return new JSONResult(ResponseCodeConstants.FAILURE,"查询失败");}
     else {
            return new JSONResult(ResponseCodeConstants.SUCCESS,JSON.parse(logistics));
        }
    }

    @RequestMapping(value = "/getDeliveryInfo" ,method = RequestMethod.GET)
    public JSONResult getDeliveryInfo(Principal principal,DeliveryInput deliveryInput ){
        User user = (User) redisUtils.get(principal.getName());
        return new JSONResult(petrolDeliveryRecordsService.getDeliveryInfo(user.getUserId(),deliveryInput.getPetrolKind()+""));

    }

}
