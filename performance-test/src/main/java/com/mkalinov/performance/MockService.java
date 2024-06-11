package com.mkalinov.performance;

import java.util.Random;

public class MockService {

  private final Random random = new Random();

  public void execute() throws Exception {
    int executionTime = random.nextInt(1200) + 1;  // 1 to 1200 milliseconds
    Thread.sleep(executionTime);
    if (random.nextDouble() < 0.3) {  // 30% failure rate
      throw new Exception("Execution failed");
    }
  }
}