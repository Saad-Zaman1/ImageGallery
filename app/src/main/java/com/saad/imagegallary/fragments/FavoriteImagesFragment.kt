package com.saad.imagegallary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saad.imagegallary.R
import com.saad.imagegallary.adapters.imagesAdapter
import com.saad.imagegallary.databinding.FragmentFavoriteImagesBinding
import com.saad.imagegallary.interfaces.onClickFavroiteInterface
import com.saad.imagegallary.room.FavoriteEntity
import com.saad.imagegallary.viewModels.ImagesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteImagesFragment : Fragment(), onClickFavroiteInterface {
    private lateinit var binding: FragmentFavoriteImagesBinding
    private val imageViewModel: ImagesViewModel by viewModels<ImagesViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_favorite_images,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView =
            binding.recyclerViewFavorite.findViewById(R.id.recycler_reuse)
        val adapter = imagesAdapter(
            emptyList(),
            emptyList(),
            true,
            this
        )
        recyclerView.adapter = adapter
        imageViewModel.getAllFavorite().observe(viewLifecycleOwner) {
            adapter.updateDataFavorite(it)
        }
        val spanCount = 2
        val layoutManager = GridLayoutManager(requireContext(), spanCount)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if ((position % 3) == 2) 2 else 1
            }
        }
    }

    override fun onClick(fav: FavoriteEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            imageViewModel.deleteFavorite(fav)
        }
    }
}