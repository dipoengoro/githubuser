package id.dipoengoro.githubuser.activity

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import id.dipoengoro.githubuser.R
import id.dipoengoro.githubuser.adapter.formatDetailUser
import id.dipoengoro.githubuser.adapter.parcelable
import id.dipoengoro.githubuser.databinding.ActivityDetailBinding
import id.dipoengoro.githubuser.model.User
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        Log.d("DetailActivity", "onCreate: ${applicationInfo.packageName}")
        intent.parcelable<User>("user")?.also {
            supportActionBar?.title = it.username.lowercase()
            binding.apply {
                user = it
                buttonShare.setOnClickListener { _ ->
                    val shareText = formatDetailUser(it)
                    try {
                        Intent().let { shareIntent ->
                            val uri = getImageToShare(binding.userImage, it)
                            shareIntent.action = Intent.ACTION_SEND
                            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText)
                            shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
                            shareIntent.type = "image/png"
                            Log.d("Uri", "onCreate: $uri")
                            startActivity(Intent.createChooser(shareIntent, "Share via"))
                        }
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
                buttonBack.setOnClickListener { finish() }
            }
        }
    }

    private fun getImageToShare(image: ImageView, user: User): Uri? {
        val bitmap = image.drawable.toBitmap()
        val imageFolder = File(cacheDir, "images")
        var uri: Uri? = null
        try {
            imageFolder.mkdirs()
            val file = File(imageFolder, "${user.username}.jpg")
            val fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()
            uri = FileProvider.getUriForFile(this, "id.dipoengoro.githubuser", file)
        } catch (e: Exception) {
            Log.d("DetailActivity", "getImageToShare: ${e.message}")
        }
        return uri
    }
}