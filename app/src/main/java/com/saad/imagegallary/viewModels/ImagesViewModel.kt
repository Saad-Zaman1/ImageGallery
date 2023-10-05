package com.saad.imagegallary.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saad.imagegallary.models.ImageList
import com.saad.imagegallary.repository.repository
import com.saad.imagegallary.room.FavoriteEntity
import com.saad.imagegallary.utils.ApplicationContextProvider
import com.saad.imagegallary.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val applicationContextProvider: ApplicationContextProvider,
    private val repository: repository
) :
    ViewModel() {

    //to do the request as soon as the viewModel loads
    init {
        val context = applicationContextProvider.getApplicationContext()
        if (NetworkUtils.isInternetAvailable(context)) {
            viewModelScope.launch(Dispatchers.IO) {
                val images = repository.getImagesfromDb()
                repository.deleteImagesfromDb(images)
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.getImages(1, 20)
        }

    }


    val images: LiveData<ImageList>
        get() = repository.images


    suspend fun addFaviorite(fav: FavoriteEntity) {
        repository.addFavorite(fav)
    }

    fun getAllFavorite(): LiveData<List<FavoriteEntity>> {
        return repository.getAllFavorite()
    }

    suspend fun deleteFavorite(favimage: FavoriteEntity) {
        return repository.deleteFavorite(favimage)
    }

}