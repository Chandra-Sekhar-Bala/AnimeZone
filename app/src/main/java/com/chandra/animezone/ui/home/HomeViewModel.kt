package com.chandra.animezone.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chandra.animezone.CONSTANTS
import com.chandra.animezone.models.AnimeList
import com.chandra.animezone.repository.network.AnimeAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val api: AnimeAPI) : ViewModel() {

    var topAnimeList = MutableLiveData<AnimeList>()
    init {
        Log.d(CONSTANTS.Tag, "Calling")
        getTopAnime()
    }
    fun getTopAnime(){
        viewModelScope.launch {
            try {
                Log.d(CONSTANTS.Tag, "trying")
                topAnimeList.value = api.getTopAnime(1)
            }catch (e : Exception){
                Log.d(CONSTANTS.Tag, "error: ${e.message}")
            }
        }
    }
}