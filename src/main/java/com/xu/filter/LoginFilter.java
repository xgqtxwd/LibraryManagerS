package com.xu.filter;

import com.alibaba.fastjson.JSONObject;
import com.xu.pojo.Result;
import com.xu.utils.JwtUtils;
import com.xu.utils.ThreadLocalUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class LoginFilter implements Filter {
   //建立放行列表，不需要登录就能访问的路径
    private static final List<String> STATIC_EXTENSIONS = Arrays.asList(
        "/login", "/register", "/public","/pay","/notify"
);

    @Override//初始化方法，可调用多次
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        /*1.获取请求路径 */
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();
        log.info("请求的url{}", requestURI);
        /*2.判断是否为登录请求，是则放行*/
        //检查当前请求路径是否在排除列表当中
        boolean isStaticResource = STATIC_EXTENSIONS.stream()
                .anyMatch(requestURI::endsWith);

        if (isStaticResource) {
            filterChain.doFilter(request, response);
            return;
        }
        /*3.获取token*/

        String jwt= request.getHeader("Authorization");
        log.info(jwt);
        /*4.判断令牌是否存在，如果不存在则返回错误信息*/
        if (!StringUtils.hasLength(jwt)) {
            log.info("jwt为空");
            response.setStatus(401);
            Result error = Result.error("NOT_LOGIN");
            //将错误信息转为json格式
            String ontLogin=JSONObject.toJSONString(error);
            response.getWriter().write(ontLogin);
            return;
        }
        /*5.校验jwt令牌是否合法*/
        try{
            JwtUtils.parseJWT(jwt);
        }catch (Exception e){
            e.printStackTrace();

            log.info("令牌非法，返回错误信息");
            response.setStatus(401);
            Result error = Result.error("NOT_LOGIN");
            String ontLogin=JSONObject.toJSONString(error);
            response.getWriter().write(ontLogin);
            return;
        }
        //6.放行操作
        log.info("登录成功，放行");
        Map<String, Object> map = JwtUtils.parseJWT(jwt);
        //将解析后的用户信息存入ThreadLocal
        ThreadLocalUtil.set(map);
        filterChain.doFilter(servletRequest, servletResponse);
    }
    @Override//初始化方法，调用一次
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        System.out.println("初始化已经执行");
    }

    @Override//销毁方法调用一次
    public void destroy() {
        //从ThreadLocal中移除用户信息
        ThreadLocalUtil.remove();
        Filter.super.destroy();
        System.out.println("销毁已经执行");
    }
}
