package com.durgaprabhu.interview.gateway.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class CompanyDetailsSearchEngineFilter implements Filter {

    private static final String X_API_KEY = "x-api-key";
    private static final String X_API_KEY_VALUE = "localtest";


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String apiKey = httpServletRequest.getHeader(X_API_KEY);

        if(Objects.nonNull(apiKey) && apiKey.equals(X_API_KEY_VALUE)){
            filterChain.doFilter(httpServletRequest, servletResponse);
        }else{
            HttpServletResponse http = (HttpServletResponse) servletResponse;
            http.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorised access");
        }

    }

//    @Bean
//    public FilterRegistrationBean<CompanyDetailsSearchEngineFilter> companyDetailsSearchEngineFilterFilterRegistrationBean(){
//        FilterRegistrationBean<CompanyDetailsSearchEngineFilter> registrationBean = new FilterRegistrationBean<>();
//
//        registrationBean.setFilter(new CompanyDetailsSearchEngineFilter());
//        registrationBean.addUrlPatterns("/Companies/*");
//
//        return registrationBean;
//    }
}