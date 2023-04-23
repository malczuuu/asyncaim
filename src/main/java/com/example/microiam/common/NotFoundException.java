package com.example.microiam.common;

import io.github.malczuuu.problem4j.core.Problem;
import io.github.malczuuu.problem4j.core.ProblemException;
import org.springframework.http.HttpStatus;

public class NotFoundException extends ProblemException {

  public NotFoundException(String detail) {
    super(
        Problem.builder()
            .title(HttpStatus.NOT_FOUND.getReasonPhrase())
            .status(HttpStatus.NOT_FOUND.value())
            .detail(detail)
            .build());
  }
}
