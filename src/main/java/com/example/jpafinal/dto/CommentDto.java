package com.example.jpafinal.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class CommentDto {
    private Long commentId;
    private Long memberId;
    private String content;
    private String email;
    private Date RegDate;

}
