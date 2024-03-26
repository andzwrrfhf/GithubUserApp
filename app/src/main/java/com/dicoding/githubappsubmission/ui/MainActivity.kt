package com.dicoding.githubappsubmission.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubappsubmission.R
import com.dicoding.githubappsubmission.data.response.ItemsItem
import com.dicoding.githubappsubmission.data.response.UserResponse
import com.dicoding.githubappsubmission.data.retrofit.ApiConfig
import com.dicoding.githubappsubmission.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    val adapter = UserAdapter()

    companion object {
        private const val USERNAME = "arif"
        private const val TAG = "MainViewModel"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.adapter = adapter

        mainViewModel.searchUser("Arif")

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        mainViewModel.userlist.observe(this) { userList ->
            setUserList(userList)
        }

        mainViewModel.isLoading.observe(this){ isLoading ->
            showLoading(isLoading)
        }

        getUserList()

        with(binding){
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.hint = searchView.text
                    searchView.hide()
                    if(actionId == EditorInfo.IME_ACTION_SEARCH){
                        val query = searchView.text.toString()
                        mainViewModel.searchUser(query)
                        return@setOnEditorActionListener true
                    }
                    false
                }
        }

    }

    private fun setUserList(userList: List<ItemsItem>) {
        adapter.submitList(userList)
        binding.rvUser.adapter = adapter
    }

    private fun getUserList() {
        showLoading(true)
        val client = ApiConfig.getApiService().getUser(USERNAME)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
