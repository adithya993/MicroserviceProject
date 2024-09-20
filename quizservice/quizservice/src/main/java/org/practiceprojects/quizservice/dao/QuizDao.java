package org.practiceprojects.quizservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.practiceprojects.quizservice.model.Quiz;
public interface QuizDao extends JpaRepository<Quiz, Integer> {
}
