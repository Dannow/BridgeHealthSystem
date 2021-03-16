package com.hardworkgroup.bridge_health_system.system_common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /*
     * 解决跨域问题
     *
     * registry跨域对象
     * 访问路径,请求来源，方法，最大时间，允许携带
     * */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080", "null")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .maxAge(3600)
                .allowCredentials(true);
    }
    //url访问文件位置，仅限本地转移
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //文件磁盘图片url 映射
        //配置server虚拟路径，handler为前台访问的目录，locations为files相对应的本地路径
        registry.addResourceHandler("/img/**").addResourceLocations("file:/home/dong/doucments/BridgeHealthSystem/bridge_health_system_picture/img/");
        //registry.addResourceHandler("/img/**").addResourceLocations("file:D:/fwwb/xxzl/");
    }
}
