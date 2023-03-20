package com.chandra.animezone.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.transition.Slide
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.chandra.animezone.CONSTANTS
import com.chandra.animezone.R
import com.chandra.animezone.databinding.FragmentHomeBinding
import com.chandra.animezone.repository.network.AnimeAPI
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemChangeListener
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
        viewModel.topAnimeList.observe(this){
            Log.d("TAGTAG", "onResume: $it")
        }
        val imageList = ArrayList<SlideModel>()
        viewModel.popularAnimeList.observe(this){
            for( item in it){
                imageList.add(SlideModel(item.entry!!.images!!.jpg!!.largeImageUrl, item.entry.title, ScaleTypes.CENTER_INSIDE))
            }
            binding.imageSlider.setImageList(imageList = imageList)
        }
//        binding.imageSlider.setItemClickListener(object : ItemClickListener {
//            override fun onItemSelected(position: Int) {
//                // You can listen here
//                Toast.makeText(requireContext(), "clicked $position", Toast.LENGTH_SHORT).show()
//                Log.d(CONSTANTS.TAG, "onItemSelected: $position ")
//            }
//        })

    }
}
