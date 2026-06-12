# Advanced Programming — Assignment 1: Mini Trading Platform

A mini online trading platform built in Java, applying core OOP concepts and design patterns covered in the Advanced Programming course.

## Concepts Applied

**OOP & Class Design**
- Abstract classes and inheritance: `Instrument` → `Stock`, `Bond`, `Derivative` → `Option`, `Future`
- Interfaces: `Tradeable`, `Priceable`, `PricingStrategy`
- Generics: `RiskAnalyzer<T extends Instrument>`

**Design Patterns**
- **Strategy** — swappable pricing algorithms (`SimplePricingStrategy`, `RiskAdjustedPricingStrategy`)
- **Observer** — `Portfolio` notifies registered observers on position changes
- **Visitor** — `TaxReportVisitor` computes tax liability per instrument type without modifying class hierarchy

## Features
- Instrument hierarchy with polymorphic `riskScore()` and `assetClass()`
- Portfolio management: add/remove positions, market value, unrealized P&L, allocation breakdown
- Runtime-swappable pricing via `revalueAll(PricingStrategy)`
- Generic risk analysis: average, highest/lowest risk, threshold filtering

## Tech
Java | OOP | Design Patterns
