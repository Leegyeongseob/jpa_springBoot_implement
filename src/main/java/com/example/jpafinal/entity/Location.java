package com.example.jpafinal.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long locationId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_id")
    private Board board;
    // 주소
    private String address;
    // 위도
    private Double latitude;
    // 경도
    private Double longitude;

}
