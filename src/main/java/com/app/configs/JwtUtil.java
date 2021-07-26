package com.app.configs;

import com.app.models.User;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private static  final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private String jwtSecret = "nhom4";

    //    thời gian 1 ngày
    private int jwtExpirationMs = 86400000;

    //hàm khởi tạo 1 token và gửi về phía người dùng
    public String generateJwtToken(Authentication authentication){
        User userPrincipal = (User) authentication.getPrincipal();


        return Jwts.builder()
                //trả về một chuỗi  jwt sau khi được khởi tạo từ username và password  đăng nhập
                //xác nhận quyền sở hữu
                .setSubject(userPrincipal.getUsername())
                //lấy thời gian khởi tạo token
                .setIssuedAt(new Date())
                //cài đặt thời gian hết hạn
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                //tạo khoá theo thuật toán mã hoá và key của mình
                .signWith(SignatureAlgorithm.HS512,jwtSecret)
                //xây dựng và gói nó thành 1 chuỗi token
                .compact();
    }

    // lấy username từ chuỗi token khi người dùng gửi token lên server
    public String getUserNameFormJwtToken(String token){

        //giải mã hoá từ key của mình và từ subject đã đăng ký
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken){
        try {
            //kiểm tra xem token này có hợp lệ này ko bằng cách thử giải mã hoá
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return  true;
        }
        catch (SignatureException e){
            //sai chữ ký
            logger.error("invalid jwt signature: {}",e.getMessage());
        }
        catch (MalformedJwtException e){
            //không đúng định dạng
            logger.error("invalid jwt token: {}",e.getMessage());
        }
        catch (ExpiredJwtException e){
            //token hết hạn
            logger.error("jwt token is expired: {}",e.getMessage());
        }
        catch (IllegalArgumentException e){
            //chuỗi token bị trống
            logger.error("jwt claims string is empty: {}",e.getMessage());
        }
        return false;
    }
}
