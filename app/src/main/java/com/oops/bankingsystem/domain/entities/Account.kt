package com.oops.bankingsystem.domain.entities

data class Account(
    val accountId: String,
    var balance: Double,
    val transactionHistory: MutableList<Transaction> = mutableListOf()
) {
    fun printAccountDetails() {
        println("Account ID: $accountId")
        println("Balance: $balance")
        println("Transaction History: ${transactionHistory.size} transactions")
    }
}