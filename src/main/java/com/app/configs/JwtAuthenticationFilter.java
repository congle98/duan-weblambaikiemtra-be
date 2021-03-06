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
              System.out.println("????y l?? detail c???a au token"+ usernamePasswordAuthenticationToken.getDetails());
                // t???t c??? c??c request g???i l??n server s??? ph???i ??i qua b??? l???c n??y, n?? l???y m?? token t??? request
              //l???y userName t??? m?? token r???i l???y v??? 1 userDetail
              // v?? d???ng l??n m???t th???ng principal t??? userDetail ????? add v??o context c???a security;
              System.out.println("v??o b??? l???c r???i"+ SecurityContextHolder.getContext().getAuthentication());
          }
      }
      else {
          System.out.println("token is not valid");
      }
      //ch???c l?? tr??? v??? th??ng tin t??? 1 request
      filterChain.doFilter(request,response);
    }
}
