package com.example.calculatorapp

import org.junit.Assert.assertEquals
import org.junit.Test

class CalculatorLogicTest {

    @Test
    fun testBasicArithmetic() {
        assertEquals("5", CalculatorEvaluator.calculate("2+3"))
        assertEquals("10", CalculatorEvaluator.calculate("20-10"))
        assertEquals("42", CalculatorEvaluator.calculate("6*7"))
        assertEquals("5", CalculatorEvaluator.calculate("20/4"))
        assertEquals("14", CalculatorEvaluator.calculate("2+3*4"))
        assertEquals("20", CalculatorEvaluator.calculate("(2+3)*4"))
    }

    @Test
    fun testDecimals() {
        assertEquals("0.3", CalculatorEvaluator.calculate("0.1+0.2"))
        assertEquals("2.5", CalculatorEvaluator.calculate("5/2"))
    }

    @Test
    fun testTrigonometricFunctions() {
        assertEquals("0.5", CalculatorEvaluator.calculate("sin(30)"))
        assertEquals("0.5", CalculatorEvaluator.calculate("cos(60)"))
        assertEquals("1", CalculatorEvaluator.calculate("tan(45)"))
    }

    @Test
    fun testSquareRootCubeAndPower() {
        assertEquals("3", CalculatorEvaluator.calculate("sqrt(9)"))
        assertEquals("8", CalculatorEvaluator.calculate("2^3"))
        assertEquals("32", CalculatorEvaluator.calculate("2^5"))
    }

    @Test
    fun testFactorial() {
        assertEquals("1", CalculatorEvaluator.calculate("0!"))
        assertEquals("1", CalculatorEvaluator.calculate("1!"))
        assertEquals("120", CalculatorEvaluator.calculate("5!"))
        assertEquals("720", CalculatorEvaluator.calculate("6!"))
    }

    @Test
    fun testPercent() {
        assertEquals("0.5", CalculatorEvaluator.calculate("50%"))
        assertEquals("20", CalculatorEvaluator.calculate("200*10%"))
    }

    @Test
    fun testAutoCloseParentheses() {
        assertEquals("0.5", CalculatorEvaluator.calculate("sin(30"))
        assertEquals("3", CalculatorEvaluator.calculate("sqrt(9"))
    }

    @Test
    fun testNegativeNumbers() {
        assertEquals("-2", CalculatorEvaluator.calculate("-5+3"))
        assertEquals("-15", CalculatorEvaluator.calculate("5*-3"))
    }

    @Test
    fun testErrors() {
        assertEquals("Xato", CalculatorEvaluator.calculate("5/0"))
        assertEquals("Xato", CalculatorEvaluator.calculate("sqrt(-4)"))
        assertEquals("Xato", CalculatorEvaluator.calculate("tan(90)"))
    }
}