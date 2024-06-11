package com.mkalinov.counter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DuplicatesCounterApp {

  private final InputReader inputReader;
  private final ResultPrinter resultPrinter;
  private final Counter counter;

  public DuplicatesCounterApp() {
    this.inputReader = new InputReader();
    this.resultPrinter = new ResultPrinter();
    this.counter = new NonUniqueCountingStrategy();
  }

  public static void main(String[] args) {
    DuplicatesCounterApp app = new DuplicatesCounterApp();
    app.run();
  }

  public void run() {
    int[] numbers = inputReader.readInput();
    int result = counter.count(numbers);
    resultPrinter.printResult(result);
  }
}