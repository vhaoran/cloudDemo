package com.wei.tempm.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableEurekaClient
public class Hello {

@Value("${server.port}")
String port;

@RequestMapping("/hello")
public String hello(@RequestParam(value = "name", defaultValue = "ws") String name) {
	return "class:Hello->hello " + name + " ,i am from port:" + port;
}

@RequestMapping("/test/{name}")
public String test(
	@PathVariable("name") String name) {
	return "class:Hello->test " + name + " ,i am from port:" + port;
}

}
