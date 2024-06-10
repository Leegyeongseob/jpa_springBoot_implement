package com.example.jpafinal.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long categoryId;
    private String categoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy= "category" , fetch = FetchType.LAZY)
    private List<Board> boards;
}
