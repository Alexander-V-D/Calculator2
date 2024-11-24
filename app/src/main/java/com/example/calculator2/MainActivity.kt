package com.example.calculator2

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var timeInputET1: EditText
    private lateinit var timeInputET2: EditText

    private lateinit var plusBTN: Button
    private lateinit var minusBTN: Button

    private lateinit var resultTW: TextView

    private lateinit var toolbarMain: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        timeInputET1 = findViewById(R.id.timeInputET1)
        timeInputET2 = findViewById(R.id.timeInputET2)

        plusBTN = findViewById(R.id.plusBTN)
        minusBTN = findViewById(R.id.minusBTN)

        resultTW = findViewById(R.id.resultTW)

        plusBTN.setOnClickListener(this)
        minusBTN.setOnClickListener(this)

        toolbarMain = findViewById(R.id.toolbarMain)
        setSupportActionBar(toolbarMain)
        title = "Калькулятор времени"
        toolbarMain.subtitle = "Версия 2.0"
        toolbarMain.setLogo(R.drawable.ic_calculate)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.clear -> {
                timeInputET1.text.clear()
                timeInputET2.text.clear()
                resultTW.text = getString(R.string.result_base_text)
                Toast.makeText(
                    applicationContext,
                    "Данные очищены",
                    Toast.LENGTH_LONG
                ).show()
                resultTW.setTextColor(Color.parseColor("#FF000000"))
            }

            R.id.exit -> {
                Toast.makeText(
                    applicationContext,
                    "Приложение закрыто",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.plusBTN -> {
                val first = convertTimeToSeconds(timeInputET1.text.toString())
                val second = convertTimeToSeconds(timeInputET2.text.toString())
                val result = convertSecondsToTime(first + second)
                resultTW.text = result
                Toast.makeText(
                    applicationContext,
                    "Результат: $result",
                    Toast.LENGTH_LONG
                ).show()
                resultTW.setTextColor(Color.parseColor("#8B0000"))
            }

            R.id.minusBTN -> {
                val first = convertTimeToSeconds(timeInputET1.text.toString())
                val second = convertTimeToSeconds(timeInputET2.text.toString())
                val result = convertSecondsToTime(first - second)
                resultTW.text = result
                Toast.makeText(
                    applicationContext,
                    "Результат: $result",
                    Toast.LENGTH_LONG
                ).show()
                resultTW.setTextColor(Color.parseColor("#8B0000"))
            }
        }
    }

    private fun convertTimeToSeconds(time: String): Int {
        var seconds = 0
        var timeString = time
        if (timeString.contains('h')) {
            seconds = 3600 * timeString.takeWhile { it.isDigit() }.toInt()
            timeString = timeString.dropWhile { it.isDigit() }.drop(1)
        }
        if (timeString.contains('m')) {
            seconds += 60 * timeString.takeWhile { it.isDigit() }.toInt()
            timeString = timeString.dropWhile { it.isDigit() }.drop(1)
        }
        if (timeString.contains('s')) {
            seconds += timeString.takeWhile { it.isDigit() }.toInt()
        }
        return seconds
    }

    private fun convertSecondsToTime(seconds: Int): String {
        return "${seconds / 3600}h${seconds % 3600 / 60}m${seconds % 3600 % 60}s"
    }
}