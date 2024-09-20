package org.practiceprojects.quizservice.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor // this gives parameterized and default constructor
public class Response {
    private Integer id;
    private String response;
}
