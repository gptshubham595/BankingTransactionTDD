package com.oops.bankingsystem.domain.entities

import kotlinx.coroutines.flow.flow
import mimickApiCall

data class Transaction(
    val transactionId: String,
    val type: TransactionType,
    val amount: Double,
    val date: Long = System.currentTimeMillis(),
    var transactionStatus: TransactionStatus = TransactionStatus.PENDING
) {
    //observer pattern add accounts who are involved in transaction

    suspend fun performTransaction(
        sendersAccount: Account,
        receiversAccount: Account? = null,
        transaction: Transaction
    ) = flow {
        mimickApiCall(
            sendersAccount,
            receiversAccount,
            transaction
        ).collect { transactionStatus: TransactionStatus ->
            when (transactionStatus) {
                TransactionStatus.SUCCESS -> {
                    this@Transaction.transactionStatus = TransactionStatus.SUCCESS
                    emit(transaction)
                }

                TransactionStatus.FAILED -> {
                    this@Transaction.transactionStatus = TransactionStatus.FAILED
                    emit(transaction)
                }

                TransactionStatus.PENDING -> {
                    this@Transaction.transactionStatus = TransactionStatus.PENDING
                    emit(transaction)
                }
            }
        }
    }
}


