import com.oops.bankingsystem.domain.entities.Account
import com.oops.bankingsystem.domain.entities.Transaction
import com.oops.bankingsystem.domain.entities.TransactionStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

suspend fun mimickApiCall(
    sendersAccount: Account,
    receiversAccount: Account?,
    transactionType: Transaction
) = flow<TransactionStatus> {
    delay(2000)
    val random = (0..10).random()
    if (random % 2 == 0) {
        emit(TransactionStatus.SUCCESS)
    } else if (random == 5) {
        emit(TransactionStatus.PENDING)
        val random2 = (0..10).random()
        if (random2 % 2 == 0)
            emit(TransactionStatus.SUCCESS)
        else
            emit(TransactionStatus.FAILED)
    } else {
        emit(TransactionStatus.FAILED)
    }
}