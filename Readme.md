# Problem Statement:

Design and implement a banking transaction system in Kotlin that follows SOLID and OOP principles,
leverages synchronization for handling concurrent transactions, and is optimized for
high-performance reads and writes. The system should prioritize:

### Transaction Processing:

- Ensure that transactions (like deposits, withdrawals, transfers) are processed accurately (either completed or gets rolled out) and in
  real-time.

### Concurrency:

- Multiple users should be able to perform transactions concurrently without data corruption or
  inconsistencies.

### Optimized Reads and Writes:

- Read operations (checking balance, etc.) should be fast and efficient.
- Write operations (such as transferring funds or making deposits) should ensure data integrity.
- Transaction history visibility is less critical than transaction accuracy.

### Design Patterns:

- Incorporate design patterns like Singleton, Factory, Observer, etc., where applicable, to promote
  flexibility, reusability, and maintainability.

## Approach:

1. <b>Defining System Components (Top-Level)</b>
   We'll first define the key components of our system

 - Accounts: Represents individual user accounts, with attributes like balance, transaction history,
  and user details.
 - Transactions: Represents the different types of transactions: Deposit, Withdrawal, Transfer.
 - TransactionManager: Handles the execution of transactions, ensuring proper synchronization when multiple users are involved.
- Synchronization Mechanism: Ensures that concurrent transactions do not result in race conditions,
  deadlocks, or inconsistencies.
- Optimized Read/Write Strategy: A plan for read-heavy and write-heavy scenarios.

<hr>

2. <b>Detailed Design (SOLID and OOP Principles)</b>
   Each component will follow SOLID principles, and the system will use Object-Oriented concepts
   such as inheritance, polymorphism, and abstraction. Some specific examples:

- S - Single Responsibility: Each class will have a single, well-defined
  responsibility.
- O - Open/Closed: The system should be open to extension but closed for
  modification.
- L - Liskov Substitution: Derived classes should be able to substitute their base
  classes without breaking the system.
- I - Interface Segregation: Multiple small interfaces are better than one large,
  bloated interface.
- D - Dependency Inversion: Depend on abstractions, not on concrete classes.

<hr>

3. <b>Synchronization and Concurrency Management</b>
    We’ll employ synchronization techniques to manage concurrent access to account balances and prevent race conditions:

- Locks/Semaphores: Protect critical sections when updating account balances.
- Atomic Transactions: Ensure all transactions are either fully completed or fully rolled back in case of failure.

<hr>

4. <b>Read-Optimized vs Write-Optimized Approaches</b>

- Read-Optimized: For scenarios where reading account details (e.g., balance, transaction history) is more frequent than writing (transactions).
Use of caching mechanisms for frequently accessed data.
- Eventual consistency model, where real-time balance checks are optimized for speed.
- Write-Optimized: For handling high write loads (e.g., during peak transaction times).
- Use of batch processing or queuing for writes.
- Ensuring atomicity using transactional mechanisms like ACID principles.
<hr>

5. <b>Using Design Patterns</b>
  We'll use several design patterns to ensure the flexibility and maintainability of the system:

- Singleton: For managing global transaction managers or database connections.
- Factory Pattern: To create different types of transactions (Deposit, - Withdrawal, Transfer).
- Observer Pattern: To notify different parts of the system when an account balance or transaction history is updated.

## Step-by-Step Execution:
Account and Transaction Model:

- Start by defining the Account and Transaction models.
Make these models flexible, allowing for extension (using inheritance or interfaces).
Transaction Processing Logic:

- Develop the TransactionManager that will handle the orchestration of transactions.
Incorporate synchronization mechanisms here.
Concurrency Control:

- Implement locking and ensure that multiple concurrent transactions don’t lead to data inconsistencies.
Test for race conditions and edge cases.
Read/Write Optimization:

- Implement caching for read-heavy operations.
Handle high write loads with batching, queuing, and atomic transaction processing.
Integration of Design Patterns:

- Refactor the system to use design patterns that increase modularity and flexibility.


## Factory Pattern for Transaction Types
## Singleton Pattern for TransactionManager(Sync with Locks on multiThreads), Cache
## Observer Pattern: To notify the system when account balances or transaction histories are updated.
## Read optimized: Cache
## Write optimized: Batch processing, ACID principles

