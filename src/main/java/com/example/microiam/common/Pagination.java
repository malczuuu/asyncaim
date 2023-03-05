package com.example.microiam.common;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class Pagination {

  public static Pagination parse(String page, String size) {
    return new Pagination(parseValue(page, 0), parseValue(size, 20));
  }

  private static int parseValue(String value, int defaultValue) {
    try {
      int parsedValue = Integer.parseInt(value);
      if (parsedValue < 0) {
        parsedValue = defaultValue;
      }
      return parsedValue;
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  private final int page;
  private final int size;

  public Pagination(int page, int size) {
    this.page = page;
    this.size = size;
  }

  public int getPage() {
    return page;
  }

  public int getSize() {
    return size;
  }

  public Pageable asPageable(Sort.Order... sortOrders) {
    return PageRequest.of(page, size, Sort.by(sortOrders));
  }
}
