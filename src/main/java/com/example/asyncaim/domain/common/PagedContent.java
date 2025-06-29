package com.example.asyncaim.domain.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.function.Function;

public record PagedContent<T>(
    @JsonProperty("content") List<T> content,
    @JsonProperty("page") Integer page,
    @JsonProperty("size") Integer size,
    @JsonProperty("totalElements") Long totalElements) {

  public <R> PagedContent<R> map(Function<T, R> mapper) {
    return new PagedContent<>(
        content().stream().map(mapper).toList(), page(), size(), totalElements());
  }
}
