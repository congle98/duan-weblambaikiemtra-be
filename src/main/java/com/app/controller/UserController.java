package com.app.controller;

import com.app.dto.UserRequestCreate;
import com.app.exceptions.UserFoundException;
import com.app.models.Role;
import com.app.models.User;
import com.app.services.role.IRoleService;
import com.app.services.user.IUserService;
import com.app.services.user.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<?> saveUser(@RequestBody UserRequestCreate userRequestCreate) throws Exception {
            if(userService.findByUserName(userRequestCreate.getUsername()).isPresent()){
                throw new UserFoundException();
            }
            User user = new User();
            user.setUsername(userRequestCreate.getUsername());
            user.setEmail(userRequestCreate.getEmail());
            user.setPassword(bCryptPasswordEncoder.encode(userRequestCreate.getPassword()));
            Role role = roleService.findById(1l).get();
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(role);
            user.setRoles(roleSet);
            User userRp = userService.save(user);
            //nếu tài khoản tồn tại thì thows ra 1 userfoundexception đã tạo hoặc ra luôn exception
            return new ResponseEntity<>(userRp, HttpStatus.CREATED);



    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findUser(@PathVariable Long id){
        User user = userService.findById(id).get();
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<?>updateUser(@RequestBody User user){
        User user1 = userService.findById(user.getId()).get();
        user1.setAvatar(user.getAvatar());




//        userService.save(user),
        return new ResponseEntity<>(userService.save(user1),HttpStatus.OK);
    }

//    @PostMapping("/signin")
//    public ResponseEntity<?> signIn(@RequestBody )


    //dùng userfoundException để trả về nếu gặp userfoundexception hoặc
    // dùng exception để bao hàm toàn bộ lỗi và trả về cũng dc

//    @ExceptionHandler(UserFoundException.class)
//    public  ResponseEntity<?> exceptionHandler(UserFoundException ex){
//        return new ResponseEntity<>(ex,HttpStatus.NOT_FOUND);
//    }
    @ExceptionHandler(Exception.class)
    public  ResponseEntity<?> exceptionHandler(Exception ex){
        return new ResponseEntity<>(ex,HttpStatus.NOT_FOUND);
    }
}
