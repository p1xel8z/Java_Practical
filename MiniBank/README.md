# Virtual MiniBank

A comprehensive Java banking application built incrementally through 14 practical labs (P1–P14).

## Architecture & Structure

The codebase is organized into modular packages:

```
c:\Projects\Java\MiniBank\
├── model\
│   ├── Account.java               (P1, P6-P12: Base account with @Id, @Positive, synchronized, Serializable, Comparable)
│   ├── SavingsAccount.java        (P1: Savings account with min-balance rule)
│   ├── CurrentAccount.java        (P1: Current account with overdraft limit)
│   ├── FixedDepositAccount.java   (P1: Fixed deposit account with zero-withdrawal rule)
│   ├── Customer.java              (P2, P11: Cloneable and Serializable customer with inner Address)
│   ├── BankInfo.java              (P4: Record holding bank name and branch)
│   ├── Command.java               (P4: Record for parsed transaction commands)
│   ├── TransactionType.java       (P4: Enum DEPOSIT, WITHDRAW, TRANSFER)
│   ├── Transactable.java          (P6, P8: Interface declaring deposit/withdraw with checked exceptions)
│   ├── InterestBearing.java       (P6: Interface with default yearlyInterest() method)
│   ├── Premium.java               (P6: Marker interface)
│   └── annotation\
│       ├── Id.java                (P7: Runtime marker annotation on primary key fields)
│       ├── Positive.java          (P7: Runtime validation annotation with message)
│       ├── MaxLength.java         (P7: Runtime length constraint annotation)
│       └── Audit.java             (P14: Method annotation for reflective logging)
│
├── exception\
│   ├── BankException.java         (P8: Checked base exception class)
│   ├── InsufficientFundsException.java (P8: Holds shortfall amount)
│   ├── AccountNotFoundException.java   (P8: Thrown when account ID is not found)
│   └── InvalidAmountException.java     (P8: Thrown for amounts <= 0)
│
├── service\
│   ├── WithdrawRule.java          (P6: Functional interface allow(Account, long))
│   ├── AccountWorker.java         (P9: Runnable executing repeated deposits)
│   ├── TransactionProcessor.java  (P10, P12: ExecutorService pool with ConcurrentHashMap)
│   ├── AccountStore.java          (P12: HashMap store, ArrayList history, Comparator sort)
│   ├── Repository.java            (P13: Generic Repository<T> using @Id reflection, @SafeVarargs saveAll, bounded average())
│   ├── BankService.java           (P14: Business façade wiring repository, Validator, exceptions, and @Audit logging)
│   └── MiniBank.java              (P6, P14: Main runnable harness, P1-P13 demos, and live interactive menu)
│
├── util\
│   ├── Validator.java             (P6: Regex validation for Mobile, Email, PAN, IFSC)
│   ├── AnnotationValidator.java   (P7: Reflection-based validator for @Positive and @MaxLength)
│   ├── StatementFormatter.java    (P4: Formats account statements)
│   ├── CommandParser.java         (P4: Parses string transaction commands)
│   ├── StatePersister.java        (P11: Serializes/deserializes Account[] via Object streams)
│   ├── TransactionLog.java        (P11: Appends transaction logs using Files.write)
│   └── ReportGenerator.java       (P11: Walks logs folder with DirectoryStream, BufferedReader, and FileAttributes)
│
├── test\
│   └── BankServiceTest.java       (P14: JUnit 5 test suite verifying deposits, over-limit exceptions, and concurrent thread safety)
│
├── lib\
│   └── junit-platform-console-standalone.jar (JUnit 5 test runner)
│
├── MiniBank.jar                   (P6, P14: Runnable executable JAR)
└── README.md                      (P14: Complete project documentation)
```

---

## Practical Implementations Overview

- **P6 (Interfaces, Packages, Runnable JAR)**
  - `Transactable`, `InterestBearing` (with default `yearlyInterest()`), `WithdrawRule` (used via anonymous class & lambda), `Premium` (marker).
  - Package structure: `model`, `service`, `util`.
  - Static import: `isValidEmail` from `util.Validator`.
  - Runnable JAR with `Main-Class: service.MiniBank`.

- **P7 (Annotations & Reflection)**
  - `@Id`, `@Positive`, `@MaxLength` defined in `model.annotation`.
  - `@Override` added on all overridden methods; `@FunctionalInterface` on `WithdrawRule`.
  - `AnnotationValidator.validate(obj)` uses reflection (`getDeclaredFields()`, `isAnnotationPresent()`, `setAccessible(true)`, `field.get(obj)`).
  - Validates negative opening balance and prints errors.

