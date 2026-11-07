package com.example.calculatorapp

import net.objecthunter.exp4j.ExpressionBuilder
import net.objecthunter.exp4j.function.Function
import net.objecthunter.exp4j.operator.Operator
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

object CalculatorEvaluator {

    private val sinFunc = object : Function("sin", 1) {
        override fun apply(vararg args: Double): Double = sin(Math.toRadians(args[0]))
    }
    private val cosFunc = object : Function("cos", 1) {
        override fun apply(vararg args: Double): Double = cos(Math.toRadians(args[0]))
    }
    private val tanFunc = object : Function("tan", 1) {
        override fun apply(vararg args: Double): Double {
            val deg = args[0] % 360
            if ((abs(deg) == 90.0) || (abs(deg) == 270.0)) {
                throw ArithmeticException("Tan undefined for 90/270 degrees")
            }
            return tan(Math.toRadians(args[0]))
        }
    }
    private val sqrtFunc = object : Function("sqrt", 1) {
        override fun apply(vararg args: Double): Double {
            if (args[0] < 0) throw ArithmeticException("Negative sqrt")
            return sqrt(args[0])
        }
    }

    private val factorialOp = object : Operator("!", 1, true, PRECEDENCE_POWER + 1) {
        override fun apply(vararg args: Double): Double {
            val arg = args[0]
            if ((arg < 0) || (arg != floor(arg))) {
                throw IllegalArgumentException("Invalid factorial arg")
            }
            val n = arg.toLong()
            if (n > 20) throw ArithmeticException("Factorial overflow")
            var res = 1.0
            for (i in 2..n) {
                res *= i
            }
            return res
        }
    }

    fun calculate(currentExpression: String): String {
        if (currentExpression.isEmpty()) return "0"

        return try {
            var expr = currentExpression

            // Auto-close open parentheses
            val openCount = expr.count { it == '(' }
            val closeCount = expr.count { it == ')' }
            if (openCount > closeCount) {
                expr += ")".repeat(openCount - closeCount)
            }

            // Replace percent with division by 100
            expr = expr.replace("%", "/100")

            val exp = ExpressionBuilder(expr)
                .functions(sinFunc, cosFunc, tanFunc, sqrtFunc)
                .operator(factorialOp)
                .build()

            val value = exp.evaluate()
            formatResult(value)
        } catch (_: Exception) {
            "Xato"
        }
    }

    fun formatResult(result: Double): String {
        if (result.isNaN() || result.isInfinite()) {
            return "Xato"
        }
        return try {
            val bd = BigDecimal.valueOf(result).setScale(8, RoundingMode.HALF_UP).stripTrailingZeros()
            bd.toPlainString()
        } catch (_: Exception) {
            "Xato"
        }
    }
}