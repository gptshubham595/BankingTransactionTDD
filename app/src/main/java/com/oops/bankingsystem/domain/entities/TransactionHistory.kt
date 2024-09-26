package com.oops.bankingsystem.domain.entities

data class TransactionHistory(
    private val historyMapping: MutableList<Transaction> = mutableListOf()
) {

    fun addTransaction(accountId: String, transaction: Transaction) {
        historyMapping.add(transaction)
    }

    fun getTransactionHistory(): List<Transaction>? {
        return historyMapping
    }
}