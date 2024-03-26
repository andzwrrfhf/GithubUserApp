package com.dicoding.githubappsubmission.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.githubappsubmission.R
import com.dicoding.githubappsubmission.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_REALNAME = "extra_realname"
        const val EXTRA_AVATAR = "extra_avatar_url"
        const val EXTRA_FOLLOWERS_COUNT = "extra_followers_count"
        const val EXTRA_FOLLOWING_COUNT = "extra_following_count"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_1,
            R.string.tab_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        if (username != null) {
            detailViewModel.getUserDetail(username)
        }

        detailViewModel.userdetail.observe(this) { userDetail ->
            binding.apply {
                tvUsername.text = userDetail.login
                Glide.with(this@DetailUserActivity)
                    .load(userDetail.avatarUrl)
                    .into(ivProfile)
                tvRealname.text = userDetail.name
                tvFollowercount.text = "${userDetail.followers} Followers"
                tvFollowingcount.text = "${userDetail.following} Following"
            }
        }

        val pagerAdapter = SectionPagerAdapter(this, username ?: "")
        val viewPager = binding.viewPager
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabs, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.tab_1)
                1 -> getString(R.string.tab_2)
                else -> null
            }
        }.attach()

        detailViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
