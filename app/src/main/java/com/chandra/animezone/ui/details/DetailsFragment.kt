package com.chandra.animezone.ui.details

import android.icu.number.IntegerWidth
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.chandra.animezone.CONSTANTS
import com.chandra.animezone.R
import com.chandra.animezone.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var viewModel: DetailsViewModel
    private lateinit var binding: FragmentDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
        val args: DetailsFragmentArgs by navArgs()
        viewModel.getAnimeDetails(args.id)
    }

    override fun onResume() {
        super.onResume()
        viewModel.animeDetails.observe(this) {
            Log.d(CONSTANTS.TAG, "onResume: $it")
        }

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }



}