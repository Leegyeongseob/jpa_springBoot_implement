package com.example.jpafinal.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

public class MemberDto {
    private String pwd;
    private String name;
    private String email;
    private String image;
    private Date regDate;
}
