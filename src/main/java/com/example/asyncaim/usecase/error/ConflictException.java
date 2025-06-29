package com.example.asyncaim.usecase.error;

import io.github.malczuuu.problem4j.core.Problem;
import io.github.malczuuu.problem4j.core.ProblemException;
import org.springframework.http.HttpStatus;

public class ConflictException extends ProblemException {

  public ConflictException(String detail) {
    super(
        Problem.builder()
            .title(HttpStatus.CONFLICT.getReasonPhrase())
            .status(HttpStatus.CONFLICT.value())
            .detail(detail)
            .build());
  }
}
