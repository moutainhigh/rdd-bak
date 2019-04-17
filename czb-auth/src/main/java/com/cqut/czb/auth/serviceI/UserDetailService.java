package com.cqut.czb.auth.serviceI;

import com.cqut.czb.bn.entity.entity.User;

public interface UserDetailService {
    Boolean register(User user);
    Boolean checkAccount(User user);
}
