package com.example.ck_cmvvm.screen.community.detail

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.ck_cmvvm.data.repository.SharedPreferencesRepository
import com.example.ck_cmvvm.databinding.ActivityCommunityDetailBinding
import com.example.ck_cmvvm.screen.base.BaseActivity
import com.example.ck_cmvvm.widget.adapter.ChatAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CommunityDetailActivity: BaseActivity<CommunityDetailViewModel, ActivityCommunityDetailBinding>() {

    private val adapter = ChatAdapter()

    private val sharedPreferencesRepository: SharedPreferencesRepository by lazy {
        SharedPreferencesRepository(this)
    }

    override val viewModel by viewModel<CommunityDetailViewModel>()

    override fun getViewBinding(): ActivityCommunityDetailBinding = ActivityCommunityDetailBinding.inflate(layoutInflater)

    override fun observeData() = viewModel.chatState.observe(this){
        adapter.submitList(it)
    }

    @SuppressLint("SimpleDateFormat")
    override fun initViews() = with(binding){
        super.initViews()

        val userInfo = sharedPreferencesRepository.getUserInfo()
        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        val uri = intent.getStringExtra("uri")
        val time = intent.getLongExtra("time", 0)
        val name = intent.getStringExtra("name")
        val timeChat = System.currentTimeMillis()

        val data = Date(time)

        val postDate = SimpleDateFormat("MM/dd", Locale.getDefault())
        val postTime = SimpleDateFormat("hh:mm", Locale.getDefault())

        profileName.text = name
        titleTextView.text = title
        contentTextView.text = content
        Glide.with(imageView)
            .load(uri)
            .centerCrop()
            .into(imageView)
        dateTextView.text = postDate.format(data)
        timeTextView.text = postTime.format(data)

        chatRecyclerView.layoutManager = LinearLayoutManager(this@CommunityDetailActivity)
        chatRecyclerView.adapter = adapter
        viewModel.fetchChat(time.toString())

        chatButton.setOnClickListener {
            val chat = chatEditText.text.toString()
            val chatname = userInfo.userName
            viewModel.uploadChat(chatname!!, chat, timeChat, time.toString())
            viewModel.fetchChat(time.toString())
            chatEditText.text = null
            closeKeyboard(this@CommunityDetailActivity)
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("ServiceCast")
    fun closeKeyboard(activity: Activity) {
        val view = activity.currentFocus
        if (view != null) {
            val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}