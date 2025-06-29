package com.example.asyncaim.usecase.error;

import io.github.malczuuu.problem4j.core.Problem;
import io.github.malczuuu.problem4j.core.ProblemException;
import org.springframework.http.HttpStatus;

public class BadRequestException extends ProblemException {

  public BadRequestException(String detail) {
    super(
        Problem.builder()
            .title(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .status(HttpStatus.BAD_REQUEST.value())
            .detail(detail)
            .build());
  }
}
