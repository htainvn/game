package com.example.game.services;

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

  public void put(String url, String body) {
    this.restTemplate.put(url, body);
  }

  public void delete(String url) {
    this.restTemplate.delete(url);
  }
}
