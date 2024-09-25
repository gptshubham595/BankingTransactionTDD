package com.oops.bankingsystem.domain.entities

import java.util.UUID

//Factory Pattern
sealed class TransactionType {
    object Deposit : TransactionType()
    object Withdrawal : TransactionType()
    object Transfer : TransactionType()
}

data class Transaction(
    val transactionId: String,
    val type: TransactionType,
    val amount: Double,
    val date: Long = System.currentTimeMillis()
)


