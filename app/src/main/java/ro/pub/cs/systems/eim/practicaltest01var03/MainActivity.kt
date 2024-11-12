package ro.pub.cs.systems.eim.practicaltest01var03

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi

class MainActivity : ComponentActivity() {

    private lateinit var plusButton: Button
    private lateinit var minusButton: Button
    private lateinit var editText1 : EditText
    private lateinit var editText2 : EditText
    private lateinit var navToSecondActivity : Button
    private lateinit var startForResult: ActivityResultLauncher<Intent>
    private lateinit var intentFilter: IntentFilter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_pratical_test01_var03_main)

        editText1 = findViewById(R.id.editText1)
        editText2 = findViewById(R.id.editText2)

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("VALUE1")) {
                editText1.setText(savedInstanceState.getString("VALUE1"))
            } else {
                editText1.setText("0")
            }
            if (savedInstanceState.containsKey("VALUE2")) {
                editText2.setText(savedInstanceState.getString("VALUE2"))
            } else {
                editText2.setText("0")
            }
        } else {
            editText1.setText("0")
            editText2.setText("0")
        }

        plusButton = findViewById(R.id.left_button)
        plusButton.setOnClickListener {
            val value = editText1.text.toString().toInt()
            editText1.setText((value + 1).toString())
            startService(editText1, editText2)
        }

        minusButton = findViewById(R.id.right_button)

        minusButton.setOnClickListener {
            val value = editText1.text.toString().toInt()
            editText1.setText((value - 1).toString())
            startService(editText1, editText2)
        }

        startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                if (data != null) {
                    val value3 = data.getIntExtra("VALUE3", -1)
                    Toast.makeText(this, "The activity returned with result $value3", Toast.LENGTH_LONG).show()
                }
            }
        }

        navToSecondActivity = findViewById(R.id.navigateToSecondaryActivityButton)
        navToSecondActivity.setOnClickListener {
            val intent = Intent(this, PracticalTest01Var03SecondaryActivity::class.java)
            intent.putExtra("VALUE1", editText1.text.toString().toInt())
            intent.putExtra("VALUE2", editText2.text.toString().toInt())
            startForResult.launch(intent)
        }

        intentFilter = IntentFilter()
        for (term in Constants.allTerms) {
            intentFilter.addAction(term)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("VALUE1", editText1.text.toString())
        outState.putString("VALUE2", editText2.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState.containsKey("VALUE1")) {
            editText1.setText(savedInstanceState.getString("VALUE1"))
        } else {
            editText1.setText("0")
        }
        if (savedInstanceState.containsKey("VALUE2")) {
            editText2.setText(savedInstanceState.getString("VALUE2"))
        } else {
            editText2.setText("0")
        }
        //        afiseaza edit text 1 si 2 cu toast
        Toast.makeText(this, editText1.text.toString(), Toast.LENGTH_LONG).show()
        Toast.makeText(this, editText2.text.toString(), Toast.LENGTH_LONG).show()
    }

    private fun startService(editText1: EditText, editText2: EditText) {
        val val1 = editText1.text.toString().toInt()
        val val2 = editText2.text.toString().toInt()

        Log.d("Service", "Starting service with values $val1 and $val2")

        val intent1 = Intent(applicationContext, PracticalTest01Var03Service::class.java)
        stopService(intent1)



        val intent = Intent(this, PracticalTest01Var03Service::class.java)
        intent.putExtra("INPUT1", val1)
        intent.putExtra("INPUT2", val2)
        startService(intent)
    }

    private lateinit var BroadcastRecv: MessageBroadcastReceiver

    class MessageBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("BroadcastReceiver", "Received sum: " +  intent?.getIntExtra("Sum", 0).toString())
            Log.d("BroadcastReceiver", "Received diff: " +  intent?.getIntExtra("Diff", 0).toString())
        }
    }

    override fun onStart() {
        super.onStart()
        BroadcastRecv = MessageBroadcastReceiver()
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(BroadcastRecv)

        val intent = Intent(applicationContext, PracticalTest01Var03Service::class.java)
        stopService(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()

        registerReceiver(BroadcastRecv, IntentFilter("ACTION"), Context.RECEIVER_EXPORTED)
    }

    override fun onPause() {
        unregisterReceiver(BroadcastRecv)
        super.onPause()
    }

}
