package com.github.team66.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class MultipartResolverConfig {
	
	public static final Long MAX_UPLOAD = 10L * 1024 * 1024;

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(MAX_UPLOAD);
		return multipartResolver;
	}
	
}