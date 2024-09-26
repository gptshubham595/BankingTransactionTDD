package com.oops.bankingsystem.core.transaction

import com.oops.bankingsystem.domain.entities.Account
import com.oops.bankingsystem.domain.entities.Transaction
import com.oops.bankingsystem.domain.entities.TransactionType
import java.util.concurrent.locks.ReentrantLock

object TransactionManager {

    private val accountLocks = mutableMapOf<String, ReentrantLock>()

    suspend fun processTransaction(
        senderAccount: Account,
        receiverAccount: Account?,
        transaction: Transaction,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ): Boolean {
        val lock = accountLocks.getOrPut(senderAccount.accountId) { ReentrantLock() }
        lock.lock()
        return try {
            when (transaction.type) {
                is TransactionType.Deposit -> deposit(
                    senderAccount,
                    transaction,
                    onDepositSuccess(senderAccount, transaction),
                    onDepositFailure(senderAccount, transaction),
                )

                is TransactionType.Withdrawal -> withdrawal(
                    senderAccount, transaction,
                    onWithdrawalSuccess(senderAccount, transaction),
                    onWithdrawalFailure(senderAccount, transaction),
                )

                is TransactionType.Transfer -> transfer(
                    senderAccount,
                    receiverAccount,
                    transaction
                )
            }
            true
        } finally {
            lock.unlock()
        }
    }

    suspend fun addTransaction(
        senderAccount: Account,
        receiverAccount: Account?,
        transaction: Transaction
    ) {
        when (transaction.type) {
            is TransactionType.Deposit -> {
                senderAccount.getTransactionHistory().addTransaction(
                    senderAccount.accountId,
                    transaction
                )
            }

            is TransactionType.Withdrawal -> {
                senderAccount.getTransactionHistory().addTransaction(
                    senderAccount.accountId,
                    transaction
                )
            }

            is TransactionType.Transfer -> {
                senderAccount.getTransactionHistory().addTransaction(
                    senderAccount.accountId,
                    transaction
                )
                receiverAccount?.accountId?.let { receiverAccountId ->
                    receiverAccount.getTransactionHistory().addTransaction(
                        receiverAccountId,
                        transaction
                    )
                }

            }
        }
    }
}





