package com.afterclass.storyviewer

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.marginEnd
import androidx.core.view.setMargins
import com.afterclass.storyviewer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val STORY_COUNT = 5
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding!!.root
        setContentView(view)

        val layoutProgressBars = binding.layoutProgressBars

        val displayMatrics = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(displayMatrics)
        Log.d("TAG",displayMatrics.widthPixels.toString())

        var i =1

        while(i<=5){

            val storyBarLayout = FrameLayout(this)
            var paramsFrameLayout = storyBarLayout.layoutParams
            val layoutParamsFrameLayout = ViewGroup.LayoutParams(((displayMatrics.widthPixels/STORY_COUNT)-10),ViewGroup.LayoutParams.MATCH_PARENT)
            paramsFrameLayout=layoutParamsFrameLayout
            storyBarLayout.layoutParams=paramsFrameLayout


            val storyBar1=View(this)
            var paramsStory1 = storyBar1.layoutParams
            val layoutParamsStory1 = ViewGroup.LayoutParams(((displayMatrics.widthPixels/STORY_COUNT)-10),ViewGroup.LayoutParams.MATCH_PARENT)
            paramsStory1 = layoutParamsStory1
            storyBar1.layoutParams=paramsStory1
            storyBar1.setBackgroundColor(Color.WHITE)

            val storyBar2=View(this)
            var paramsStory2 = storyBar2.layoutParams
            val layoutParamsStory2 = ViewGroup.LayoutParams(((displayMatrics.widthPixels/STORY_COUNT)-10),ViewGroup.LayoutParams.MATCH_PARENT)
            paramsStory2= layoutParamsStory2
            storyBar2.layoutParams=paramsStory2
            storyBar2.scaleX=0F
            storyBar2.setBackgroundColor(Color.RED)


            val storyGap = View(this)
            var paramsGap = storyGap.layoutParams
            val layoutParamsGap = ViewGroup.LayoutParams(10,ViewGroup.LayoutParams.MATCH_PARENT)
            storyGap.layoutParams=layoutParamsGap
            storyGap.setBackgroundColor(Color.BLACK)

            storyBarLayout.addView(storyBar1)
            storyBarLayout.addView(storyBar2)


            layoutProgressBars.addView(storyBarLayout)
            layoutProgressBars.addView(storyGap)

            storyBar2.pivotX=0F
            storyBar2.pivotY=0F
            storyBar2.animate().scaleX(1F).setStartDelay(((i-1)*1600).toLong()).setDuration(2000).start()

            i++
        }


    }
}