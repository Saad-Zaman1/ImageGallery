package com.saad.imagegallary.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.saad.imagegallary.fragments.AllImagesFragment
import com.saad.imagegallary.fragments.FavoriteImagesFragment

class VPadapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0)
            AllImagesFragment()
        else
            FavoriteImagesFragment()
    }
}