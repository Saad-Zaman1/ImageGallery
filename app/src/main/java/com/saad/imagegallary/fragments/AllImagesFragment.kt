package com.saad.imagegallary.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saad.imagegallary.R
import com.saad.imagegallary.activities.ImageDetailActivity
import com.saad.imagegallary.adapters.imagesAdapter
import com.saad.imagegallary.databinding.FragmentAllImagesBinding
import com.saad.imagegallary.interfaces.onClickFavroiteInterface
import com.saad.imagegallary.models.Hit
import com.saad.imagegallary.room.FavoriteEntity
import com.saad.imagegallary.viewModels.ImagesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class AllImagesFragment : Fragment(), onClickFavroiteInterface {
    private lateinit var binding: FragmentAllImagesBinding

    private lateinit var adapter: imagesAdapter
    val imageViewModel: ImagesViewModel by viewModels()
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

        binding.recyclerView.recyclerReuse.visibility = View.GONE
        binding.shimmerLayout.startShimmerAnimation()


        val recyclerView: RecyclerView = binding.recyclerView.recyclerReuse

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
        imageViewModel.getFav().observe(viewLifecycleOwner) {
            Log.i("FavoriteListing", "$it")
            adapter.updateFavoriteList(it)
        }
        imageViewModel.images.observe(viewLifecycleOwner) {
            adapter.updateDataImages(it.hits)
            binding.shimmerLayout.stopShimmerAnimation()
            binding.shimmerLayout.visibility = View.GONE
            binding.recyclerView.recyclerReuse.visibility = View.VISIBLE
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
            CoroutineScope(Dispatchers.IO).launch {
                imageViewModel.getImagesByCatagories(1, 20, selectedCategory)
            }

            val categoryTextViews = arrayOf(
                binding.categoryAll,
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
            binding.recyclerView.recyclerReuse.visibility = View.GONE
            binding.shimmerLayout.visibility = View.VISIBLE
            binding.shimmerLayout.startShimmerAnimation()
            imageViewModel.setSelectedCategory("")
        }
        binding.categoryFashion.setOnClickListener {
            binding.recyclerView.recyclerReuse.visibility = View.GONE
            binding.shimmerLayout.visibility = View.VISIBLE
            binding.shimmerLayout.startShimmerAnimation()
            imageViewModel.setSelectedCategory("fashion")
        }
        binding.categoryNature.setOnClickListener {
            binding.recyclerView.recyclerReuse.visibility = View.GONE
            binding.shimmerLayout.visibility = View.VISIBLE
            binding.shimmerLayout.startShimmerAnimation()
            imageViewModel.setSelectedCategory("nature")
        }
        binding.categoryMusic.setOnClickListener {
            binding.recyclerView.recyclerReuse.visibility = View.GONE
            binding.shimmerLayout.visibility = View.VISIBLE
            binding.shimmerLayout.startShimmerAnimation()
            imageViewModel.setSelectedCategory("music")
        }
        binding.categoryScience.setOnClickListener {
            binding.recyclerView.recyclerReuse.visibility = View.GONE
            binding.shimmerLayout.visibility = View.VISIBLE
            binding.shimmerLayout.startShimmerAnimation()
            imageViewModel.setSelectedCategory("science")
        }
        binding.categoryComputer.setOnClickListener {
            binding.recyclerView.recyclerReuse.visibility = View.GONE
            binding.shimmerLayout.visibility = View.VISIBLE
            binding.shimmerLayout.startShimmerAnimation()
            imageViewModel.setSelectedCategory("computer")
        }
        binding.categoryBusiness.setOnClickListener {
            binding.recyclerView.recyclerReuse.visibility = View.GONE
            binding.shimmerLayout.visibility = View.VISIBLE
            binding.shimmerLayout.startShimmerAnimation()
            imageViewModel.setSelectedCategory("business")
        }
        binding.categoryPlaces.setOnClickListener {
            binding.recyclerView.recyclerReuse.visibility = View.GONE
            binding.shimmerLayout.visibility = View.VISIBLE
            binding.shimmerLayout.startShimmerAnimation()
            imageViewModel.setSelectedCategory("places")
        }
        binding.categoryFood.setOnClickListener {
            binding.recyclerView.recyclerReuse.visibility = View.GONE
            binding.shimmerLayout.visibility = View.VISIBLE
            binding.shimmerLayout.startShimmerAnimation()
            imageViewModel.setSelectedCategory("food")
        }


    }

    override fun onClick(fav: FavoriteEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            val isFav = imageViewModel.checkFavorite(fav.id)

            withContext(Dispatchers.Main) {
                if (isFav == null) {
                    imageViewModel.addFaviorite(fav)
                    Toast.makeText(
                        requireContext(),
                        "Added to favorites",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "This item is already a favorite",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
        }
    }


    override fun onClickDetails(image: Hit) {
        val intent = Intent(requireContext(), ImageDetailActivity::class.java)
        Log.i("ImagesData", "${image.likes}")
        intent.putExtra("imageDetails", image)
        startActivity(intent)
    }
}