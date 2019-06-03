package com.wei.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "api-service")
public interface HelloClient {

@GetMapping("/hello")
public String hello(
 @RequestParam(value = "name", defaultValue = "ws") String name);

}
