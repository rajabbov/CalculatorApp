<p align="center">
  <img src="docs/banner.svg" alt="CalculatorApp — Parses whole expressions with real precedence — and it's unit-tested." width="100%">
</p>

![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?logo=kotlin&logoColor=white) ![Android](https://img.shields.io/badge/Android-3DDC84?logo=android&logoColor=white) ![minSdk 24](https://img.shields.io/badge/minSdk_24-555)

A scientific calculator that **parses whole expressions** instead of evaluating key by key, so `2+3×4` gives 14, not 20. The parsing lives in a separate, UI-free `CalculatorEvaluator` class — and that class is covered by **unit tests**.

## Features

- Full expression input with correct **operator precedence** and **parentheses**
- **sin, cos, tan** in degrees, **√**, powers and **factorial `!`**
- **Percent** support (`50%` → `0.5`)
- **Auto-closing parentheses** — forget a `)` and it is added for you
- **Negative numbers** and decimals
- Clear error results for invalid input (negative square root, non-integer factorial, …)
- **JUnit test suite** with 9 test cases

## How it works

### Custom functions on top of exp4j
[exp4j](https://www.objecthunter.net/exp4j/) handles parsing; the calculator extends it with its own functions and a postfix operator:

```kotlin
private val sinFunc = object : Function("sin", 1) {
    override fun apply(vararg args: Double) = sin(Math.toRadians(args[0]))
}
private val factorialOp = object : Operator("!", 1, true, PRECEDENCE_POWER + 1) { /* … */ }
```

Before evaluation, the input is normalised — `%` becomes `/100`, missing closing brackets are appended — and the result is formatted to drop trailing `.0`.

### Tests
`CalculatorLogicTest` covers arithmetic, decimals, trigonometry, roots and powers, factorial, percent, auto-closing brackets, negative numbers and error cases. Run them with:

```bash
./gradlew test
```

## Project structure

```
com.example.calculatorapp
├── CalculatorEvaluator.kt
└── MainActivity.kt
```

<sub>2 source files · 252 lines of code · 1 layout</sub>

## Tech stack

| | |
|---|---|
| **Language** | Kotlin |
| **UI** | XML layouts |
| **Min / Target SDK** | 24 / 35 |
| **exp4j** | Expression parsing |

## Getting started

```bash
git clone https://github.com/rajabbov/CalculatorApp.git
```

1. Open the folder in **Android Studio** (Ladybug or newer).
2. Let Gradle sync.
3. Run the `app` configuration on a device or emulator.

---

Made by **Ibrohimjon Rajabboyev** · [@rajabbov](https://github.com/rajabbov)
