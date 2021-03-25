package com.cqut.czb.bn.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Liyan
 */
@RestController
@RequestMapping("/")
public class WxConfigurationController {

    @GetMapping("MP_verify_rQXBi1HTO8cOpwkb.txt")
    public String MP_verify_rQXBi1HTO8cOpwkb(){
        return "rQXBi1HTO8cOpwkb";
    }
}