- **P8 (Exception Handling & Fault Tolerance)**
  - Custom checked exception hierarchy: `BankException` -> `InsufficientFundsException` (with `shortfall`), `AccountNotFoundException`, `InvalidAmountException`.
  - `deposit()` and `withdraw()` throw checked exceptions.
  - `transfer()` with `try-catch-finally` and re-throw.
  - Demonstrated with friendly messages and try-with-resources via `BankResource` (`AutoCloseable`).

- **P9 (Multithreading & Synchronization)**
  - `AccountWorker` implements `Runnable`.
  - Starts 10 worker threads, calls `join()`.
  - `deposit()` and `withdraw()` made `synchronized` to eliminate race conditions and guarantee exact balances.

- **P10 (Concurrency Engine, Producer-Consumer & Deadlock Fix)**
  - `TransactionProcessor` with `Executors.newFixedThreadPool(4)`, `submit()`, `stop()`.
  - Producer-consumer queue using `wait()` and `notify()` inside `synchronized` blocks.
  - Demonstrates deadlock risk with unordered locking (`transferUnsafe`) and the fix with ordered locking (`transfer`) locking the smaller `accountNumber` first.

- **P11 (Serialization & NIO File I/O)**
  - `Account`, subclasses, and `Customer` implement `Serializable` with `serialVersionUID`.
  - `StatePersister.save()` / `load()` using `ObjectOutputStream` / `ObjectInputStream`.
  - `TransactionLog.append()` using `Files.write(...)` with `CREATE` and `APPEND`.
  - `ReportGenerator` walks folder with `Files.newDirectoryStream`, reads with `BufferedReader`, reads attributes with `Files.readAttributes`, and outputs `report.txt`.

- **P12 (Collections Framework)**
  - `AccountStore` backed by `HashMap<String, Account>`.
  - `ArrayList<String>` transaction history.
  - `sortedByBalance()` using `Comparator.comparingLong(...)`.
  - `Account` implements `Comparable<Account>` comparing `accountNumber`.
  - `TransactionProcessor` uses `ConcurrentHashMap` for thread-safe concurrent access.

- **P13 (Generic Repository Pattern)**
  - `Repository<T>` with `save(T)`, `findById(String)`, `findAll()`, `delete(String)`.
  - `save(T)` finds the ID reflectively via `@Id`.
  - `@SafeVarargs final void saveAll(T... items)`.
  - Generic bounded method `static <N extends Number> double average(N[] values)`.

- **P14 (Capstone BankService, Persistence & Tests)**
  - `BankService` wires `Repository<Account>`, `Validator`, `AnnotationValidator`, exceptions, and batch operations.
  - Practical 1 menu upgraded to 9 interactive options: Open Account, Deposit, Withdraw, Transfer, Statement, List Accounts, Report, Batch Transactions, Exit.
  - Loads saved accounts on startup and persists on exit.
  - Reflective `@Audit` logging on service methods.
  - JUnit 5 test suite in `BankServiceTest.java`.
  - Packaged executable `MiniBank.jar`.

---

## Prerequisites

- **Java 21** (JDK 21) — required for records, pattern matching, sealed classes, and switch expressions.
- `javac` and `java` must point to JDK 21+. Verify with: `javac -version`

---

## How to Build and Run

### 1. Compile All Sources
```powershell
javac -d bin @sources.txt
```

### 2. Compile Tests
```powershell
javac -d bin -cp "bin;lib/junit-platform-console-standalone.jar" test/BankServiceTest.java
```

### 3. Run the Application (via classpath)
```powershell
java -cp bin service.MiniBank
```

### 4. Run via Executable JAR
```powershell
java -jar MiniBank.jar
```

### 5. Build the JAR (after compiling)
```powershell
jar cfm MiniBank.jar META-INF/MANIFEST.MF -C bin .
```

### 6. Run JUnit 5 Tests
```powershell
java -jar lib/junit-platform-console-standalone.jar execute --class-path bin -c test.BankServiceTest
```

---

## Test Results

All 3 JUnit 5 tests pass:

| Test | Status |
|------|--------|
| `depositIncreasesBalance` | ✅ PASS |
| `withdrawOverLimitThrowsException` | ✅ PASS |
| `concurrentDepositsAreCorrect` | ✅ PASS |
