package com.oops.bankingsystem.data

import com.oops.bankingsystem.domain.entities.Account

/** Read-Optimized (Caching)
We will implement caching for frequently accessed data like account balances to improve read performance.
We can go with LRU cache
 */
object Cache {

    private val accountCache = mutableMapOf<String, Account>()

    fun getAccount(accountId: String): Account? = accountCache[accountId]
    fun putAccount(account: Account) {
        accountCache[account.accountId] = account
    }
}


/** Write-Optimized (Transactional Write)
For writes, we ensure atomicity, consistency, isolation, and durability (ACID) principles.
We can further optimize by implementing batch writes or message queuing systems if needed for heavy load scenarios.
 */
