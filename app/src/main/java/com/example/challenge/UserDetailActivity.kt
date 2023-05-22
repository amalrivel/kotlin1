package com.example.challenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.challenge.adapter.SectionsPagerAdapter
import com.example.challenge.databinding.ActivityUserDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var viewModel: MainViewModel

    companion object {
        const val EXTRA_USER = "extra_user"
        var username = String()

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.detail_user)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        username = intent.getStringExtra(EXTRA_USER).toString()
        if (username != null) {
            viewModel.detailUser(username)
        }

        viewModel.userDetail.observe(this) { _userDetail ->
            Glide.with(this)
                .load(_userDetail.avatarUrl)
                .skipMemoryCache(true)
                .into(binding.imgAvatar)

            binding.tvAccount.text = _userDetail.name
            binding.tvUsername.text = _userDetail.login
            val followerCount = _userDetail.followers
            val followingCount = _userDetail.following
            binding.tvFollower.text = getString(R.string.user_follower, followerCount.toString())
            binding.tvFollowing.text = getString(R.string.user_following, followingCount.toString())
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}