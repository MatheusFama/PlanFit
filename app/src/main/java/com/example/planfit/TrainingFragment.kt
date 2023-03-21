package com.example.planfit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.planfit.databinding.FragmentTrainingBinding


/**
 * A simple [Fragment] subclass.
 * Use the [TrainingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TrainingFragment : Fragment() {

    lateinit var binding: FragmentTrainingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrainingBinding.inflate(inflater, container, false)

        binding.btn.setOnClickListener {

        }
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }



}