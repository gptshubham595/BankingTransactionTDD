package com.oops.bankingsystem.presentation

import com.oops.bankingsystem.core.TransactionFactory
import com.oops.bankingsystem.domain.entities.Account
import com.oops.bankingsystem.domain.entities.TransactionType


fun main() {
    // Create an account
    val account = Account(accountId = "12345", balance = 1000.0)

    // Print initial account details
    account.printAccountDetails()

    // Create deposit transaction using the factory
    val depositTransaction = TransactionFactory.createTransaction(TransactionType.Deposit, 500.0)
    account.transactionHistory.add(depositTransaction)
    account.balance += depositTransaction.amount

    // Create withdrawal transaction using the factory
    val withdrawalTransaction =
        TransactionFactory.createTransaction(TransactionType.Withdrawal, 200.0)
    account.transactionHistory.add(withdrawalTransaction)
    account.balance -= withdrawalTransaction.amount

    // Print updated account details
    account.printAccountDetails()

    // Output the transaction history
    println("Transaction History:")
    account.transactionHistory.forEach { println(it) }
}