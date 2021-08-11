package com.app.controller;

import com.app.configs.JwtUtil;
import com.app.models.JwtRequest;
import com.app.models.JwtResponse;
import com.app.models.User;
import com.app.services.user.IUserService;
import com.app.services.user.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
public class AuthenticateController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private IUserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    private void authenticate(String username,String password) throws Exception {
        try{

        }
        catch (DisabledException e){
            throw  new Exception("User disable"+e.getMessage());
        }
        catch (BadCredentialsException e){
            throw  new Exception("invalid credentials"+e.getMessage());
        }
    }

    @PostMapping("/generate-token")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),jwtRequest.getPassword()));
        //thêm đối tượng này vào secutiry để xử lý tiếp
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //khởi tạo jwt từ đối tượng này
        String jwt = jwtUtil.generateJwtToken(authentication);
        //tạo đối tượng userdetail từ authen.getPrincipal
        System.out.println("jwt là "+jwt);


        return new ResponseEntity<>(
                    new JwtResponse(jwt), HttpStatus.OK);


    }

    @GetMapping("/current-user")
    public User getCurrentUser(Principal principal){

        return (User) this.userDetailsService.loadUserByUsername(principal.getName());
    }
}
