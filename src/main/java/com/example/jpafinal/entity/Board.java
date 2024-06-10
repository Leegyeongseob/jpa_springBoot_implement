package com.example.jpafinal.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long boardId;
    private String title;
    @Lob
    @Column(length=10000)
    private String content;
    private String imgPath;
    private LocalDateTime regDate;

    @PrePersist
    private void prePersist(){
        regDate = LocalDateTime.now();
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") //외래키
    private Member member; //작성자

    // 카테고리 추가
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;

    //Board와 Comment는 1:N관계
    @OneToMany(mappedBy = "board",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    //Borad와 Location의 관계는 1:1 관계
    @OneToOne(mappedBy = "board", cascade = CascadeType.ALL,orphanRemoval = true)
    private Location location;


}
