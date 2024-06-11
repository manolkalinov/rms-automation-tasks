package com.mkalinov.counter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResultPrinter {

  public void printResult(int result) {
    final String messageTemplate = "Result printed: '%s'";
    final String message = String.format(messageTemplate, result);
    System.out.println(message);
    log.info(message);
  }
}