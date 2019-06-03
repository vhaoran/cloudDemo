package com.wei.tempm;

import java.util.Properties;

import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
@RestController
@EnableSwagger2
public class TempmApplication {

	public static void main(String[] args) {
		SpringApplication.run(TempmApplication.class, args);
	}

	@Value("${server.port}")
	String port;
//  pageHelper方法一
//	@Bean
//	PageHelper pageHelper() {
//		// 分页插件
//		PageHelper page = new PageHelper();
//		Properties p = new Properties();
//		p.setProperty("reasonable", "true");
//		p.setProperty("supportMethodsArguments", "true");
//		p.setProperty("returnPageInfo", "check");
//		p.setProperty("params", "count=countSql");
//		page.setProperties(p);
//
//		// 添加插件
//		new SqlSessionFactoryBean().setPlugins(new Interceptor[] { pageHelper });
//		return page;
//	}
	

}
