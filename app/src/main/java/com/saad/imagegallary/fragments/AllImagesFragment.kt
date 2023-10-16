package com.saad.imagegallary.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
import com.saad.imagegallary.databinding.FragmentAllImagesBinding
import com.saad.imagegallary.interfaces.onClickFavroiteInterface
import com.saad.imagegallary.room.FavoriteEntity
import com.saad.imagegallary.viewModels.ImagesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllImagesFragment : Fragment(), onClickFavroiteInterface {
    private lateinit var binding: FragmentAllImagesBinding

    private lateinit var adapter: imagesAdapter
    val imageViewModel: ImagesViewModel by viewModels<ImagesViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_all_images, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.recyclerView.findViewById(R.id.recycler_reuse)

        adapter = imagesAdapter(
            emptyList(),
            emptyList(),
            emptyList(),
            false,
            this
        )
//        imageViewModel.getFav().observe(viewLifecycleOwner) {
//            adapter.updateFavData(it)
//        }

        imageViewModel.images.observe(viewLifecycleOwner) {
            adapter.updateDataImages(it.hits)
            Log.i("MyData", it.toString())
        }

        val spanCount = 2
        val layoutManager = GridLayoutManager(requireContext(), spanCount)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if ((position % 3) == 2) 2 else 1
            }
        }

        imageViewModel.getSelectedCategory().observe(viewLifecycleOwner) { selectedCategory ->
//            Toast.makeText(requireContext(), " $selectedCategory", Toast.LENGTH_SHORT).show()
            CoroutineScope(Dispatchers.IO).launch {
                imageViewModel.getImagesByCatagories(1, 20, selectedCategory)
            }

            Log.i("Tagofselected", "$selectedCategory")

            val categoryTextViews = arrayOf(
                binding.categoryFashion,
                binding.categoryNature,
                binding.categoryComputer,
                binding.categoryBusiness,
                binding.categoryFood,
                binding.categoryMusic,
                binding.categoryPlaces,
                binding.categoryScience

            )

            categoryTextViews.forEach { textView ->
                if (textView.tag == selectedCategory) {
                    textView.setTextColor(Color.WHITE)
                    textView.isSelected = true
                } else {
                    textView.setTextColor(Color.BLACK)
                    textView.isSelected = false
                }
            }
        }
        binding.categoryAll.setOnClickListener {
            imageViewModel.setSelectedCategory("")
        }
        binding.categoryFashion.setOnClickListener {
            imageViewModel.setSelectedCategory("fashion")
        }
        binding.categoryNature.setOnClickListener {
            imageViewModel.setSelectedCategory("nature")
        }
        binding.categoryMusic.setOnClickListener {
            imageViewModel.setSelectedCategory("music")
        }
        binding.categoryScience.setOnClickListener {
            imageViewModel.setSelectedCategory("science")
        }
        binding.categoryComputer.setOnClickListener {
            imageViewModel.setSelectedCategory("computer")
        }
        binding.categoryBusiness.setOnClickListener {
            imageViewModel.setSelectedCategory("business")
        }
        binding.categoryPlaces.setOnClickListener {
            imageViewModel.setSelectedCategory("places")
        }
        binding.categoryFood.setOnClickListener {
            imageViewModel.setSelectedCategory("food")
        }
    }

    override fun onClick(fav: FavoriteEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            imageViewModel.addFaviorite(fav)
        }
    }
}