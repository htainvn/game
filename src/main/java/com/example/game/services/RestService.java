package com.example.game.services;
import java.util.Collections;
import java.util.HashMap;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestService {
  private RestTemplate restTemplate;

  public RestService() {
    this.restTemplate = new RestTemplate();
  }

  public String get(String url) {
    return this.restTemplate.getForObject(url, String.class);
  }

  public String post(String url, String body) {
    return this.restTemplate.postForObject(url, body, String.class);
  }

  public String getWithHeaders(String url, String body, HashMap<String, String> headers) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    httpHeaders.setBearerAuth(headers.get("Authorization"));
    HttpEntity<String> entity = new HttpEntity<>(body, httpHeaders);
    ResponseEntity<String> response = restTemplate.exchange(
        url, HttpMethod.GET, entity, String.class);
    return response.getBody();
  }

  public String postWithHeaders(String url, String body, HashMap<String, String> headers) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    httpHeaders.setBearerAuth(headers.get("Authorization"));
    HttpEntity<String> entity = new HttpEntity<>(body, httpHeaders);
    return this.restTemplate.postForObject(url, entity, String.class);
  }

  public void put(String url, String body) {
    this.restTemplate.put(url, body);
  }

  public void delete(String url) {
    this.restTemplate.delete(url);
  }
}
