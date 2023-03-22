package com.chandra.animezone.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chandra.animezone.CONSTANTS
import com.chandra.animezone.models.AnimeDBModel
import com.chandra.animezone.models.Response
import com.chandra.animezone.repository.database.AnimeDAO
import com.chandra.animezone.repository.network.AnimeAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val animeAPI: AnimeAPI,
    private val dao: AnimeDAO
) : ViewModel() {
    private var _animeDetails = MutableLiveData<Response?>()
    val animeDetails: LiveData<Response?>
        get() = _animeDetails
    private var _existInDB = MutableLiveData<Boolean>()
    val existInDB: LiveData<Boolean>
        get() = _existInDB
    private var animeID: Int? = null
    fun getAnimeDetails(id: Int) {
        animeID = id
        checkIfAlreadyExist(id)
        viewModelScope.launch {
            try {
                Log.d(CONSTANTS.TAG, "getAnimeDetails : id: $id")
                val response = animeAPI.getAnimeDetails(id)
                _animeDetails.value = response.data
            } catch (e: Exception) {
                Log.d(CONSTANTS.TAG, "getAnimeDetails: error : ${e.message}")
            }
        }
    }

    // check if the anime already saved inside the Database
    private fun checkIfAlreadyExist(id: Int) {
        viewModelScope.launch {
            _existInDB.value = dao.getAllAnime().any { it.id == id }
        }

    }

    // check if the movie is already saved then remove else save
    fun saveMovieToDB() {

        if (animeID != null) {
            viewModelScope.launch {
                if (_existInDB.value == true) {
                    animeID?.let { dao.deleteAnime(it) }
                } else {
                    val data = _animeDetails.value
                    val model = AnimeDBModel(
                        animeID!!,
                        data?.images?.jpg?.largeImageUrl ?: "",
                        data?.title ?: "",
                        data?.score ?: 0.0
                    )
                    dao.insertAnime(model)
                }
                checkIfAlreadyExist(animeID!!)
            }
        }

    }


}