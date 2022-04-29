package com.dicoding.picodiploma.loginwithanimation.view.main

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.api.ApiConfig
import com.dicoding.picodiploma.loginwithanimation.api.GetAllStoriesResponse
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityMainBinding
import com.dicoding.picodiploma.loginwithanimation.model.UserPreference
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.detail.DetailActivity
import com.dicoding.picodiploma.loginwithanimation.view.login.LoginViewModel
import com.dicoding.picodiploma.loginwithanimation.view.story.AdapterStory
import com.dicoding.picodiploma.loginwithanimation.view.story.AddStoryActivity
import com.dicoding.picodiploma.loginwithanimation.view.story.ListStory
import com.dicoding.picodiploma.loginwithanimation.view.welcome.WelcomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var storyAdapter: AdapterStory
    private val _getStory: MutableLiveData<List<ListStory>> = MutableLiveData()
    private val getStory: LiveData<List<ListStory>> = _getStory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storyAdapter = AdapterStory(listOf())

        storyAdapter.setOnItemClickCallback(object: AdapterStory.OnItemClickCallback{
            override fun onItemClick(result: ListStory) {
//                Toast.makeText(this@MainActivity, result.name, Toast.LENGTH_SHORT).show()
                Intent(this@MainActivity, DetailActivity::class.java).apply {
                    this.putExtra(DetailActivity.EXTRA_NAME, result.name)
                    this.putExtra(DetailActivity.EXTRA_DESCRIPTION, result.description)
                    this.putExtra(DetailActivity.EXTRA_PHOTO, result.photoUrl)
                    startActivity(this)
                }
            }
        })

        binding.btnAddStory.setOnClickListener { addStory() }

        setupView()
        setupViewModel()
        setupListStories()
        getStoryFromApi()
    }

    private fun addStory() {
        val intent = Intent(this, AddStoryActivity::class.java)
        startActivity(intent)
    }

    private fun getStoryFromApi() {
        showLoading(true)
        loginViewModel.getToken().observe(this){ token ->

        ApiConfig.instances.getAllStories("Bearer $token").enqueue(object : Callback<GetAllStoriesResponse>{
            override fun onResponse(call: Call<GetAllStoriesResponse>, response: Response<GetAllStoriesResponse>) {
                showLoading(false)
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null){
                        _getStory.value = result.listStory
                        getAllStory()
                    } else {
                        Toast.makeText(this@MainActivity, "Tidak ada story", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetAllStoriesResponse>, t: Throwable) {
                showLoading(false)
                Log.d("Story", "Failed ambil story")
                Toast.makeText(this@MainActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })}
    }

    private fun getAllStory() {
        getStory.observe(this){ story ->
//            storyAdapter = AdapterStory(story)
            storyAdapter.setStories(story)
            binding.rvListStories.adapter = storyAdapter
        }
    }

    private fun setupListStories() {
        binding.apply {
            rvListStories.layoutManager = LinearLayoutManager(this@MainActivity)
            rvListStories.setHasFixedSize(true)
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(this, ViewModelFactory(UserPreference.getInstance(dataStore)))[MainViewModel::class.java]
        loginViewModel = ViewModelProvider(this, ViewModelFactory(UserPreference.getInstance(dataStore)))[LoginViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu1 -> {
                loginViewModel.setLoginData("", "", false)
                val intent = Intent(this, WelcomeActivity::class.java)
                startActivity(intent)
            }
            R.id.menu2 -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}