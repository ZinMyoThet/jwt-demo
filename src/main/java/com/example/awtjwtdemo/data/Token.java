package com.example.awtjwtdemo.data;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Token(String refreshToken, LocalDateTime issuedAt,LocalDateTime expiredAt) {


}
