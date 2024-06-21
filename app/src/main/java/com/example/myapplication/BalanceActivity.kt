package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class BalanceActivity : AppCompatActivity() {

    private lateinit var backButton: ImageButton
    private lateinit var refreshButton: Button
    private lateinit var addFundsButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.balance)

        backButton = findViewById(R.id.backButton)
        refreshButton = findViewById(R.id.refreshButton)
        addFundsButton = findViewById(R.id.addFundsButton)

        backButton.setOnClickListener{
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }
    }
}