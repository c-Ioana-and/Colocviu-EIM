package ro.pub.cs.systems.eim.practicaltest01var03

import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.Date
import kotlin.math.sqrt

class ProcessingThread(private var context : Context, private var firstNumber: Int,
                       private var secondNumber: Int) : Thread() {
    private var isRunning = true

    override fun run() {
        Log.d("Processing_Thread", "Thread has started! PID: " + android.os.Process.myPid() + " TID: " + android.os.Process.myTid())
        Log.d("Processing_Thread", "First number: $firstNumber")
        Log.d("Processing_Thread", "Second number: $secondNumber")

        while (isRunning) {
            try {
                var sum = firstNumber + secondNumber
                var diff = firstNumber * secondNumber

                Log.d("Processing_Thread", "Sum: $sum")
                Log.d("Processing_Thread", "Diff: $diff")

                var intent = Intent().apply {
                    action = "ACTION"
                    putExtra("Sum", sum.toString())
                    putExtra("Diff", diff.toString())
                }
                context.sendBroadcast(intent)

                sleep(5000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }

        Log.d("Processing_Thread", "Thread has stopped!");
    }

    fun stopThread() {
        isRunning = false
    }
}