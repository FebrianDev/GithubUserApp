package com.febrian.githubapp.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.febrian.githubapp.ui.viewmodel.DataViewModel
import com.febrian.githubapp.ui.adapter.ListAdapter
import com.febrian.githubapp.data.database.DataDao
import com.febrian.githubapp.data.database.DatabaseRoom
import com.febrian.githubapp.data.entity.GithubUsers
import com.febrian.githubapp.data.entity.User
import com.febrian.githubapp.databinding.FragmentFavoriteBinding


class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private var c: Context? = null
    private lateinit var database: DataDao

    override fun onAttach(context: Context) {
        super.onAttach(context)
        c = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = DatabaseRoom.getDatabase(c!!.applicationContext).dataDao()

       DataViewModel(c!!).getData().observe(requireActivity(), { t ->
            binding.rv.layoutManager = LinearLayoutManager(c)
            val list = getData(t)
            binding.rv.adapter = ListAdapter(list, requireActivity())
        })
    }

    private fun getData(t: GithubUsers?): ArrayList<User> {
        val list = ArrayList<User>()
        val listData = database.getAllData()
        for (i in listData.indices) {
            for (j in t!!.users!!.indices) {
                if (t.users!![j].name == listData[i].name) {
                    list.add(t.users!![j])
                }
            }
        }
        return list
    }


}