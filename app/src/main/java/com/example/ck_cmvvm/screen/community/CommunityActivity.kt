package com.example.ck_cmvvm.screen.community

import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ck_cmvvm.databinding.ActivityCommunityBinding
import com.example.ck_cmvvm.screen.base.BaseActivity
import com.example.ck_cmvvm.screen.community.create.CreateCommunityActivity
import com.example.ck_cmvvm.screen.community.detail.CommunityDetailActivity
import com.example.ck_cmvvm.widget.adapter.CommunityAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class CommunityActivity: BaseActivity<CommunityViewModel, ActivityCommunityBinding>() {

    override val viewModel by viewModel<CommunityViewModel>()

    override fun getViewBinding(): ActivityCommunityBinding = ActivityCommunityBinding.inflate(layoutInflater)

    private val adapter by lazy {
        CommunityAdapter( onItemClicked = { communityModel ->
            val intent = Intent(this, CommunityDetailActivity::class.java)
            intent.putExtra("title", communityModel.title)
            intent.putExtra("content", communityModel.content)
            intent.putExtra("uri", communityModel.uri)
            intent.putExtra("time", communityModel.time)
            intent.putExtra("name", communityModel.name)
            startActivity(intent)
        })
    }

    override fun observeData() = viewModel.communityState.observe(this) {
        Log.d("community", it.toString())
        adapter.submitList(it)
    }

    override fun initViews() = with(binding){
        super.initViews()

        viewModel.fetchCommunity()
        communityRecyclerView.layoutManager = LinearLayoutManager(this@CommunityActivity)
        communityRecyclerView.adapter = adapter

        createFloatButton.setOnClickListener {
            val intent = Intent(this@CommunityActivity, CreateCommunityActivity::class.java)
            startActivity(intent)
        }

        backButton.setOnClickListener {
            finish()
        }
    }
}