package com.chandra.animezone.ui.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.chandra.animezone.adapter.ItemClickListener
import com.chandra.animezone.adapter.PopularAnimeAdapter
import com.chandra.animezone.databinding.FragmentSavedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedFragment : Fragment(), ItemClickListener {

    private lateinit var viewModel: SavedViewModel
    private lateinit var binding: FragmentSavedBinding
    private lateinit var adapter: PopularAnimeAdapter
    private lateinit var layoutManager: GridLayoutManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SavedViewModel::class.java]
        adapter = PopularAnimeAdapter(this)
        layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllAnime()
        viewModel.animeList.observe(this) {
            adapter.submitList(it)
            setLayoutRespectively(it.isEmpty())
        }
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnGoHome.setOnClickListener{
            findNavController().popBackStack()
        }
    }

    // if the data is 0 then show the blank layout
    private fun setLayoutRespectively(empty: Boolean) {
        when (empty) {
            true -> {
                binding.recyclerView.visibility = View.VISIBLE
                binding.noSavedMovieLayout.visibility = View.GONE
            }
            false ->{
                binding.recyclerView.visibility = View.VISIBLE
                binding.noSavedMovieLayout.visibility = View.GONE
            }
        }
    }

    override fun OnItemCLicked(anime_id: Int?) {
        if (anime_id == null) {
            Toast.makeText(
                requireContext(),
                "Details not available at this moment",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            val direction = SavedFragmentDirections.actionSavedFragmentToDetailsFragment(anime_id)
            findNavController().navigate(direction)
        }
    }
}