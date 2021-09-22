package com.febrian.githubapp.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.febrian.githubapp.ui.viewmodel.DataViewModel
import com.febrian.githubapp.ui.adapter.ListAdapter
import com.febrian.githubapp.data.entity.User
import com.febrian.githubapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var c: Context? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        c = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DataViewModel(c!!).getData().observe(requireActivity(), {
            val data = it.users as ArrayList<User>

            binding.rv.layoutManager = LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false)
            binding.rv.setHasFixedSize(true)
            binding.rv.adapter = ListAdapter(data, requireActivity())

        })
    }

}