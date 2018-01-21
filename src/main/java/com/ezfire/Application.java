package com.ezfire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by lcy on 2018/1/20.
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);

	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		String propertiesFilename = getPropertiesFilename();

		PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
		configurer.setLocation(new ClassPathResource(propertiesFilename));

		return configurer;
	}

	private static String getPropertiesFilename() {
		String activeProfile = getProfile();
		return activeProfile + ".properties";
	}

	private static String getProfile() {
		return System.getProperty("spring.profiles.active", "ezfiresvr");
	}

	private static Properties appConfig;

	public static Properties getAppConfig(){
		if(appConfig != null){
			return appConfig;
		}

		String propertiesFilename = getPropertiesFilename();

		try {
			appConfig = new Properties();
			appConfig.load(Application.class.getClassLoader().getResourceAsStream(propertiesFilename));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return appConfig;
	}
}
