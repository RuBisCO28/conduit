package com.realworld.conduit.config;

import com.realworld.conduit.domain.service.JwtService;
import com.realworld.conduit.domain.service.UserService;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

@SuppressWarnings("SpringJavaAutowiringInspection")
public class JwtTokenFilter extends OncePerRequestFilter {
  @Autowired private UserService userService;
  @Autowired private JwtService jwtService;
  private static final String header = "X-AUTH-TOKEN";

  @Override
  protected void doFilterInternal(
    HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {
    getTokenString(request.getHeader(header))
      .flatMap(token -> jwtService.getSubFromToken(token))
      .ifPresent(
        id -> {
          if (SecurityContextHolder.getContext().getAuthentication() == null) {
            final var user = userService.findById(id);
            if (user != null) {
              UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                  user, null, Collections.emptyList());
              authenticationToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));
              SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
          }
        });

    filterChain.doFilter(request, response);
  }

  private Optional<String> getTokenString(String header) {
    if (header == null) {
      return Optional.empty();
    } else {
      return Optional.ofNullable(header);
    }
  }
}
