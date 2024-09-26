package com.oops.bankingsystem.core.transaction

import com.oops.bankingsystem.domain.entities.Transaction
import com.oops.bankingsystem.domain.entities.TransactionType
import java.util.UUID

object TransactionFactory {
    fun createTransaction(type: TransactionType, amount: Double): Transaction {
        return Transaction(transactionId = generateTransactionId(), type = type, amount = amount)
    }

    private fun generateTransactionId(): String = UUID.randomUUID().toString()
}