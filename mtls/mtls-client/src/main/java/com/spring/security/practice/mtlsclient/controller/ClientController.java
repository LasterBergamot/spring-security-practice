package com.spring.security.practice.mtlsclient.controller;

import java.net.URI;
import java.net.URISyntaxException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/ssp-mtls-client")
@Slf4j
@RequiredArgsConstructor
public class ClientController {

    private final Environment environment;

    private final RestTemplate restTemplate;

    @GetMapping(value = "/data")
    public String getData() {
        log.info("Returning data from ClientController!");
        return "DataFromClient";
    }

    @GetMapping("/server-data")
    public String getServerData() {
        String serverEndpoint = environment.getProperty("ms-service");

        try {
            return restTemplate.getForObject(new URI(serverEndpoint), String.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return "Exception occurred";
    }
}
