package com.example.security.security.filter;

import com.example.security.domain.AccountDto;
import com.example.security.security.token.AjaxAuthenticationToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private ObjectMapper objectMapper = new ObjectMapper();

    protected AjaxLoginProcessingFilter() {
        // 해당 url과 요청이 일치하면 작동하는 필터
        super(new AntPathRequestMatcher("/api/login"));
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        
        if(isAjax(request)){
            throw new IllegalStateException("authentication is not supported");
        }
        AccountDto accountDto = objectMapper.readValue(request.getReader(), AccountDto.class);

        if (StringUtils.isEmpty(accountDto.getUsername()) || StringUtils.isEmpty(accountDto.getPassword())) {
            throw new AuthenticationServiceException("Username or Password not provided");
        }
        AjaxAuthenticationToken token = new AjaxAuthenticationToken(accountDto.getUsername(),accountDto.getPassword());

        return this.getAuthenticationManager().authenticate(token);
    }

    private boolean isAjax(HttpServletRequest request) {

        // ajax 여부를 체크하는 법
        // header 에 넣은 값을 체크해서 ajax 로그인인지 아닌지 체크
        if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
            return true;
        }
        return false;
    }
}
