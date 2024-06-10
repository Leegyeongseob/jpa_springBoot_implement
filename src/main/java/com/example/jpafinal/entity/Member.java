package com.example.jpafinal.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString(exclude = "pwd")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;
    @Column(length = 100)
    private String name;
    @Column(nullable = false)
    private String pwd;
    @Column(unique = true , length = 150)
    private String email;
    @Column(length= 255)
    private String image;

    private LocalDateTime regDate;

    @PrePersist
    public void prePersist(){
        this.regDate = LocalDateTime.now();
    }
}

