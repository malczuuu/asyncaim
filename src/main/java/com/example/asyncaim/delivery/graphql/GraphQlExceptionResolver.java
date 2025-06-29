package com.example.asyncaim.delivery.graphql;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import io.github.malczuuu.problem4j.core.Problem;
import io.github.malczuuu.problem4j.core.ProblemException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class GraphQlExceptionResolver extends DataFetcherExceptionResolverAdapter {

  @Override
  protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
    ErrorType errorType = ErrorType.INTERNAL_ERROR;
    Map<String, Object> extensions = new HashMap<>();

    Problem problem =
        Problem.builder()
            .title(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .build();

    if (ex instanceof ProblemException p) {
      problem = p.getProblem();
      HttpStatus status = HttpStatus.valueOf(problem.getStatus());
      if (status == HttpStatus.UNAUTHORIZED) {
        errorType = ErrorType.UNAUTHORIZED;
      } else if (status == HttpStatus.FORBIDDEN) {
        errorType = ErrorType.FORBIDDEN;
      } else if (status == HttpStatus.NOT_FOUND) {
        errorType = ErrorType.NOT_FOUND;
      } else if (status.is4xxClientError()) {
        errorType = ErrorType.BAD_REQUEST;
      }

      for (String name : problem.getExtensions()) {
        extensions.put(name, problem.getExtensionValue(name));
      }
    }

    return GraphqlErrorBuilder.newError(env)
        .message(problem.getDetail())
        .errorType(errorType)
        .extensions(extensions)
        .build();
  }
}
