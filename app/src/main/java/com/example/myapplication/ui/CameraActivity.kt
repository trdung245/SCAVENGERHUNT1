package com.example.myapplication.ui

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
import com.example.myapplication.data.ipfs.IPFSUtils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.file.Files
import java.util.Arrays

class CameraActivity : AppCompatActivity() {

    private lateinit var ivUser: ImageView
    private lateinit var btnTakePicture: Button
    private lateinit var backButton: ImageButton

    private lateinit var takePictureLauncher: ActivityResultLauncher<Void?>

    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 1
        private const val REQUEST_CODE = 1001
    }

    private val client = OkHttpClient()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera_activity)

        ivUser = findViewById(R.id.ivUser)
        btnTakePicture = findViewById(R.id.btnTakePicture)
        backButton = findViewById(R.id.backButton)

        takePictureLauncher = registerForActivityResult(
            ActivityResultContracts.TakePicturePreview()
        ) { bitmap: Bitmap? ->
            bitmap?.let {
                ivUser.setImageBitmap(it)
                val fileUri = saveBitmapToFile(this, it, "captured_image")
                fileUri?.let { uri ->
                    Toast.makeText(this, "Image saved to: $uri", Toast.LENGTH_LONG).show()
                    uploadImageToIPFS(fileUri)
                }
            }
        }

        btnTakePicture.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
            } else {
                takePictureLauncher.launch(null)
            }
        }

        backButton.setOnClickListener {
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun saveBitmapToFile(context: Context, bitmap: Bitmap, capturedImage: String): Uri? {
        val imagesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        if (imagesDir != null && !imagesDir.exists()) {
            imagesDir.mkdirs()
        }

        val imageFile = File(imagesDir, "$capturedImage.jpg")
        return try {
            FileOutputStream(imageFile).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
                out.flush()
            }
            FileProvider.getUriForFile(context, "com.example.myapplication.fileprovider", imageFile)
        } catch (e : IOException) {
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

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@CameraActivity, "Failed to upload image to IPFS", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                if (!response.isSuccessful) {
                    runOnUiThread {
                        Toast.makeText(this@CameraActivity, "Failed to upload image to IPFS", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val responseJson = JSONObject(response.body!!.string())
                    val ipfsUrl = responseJson.getString("IpfsHash")
                    compareImages(ipfsUrl, "local_image_name.jpg") // Replace "local_image_name.jpg" with actual image name
                }
            }
        })
    }

    private fun compareImages(ipfsUrl: String, localImageName: String) {
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("ipfsImageUrl", ipfsUrl)
            .addFormDataPart("localImageName", localImageName)
            .build()

        val request = Request.Builder()
            .url("http://localhost:3000") // Replace with your server URL
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@CameraActivity, "Failed to compare images", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                if (!response.isSuccessful) {
                    runOnUiThread {
                        Toast.makeText(this@CameraActivity, "Failed to compare images", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val responseJson = JSONObject(response.body!!.string())
                    val similarity = responseJson.getDouble("similarity")
                    runOnUiThread {
                        Toast.makeText(this@CameraActivity, "Image similarity: $similarity", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }


    private fun verifyUpload(originalContent: ByteArray, fileHash: String) {
        Thread {
            try {
                val downloadedContent = IPFSUtils.downloadFile(fileHash)
                val isMatch = Arrays.equals(originalContent, downloadedContent)
                runOnUiThread {
                    if (isMatch) {
                        Toast.makeText(this, "Upload verified successfully", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "Upload verification failed", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this, "Failed to verify upload", Toast.LENGTH_LONG).show()
                }
            }
        }.start()
    }

    private fun getUriForFile(file: File): Uri {
        val authority = "com.example.myapplication.fileprovider"
        return FileProvider.getUriForFile(this, authority, file)
    }

    private fun shareFile(file: File) {
        val fileUri = getUriForFile(file)
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "application/pdf"
            putExtra(Intent.EXTRA_STREAM, fileUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(Intent.createChooser(intent, "Share file via"))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let {
                // Use the URI as needed
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CAMERA_PERMISSION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    takePictureLauncher.launch(null)
                } else {
                    Toast.makeText(this, "Camera permission is required to use this feature", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
