package com.example.ck_cmvvm.widget.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.ck_cmvvm.R
import com.example.ck_cmvvm.screen.main.home.question.compilation.QuestionCompilationActivity

class CustomPagerAdapter(private val context: Context, private val items: List<PageItem>) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.viewholder_home_viewpager, container, false) as ViewGroup

        val imageView = layout.findViewById<ImageView>(R.id.homeQuestionImage)
        val textView = layout.findViewById<TextView>(R.id.homeQuestionText)

        val item = items[position]
        imageView.setImageResource(item.imageResId)
        textView.text = item.text

        imageView.setOnClickListener {
            val intent = Intent(context, QuestionCompilationActivity::class.java)
            when(position){
                0 -> intent.putExtra("difficulty", "쉬움")
                1 -> intent.putExtra("difficulty", "보통")
            }
            context.startActivity(intent)
        }

        container.addView(layout)
        return layout
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return items.size
    }
}

data class PageItem(val imageResId: Int, val text: String)