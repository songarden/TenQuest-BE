package com.kns.tenquest.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;


@Data
@Table(name="question_table")
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment //이거도 UUID 도 자동생성으로 만들어야하는지 모르겠음
    @Column(name="question_id")
    private UUID questionId;

    @Column(name="question_content")
    private String questionContent;


    //@ManyToOne // Many = Question , One = Category  : 한개의 카테고리는. .여러개의 질문객체를 가질수있다 . : hibernate 에서는 못씀
    @JoinColumn(name="category_id")  //이부분 맞는지 잘모르겠음 확인해봐야됨 다시
    @Column(name="question_category_id")
    private int questionCategoryId;


}
