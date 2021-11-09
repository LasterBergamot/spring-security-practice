package com.spring.security.practice.mtlsserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ssp-mtls-server")
@Slf4j
public class ServerController {

    @GetMapping(value = "/data")
    public String getData() {
        log.info("Returning data from ServerController!");
        return "DataFromServer";
    }
}
