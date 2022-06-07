package com.erkanberk.guardiandemo;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;



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
        headers.add("Authorization", jsonObj.getString("token"));
        HttpEntity<?> request = new HttpEntity<Object>(uriParams,headers);

        String responseString = restTemplate.postForObject(env.getProperty("api.transactions.report.url"),request,String.class,uriParams);


        return responseString;

    }

    @GetMapping("/list")
    public String getTransactionList() throws JSONException {

        restTemplate = new RestTemplate();
        JSONObject jsonObj = new JSONObject(getLogin());

        MultiValueMap<String, String> uriParams = new LinkedMultiValueMap<String, String>();
        uriParams.add("fromDate",   env.getProperty("api.transactions.from"));
        uriParams.add("toDate",  env.getProperty("api.transactions.to"));
        uriParams.add("merchant",  env.getProperty("api.transactions.merchant"));
        uriParams.add("acquirer",  env.getProperty("api.transactions.acquirer"));
        //uriParams.add("status",  env.getProperty("api.transactions.status"));
        //uriParams.add("operation",  env.getProperty("api.transactions.operation"));
        //uriParams.add("paymentMethod",  env.getProperty("api.transactions.method"));
        //uriParams.add("errorCode",  env.getProperty("api.transactions.ecode"));
        //uriParams.add("filterField",  env.getProperty("api.transactions.ffield"));
        //uriParams.add("filterValue",  env.getProperty("api.transactions.fvalue"));
        //uriParams.add("page",  env.getProperty("api.transactions.page"));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", jsonObj.getString("token"));
        HttpEntity<?> request = new HttpEntity<Object>(uriParams,headers);

        String responseString = restTemplate.postForObject(env.getProperty("api.transactions.list.url"),request,String.class,uriParams);


        return responseString;

    }

    @GetMapping("/detail")
    public String getTransactionDetail() throws JSONException {

        restTemplate = new RestTemplate();
        JSONObject jsonObj = new JSONObject(getLogin());

        MultiValueMap<String, String> uriParams = new LinkedMultiValueMap<String, String>();
        uriParams.add("transactionId",   env.getProperty("api.transactions.id"));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", jsonObj.getString("token"));
        HttpEntity<?> request = new HttpEntity<Object>(uriParams,headers);

        String responseString = restTemplate.postForObject(env.getProperty("api.transactions.detail.url"),request,String.class,uriParams);


        return responseString;

    }

    @GetMapping("/client")
    public String getClient() throws JSONException {

        restTemplate = new RestTemplate();
        JSONObject jsonObj = new JSONObject(getLogin());

        MultiValueMap<String, String> uriParams = new LinkedMultiValueMap<String, String>();
        uriParams.add("transactionId",   env.getProperty("api.transactions.id"));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", jsonObj.getString("token"));
        HttpEntity<?> request = new HttpEntity<Object>(uriParams,headers);

        String responseString = restTemplate.postForObject(env.getProperty("api.client.url"),request,String.class,uriParams);


        return responseString;

    }
}
