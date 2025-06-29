package com.example.asyncaim.domain.common;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public record Pagination(int page, int size) {

  public static final int DEFAULT_PAGE = 0;
  public static final int DEFAULT_SIZE = 20;

  public static Pagination of(Integer page, Integer size) {
    int pageValue = page != null && page >= 0 ? page : DEFAULT_PAGE;
    int sizeValue = size != null && size >= 1 ? size : DEFAULT_SIZE;
    return new Pagination(pageValue, sizeValue);
  }

  public static Pagination parse(String page, String size) {
    return new Pagination(parsePage(page), parseSize(size));
  }

  private static int parsePage(String value) {
    try {
      int parsedValue = Integer.parseInt(value);
      return parsedValue < 0 ? DEFAULT_PAGE : parsedValue;
    } catch (NumberFormatException e) {
      return DEFAULT_PAGE;
    }
  }

  private static int parseSize(String value) {
    try {
      int parsedValue = Integer.parseInt(value);
      return parsedValue < 1 ? DEFAULT_SIZE : parsedValue;
    } catch (NumberFormatException e) {
      return DEFAULT_SIZE;
    }
  }

  public Pageable asPageable(Sort.Order... sortOrders) {
    return PageRequest.of(page, size, Sort.by(sortOrders));
  }
}
