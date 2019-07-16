package com.autozcare.main.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.autozcare.main.constants.AutozcareConstants;
import com.autozcare.main.service.CustomUserDetailsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
        	
        	getJwtFromRequest(request)
        	.filter(tokenProvider::validateToken)
        	.map(this::getMobileNumberFromJwtAndLoadUser)
        	.map(userDetail -> this.prepareUsernamePasswordAuthenticationToken(userDetail, request))
        	.map(authentication -> {
        		SecurityContextHolder.getContext().setAuthentication(authentication);
        		return authentication;
        	});
        	
        } catch (Exception ex) {
            log.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

    private Optional<String> getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(AutozcareConstants.BEARER_WITH_SPACE)) {
            return Optional.of(bearerToken.substring(7, bearerToken.length()));
        }
        return Optional.empty();
    }
    
    private UserDetails getMobileNumberFromJwtAndLoadUser(String jwtToken) {
        String mobileNumber = tokenProvider.getMobileNumberFromJwt(jwtToken);
        return customUserDetailsService.findUserByMobileNumber(mobileNumber);
    }
    
    private UsernamePasswordAuthenticationToken prepareUsernamePasswordAuthenticationToken(UserDetails userDetails, HttpServletRequest request) {
    	UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authentication;
    }
}
