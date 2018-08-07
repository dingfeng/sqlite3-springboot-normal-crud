package sjtu.edu.cn.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerInterceptor;
import sjtu.edu.cn.vo.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 丁峰
 * @date 2018/7/12 9:34
 * @see SecurityInterceptor
 */
@Slf4j
public class SecurityInterceptor implements HandlerInterceptor {
    public final static String TOKEN_PARAMETER = "token";

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
        httpServletResponse.setHeader("Access-Control-Expose-Headers", "*");
        return true;
    }

    @ExceptionHandler(Throwable.class)
    public Response handleException(Throwable e) {
        log.error("error=", e);
        return Response.failure();
    }
}
