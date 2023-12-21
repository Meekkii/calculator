package com.Meekkii.calculator;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CalculatorTest {

    @Test
    public void testSum() {
        Calculator calculator = new Calculator();
        assertEquals(8, calculator.sum(3, 5));
    }
}

