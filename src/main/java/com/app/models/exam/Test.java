package com.app.models.exam;

import com.app.models.User;

import javax.persistence.*;

@Entity
@Table
public class Test  {
    // test là 1 bài thì và  là quiz_user

    //thay vì tạo many to many ở quiz và user, em sẽ tạo ra thằng này, trong thằng này có 1 user và 1 quiz.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Quiz quiz;
    // một quiz quiz là đề được làm bởi nhiều user;

    @OneToOne
    private User user;
    // một user thì tham gia được nhiều quiz


    private double marksGot;

    private int correctAnswers;

    private int attempted;


    public Test() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getMarksGot() {
        return marksGot;
    }

    public void setMarksGot(double marksGot) {
        this.marksGot = marksGot;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getAttempted() {
        return attempted;
    }

    public void setAttempted(int attempted) {
        this.attempted = attempted;
    }
}
