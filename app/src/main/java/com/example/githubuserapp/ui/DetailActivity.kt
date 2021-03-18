package com.example.githubuserapp.ui


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.githubuserapp.model.User
import com.example.githubuserapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var actionBar = supportActionBar

        actionBar?.title = "Detail"
        actionBar?.setDisplayHomeAsUpEnabled(true)


        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User


        Glide.with(this).load(user.avatar).circleCrop().into(binding.imgDetail)
        binding.tvFollowerDetail.text = user.follower.toString()
        binding.tvFollowingDetail.text = user.following.toString()
        binding.tvUsernameDetail.text = user.username
        binding.tvNameDetail.text = user.name
        binding.tvCompanyDetail.text = user.company
        binding.tvLocationDetail.text = user.location
        binding.tvRepositoryDetail.text = user.repository

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}