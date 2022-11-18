package com.example.awtjwtdemo.service;

import lombok.Getter;

@Getter
public class Login {


        private final Jwt accessToken;
        private final Jwt refreshToken;
        private  static final  Long ACCESS_TOKEN_VALIDITY=1L;
        private  static  final Long REFRESH_TOKEN_VALIDITY=1440L;

        private Login(Jwt accessToken, Jwt refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
        public static Login of(Long userId,String accessSecret,String refreshSecret){
            return new Login(
                    Jwt.of(userId,ACCESS_TOKEN_VALIDITY,accessSecret),
                    Jwt.of(userId,REFRESH_TOKEN_VALIDITY,refreshSecret)
            );
        }
    public static Login of(Long userId, String accessSecret, Jwt refreshToken) {
        return new Login(
                Jwt.of(userId, 1L, accessSecret),
                refreshToken
        );
    }

    }

