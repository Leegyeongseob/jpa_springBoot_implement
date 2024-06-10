package com.example.jpafinal.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
@Getter
@Setter
public class BoardDto {
    private Long boardId;
    private Long categoryId;
    private String email;
    private String title;
    private String content;
    private String address;
    private String img;
    private LocalDateTime regDate;
    private Double latitude;
    private Double longitude;
}
