/**
 * This project is a project which provide API for getting short url by long URI.
 *
 * Here is short description: ( for more detailed description please read README.md or
 * go to https://github.com/theshamuel/shortening-url )
 *
 * Back-end: Spring (Spring Boot, Spring IoC, Spring Data, Spring Test), JWT library, Java8
 * DB: MongoDB
 * Tools: git,maven,docker.
 *
 */
package com.theshamuel.shrturl.filter;

import io.jsonwebtoken.*;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * The JWT filter class.
 *
 * @author Alex Gladkikh
 */
public class JwtFilter extends GenericFilterBean {

    @Override
    public void doFilter(final ServletRequest req,
                         final ServletResponse res,
                         final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        final String authHeader = request.getHeader("Authorization");
        if (((authHeader == null || !authHeader.startsWith("Bearer ")) && request.getRequestURI()!=null
                && request.getRequestURI().toLowerCase().contains("api")
                && !request.getRequestURI().toLowerCase().contains("/links")
                && !request.getMethod().toLowerCase().equals("post"))) {
            ((HttpServletResponse)res).sendError(403,"Missing or invalid Authorization header.");
        }else if (authHeader != null && authHeader.startsWith("Bearer ") ) {
            final String token = authHeader.substring(7);
            try {
                final Claims claims = Jwts.parser().setSigningKey("SuperKey182")
                        .parseClaimsJws(token).getBody();
                request.setAttribute("claims", claims);
            } catch (SignatureException e) {
                ((HttpServletResponse)res).sendError(HttpServletResponse.SC_UNAUTHORIZED,"Invalid token.");
                throw new SignatureException("Invalid signature of token.");
            } catch (ExpiredJwtException e) {
                ((HttpServletResponse)res).sendError(HttpServletResponse.SC_UNAUTHORIZED,"Token session is expired.");
                throw new ExpiredJwtException(Jwts.parser().setSigningKey("SuperKey182")
                        .parseClaimsJws(token).getHeader(),(Claims)request.getAttribute("claims"),"Token session is expired.");
            }
        }

        chain.doFilter(request, response);
    }

}
