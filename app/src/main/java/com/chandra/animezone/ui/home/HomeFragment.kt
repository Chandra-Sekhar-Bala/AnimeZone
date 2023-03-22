package com.chandra.animezone.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chandra.animezone.CONSTANTS
import com.chandra.animezone.adapter.ItemClickListener
import com.chandra.animezone.adapter.PopularAnimeAdapter
import com.chandra.animezone.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), ItemClickListener {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: PopularAnimeAdapter
    private lateinit var layoutManager: GridLayoutManager


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
        adapter = PopularAnimeAdapter(this)
        layoutManager = GridLayoutManager(requireContext(), 2)
        binding.topAnimeRecycler.layoutManager = layoutManager
        binding.topAnimeRecycler.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        binding.saved.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSavedFragment())
        }
        binding.search.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment())
        }

        viewModel.popularAnimeList.observe(this) {
            binding.imageSlider.setImageList(imageList = it)
        }
        viewModel.topAnimeList.observe(this) {
            adapter.submitList(it)

            if (binding.shimmerLayout.visibility == View.VISIBLE) {
                binding.shimmerLayout.visibility = View.GONE
                binding.popularTxt.visibility = View.VISIBLE
                binding.imageSlider.visibility = View.VISIBLE
            }
        }
        // for pagination : Listening to recyclerView scrolling
        binding.topAnimeRecycler.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val lastVisible = layoutManager.findLastVisibleItemPosition()
                    val totalItemCount = layoutManager.itemCount
                    if (lastVisible == totalItemCount - 4 || lastVisible == totalItemCount - 5) {
                        viewModel.loadNextTopAnimePage()
                    }
                }
            })
    }

    // callback when the item is clicked
    override fun OnItemCLicked(anime_id: Int?) {
        if (anime_id == null) {
            Toast.makeText(
                requireContext(),
                "Details not available at this moment",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            val direction = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(anime_id)
            findNavController().navigate(direction)
        }
    }
}

