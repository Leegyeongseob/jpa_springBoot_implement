package com.example.jpafinal.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long movieId;
    //영화 링크
    private String movieLink;
    //섬네일 url
    private String image;
    private String title;
    // 평점
    private String score;
    // 남녀 비율?
    private String rate;
    // 예약 여부
    private String reservation;
    // 날짜
    private String date;
}
