package com.example.ck_cmvvm.screen.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.example.ck_cmvvm.R
import com.example.ck_cmvvm.data.repository.SharedPreferencesRepository
import com.example.ck_cmvvm.databinding.ActivityMainBinding
import com.example.ck_cmvvm.screen.main.calendar.CalendarFragment
import com.example.ck_cmvvm.screen.main.home.HomeFragment
import com.example.ck_cmvvm.screen.main.summary.SummaryFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() = with(binding) {
        bottomNavigation.setOnNavigationItemSelectedListener(this@MainActivity)
        showFragment(HomeFragment.newInstance(), HomeFragment.TAG)
        // 앱 실행 시 메인액티비티 화면에 띄워질 초기 프래그먼트 설정. 홈프래그먼트로 지정해준다.
    }

    private fun showFragment(fragment: Fragment, tag: String){
        val findFragment = supportFragmentManager.findFragmentByTag(tag) // 태그로 어떤 프래그먼트인지 구분
        supportFragmentManager.fragments.forEach{fm -> // 우선 모든 프래그먼트를 숨김.
            supportFragmentManager.beginTransaction().hide(fm).commitAllowingStateLoss()
        }
        findFragment?.let{ // 찾은 프래그먼트가 있으면 보여주고, 없으면 새로 생성.
            supportFragmentManager.beginTransaction().show(it).commitAllowingStateLoss()
        }?:kotlin.run {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, fragment, tag)
                .commitAllowingStateLoss()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.navigation_home -> {
                showFragment(HomeFragment.newInstance(), HomeFragment.TAG)
                true
            }
            R.id.navigation_calendar -> {
                showFragment(CalendarFragment.newInstance(), CalendarFragment.TAG)
                true
            }
            R.id.navigation_summary -> {
                showFragment(SummaryFragment.newInstance(), SummaryFragment.TAG)
                true
            }
            else -> false
        }
    }

}