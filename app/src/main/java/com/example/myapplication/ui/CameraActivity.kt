package com.example.myapplication.ui

import android.Manifest
import android.annotation.SuppressLint
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
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
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
    }

    private val client = OkHttpClient()

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
                    uploadImageToIPFS(uri)
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
        backButton.setOnClickListener {// Navigate back to the main menu
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

    private fun uploadImageToIPFS(uri: Uri) {
        val file = File(uri.path!!)
        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())

        val multipartBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file", file.name, requestBody)
            .build()

        val request = Request.Builder()
            .url("https://api.pinata.cloud/pinning/pinFileToIPFS") // Replace with your IPFS API endpoint
            .post(multipartBody)
            .addHeader("pinata_api_key", "YOUR_PINATA_API_KEY") // Replace with your API key
            .addHeader("pinata_secret_api_key", "YOUR_PINATA_SECRET_API_KEY") // Replace with your secret API key
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@CameraActivity, "Failed to upload image to IPFS", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    runOnUiThread {
                        Toast.makeText(this@CameraActivity, "Failed to upload image to IPFS", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val responseJson = JSONObject(response.body!!.string())
                    val ipfsUrl = responseJson.getString("IpfsHash")
                    sendIpfsUrlToServerForComparison(ipfsUrl)
                }
            }
        })
    }

    private fun sendIpfsUrlToServerForComparison(ipfsUrl: String): Float {
        var similarityRatio: Float = 0.0f
        val localImageName = "local_image_name.jpg" // Replace with the actual local image name
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("ipfsImageUrl", ipfsUrl)
            .addFormDataPart("localImageName", localImageName)
            .build()

        val request = Request.Builder()
            .url("http://your_server_url/compare") // Replace with your server URL
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@CameraActivity, "Failed to compare images", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    runOnUiThread {
                        Toast.makeText(this@CameraActivity, "Failed to compare images", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val responseJson = JSONObject(response.body!!.string())
                    val similarity = responseJson.getDouble("similarity").toFloat()
                    runOnUiThread {
                        Toast.makeText(this@CameraActivity, "Image similarity: $similarity", Toast.LENGTH_SHORT).show()
                    }
                    similarityRatio = similarity
                }
            }
        })

        return similarityRatio
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