package com.mysite.sbb;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload-path}")
    private String uploadPath;  // properties에 정의된 값만 사용 (기본값 제거)

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /uploads/** URL로 접근 → 실제 파일 경로 서빙
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadPath + "/");  // Windows/Mac/Linux 호환
    }
}