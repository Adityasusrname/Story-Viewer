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
            paramsFrameLayout=ViewGroup.LayoutParams(((displayMatrics.widthPixels/STORY_COUNT)-10),ViewGroup.LayoutParams.MATCH_PARENT)
            storyBarLayout.layoutParams=paramsFrameLayout
            storyBarLayout.id=i*10                //Assigning id to each frame layout


            val storyBarBelow=View(this)
            var paramsStoryBarBelow = storyBarBelow.layoutParams
            paramsStoryBarBelow = ViewGroup.LayoutParams(((displayMatrics.widthPixels/STORY_COUNT)-10),ViewGroup.LayoutParams.MATCH_PARENT)
            storyBarBelow.layoutParams=paramsStoryBarBelow
            storyBarBelow.setBackgroundColor(Color.WHITE)

            val storyBarAbove=View(this)
            var paramsStoryBarAbove = storyBarAbove.layoutParams
            paramsStoryBarAbove= ViewGroup.LayoutParams(((displayMatrics.widthPixels/STORY_COUNT)-10),ViewGroup.LayoutParams.MATCH_PARENT)
            storyBarAbove.layoutParams=paramsStoryBarAbove
            storyBarAbove.scaleX=0F
            storyBarAbove.setBackgroundColor(Color.RED)
            storyBarAbove.id=i                         //Assigning id to each storyBarAbove


            val storyGap = View(this)
            var paramsGap = storyGap.layoutParams
            storyGap.layoutParams= ViewGroup.LayoutParams(10,ViewGroup.LayoutParams.MATCH_PARENT)
            storyGap.setBackgroundColor(Color.BLACK)

            storyBarLayout.addView(storyBarBelow)
            storyBarLayout.addView(storyBarAbove)


            layoutProgressBars.addView(storyBarLayout)
            layoutProgressBars.addView(storyGap)

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

            var viewPrevious:View?=null
            var viewNext:View?=null

            if((position+2)<=5){  //Trying to access the view next to the current view

                val frameLayout2=binding.layoutProgressBars.findViewById<FrameLayout>((position+2)*10)
                viewNext=frameLayout2.findViewById<View>(position+2)
            }
            if(position>=1) //Trying to access the view previous to the current view
            {

                val frameLayout1=binding.layoutProgressBars.findViewById<FrameLayout>((position)*10)
                viewPrevious=frameLayout1.findViewById<View>(position)
            }

            view.pivotX=0F
            view.pivotY=0F
            view.scaleX=0F

            viewNext?.let {
                it.animate().cancel()
                it.scaleX=0F

            }

            viewPrevious?.let {
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