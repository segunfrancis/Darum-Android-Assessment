package com.segunfrancis.darumandroidassessment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.segunfrancis.darumandroidassessment.databinding.FragmentListBinding

class ListFragment : Fragment(R.layout.fragment_list) {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentListBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
