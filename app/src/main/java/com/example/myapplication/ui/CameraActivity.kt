package ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.myapplication.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class CameraActivity : AppCompatActivity() {

    private lateinit var ivUser: ImageView
    private lateinit var btnTakePicture: Button
    private lateinit var backButton: ImageButton

    // Define ActivityResultLauncher for taking a picture
    private lateinit var takePictureLauncher: ActivityResultLauncher<Void?>

    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 1
        private const val REQUEST_CODE = 1001
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera_activity) // Ensure the layout name matches your XML file

        ivUser = findViewById(R.id.ivUser)
        btnTakePicture = findViewById(R.id.btnTakePicture)
        backButton = findViewById(R.id.backButton) // Initialize the back button

        // Register the launcher for the result of taking a picture
        takePictureLauncher = registerForActivityResult(
            ActivityResultContracts.TakePicturePreview()
        ) { bitmap: Bitmap? ->
            bitmap?.let {
                ivUser.setImageBitmap(it) // Display the captured image
                val fileUri = saveBitmapToFile(this, it, "captured_image")
                fileUri?.let { uri ->
                    Toast.makeText(this, "Image saved to: $uri", Toast.LENGTH_LONG).show()
                }
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

        // Set up click listener for the back button
        backButton.setOnClickListener {
            // Navigate back to the main menu
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
            finish() // Optional: close the current activity
        }
    }

    // Save the bitmap to a file and return its URI
    private fun saveBitmapToFile(context: Context, bitmap: Bitmap, capturedImage: String): Uri? {
        // Get the external files directory for images
        val imagesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        if (imagesDir != null && !imagesDir.exists()) {
            imagesDir.mkdirs()
        }

        // Create the file in the external files directory
        val imageFile = File(imagesDir, "$capturedImage.jpg")
        return try {
            FileOutputStream(imageFile).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out) // Compress to 90% quality
                out.flush()
            }
            // Get the content URI for the saved file using FileProvider
            FileProvider.getUriForFile(context, "com.example.myapplication.fileprovider", imageFile)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    // Example method to get a URI for a file
    private fun getUriForFile(file: File): Uri {
        val authority = "com.example.myapplication.fileprovider"
        return FileProvider.getUriForFile(this, authority, file)
    }

    // Example method to share a file
    private fun shareFile(file: File) {
        val fileUri = getUriForFile(file)

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "application/pdf" // Change according to your file type
            putExtra(Intent.EXTRA_STREAM, fileUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        startActivity(Intent.createChooser(intent, "Share file via"))
    }

    // Example method to handle onActivityResult
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                // Use the URI as needed
            }
        }
    }

    // Handle the result of the permission request
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
