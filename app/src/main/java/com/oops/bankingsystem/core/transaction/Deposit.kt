package com.oops.bankingsystem.core.transaction

import com.oops.bankingsystem.core.transaction.TransactionManager.addTransaction
import com.oops.bankingsystem.domain.entities.Account
import com.oops.bankingsystem.domain.entities.Transaction
import com.oops.bankingsystem.domain.entities.TransactionStatus

suspend inline fun onDepositFailure(
    senderAccount: Account,
    transaction: Transaction
): suspend () -> Unit = {
    addTransaction(
        senderAccount,
        null,
        transaction
    )
    senderAccount.notify(transaction)
    println("Deposit of ${transaction.amount} failed")
}

suspend inline fun onDepositSuccess(
    senderAccount: Account,
    transaction: Transaction
): suspend () -> Unit = {
    addTransaction(
        senderAccount,
        null,
        transaction
    )
    senderAccount.deposit(transaction.amount)
    senderAccount.notify(transaction)
    println("Deposit of ${transaction.amount} successful!")

}

// Deposit logic
suspend fun deposit(
    senderAccount: Account,
    transaction: Transaction,
    onSuccess: suspend () -> Unit,
    onFailure: suspend () -> Unit
) {
    transaction.performTransaction(senderAccount, null, transaction).collect {
        when (it.transactionStatus) {
            TransactionStatus.SUCCESS -> onSuccess()
            TransactionStatus.FAILED -> onFailure()
            TransactionStatus.PENDING -> print("Transaction is pending")
        }
    }
}