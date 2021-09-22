package com.febrian.githubapp.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.febrian.githubapp.data.entity.User
import com.febrian.githubapp.databinding.FragmentHomeBinding
import com.febrian.githubapp.ui.adapter.ListAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        setObserver("")

        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                setObserver(p0.toString())
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                setObserver(p0.toString())
                return true
            }

        })

        viewModel.isLoading.observe(viewLifecycleOwner, {
            setLoading(it)
        })
    }

    private fun setObserver(query: String) {
        if (query == "") {
            viewModel.getUser().observe(viewLifecycleOwner, {
                setUsers(it)
            })
        } else {
            viewModel.getSearchUser(query).observe(viewLifecycleOwner, {
                val data = it.items as ArrayList<User>
                setUsers(data)
            })
        }
    }

    private fun setUsers(user: ArrayList<User>) {
        binding.rv.layoutManager = LinearLayoutManager(
            requireContext().applicationContext,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.rv.setHasFixedSize(true)
        binding.rv.adapter = ListAdapter(user, requireActivity())
    }

    private fun setLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}