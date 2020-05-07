package com.app.app_demo.ui_activities

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.app.app_demo.R
import com.app.app_demo.ui.main.presentor.SplashPresenter
import com.app.app_demo.utils.BaseActivity
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : BaseActivity(){
    lateinit var sPresenter: SplashPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)
        sPresenter = SplashPresenter(this)

        val animFadeIn: Animation = AnimationUtils.loadAnimation(this,
            R.anim.fade_out
        )
        animFadeIn.reset()
        val imageView = findViewById<ImageView>(R.id.home)
        imageView.clearAnimation()
        imageView.startAnimation(animFadeIn)
        val progressBar: ProgressBar = findViewById(R.id.progressBar)
        val timer = object: CountDownTimer(20000,1000){
            override fun onFinish() {}

            override fun onTick(p0: Long) {
                progressBar.progress = progressBar.progress+5
                if (progressBar.progress > 20 && userToken == 0 && userContact?.isEmpty()!!){
                    startActivity(Intent(applicationContext,  HomePageActivity::class.java))
                    this.cancel()
                    finish()
                }
            }
        }
        timer.start()
        sPresenter.checkAppTokenAvailable()
    }

    fun updateUI() {
        runOnUiThread {
            progressBar.progress = 100
            startActivity(Intent(applicationContext,  HomePageActivity::class.java))
            finish()
        }
    }

    fun showErrorMessage(message: String?) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}