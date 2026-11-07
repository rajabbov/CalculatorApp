package com.example.calculatorapp

import org.junit.Assert.assertEquals
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals("4", CalculatorEvaluator.calculate("2+2"))
    }
}