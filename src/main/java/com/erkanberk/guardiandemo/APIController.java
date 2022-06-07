package com.erkanberk.guardiandemo;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@RestController
public class APIController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Environment env;
    @Value( "${api.login.url}" )
    private String loginurl;

    @GetMapping("/login")
    public String getLogin(){

        restTemplate = new RestTemplate();

        MultiValueMap<String, String> uriParams = new LinkedMultiValueMap<String, String>();
        uriParams.add("email",   env.getProperty("api.login.email"));
        uriParams.add("password",  env.getProperty("api.login.password"));

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> request = new HttpEntity<Object>(uriParams,headers);

        String responseString = restTemplate.postForObject(loginurl,request,String.class,uriParams);


        return responseString;

    }


}
