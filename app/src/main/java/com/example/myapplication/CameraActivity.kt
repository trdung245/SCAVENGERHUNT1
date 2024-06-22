package com.example.myapplication

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.myapplication.R

class CameraActivity : AppCompatActivity() {

    private lateinit var ivUser: ImageView
    private lateinit var btnTakePicture: Button

    // Define ActivityResultLauncher for taking a picture
    private lateinit var takePictureLauncher: ActivityResultLauncher<Void?>

    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera_activity)

        ivUser = findViewById(R.id.ivUser)
        btnTakePicture = findViewById(R.id.btnTakePicture)

        // Register the launcher for the result of taking a picture
        takePictureLauncher = registerForActivityResult(
            ActivityResultContracts.TakePicturePreview()
        ) { bitmap: Bitmap? ->
            bitmap?.let {
                ivUser.setImageBitmap(it)
            }
        }

        btnTakePicture.setOnClickListener {
            // Check for camera permission
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // Request camera permission
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
            } else {
                // Permission already granted, launch the camera
                takePictureLauncher.launch(null)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CAMERA_PERMISSION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission was granted, launch the camera
                    takePictureLauncher.launch(null)
                } else {
                    // Permission denied, show a message to the user
                    Toast.makeText(this, "Camera permission is required to use this feature", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
