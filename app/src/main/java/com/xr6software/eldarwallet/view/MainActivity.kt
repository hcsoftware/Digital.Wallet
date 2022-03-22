package com.xr6software.eldarwallet.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.xr6software.eldarwallet.R
import com.xr6software.eldarwallet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        val view : View = viewBinding.root
        setContentView(view)

        supportActionBar?.hide()

        startAnimation()

    }

    private fun startAnimation() {

        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.logo_animation)
        viewBinding.maImageviewLogo.startAnimation(animation)

        animation.setAnimationListener(object: Animation.AnimationListener{
            override fun onAnimationStart(animation: Animation?) {
                //TODO("Not yet implemented")
            }

            override fun onAnimationEnd(animation: Animation?) {

                startActivity(Intent(applicationContext, LoginActivity::class.java))
                finish()
            }

            override fun onAnimationRepeat(animation: Animation?) {
                //TODO("Not yet implemented")
            }
        })

    }
}