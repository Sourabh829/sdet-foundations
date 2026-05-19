# Bank Account Hierarchy

Demonstrates core OOP concepts in Java:
- **Abstraction** — `BankAccount` abstract class
- **Encapsulation** — private fields with getters
- **Inheritance** — `SavingsAccount`, `CurrentAccount`, `FixedDepositAccount` extend `BankAccount`
- **Polymorphism** — `calculateInterest()` overridden per account type
- **Composition** — `Address` embedded in `BankAccount`
- **Comparator** — sort accounts by balance or holder name

## Structure

```
src/
├── model/          — Domain classes
├── comparator/     — Sorting strategies
├── service/        — Business logic
└── Main.java       — Entry point
```
