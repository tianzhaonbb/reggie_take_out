package com.tz.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.tz.reggie.common.BaseContext;
import com.tz.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();

        String[] urls = new String[]{

                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**"

        };

        //判断是否需要处理，true表示不需要处理
        boolean check = check(urls, requestURI);
        log.info("拦截到请求" + requestURI);


        if(check){
            log.info("本次请求{}不需要处理",requestURI);
            filterChain.doFilter(request,response);
            return;
        }

        //对于需要处理的请求，进行判断是否登录
        if(request.getSession().getAttribute("employee") != null){
            log.info("用户已登录,用户id为：{}",request.getSession().getAttribute("employee"));

            Long empId = (Long) request.getSession().getAttribute("employee");

            long id = Thread.currentThread().getId();
            log.info("线程的id：{}",id);

            BaseContext.setThreadLocal(empId);


            filterChain.doFilter(request,response);
            return;
        }

        log.info("用户未登录");
        //没有登录的部分进行处理,给前端写数据进行响应
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;

    }

    /**
     * 路径匹配
     * @param requestUrl
     * @return
     */

    public boolean check(String[] urls ,String requestUrl){

        for(String url : urls){
            boolean match = PATH_MATCHER.match(url, requestUrl);

            if(match){
                return true;
            }

        }

        return false;
    }

}
