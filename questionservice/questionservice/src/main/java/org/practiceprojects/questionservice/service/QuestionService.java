package org.practiceprojects.questionservice.service;

import org.practiceprojects.questionservice.dao.QuestionDao;
import org.practiceprojects.questionservice.model.Question;
import org.practiceprojects.questionservice.model.QuestionWrapper;
import org.practiceprojects.questionservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;
    public ResponseEntity<List<Question>> getAllQuestions() {
        try{
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try{
            return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try{
            questionDao.save(question);
            return new ResponseEntity<>("success", HttpStatus.CREATED);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteQuestionByID(String id) {
        try{
            questionDao.deleteById(Integer.parseInt(id));
            return new ResponseEntity<>("Deleted question successfully", HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, int numQ) {
        try{
            List<Integer> questionIds = questionDao.findRandomQuestionByCategory(categoryName, numQ);
            return new ResponseEntity<>(questionIds, HttpStatus.CREATED);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();
        try{
            for(int id : questionIds){
                Optional<Question> obj = questionDao.findById(id);
                wrappers.add(new QuestionWrapper(obj.get().getId(), obj.get().getQuestion(), obj.get().getOption1(), obj.get().getOption2(), obj.get().getOption3(), obj.get().getOption4()));
            }
            return new ResponseEntity<>(wrappers, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(wrappers, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int score = 0;
        try{
            for(Response response : responses){
                //find response for this question
                if(response.getResponse().equals(questionDao.findById(response.getId()).get().getCorrectanswer()))
                        score++;

            }
            return new ResponseEntity<>(score,HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(score, HttpStatus.BAD_REQUEST);
    }
}
