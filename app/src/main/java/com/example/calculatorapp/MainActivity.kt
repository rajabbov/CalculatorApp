package com.example.calculatorapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private lateinit var resultTextView: TextView
    private var currentExpression = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultTextView = findViewById(R.id.resultTextView)

        val numberMap = mapOf(
            R.id.button0 to "0",
            R.id.button1 to "1",
            R.id.button2 to "2",
            R.id.button3 to "3",
            R.id.button4 to "4",
            R.id.button5 to "5",
            R.id.button6 to "6",
            R.id.button7 to "7",
            R.id.button8 to "8",
            R.id.button9 to "9"
        )
        numberMap.forEach { (id, digit) ->
            findViewById<MaterialButton>(id).setOnClickListener {
                appendDigit(digit)
            }
        }

        findViewById<MaterialButton>(R.id.buttonPlus).setOnClickListener { appendOperator("+") }
        findViewById<MaterialButton>(R.id.buttonMinus).setOnClickListener { appendOperator("-") }
        findViewById<MaterialButton>(R.id.buttonMultiply).setOnClickListener { appendOperator("*") }
        findViewById<MaterialButton>(R.id.buttonDivide).setOnClickListener { appendOperator("/") }

        findViewById<MaterialButton>(R.id.buttonDot).setOnClickListener { appendDot() }
        findViewById<MaterialButton>(R.id.buttonPercent).setOnClickListener { appendPercent() }

        findViewById<MaterialButton>(R.id.buttonParenOpen).setOnClickListener { appendOpenParenthesis() }
        findViewById<MaterialButton>(R.id.buttonParenClose).setOnClickListener { appendCloseParenthesis() }

        findViewById<MaterialButton>(R.id.buttonSin).setOnClickListener { appendFunction("sin(") }
        findViewById<MaterialButton>(R.id.buttonCos).setOnClickListener { appendFunction("cos(") }
        findViewById<MaterialButton>(R.id.buttonTan).setOnClickListener { appendFunction("tan(") }
        findViewById<MaterialButton>(R.id.buttonSqrt).setOnClickListener { appendFunction("sqrt(") }
        findViewById<MaterialButton>(R.id.buttonCube).setOnClickListener { appendPower("^3") }
        findViewById<MaterialButton>(R.id.buttonPower).setOnClickListener { appendPower("^") }
        findViewById<MaterialButton>(R.id.buttonFactorial).setOnClickListener { appendFactorial() }

        findViewById<MaterialButton>(R.id.buttonClear).setOnClickListener { clearExpression() }
        findViewById<MaterialButton>(R.id.buttonDelete).setOnClickListener { deleteLastCharacter() }
        findViewById<MaterialButton>(R.id.buttonEqual).setOnClickListener { calculateResult() }
    }

    private fun appendDigit(digit: String) {
        if (currentExpression == "0") {
            currentExpression = digit
        } else {
            currentExpression += digit
        }
        resultTextView.text = currentExpression
    }

    private fun appendDot() {
        if (currentExpression.isEmpty() || currentExpression.last() in listOf('+', '-', '*', '/', '^', '(', '%')) {
            currentExpression += "0."
        } else {
            val lastNumber = currentExpression.takeLastWhile { it.isDigit() || it == '.' }
            if (!lastNumber.contains('.')) {
                currentExpression += "."
            }
        }
        resultTextView.text = currentExpression
    }

    private fun appendOperator(op: String) {
        if (currentExpression.isEmpty()) {
            if (op == "-") {
                currentExpression = "-"
                resultTextView.text = currentExpression
            }
            return
        }

        val lastChar = currentExpression.last()
        if (lastChar in listOf('+', '-', '*', '/', '^')) {
            if (op == "-" && lastChar in listOf('*', '/', '^')) {
                currentExpression += "-"
            } else if (currentExpression.length > 1 && currentExpression.dropLast(1).last() in listOf('*', '/', '^') && lastChar == '-') {
                currentExpression = currentExpression.dropLast(2) + op
            } else {
                currentExpression = currentExpression.dropLast(1) + op
            }
        } else if (lastChar == '.') {
            currentExpression = currentExpression.dropLast(1) + op
        } else {
            currentExpression += op
        }
        resultTextView.text = currentExpression
    }

    private fun appendPercent() {
        if (currentExpression.isNotEmpty() && (currentExpression.last().isDigit() || currentExpression.last() == ')')) {
            currentExpression += "%"
            resultTextView.text = currentExpression
        }
    }

    private fun appendOpenParenthesis() {
        if (currentExpression == "0") {
            currentExpression = "("
        } else if (currentExpression.isNotEmpty() && (currentExpression.last().isDigit() || currentExpression.last() in listOf(')', '!'))) {
            currentExpression += "*("
        } else {
            currentExpression += "("
        }
        resultTextView.text = currentExpression
    }

    private fun appendCloseParenthesis() {
        val openCount = currentExpression.count { it == '(' }
        val closeCount = currentExpression.count { it == ')' }
        if (openCount > closeCount && currentExpression.isNotEmpty() && (currentExpression.last().isDigit() || currentExpression.last() in listOf(')', '!'))) {
            currentExpression += ")"
            resultTextView.text = currentExpression
        }
    }

    private fun appendFunction(func: String) {
        if (currentExpression == "0") {
            currentExpression = func
        } else if (currentExpression.isNotEmpty() && (currentExpression.last().isDigit() || currentExpression.last() in listOf(')', '!'))) {
            currentExpression += "*$func"
        } else {
            currentExpression += func
        }
        resultTextView.text = currentExpression
    }

    private fun appendPower(powerStr: String) {
        if (currentExpression.isNotEmpty() && (currentExpression.last().isDigit() || currentExpression.last() in listOf(')', '!'))) {
            currentExpression += powerStr
            resultTextView.text = currentExpression
        }
    }

    private fun appendFactorial() {
        if (currentExpression.isNotEmpty() && (currentExpression.last().isDigit() || currentExpression.last() == ')')) {
            currentExpression += "!"
            resultTextView.text = currentExpression
        }
    }

    private fun clearExpression() {
        currentExpression = ""
        resultTextView.text = "0"
    }

    private fun deleteLastCharacter() {
        if (currentExpression.isNotEmpty()) {
            val functions = listOf("sin(", "cos(", "tan(", "sqrt(")
            val matchedFunc = functions.firstOrNull { currentExpression.endsWith(it) }
            currentExpression = if (matchedFunc != null) {
                currentExpression.dropLast(matchedFunc.length)
            } else {
                currentExpression.dropLast(1)
            }
            resultTextView.text = currentExpression.ifEmpty { "0" }
        }
    }

    private fun calculateResult() {
        if (currentExpression.isEmpty()) {
            resultTextView.text = "0"
            return
        }

        val errorText = getString(R.string.error)
        val res = CalculatorEvaluator.calculate(currentExpression)
        if (res == "Xato") {
            currentExpression = ""
            resultTextView.text = errorText
        } else {
            currentExpression = res
            resultTextView.text = currentExpression
        }
    }
}