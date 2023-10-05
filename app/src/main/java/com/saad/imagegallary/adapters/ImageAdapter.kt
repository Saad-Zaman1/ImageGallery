package com.saad.imagegallary.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.saad.imagegallary.R
import com.saad.imagegallary.databinding.HomeImagesViewBinding
import com.saad.imagegallary.interfaces.onClickFavroiteInterface
import com.saad.imagegallary.models.Hit
import com.saad.imagegallary.room.FavoriteEntity

class imagesAdapter(
    private var imageList: List<Hit>,
    private var favList: List<FavoriteEntity>,
    private val isFavorite: Boolean,
    private val clickFav: onClickFavroiteInterface
) : Adapter<imagesAdapter.myViewHolder>() {
    inner class myViewHolder(val binding: HomeImagesViewBinding) : ViewHolder(binding.root) {
        init {
            if (isFavorite) {
                binding.ivOverlayIcon.setOnClickListener {
                    val position = adapterPosition
                    clickFav.onClick(favList[position])
                }
            } else {
                binding.ivOverlayIcon.setOnClickListener {
                    binding.ivOverlayIcon.setImageResource(R.drawable.baseline_favorite_24)
                    val position = adapterPosition
                    val newFAvList = FavoriteEntity(
                        0,
                        imageList[position].largeImageURL,
                        imageList[position].previewURL,
                        imageList[position].comments,
                        imageList[position].likes,
                        imageList[position].views
                    )
                    clickFav.onClick(newFAvList)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        return myViewHolder(
            HomeImagesViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return if (isFavorite) {
            favList.size
        } else {
            imageList.size
        }
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        if (isFavorite) {
            holder.binding.ivOverlayIcon.setImageResource(R.drawable.baseline_favorite_24)
            val favorite = favList[position]
            Glide.with(holder.itemView.context).load(favorite.largeImageURL)
                .into(holder.binding.ivThumbnail)
            holder.binding.tvLike.text = favorite.likes.toString()
            holder.binding.tvComment.text = favorite.comments.toString()
            holder.binding.tvSee.text = favorite.views.toString()
        } else {
            val images = imageList[position]
            Glide.with(holder.itemView.context).load(images.largeImageURL)
                .into(holder.binding.ivThumbnail)
            holder.binding.tvLike.text = images.likes.toString()
            holder.binding.tvComment.text = images.comments.toString()
            holder.binding.tvSee.text = images.views.toString()
        }

    }

    fun updateDataFavorite(newData: List<FavoriteEntity>) {
        favList = newData
        notifyDataSetChanged()
    }

    fun updateDataImages(newData: List<Hit>) {
        imageList = newData
        notifyDataSetChanged()
    }

}