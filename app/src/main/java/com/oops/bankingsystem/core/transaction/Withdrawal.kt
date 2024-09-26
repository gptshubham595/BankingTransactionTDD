package com.oops.bankingsystem.core.transaction

import com.oops.bankingsystem.commons.exceptions.InsufficientFundsException
import com.oops.bankingsystem.core.transaction.TransactionManager.addTransaction
import com.oops.bankingsystem.domain.entities.Account
import com.oops.bankingsystem.domain.entities.Transaction
import com.oops.bankingsystem.domain.entities.TransactionStatus

// Withdrawal logic with basic validation
suspend fun withdrawal(
    senderAccount: Account,
    transaction: Transaction,
    onSuccess: suspend () -> Unit,
    onFailure: suspend () -> Unit
) {
    transaction.performTransaction(senderAccount, null, transaction).collect {
        when (it.transactionStatus) {
            TransactionStatus.SUCCESS -> {
                if (senderAccount.getAccountBalance() >= transaction.amount) {
                    transaction.transactionStatus = TransactionStatus.SUCCESS
                    onSuccess()
                } else {
                    transaction.transactionStatus = TransactionStatus.FAILED
                    onFailure()
                    try {
                        throw InsufficientFundsException()
                    } catch (e: Exception) {
                        println("ERR: ${e.message}")
                    }
                }
            }

            TransactionStatus.FAILED -> onFailure()

            TransactionStatus.PENDING -> print("Transaction is pending")
        }
    }
}

suspend inline fun onWithdrawalFailure(
    senderAccount: Account,
    transaction: Transaction
): suspend () -> Unit = {
    addTransaction(
        senderAccount,
        null,
        transaction
    )
    senderAccount.notify(transaction)
    println("Withdraw of ${transaction.amount} failed")
}

suspend inline fun onWithdrawalSuccess(
    senderAccount: Account,
    transaction: Transaction
): suspend () -> Unit = {
    addTransaction(
        senderAccount,
        null,
        transaction
    )
    senderAccount.withdraw(transaction.amount)
    senderAccount.notify(transaction)
    println("Withdraw of ${transaction.amount} successful!")
}