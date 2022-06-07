package com.erkanberk.guardiandemo;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONException;
import org.json.JSONObject;
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


    @GetMapping("/login")
    public String getLogin(){

        restTemplate = new RestTemplate();

        MultiValueMap<String, String> uriParams = new LinkedMultiValueMap<String, String>();
        uriParams.add("email",   env.getProperty("api.login.email"));
        uriParams.add("password",  env.getProperty("api.login.password"));

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> request = new HttpEntity<Object>(uriParams,headers);

        String responseString = restTemplate.postForObject(env.getProperty("api.login.url"),request,String.class,uriParams);


        return responseString;

    }

    @GetMapping("/report")
    public String getTransactionReport() throws JSONException {

        restTemplate = new RestTemplate();
        JSONObject jsonObj = new JSONObject(getLogin());

        MultiValueMap<String, String> uriParams = new LinkedMultiValueMap<String, String>();
        uriParams.add("fromDate",   env.getProperty("api.transactions.from"));
        uriParams.add("toDate",  env.getProperty("api.transactions.to"));
        uriParams.add("merchant",  env.getProperty("api.transactions.merchant"));
        uriParams.add("acquirer",  env.getProperty("api.transactions.acquirer"));

        HttpHeaders headers = new HttpHeaders();
        System.out.println("token:"+jsonObj.getString("token"));
        headers.add("Authorization", jsonObj.getString("token"));
        HttpEntity<?> request = new HttpEntity<Object>(uriParams,headers);

        String responseString = restTemplate.postForObject(env.getProperty("api.transactions.url"),request,String.class,uriParams);


        return responseString;

    }

}
