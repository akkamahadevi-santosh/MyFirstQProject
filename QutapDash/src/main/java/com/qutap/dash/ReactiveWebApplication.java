
package com.qutap.dash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableCaching
@EnableAutoConfiguration
//@EnableResourceServer
@ComponentScan(basePackages="com.qutap.dash")
public class ReactiveWebApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(ReactiveWebApplication.class, args);
	}
	
	@Bean
	public Docket api(){
	return new Docket(DocumentationType.SWAGGER_2)
								.select().apis(RequestHandlerSelectors.any())
								.paths(PathSelectors
								.any()) 
								.build();
	}
	
	/*@Bean
	protected ResourceServerConfigurerAdapter resourceServerConfigurerAdapter() {
	   return new ResourceServerConfigurerAdapter() {
	       @Override
	       public void configure(HttpSecurity http) throws Exception {
	           http.authorizeRequests()
	                   .antMatchers("/", "/index.html", "/Qutap/sign-in-widget-config").permitAll()
	                   .anyRequest().authenticated();
	       }
	   };
	}*/
}