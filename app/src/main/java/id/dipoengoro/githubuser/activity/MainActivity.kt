package id.dipoengoro.githubuser.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import id.dipoengoro.githubuser.R
import id.dipoengoro.githubuser.adapter.ListUserAdapter
import id.dipoengoro.githubuser.databinding.ActivityMainBinding
import id.dipoengoro.githubuser.model.ListUser
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            recyclerView.adapter = ListUserAdapter(ListUserAdapter.OnClickListener {
                startActivity(Intent(this@MainActivity, DetailActivity::class.java).apply {
                    putExtra("user", it)
                })
            })
            binding.listUser = parseJson()
        }
    }

    private fun readFromAsset(): String? {
        val fileName = "githubuser.json"
        val json: String?
        try {
            val inputStream: InputStream = assets.open(fileName)
            json = inputStream.bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return json
    }

    private fun parseJson(): ListUser = Gson().fromJson(readFromAsset(), ListUser::class.java)
}