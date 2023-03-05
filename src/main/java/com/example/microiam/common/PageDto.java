package com.example.microiam.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record PageDto<T>(
    @JsonProperty("content") List<T> content,
    @JsonProperty("page") Integer page,
    @JsonProperty("size") Integer size,
    @JsonProperty("total_elements") Long totalElements) {}
