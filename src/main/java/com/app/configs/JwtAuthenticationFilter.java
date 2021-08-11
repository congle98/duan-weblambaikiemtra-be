package com.app.configs;

import com.app.models.User;
import com.app.services.user.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
      String requestTokenHeader =   request.getHeader("Authorization");
      String username = null;
      String jwtToken = null;
      if(requestTokenHeader!=null&&requestTokenHeader.startsWith("Bearer ")){
            jwtToken = requestTokenHeader.substring(7);
            try{
                username = jwtUtil.getUserNameFormJwtToken(jwtToken);
            }
           catch (ExpiredJwtException e){
                e.printStackTrace();

           }catch (Exception e){
                e.printStackTrace();
            }
      }
      else {
          System.out.println("invalid token, not start with bearer string ");
      }

      if (username!=null&& SecurityContextHolder.getContext().getAuthentication()==null){
          UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
          if(this.jwtUtil.validateJwtToken(jwtToken)){
              UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
              System.out.println("đây là detail của au token"+ usernamePasswordAuthenticationToken.getDetails());
                // tất cả các request gửi lên server sẽ phải đi qua bộ lọc này, nó lấy mã token từ request
              //lấy userName từ mã token rồi lấy về 1 userDetail
              // và dựng lên một thằng principal từ userDetail để add vào context của security;
              System.out.println("vào bộ lọc rồi"+ SecurityContextHolder.getContext().getAuthentication());
          }
      }
      else {
          System.out.println("token is not valid");
      }
      //chắc là trả về thông tin từ 1 request
      filterChain.doFilter(request,response);
    }
}
