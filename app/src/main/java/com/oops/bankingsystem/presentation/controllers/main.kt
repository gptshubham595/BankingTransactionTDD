package com.oops.bankingsystem.presentation.controllers

import com.oops.bankingsystem.core.thread.CustomHandlerThread
import com.oops.bankingsystem.core.transaction.TransactionFactory
import com.oops.bankingsystem.core.transaction.TransactionManager
import com.oops.bankingsystem.domain.entities.Account
import com.oops.bankingsystem.domain.entities.TransactionType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


fun main() {
    val customHandlerThread by lazy { CustomHandlerThread("TransactionHandler") }


    // Create an account
    val personA_Account = Account(accountId = "12345", balance = 1000.0)
    val personB_Account = Account(accountId = "67890", balance = 500.0)

    val transactionManager = TransactionManager


    CoroutineScope(Dispatchers.IO).launch {
        println("PersonA Account balance ${personA_Account.printAccountDetails()}")
        println("PersonB Account balance ${personB_Account.printAccountDetails()}")
        println("PersonA Deposit Amount ${500}")
        depositAmount(personA_Account, transactionManager, 500.0)
        println("PersonA Withdraws Amount ${200}")
        withdrawAmount(personA_Account, transactionManager, 200.0)
        //transfer amount
        println("PersonA Transfer Amount ${100} to PersonB")
        transferAmount(personA_Account, personB_Account, transactionManager, 100.0)
        // Output the transaction history
        println("Transaction History A:")
        println(personA_Account.getTransactionHistory().getTransactionHistory().toString())

        println("Transaction History B:")
        println(personB_Account.getTransactionHistory().getTransactionHistory().toString())
    }

    val t1 = Thread {
        while (true) {
            try {
                Thread.sleep(500)
            } catch (e: InterruptedException) {
                throw RuntimeException(e)
            }
        }
    }

    try {
        t1.start()
        t1.join()

    } catch (e: InterruptedException) {
        throw RuntimeException(e)
    }
}

suspend fun transferAmount(
    senderAccount: Account,
    receiverAccount: Account,
    transactionManager: TransactionManager,
    amount: Double
) {
// Create withdrawal transaction using the factory
    val transferTransaction =
        TransactionFactory.createTransaction(TransactionType.Transfer, amount)
    transactionManager.processTransaction(
        senderAccount,
        receiverAccount,
        transferTransaction,
        onSuccess = {
            println("Transfer of ${transferTransaction.amount} successful!")
        },
        onFailure = {
            println("Transfer of ${transferTransaction.amount} failed")
        })
}

suspend fun withdrawAmount(
    account: Account,
    transactionManager: TransactionManager,
    amount: Double
) {
    val withdrawalTransaction =
        TransactionFactory.createTransaction(TransactionType.Withdrawal, amount)
    transactionManager.processTransaction(
        account,
        null,
        withdrawalTransaction,
        onSuccess = {
            println("Withdrawal of ${withdrawalTransaction.amount} successful!")
        },
        onFailure = {
            println("Withdrawal of ${withdrawalTransaction.amount} failed")
        })
}

suspend fun depositAmount(
    account: Account,
    transactionManager: TransactionManager,
    amount: Double
) {
    // Create deposit transaction using the factory
    val depositTransaction =
        TransactionFactory.createTransaction(TransactionType.Deposit, amount)
    transactionManager.processTransaction(
        account,
        null,
        depositTransaction,
        onSuccess = {
            println("Deposit of ${depositTransaction.amount} successful!")
        },
        onFailure = {
            println("Deposit of ${depositTransaction.amount} failed")
        })

}