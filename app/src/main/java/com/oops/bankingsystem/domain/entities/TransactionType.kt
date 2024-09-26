package com.oops.bankingsystem.domain.entities

//Factory Pattern
sealed class TransactionType {
    data object Deposit : TransactionType()
    data object Withdrawal : TransactionType()
    data object Transfer : TransactionType()
}