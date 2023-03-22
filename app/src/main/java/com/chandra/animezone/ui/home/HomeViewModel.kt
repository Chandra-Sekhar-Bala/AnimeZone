package com.chandra.animezone.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chandra.animezone.CONSTANTS
import com.chandra.animezone.models.AnimeList
import com.chandra.animezone.models.PopularResponse
import com.chandra.animezone.models.Response
import com.chandra.animezone.repository.network.AnimeAPI
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hilt_aggregated_deps._dagger_hilt_android_internal_lifecycle_HiltWrapper_DefaultViewModelFactories_ActivityModule
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val api: AnimeAPI) : ViewModel() {
    private var _topAnimeList = MutableLiveData<List<Response>>()
    val topAnimeList: LiveData<List<Response>>
        get() = _topAnimeList

    private var _popularAnimeList = MutableLiveData<List<SlideModel>>()
    val popularAnimeList: LiveData<List<SlideModel>>
        get() = _popularAnimeList
    private var pageNumber = 1
    private var lockTopAnimeCall = false
    private var hasNextPage = true

    init {
        getPopularAnime()
        getTopAnime()
    }
    private fun getPopularAnime() {
        viewModelScope.launch {
            try {
                val response = api.getPopularAnime()
                val imageList = ArrayList<SlideModel>()
                for (item in response.data!!) {
                    imageList.add(
                        SlideModel(
                            item.entry!!.images!!.jpg!!.largeImageUrl,
                            item.entry.title
                        )
                    )
                }
                _popularAnimeList.value = imageList
            } catch (e: Exception) {
                Log.d(CONSTANTS.TAG, "getPopularAnime error: ${e.message}")
            }
        }
    }

    private fun getTopAnime() {
        viewModelScope.launch {
            try {
                if(hasNextPage) {
                    val data = api.getTopAnime(pageNumber)
                    if (topAnimeList.value == null) {
                        _topAnimeList.value = data.data
                    } else {
                        _topAnimeList.value = _topAnimeList.value?.plus(data.data)
                    }
                    hasNextPage = data.pagination?.hasNextPage ?: true
                    pageNumber += 1
                }
            } catch (e: Exception) {
                Log.d(CONSTANTS.TAG, "getTopAnime error: ${e.message}")
            }
        }
    }


    /***
     * if a user scrolls in a place twice the item will be appended
     * to prevent that till the next page is hold prevent the network call
     */

    fun loadNextTopAnimePage() {
        if (!lockTopAnimeCall) {
            getTopAnime()
        }
    }


}