package com.oops.bankingsystem.domain.entities

data class Account(
    val accountId: String,
    private var balance: Double,
    private val transactionHistory: TransactionHistory = TransactionHistory()
) {

    override fun toString(): String {
        return "Account ID: $accountId \n" +
                "Balance: $balance \n"
    }

    fun printAccountDetails() {
        println(this.toString())
    }

    fun getTransactionHistory(): TransactionHistory {
        return transactionHistory
    }

    // Function to get current balance
    fun getAccountBalance(): Double {
        return balance
    }

    // Function to fetch transaction history
    fun deposit(amount: Double) {
        balance += amount
    }

    fun withdraw(amount: Double) {
        balance -= amount
    }

    fun notify(transaction: Transaction) {
        when (transaction.type) {
            TransactionType.Deposit -> println("Deposit of ${transaction.amount} successful!")
            TransactionType.Withdrawal -> println("Withdraw of ${transaction.amount} successful!")
            TransactionType.Transfer -> println("Transfer of ${transaction.amount} successful!")
        }
    }
}