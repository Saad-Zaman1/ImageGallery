package com.saad.imagegallary.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.saad.imagegallary.R
import com.saad.imagegallary.databinding.ActivityImageDetailBinding
import com.saad.imagegallary.models.Hit
import com.saad.imagegallary.viewModels.ImagesViewModel

const val TAG = "ImageDetailActivityView"

class ImageDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageDetailBinding
    private val imageViewModel: ImagesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_detail)
        val intent: Intent = intent
        val data = intent.getSerializableExtra("imageDetails") as Hit

        Glide.with(this).load(data.largeImageURL).placeholder(R.drawable.baseline_photo_24)
            .into(binding.ivImage)
        if (data != null) {
            binding.hit = data
        } else {
            Log.i(TAG, "Data is null")
        }

    }
}