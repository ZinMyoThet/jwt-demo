package com.example.awtjwtdemo.controller;

import com.example.awtjwtdemo.data.User;
import com.example.awtjwtdemo.data.USerDao;
import com.example.awtjwtdemo.service.UserService;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;

    }

    @Autowired
    private USerDao userDao;


    @GetMapping("/hello")
    public String hello() {
        return "Hello!";
    }
    //curl -X POST -H "Content-Type: application/json" -d '{"first_name":"John","last_name":"Doe","email":"john@gmail.com","password":"12345","confirm_password":"1234"}' localhost:8000/api/register
    //curl -X POST -H "Content-Type: application/json" -d '{"first_name":"Angel","last_name":"Charm","email":"angle@gmail.com","password":"12345","confirm_password":"1234"}' localhost:8000/api/register

    record RegisterRequest(@JsonProperty("first_name")String firstName,
                           @JsonProperty("last_name")String lastName,
                           String email,String password,
                           @JsonProperty("confirm_password")String confirmPassword){}

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest registerRequest) {


        User user= userService.register(

                registerRequest.firstName(),
                registerRequest.lastName(),
                registerRequest.email(),
                registerRequest.password(),
                registerRequest.confirmPassword()


        );
        return new RegisterResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()

        );
    }
    record LoginRequest(String email,String password){}
    record LoginResponse(String token){}

    //curl -X POST -H "Content-Type: application/json" -d '{"email":"angel@gmail.com","password":"12345"}' localhost:8000/api/login
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletResponse response){
        var login=userService.login(loginRequest.email(),loginRequest.password());
        Cookie cookie=new Cookie("refresh_token",login.getRefreshToken().getToken());
        cookie.setMaxAge(3600);
        cookie.setHttpOnly(true);
        cookie.setPath("/api");
        response.addCookie(cookie);
        return new LoginResponse(
            login.getAccessToken().getToken()

        );

    }
    @GetMapping("/user")
    public  UserResponse user(HttpServletRequest request){
        var user=(User)request.getAttribute("user");
        return  new UserResponse(user.getId(),user.getFirstName(),user.getLastName(),user.getEmail());


    }


    //curl -X POST -H "Content-Type: application/json" -d '{"email":"angle@gmail.com","password":"1234"}' localhost:8000/api/login
    record RegisterResponse(Long id,@JsonProperty("first_name")String firstName,
                        @JsonProperty("last_name")String lastName,
                        String email){}

    record UserResponse(Long id,@JsonProperty("first_name")String firstName,
                            @JsonProperty("last_name")String lastName,
                            String email){}
    record RefreshResponse(String token){}

    @PostMapping("refresh")
    public RefreshResponse refresh(@CookieValue("refresh_token")String refreshToken){
        return new RefreshResponse(userService.refreshAccess(refreshToken)
                .getAccessToken()
                .getToken());

    }
    record  LogoutResponse(String msg){}
    @PostMapping("/logout")
    public  LogoutResponse logout(@CookieValue("refresh_token")String refreshToken,
                                  HttpServletResponse response){
        userService.logout(refreshToken);
        Cookie cookie=new Cookie("refresh_token",null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return   new LogoutResponse("successfull logout");


    }
}



