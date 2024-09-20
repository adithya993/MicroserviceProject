package org.practiceprojects.quizservice.service;

import org.practiceprojects.quizservice.dao.QuizDao;
import org.practiceprojects.quizservice.feign.QuizInterface;
import org.practiceprojects.quizservice.model.Quiz;
import org.practiceprojects.quizservice.model.QuestionWrapper;
import org.practiceprojects.quizservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        try{
            List<Integer> questions = quizInterface.getQuestionsForQuiz(category, numQ).getBody();

            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setQuestions(questions);
            quizDao.save(quiz);

            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        List<QuestionWrapper> questionsForUser = new ArrayList<>();
        try{
            Optional<Quiz> quiz = quizDao.findById(id);
            List<Integer> questionsFromDB = quiz.get().getQuestions();
            return quizInterface.getQuestionsFromId(questionsFromDB);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(questionsForUser, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        try{
            return quizInterface.getScore(responses);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);
    }
}
