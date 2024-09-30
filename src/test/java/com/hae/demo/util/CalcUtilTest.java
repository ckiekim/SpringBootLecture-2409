package com.hae.demo.util;

import org.junit.jupiter.api.Test;      // JUnit 5

import static org.assertj.core.api.Assertions.assertThat;

public class CalcUtilTest {
    private final CalcUtil calcUtil = new CalcUtil();

    @Test
    void testAdd() {
        // Given
        int x = 3, y = 4;

        // When
        int result = calcUtil.add(x, y);

        // Then
        assertThat(result).isEqualTo(7);
    }

    @Test
    void testMul() {
        // Given
        int x = 3, y = 4;

        // When
        int result = calcUtil.mul(x, y);

        // Then
        assertThat(result).isEqualTo(12);
    }
}
