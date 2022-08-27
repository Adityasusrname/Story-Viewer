package com.afterclass.storyviewer

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.marginEnd
import androidx.core.view.setMargins
import androidx.viewpager2.widget.ViewPager2
import com.afterclass.storyviewer.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private val STORY_COUNT = 5
    private lateinit var binding:ActivityMainBinding
    private val handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding!!.root
        setContentView(view)

        val layoutProgressBars = binding.layoutProgressBars

        val displayMatrics = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(displayMatrics)
        Log.d("TAG",displayMatrics.widthPixels.toString())

        val stories : List<Image> = listOf(Image(R.drawable.story_1), Image(R.drawable.story_2), Image(R.drawable.story_3), Image(R.drawable.story_4),
            Image(R.drawable.story_5)
        )
        val vpStoryPictures = binding.vpStoryPictures
        val adapter = StoryViewPagerAdapter()
        vpStoryPictures.adapter=adapter
        adapter.submitList(stories)
        vpStoryPictures.registerOnPageChangeCallback(StoryPageChangeCallback)

        var i =1

        while(i<=5){

            val storyBarLayout = FrameLayout(this)
            var paramsFrameLayout = storyBarLayout.layoutParams
            val layoutParamsFrameLayout = ViewGroup.LayoutParams(((displayMatrics.widthPixels/STORY_COUNT)-10),ViewGroup.LayoutParams.MATCH_PARENT)
            paramsFrameLayout=layoutParamsFrameLayout
            storyBarLayout.layoutParams=paramsFrameLayout
            storyBarLayout.id=i*10


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
            storyBar2.id=i


            val storyGap = View(this)
            var paramsGap = storyGap.layoutParams
            val layoutParamsGap = ViewGroup.LayoutParams(10,ViewGroup.LayoutParams.MATCH_PARENT)
            storyGap.layoutParams=layoutParamsGap
            storyGap.setBackgroundColor(Color.BLACK)

            storyBarLayout.addView(storyBar1)
            storyBarLayout.addView(storyBar2)


            layoutProgressBars.addView(storyBarLayout)
            layoutProgressBars.addView(storyGap)

         /*   storyBar2.pivotX=0F
            storyBar2.pivotY=0F
            storyBar2.animate().scaleX(1F).setStartDelay(((i-1)*1600).toLong()).setDuration(2000).start()*/

            i++
        }

    }

    private val nextPageRunnable = Runnable {
        binding.vpStoryPictures.currentItem++
    }

    private val StoryPageChangeCallback=object : ViewPager2.OnPageChangeCallback(){
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)

            val frameLayout=binding.layoutProgressBars.findViewById<FrameLayout>((position+1)*10)
            val view=frameLayout.findViewById<View>(position+1)

            var view1:View?=null
            var view2:View?=null

            if((position+2)<=5){

                val frameLayout2=binding.layoutProgressBars.findViewById<FrameLayout>((position+2)*10)
                view2=frameLayout2.findViewById<View>(position+2)
            }
            if(position>=1)
            {

                val frameLayout1=binding.layoutProgressBars.findViewById<FrameLayout>((position)*10)
                view1=frameLayout1.findViewById<View>(position)
            }

            view.pivotX=0F
            view.pivotY=0F
            view.scaleX=0F

            view2?.let {
                it.animate().cancel()
                it.scaleX=0F

            }

            view1?.let {
                it.animate().cancel()
                it.scaleX=1F
                Log.d("TAG",it.id.toString())
            }

            handler.removeCallbacks(nextPageRunnable)
            view.animate().scaleX(1F).setDuration(5000).setStartDelay(10).start()
            handler.postDelayed(nextPageRunnable,5000)


        }
    }







}