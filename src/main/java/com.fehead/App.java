package com.fehead;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Date;

/**
 * Hello world!
 *
 */
@RestController
@SpringBootApplication
@MapperScan("com.fehead.dao")
@EnableEurekaClient
@EnableSwagger2
@EnableFeignClients
@EnableDiscoveryClient
public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!");
        Date date=new Date();
        System.out.println(date);
        SpringApplication.run(App.class,args);
    }


}
