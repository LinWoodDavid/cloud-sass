package com.weimengchao.common.tool;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

public class RestUtil {

    private static final RestTemplate restTemplate;

    static {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(60000);
        requestFactory.setReadTimeout(60000);
        restTemplate = new RestTemplate(requestFactory);
    }

    private RestUtil() {
    }

    public static <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        return restTemplate.getForEntity(url, responseType, uriVariables);
    }

    public static <T> ResponseEntity<T> getForEntity(URI url, Class<T> responseType) throws RestClientException {
        return restTemplate.getForEntity(url, responseType);
    }

    public static <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType) throws RestClientException {
        return restTemplate.getForEntity(url, responseType);
    }

    public static <T> T getForObject(String url, Class<T> responseType) throws RestClientException {
        return restTemplate.getForObject(url, responseType);
    }

    public static <T> T getForObject(URI url, Class<T> responseType) throws RestClientException {
        return restTemplate.getForObject(url, responseType);
    }

    public static <T> ResponseEntity<T> doPost(URI url, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<? extends Map<String, ?>> request = new HttpEntity<>(uriVariables, headers);
        return restTemplate.postForEntity(url, request, responseType);
    }

    //post请求
    public static <T> ResponseEntity<T> doPost(String url, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<? extends Map<String, ?>> request = new HttpEntity<>(uriVariables, headers);
        return restTemplate.postForEntity(url, request, responseType);
    }

    //post请求
    public static <T> ResponseEntity<T> doPost(String url, Class<T> responseType, String json) throws RestClientException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(json, headers);
        return restTemplate.postForEntity(url, request, responseType);
    }

}
