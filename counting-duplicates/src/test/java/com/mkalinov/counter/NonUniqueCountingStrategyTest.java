package com.mkalinov.counter;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

@Slf4j
public class NonUniqueCountingStrategyTest {

  private Counter counter;

  @Before
  public void setUp() {
    counter = new NonUniqueCountingStrategy();
  }

  @Test
  public void verifyNoDuplicatesInInputArray() {
    int[] numbers = {1, 2, 3, 4, 5};
    int result = counter.count(numbers);
    assertThat(result).isEqualTo(0);
  }

  @Test
  public void verifySomeDuplicatesInInputArray() {
    int[] numbers = {1, 1, 2, 2, 3};
    int result = counter.count(numbers);
    assertThat(result).isEqualTo(2);
  }

  @Test
  public void verifyAllElementsAreDuplicatesInInputArray() {
    int[] numbers = {1, 1, 1, 1};
    int result = counter.count(numbers);
    assertThat(result).isEqualTo(1);
  }

  @Test
  public void verifyMinimumBoundaryValueIsCalculatedCorrectly() {
    int[] numbers = {1};
    int result = counter.count(numbers);
    assertThat(result).isEqualTo(0);
  }

  @Test
  public void verifyArrayIndexOutOfBoundsExceptionIsThrownForOutOfBoundsAccess() {
    final int[] numbers = new int[1000];

    // Using assertThatThrownBy from AssertJ to verify that the exception is thrown
    assertThatThrownBy(() -> {
      for (int i = 1; i <= 1000; i++) {
        numbers[i] = i;
      }
    }).isInstanceOf(ArrayIndexOutOfBoundsException.class)
      .hasMessageContaining("Index 1000 out of bounds for length 1000");
  }

  @Test
  public void verifyEquivalenceClassWithAllValidValues() {
    int[] numbers = {100, 200, 300, 100, 400, 200};
    int result = counter.count(numbers);
    assertThat(result).isEqualTo(2);
  }

  @Test
  public void verifyInvalidValuesThrowException() {
    int[] numbers = {0, 1001, -1, 1};
    assertThatThrownBy(() -> counter.count(numbers))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("between 1 and 1000");
  }

  @Test
  public void verifyEmptyArrayThrowsException() {
    int[] numbers = {};
    assertThatThrownBy(() -> counter.count(numbers))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("between 1 and 1000");
  }

  @Test
  public void verifySingleElementArrayIsCalculatedCorrectly() {
    int[] numbers = {42};
    int result = counter.count(numbers);
    assertThat(result).isEqualTo(0);
  }

  @Test
  public void verifyArrayWithAllSameElementsIsCalculatedCorrectly() {
    int[] numbers = {5, 5, 5, 5, 5};
    int result = counter.count(numbers);
    assertThat(result).isEqualTo(1);
  }
}
