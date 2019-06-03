package com.wei.controller;

import com.wei.client.HelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableEurekaClient
public class TestController {

@Autowired
private HelloClient helloClient;

@GetMapping("/hello/{name}")
public String testHello(@PathVariable("name") String name) {

 if (name == null) {
  name = "default -name";
 }
 return helloClient.hello("----" + name);
}

}
