package com.mkalinov.counter;

import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class InputReader {

  private final Scanner scanner;

  public InputReader() {
    scanner = new Scanner(System.in);
  }

  public int[] readInput() {
    int elementCount = readNumberOfElements();
    int[] numbers = readElements(elementCount);
    log.info("Input read successfully: {}", numbers);

    return numbers;
  }

  private int readNumberOfElements() {
    log.info("Enter the number of elements:");
    int integerValue = scanner.nextInt();
    validateNumberOfElements(integerValue);
    return integerValue;
  }

  private void validateNumberOfElements(int elementCount) {
    if (elementCount < 1 || elementCount > 1000) {
      log.error("Invalid number of elements: {}", elementCount);
      throw new IllegalArgumentException("The number of elements in the array must be between 1 and 1000.");
    }
  }

  private int[] readElements(int n) {
    int[] numbers = new int[n];
    log.info("Enter the elements:");
    for (int i = 0; i < n; i++) {
      numbers[i] = scanner.nextInt();
      validateElement(numbers[i]);
    }
    return numbers;
  }

  private void validateElement(int elementValue) {
    if (elementValue < 1 || elementValue > 1000) {
      log.error("Invalid array element: {}", elementValue);
      throw new IllegalArgumentException("Each number in the array must be between 1 and 1000.");
    }
  }
}