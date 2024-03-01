//
//  main.swift
//  HW_calculator1
//
//  Created by Маслова Екатерина  on 14.02.2024.
//

import Foundation

enum Operation: String {
    case addition = "+"
    case subtraction = "-"
    case multiplication = "*"
    case division = "/"
    case exponentiation = "^" // Example of an additional operation

    static let allValues = [addition, subtraction, multiplication, division, exponentiation]
}
struct CalculationData {
    var firstNumber: Double
    var secondNumber: Double
    var operation: Operation
}
class Calculator {
    func performCalculation(data: CalculationData) -> Double? {
        switch data.operation {
        case .addition:
            return data.firstNumber + data.secondNumber
        case .subtraction:
            return data.firstNumber - data.secondNumber
        case .multiplication:
            return data.firstNumber * data.secondNumber
        case .division:
            return data.secondNumber != 0 ? data.firstNumber / data.secondNumber : nil
        case .exponentiation:
            return pow(data.firstNumber, data.secondNumber)
        }
    }

    func readCalculationData() -> CalculationData? {
        print("Input first number: ")
        guard let firstNumberString = readLine(), let firstNumber = Double(firstNumberString) else {
            print("Invalid input for the first number.")
            return nil
        }

        print("Input second number: ")
        guard let secondNumberString = readLine(), let secondNumber = Double(secondNumberString) else {
            print("Invalid input for the second number.")
            return nil
        }

        print("Enter operation (+, -, *, /, ^): ")
        guard let operationString = readLine(), let operation = Operation(rawValue: operationString) else {
            print("Invalid operation.")
            return nil
        }

        return CalculationData(firstNumber: firstNumber, secondNumber: secondNumber, operation: operation)
    }

    func run() {
        print("Welcome to the Swift Calculator!")

        guard let calculationData = readCalculationData() else {
            print("Error reading input. Exiting.")
            return
        }

        guard let result = performCalculation(data: calculationData) else {
            print("An error occurred during the calculation. Exiting.")
            return
        }

        print("Result: \(round(result * 1000) / 1000)") // Rounding the result to 3 decimal places
    }
}
let calculator = Calculator()
calculator.run()

