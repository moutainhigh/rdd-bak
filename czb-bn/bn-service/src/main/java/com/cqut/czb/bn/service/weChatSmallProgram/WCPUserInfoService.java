package com.cqut.czb.bn.service.weChatSmallProgram;

import com.cqut.czb.bn.entity.dto.user.UserDTO;

/**
 * @Description
 * @auther nihao
 * @create 2019-11-28 20:12
 */
public interface WCPUserInfoService {

    String getRecommendQRCode(UserDTO user);
}