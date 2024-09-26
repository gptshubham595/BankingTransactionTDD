package com.oops.bankingsystem.core.transaction

import com.oops.bankingsystem.commons.exceptions.InvalidReceiverTransferException
import com.oops.bankingsystem.core.transaction.TransactionManager.addTransaction
import com.oops.bankingsystem.domain.entities.Account
import com.oops.bankingsystem.domain.entities.Transaction

// Transfer logic (this can be expanded for multi-account transfers)
suspend fun transfer(
    senderAccount: Account,
    receiverAccount: Account?,
    transaction: Transaction
) {
    if (receiverAccount == null) {
        onTransferFailure(senderAccount, receiverAccount, transaction).invoke()
        try {
            throw InvalidReceiverTransferException()
        } catch (e: Exception) {
            println("ERR: ${e.message}")
        }
        return
    }
    // This can involve multiple accounts; for now, it’s similar to withdrawal
    withdrawal(
        senderAccount,
        transaction,
        onSuccess = {
            deposit(receiverAccount, transaction,
                onSuccess = {
                    onWithdrawalSuccess(senderAccount, transaction).invoke()
                    onDepositSuccess(receiverAccount, transaction).invoke()
                },
                onFailure = {
                    onWithdrawalFailure(senderAccount, transaction)
                    onDepositFailure(receiverAccount, transaction).invoke()
                }
            )
        },
        onFailure = { onWithdrawalFailure(senderAccount, transaction) }
    )
}

suspend inline fun onTransferFailure(
    senderAccount: Account,
    receiverAccount: Account?,
    transaction: Transaction
): suspend () -> Unit = {
    addTransaction(
        senderAccount,
        receiverAccount,
        transaction
    )
    senderAccount.notify(transaction)
    receiverAccount?.notify(transaction)
    println("Transfer of ${transaction.amount} failed")
}
