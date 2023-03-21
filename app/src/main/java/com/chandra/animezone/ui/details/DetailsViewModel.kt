package com.chandra.animezone.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chandra.animezone.CONSTANTS
import com.chandra.animezone.models.AnimeDetails
import com.chandra.animezone.models.Response
import com.chandra.animezone.repository.network.AnimeAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val animeAPI: AnimeAPI) : ViewModel() {
    private var _animeDetails = MutableLiveData<Response?>()
    val animeDetails: LiveData<Response?>
        get() = _animeDetails

    fun getAnimeDetails(id: Int) {
        viewModelScope.launch {
            try {
                Log.d(CONSTANTS.TAG, "getAnimeDetails : id: $id")
                val response = animeAPI.getAnimeDetails(id)
                _animeDetails.value = response.data
            }catch (e : Exception){
                Log.d(CONSTANTS.TAG, "getAnimeDetails: error : ${e.message}")
            }
        }
    }


}