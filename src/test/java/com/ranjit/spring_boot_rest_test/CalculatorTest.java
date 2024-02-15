package com.ranjit.spring_boot_rest_test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    Calculator calculator;

    @Test
    public void sumTest(){
        calculator = new Calculator();
//        assertEquals(20,calculator.sum(20,30));
        assertEquals(50,calculator.sum(20,30));

    }

    @Test
    public void testDevide(){
        calculator=new Calculator();
        assertEquals(5,calculator.divide(10,2));
    }
}
