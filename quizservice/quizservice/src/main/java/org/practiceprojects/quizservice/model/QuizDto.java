package org.practiceprojects.quizservice.model;

import lombok.Data;

@Data
public class QuizDto {
    private String categoryName;
    private int numQuestions;
    private String title;
}
