package com.mkalinov.performance;

import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class MockServiceTest {

  private final int TOTAL_CALLS = 1000;
  private final int BATCH_SIZE = 100;
  private final MockService mockService;

  public MockServiceTest() {
    mockService = new MockService();
  }


  @Test
  @SneakyThrows
  public void testPerformance() throws InterruptedException {
    ExecutorService executorService = Executors.newFixedThreadPool(BATCH_SIZE);
    final int[] successCount = {0};
    final int[] longExecutionCount = {0};

    runPerformanceTest(executorService, successCount, longExecutionCount);

    executorService.shutdown();
    executorService.awaitTermination(1, TimeUnit.MINUTES);

    assertResults(successCount[0], longExecutionCount[0]);
  }

  private void runPerformanceTest(ExecutorService executorService, final int[] successCount,
                                  final int[] longExecutionCount) throws InterruptedException {
    List<Future<Void>> futures = new ArrayList<>();

    for (int i = 0; i < TOTAL_CALLS / BATCH_SIZE; i++) {
      submitBatch(executorService, futures, successCount, longExecutionCount);
      waitForBatchCompletion(futures);
    }
  }

  private void submitBatch(ExecutorService executorService, List<Future<Void>> futures, final int[] successCount,
                           final int[] longExecutionCount) {
    for (int j = 0; j < BATCH_SIZE; j++) {
      futures.add(executorService.submit(() -> {
        executeService(successCount, longExecutionCount);
        return null;
      }));
    }
  }

  private void executeService(final int[] successCount, final int[] longExecutionCount) {
    long startTime = System.currentTimeMillis();
    try {
      mockService.execute();
      long duration = System.currentTimeMillis() - startTime;
      updateCounts(successCount, longExecutionCount, duration);
    } catch (Exception e) {
      // Exception handling
      System.err.println("Execution failed: " + e.getMessage());
    }
  }

  private void updateCounts(final int[] successCount, final int[] longExecutionCount, long duration) {
    if (duration > 1000) {
      synchronized (MockServiceTest.class) {
        longExecutionCount[0]++;
      }
    }
    synchronized (MockServiceTest.class) {
      successCount[0]++;
    }
  }

  private void waitForBatchCompletion(List<Future<Void>> futures) throws InterruptedException {
    for (Future<Void> future : futures) {
      try {
        future.get();
      } catch (Exception e) {
        // Handle any exceptions that occur during the execution
        System.err.println("Task execution failed: " + e.getMessage());
      }
    }
    futures.clear();
  }

  private void assertResults(int successCount, int longExecutionCount) {
    double successRate = (double) successCount / TOTAL_CALLS;
    Assertions.assertThat(successRate).isGreaterThanOrEqualTo(0.70).as("Success rate is less than 70%");

    double longExecutionRate = (double) longExecutionCount / TOTAL_CALLS;
    Assertions.assertThat(longExecutionRate).isLessThanOrEqualTo(0.05).as(
      "More than 5% of calls took longer than 1 second");
  }
}
