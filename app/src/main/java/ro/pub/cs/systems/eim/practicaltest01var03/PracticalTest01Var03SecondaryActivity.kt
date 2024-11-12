package ro.pub.cs.systems.eim.practicaltest01var03

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ro.pub.cs.systems.eim.practicaltest01var03.ui.theme.PracticalTest01Var03Theme

class PracticalTest01Var03SecondaryActivity : ComponentActivity() {

    private lateinit var textview: TextView
    private lateinit var correctButton: Button
    private lateinit var incorrectButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_pratical_test01_var03_secondary)

        textview = findViewById(R.id.editText1)
        correctButton = findViewById(R.id.correct_button)
        incorrectButton = findViewById(R.id.incorrect_button)

        var input1 = intent.getIntExtra("VALUE1", 0)
        var input2 = intent.getIntExtra("VALUE2", 0)

        textview.text = (input1 + input2).toString()

        correctButton.setOnClickListener {
            setResult(RESULT_OK, Intent().putExtra("VALUE3", textview.text.toString().toInt()))
            finish()
        }

        incorrectButton.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}

