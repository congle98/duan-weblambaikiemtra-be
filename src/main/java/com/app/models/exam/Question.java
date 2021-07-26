package com.app.models.exam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 5000)
    private String content;

    private String image;

    private String option1;
    private String option2;
    private String option3;
    private String option4;

    //trường này chỉ nhằm mục đích kiểm tra đáp án chứ ko cho lưu vào db,
    // hơi cùng chút đáng lẽ phải xây riêng 1 table bài thi mới chuẩn :D
    @Transient
    private String givenAnswer;

    private String answer;

    @ManyToOne(fetch = FetchType.EAGER)
    private Quiz quiz;
}
