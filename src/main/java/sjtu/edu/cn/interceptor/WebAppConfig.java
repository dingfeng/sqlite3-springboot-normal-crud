package sjtu.edu.cn.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 丁峰
 * @date 2018/7/12 9:31
 * @see WebAppConfig
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //展示拦截所有路劲
        registry.addInterceptor(securityInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public HandlerInterceptor securityInterceptor() {
        return new SecurityInterceptor();
    }

}
