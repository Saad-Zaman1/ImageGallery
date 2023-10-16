package com.saad.imagegallary.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saad.imagegallary.models.Hit
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
    private val selectedCategory = MutableLiveData<String>().apply { value = "" }
    private val selectedCategor: LiveData<String>
        get() = selectedCategory

    init {
        val context = applicationContextProvider.getApplicationContext()
        if (NetworkUtils.isInternetAvailable(context)) {
            viewModelScope.launch(Dispatchers.IO) {
                val images = repository.getImagesfromDb()
                repository.deleteImagesfromDb(images)
            }
        }
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.getImages(1, 20)
//        }
        // Example  of how to collect the Flow in your ViewModel or wherever you call this function

        viewModelScope.launch {
            repository.getImages(2, 20, selectedCategory.value!!).collect { imageList ->
                repository.imagesLiveData.postValue(imageList)
            }
        }
    }

    suspend fun getImagesByCatagories(page: Int, per_page: Int, category: String) {
        repository.getImages(page, per_page, category).collect { imageList ->
            repository.imagesLiveData.postValue(imageList)
        }
    }

    val images: LiveData<ImageList>
        get() = repository.images

    fun getSelectedCategory(): LiveData<String> {
        return selectedCategory
    }

    fun setSelectedCategory(category: String) {
        selectedCategory.value = category
    }

    suspend fun addFaviorite(fav: FavoriteEntity) {
        repository.addFavorite(fav)
    }

    fun getFav(): LiveData<List<Hit>> {
        return repository.getFav()
    }

    fun getAllFavorite(): LiveData<List<FavoriteEntity>> {
        return repository.getAllFavorite()
    }

    suspend fun deleteFavorite(favimage: FavoriteEntity) {
        return repository.deleteFavorite(favimage)
    }

}