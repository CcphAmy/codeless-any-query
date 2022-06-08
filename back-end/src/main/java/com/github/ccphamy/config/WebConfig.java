package com.github.ccphamy.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // @Override
    // public void addCorsMappings(CorsRegistry registry) {
    //     registry.addMapping("/**")
    //         .allowedOrigins("*")
    //         .allowCredentials(true)
    //         .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
    //         .maxAge(3600);
    // }


    /**
     * 增加静态访问路径
     * 本身项目为前后端分离, 支持使用 tomcat 容器
     * 当前需要将前端项目build至resources/public下, 后续考虑指定外部路径.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/public/");
    }

    @RequestMapping("/")
    public void index(HttpServletResponse response) throws IOException {
        response.sendRedirect("/index.html");
    }

}
