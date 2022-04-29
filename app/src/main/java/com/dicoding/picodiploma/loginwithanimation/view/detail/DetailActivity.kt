package com.dicoding.picodiploma.loginwithanimation.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(false)
        binding.tvDetailName.text = intent.getStringExtra(EXTRA_NAME)
        binding.tvDetailDescription.text = intent.getStringExtra(EXTRA_DESCRIPTION)

        Glide.with(this)
            .load(intent.getStringExtra(EXTRA_PHOTO))
            .centerCrop()
            .into(binding.ivDetailPreview)
    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object{
        const val EXTRA_NAME = "extra name"
        const val EXTRA_DESCRIPTION = "extra description"
        const val EXTRA_PHOTO = "extra photo"
    }
}