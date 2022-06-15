package com.eturial.SNMPManager.server.controller;

import com.eturial.SNMPManager.server.service.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson.JSONObject;

/**
 * @author Eturial
 * @date 2022/5/4
 */

@RestController
@RequestMapping(value = "/request")
@CrossOrigin
public class ManagerController {
    @Autowired
    Server server;

    @GetMapping("/snmp")
    public void addProduct() {
        server.sendAndReceive();
    }

}
