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
     * 增加访问路径
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
