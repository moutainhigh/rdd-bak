package com.cqut.czb.bn.api.init;

import com.cqut.czb.bn.dao.mapper.RoleApiMapperExtra;
import com.cqut.czb.bn.entity.dto.roleApi.RoleApiIdDTO;
import com.cqut.czb.bn.service.impl.Cache.RolePermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Init implements CommandLineRunner {

    @Autowired
    RoleApiMapperExtra roleApiMapperExtra;

    Logger logger = LoggerFactory.getLogger(Init.class);

    @Override
    public void run(String... args) throws Exception {
        List<RoleApiIdDTO> roleApiIdDTOS = roleApiMapperExtra.getAllRoleApi();
        int count = RolePermissions.updateMap(roleApiIdDTOS);
        logger.info(count + "");
    }
}
