package com.febrian.githubapp.ui.fragment.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.febrian.githubapp.data.database.DataRoom
import com.febrian.githubapp.databinding.FragmentFavoriteBinding
import com.febrian.githubapp.ui.adapter.FavoriteAdapter

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        viewModel.getData(requireContext()).observe(viewLifecycleOwner, {
            setData(it)
        })
    }

    private fun setData(list: ArrayList<DataRoom>) {
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.adapter = FavoriteAdapter(list, requireActivity())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}