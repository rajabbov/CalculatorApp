# CalculatorApp

A scientific calculator that parses full expressions rather than evaluating key by key, with the parsing logic covered by unit tests.

## Features

- Expression evaluation via the exp4j library, wrapped in a `CalculatorEvaluator`
- Operator precedence and parentheses handled correctly
- Calculation logic isolated from the UI so it can be tested
- JUnit test suite (`CalculatorLogicTest`) covering the evaluator

## Tech stack

- **Language:** Kotlin
- **UI:** XML layouts with View binding
- **SDK:** min 24, target 35
- **Package:** `com.example.calculatorapp`
- **Libraries:** exp4j (expression parser)

<sub>5 source files · 340 lines of code · 1 layouts</sub>

## Getting started

```bash
git clone https://github.com/rajabbov/CalculatorApp.git
```

Open the folder in Android Studio, let Gradle sync, and run the `app` configuration on a device or emulator.

## License

MIT
