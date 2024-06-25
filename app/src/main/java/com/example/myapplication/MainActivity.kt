package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton1: Button
    private lateinit var registerButton2: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        usernameInput = findViewById(R.id.username_input)
        passwordInput = findViewById(R.id.password_input)
        loginButton = findViewById(R.id.loginbtn)
        registerButton1 = findViewById(R.id.registerbtn1)

        registerButton1.setOnClickListener{
            setContentView(R.layout.register)

            registerButton2 = findViewById(R.id.registerbtn2)
            registerButton2.setOnClickListener{
                val username = usernameInput.text.toString()
                val password = passwordInput.text.toString()

                val intent = Intent(this, MainMenuActivity::class.java)
                startActivity(intent)
            }
        }

        loginButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()
            Log.i("Test Credentials", "Username: $username and Password: $password")

            // Start MainMenuActivity
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }
    }
}
