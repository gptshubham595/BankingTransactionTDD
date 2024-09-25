package com.oops.bankingsystem.core

import com.oops.bankingsystem.commons.exceptions.InsufficientFundsException
import com.oops.bankingsystem.domain.entities.Account
import com.oops.bankingsystem.domain.entities.Transaction
import com.oops.bankingsystem.domain.entities.TransactionType
import java.util.concurrent.locks.ReentrantLock

object TransactionManager {

    private val accountLocks = mutableMapOf<String, ReentrantLock>()

    fun processTransaction(account: Account, transaction: Transaction): Boolean {
        val lock = accountLocks.getOrPut(account.accountId) { ReentrantLock() }
        lock.lock()
        return try {
            when (transaction.type) {
                is TransactionType.Deposit -> account.balance += transaction.amount
                is TransactionType.Withdrawal -> if (account.balance >= transaction.amount) {
                    account.balance -= transaction.amount
                } else {
                    throw InsufficientFundsException()
                }

                is TransactionType.Transfer -> {
                    // Handle transfer logic between accounts
                }
            }
            account.transactionHistory.add(transaction)
            true
        } finally {
            lock.unlock()
        }
    }
}


