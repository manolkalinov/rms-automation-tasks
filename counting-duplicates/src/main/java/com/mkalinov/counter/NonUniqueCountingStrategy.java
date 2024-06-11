package com.mkalinov.counter;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;


/**
 * NonUniqueCountingStrategy is a class that implements the Counter interface to count the number of non-unique
 * values in an array of integers.
 * It uses a HashMap to store the frequency of each number in the array.
 */
@Slf4j
public class NonUniqueCountingStrategy implements Counter {

  private final HashMap<Integer, Integer> frequencyMap = new HashMap<>();

  @Override
  public int count(int[] numbers) {

    validateInput(numbers);
    populateFrequencyMap(numbers);
    int nonUniqueCount = countNonUniqueValues();

    return nonUniqueCount;
  }

  private void validateInput(int[] numbers) {
    log.info("Validate input numbers");
    validateArraySize(numbers);
    validateArrayElements(numbers);
  }

  private void validateArraySize(int[] numbers) {
    Preconditions.checkArgument(numbers.length >= 1 && numbers.length <= 1000,
                                "The number of elements in the array must be between 1 and 1000.");
    log.debug("Array size is valid: {}", numbers.length);
  }

  private void validateArrayElements(int[] numbers) {
    for (int number : numbers) {
      Preconditions.checkArgument(number >= 1 && number <= 1000,
                                  "Each number in the array must be between 1 and 1000.");
    }
    log.debug("Array elements are valid");
  }

  private void populateFrequencyMap(int[] numbers) {
    log.info("Populating frequency map");
    for (int number : numbers) {
      frequencyMap.put(number, frequencyMap.getOrDefault(number, 0) + 1);
    }
  }

  private int countNonUniqueValues() {
    log.info("Counting non-unique values");
    int nonUniqueCount = 0;
    for (int count : frequencyMap.values()) {
      if (count > 1) {
        nonUniqueCount++;
      }
    }
    return nonUniqueCount;
  }
}
