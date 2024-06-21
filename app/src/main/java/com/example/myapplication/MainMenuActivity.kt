package com.example.myapplication;

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainMenuActivity : AppCompatActivity() {

    private lateinit var homeButton: ImageButton
    private lateinit var findgameButton: ImageButton
    private lateinit var balanceButton: ImageButton
    private lateinit var profileButton: ImageButton
    private lateinit var communityButton: ImageButton
    private lateinit var settingButton: ImageButton

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.main_menu)

        homeButton = findViewById(R.id.home_button)
        findgameButton = findViewById(R.id.find_game_button)
        balanceButton = findViewById(R.id.balance_button)
        profileButton = findViewById(R.id.profile_button)
        communityButton = findViewById(R.id.community_button)
        settingButton = findViewById(R.id.setting_button)

        balanceButton.setOnClickListener{
                val intent = Intent(this, BalanceActivity::class.java)
                startActivity(intent)
            }

        profileButton.setOnClickListener{
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        communityButton.setOnClickListener{
            val intent = Intent(this, CommunityActivity::class.java)
            startActivity(intent)
        }

        settingButton.setOnClickListener{
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
    }
}
