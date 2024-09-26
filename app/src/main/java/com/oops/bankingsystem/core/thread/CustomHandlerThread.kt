package com.oops.bankingsystem.core.thread

import android.util.Log
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.atomic.AtomicBoolean

class CustomHandlerThread(val nameOfThread: String) : Thread() {
    private val TAG = CustomHandlerThread::class.java.simpleName
    private val alive = AtomicBoolean(true)
    private val queue: ConcurrentLinkedQueue<Runnable> = ConcurrentLinkedQueue() //message queue

    fun startThread() {
        start()
    }

    override fun run() {
        super.run()
        while (alive.get()) { //looper
            sleep(2000)
            Log.d(TAG, "Running ...")
            val task: Runnable? = queue.poll()
            if (task != null) {
                task.run()
            }
        }
        Log.d(TAG, "Stopped")
    }

    fun execute(task: Runnable): CustomHandlerThread {
        queue.add(task)
        return this
    }

    fun quit() {
        alive.set(false)
    }
}