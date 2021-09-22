package com.febrian.githubapp.ui.fragment.follower

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.febrian.githubapp.data.entity.User
import com.febrian.githubapp.databinding.FollowerFragmentBinding
import com.febrian.githubapp.ui.adapter.ListAdapter

class FollowerFragment(private val username: String) : Fragment() {

    private var _binding: FollowerFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FollowerFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(FollowerViewModel::class.java)

        viewModel.getFollowers(username).observe(viewLifecycleOwner, {
            setListFollower(it)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, {
            setLoading(it)
        })
    }

    private fun setListFollower(u: ArrayList<User>) {
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.setHasFixedSize(true)
        binding.rv.adapter = ListAdapter(u, requireActivity())
    }

    private fun setLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}