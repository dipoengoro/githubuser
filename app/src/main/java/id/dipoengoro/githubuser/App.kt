package id.dipoengoro.githubuser

import android.app.Application
import android.content.Context

class App : Application() {
    private var context: Context? = null

    override fun onCreate() {
        super.onCreate()
        context?.applicationContext
    }

}
