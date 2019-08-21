package com.cqut.czb.bn.api.controller.vehicleService;

import com.cqut.czb.auth.service.UserDetailService;
import com.cqut.czb.bn.entity.dto.appCaptchaConfig.VerificationCodeDTO;
import com.cqut.czb.bn.entity.dto.user.PersonalUserDTO;
import com.cqut.czb.bn.entity.entity.vehicleService.CleanRider;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.vehicleService.RiderService;
import com.cqut.czb.bn.util.riderAttribute.MessageLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: lyk
 * @date: 8/12/2019
 */
@CrossOrigin
@RestController
@RequestMapping("/api/service/rider")
public class RiderManageController {
    @Autowired
    RiderService riderService;

    @Autowired
    UserDetailService userDetailService;

    @GetMapping("/deleteByKey")
    public JSONResult deleteByKey(@RequestParam(name = "riderId") String riderId) {
        return new JSONResult(riderService.deleteByPrimaryKey(riderId));
    }

    @PostMapping("/insert")
    public JSONResult insertSelective(CleanRider cleanRider) {

        // 初始化默认返回JSON信息
        JSONResult jsonResult = initJSONResult();

        // 判断骑手信息是否为空
        if (deal(cleanRider) || deal(cleanRider.getContactNumber())) {
            jsonResult.setMessage(MessageLog.MESSAGE_NOT_FULL);
            return jsonResult;
        }

        VerificationCodeDTO verify = initVerify(cleanRider);

        boolean resultOfVCode = userDetailService.insertVCode(verify);

        // 判断自动插入验证码是否失败
        if (!resultOfVCode) {
            jsonResult.setMessage(MessageLog.VCODE_SET_FAILD);
            return jsonResult;
        }

        PersonalUserDTO person = initPerson(cleanRider);

        String result = userDetailService.registerPersonalUser(person);

        // 判断用户表数据中插入是否失败
        if ("false".equals(result)) {
            jsonResult.setMessage(MessageLog.AUTO_REGISTE_FAID);
            return jsonResult;
        }

        Boolean result_1 = riderService.insertSelective(cleanRider);

        if (result_1 == true) {
            jsonResult.setCode(200);
            jsonResult.setMessage(MessageLog.AUTO_REGISTE_SUCCESS);
            jsonResult.setData(true);
            return jsonResult;
        }

        return jsonResult;
    }

    @PostMapping("/updateById")
    public JSONResult updateByPrimaryKeySelective(CleanRider cleanRider) {
        return new JSONResult(riderService.updateByPrimaryKeySelective(cleanRider));
    }

    /**
     * 统一查询接口
     * @param cleanRider
     * @return
     */
    @GetMapping("/getRider")
    public JSONResult getRider(CleanRider cleanRider) {
        return new JSONResult(riderService.getRider(cleanRider));
    }

    /**
     * 判断为空则返回true
     * @param obj
     * @return
     */
    private boolean deal(Object obj) {
        if (obj != null && obj.toString() != "") {
            return false;
        }
        return true;
    }

    /**
     * 初始化personal
     *
     * @param cleanRider
     * @return
     */
    private PersonalUserDTO initPerson(CleanRider cleanRider) {

        PersonalUserDTO personalUserDTO = new PersonalUserDTO();
        personalUserDTO.setUserName(cleanRider.getRiderName());
        personalUserDTO.setUserPsw("000000");
        personalUserDTO.setContent("000000");
        personalUserDTO.setUserAccount(cleanRider.getContactNumber());

        return personalUserDTO;
    }

    /**
     * 为当前电话号码设置默认验证码
     *
     * 值为 000000
     * @param cleanRider
     * @return
     */
    private VerificationCodeDTO initVerify(CleanRider cleanRider) {

        VerificationCodeDTO verify = new VerificationCodeDTO();
        verify.setUserAccount(cleanRider.getContactNumber());
        verify.setVerificationCodeId("000000");

        return verify;
    }

    /**
     * 初始化JSON返回信息
     * @return
     */
    private JSONResult initJSONResult() {
        JSONResult jsonResult = new JSONResult();
        jsonResult.setCode(500);
        jsonResult.setMessage("操作失败");
        jsonResult.setData(false);

        return jsonResult;
    }
}
