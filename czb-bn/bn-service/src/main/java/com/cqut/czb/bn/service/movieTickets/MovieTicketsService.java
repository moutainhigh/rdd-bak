package com.cqut.czb.bn.service.movieTickets;

import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;

import java.security.Principal;

public interface MovieTicketsService {

    JSONResult qianzhu(Principal principal, User user);
}
