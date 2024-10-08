package com.oops.bankingsystem.presentation

import com.oops.bankingsystem.core.transaction.TransactionFactory
import com.oops.bankingsystem.core.transaction.TransactionManager
import com.oops.bankingsystem.domain.entities.Account
import com.oops.bankingsystem.domain.entities.TransactionType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


fun main() {
    // Create an account
    val personA_Account = Account(accountId = "12345", balance= 1000.0)
    val personB_Account = Account(accountId = "67890", balance= 500.0)

    val transactionManager = TransactionManager
    val transactionHistory = TransactionManager

    // Print initial account details
    personA_Account.printAccountDetails()

    CoroutineScope(Dispatchers.IO).launch {

        depositAmount(personA_Account, transactionManager, 500.0)

        withdrawAmount(personA_Account, transactionManager, 200.0)

        // Print updated account details
        personA_Account.printAccountDetails()

        // Output the transaction history
        println("Transaction History A:")
        println(personA_Account.getTransactionHistory().getTransactionHistory().toString())

        println("Transaction History B:")
        println(personB_Account.getTransactionHistory().getTransactionHistory().toString())

        //transfer amount
        transferAmount(personA_Account, personB_Account, transactionManager, 200.0)

        // Output the transaction history
        println("Transaction History A:")
        println(personA_Account.getTransactionHistory().getTransactionHistory().toString())
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
