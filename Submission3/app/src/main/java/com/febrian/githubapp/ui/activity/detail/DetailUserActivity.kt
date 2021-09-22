package com.febrian.githubapp.ui.activity.detail

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.febrian.githubapp.R
import com.febrian.githubapp.data.database.DataDao
import com.febrian.githubapp.data.database.DataRoom
import com.febrian.githubapp.data.database.DatabaseRoom
import com.febrian.githubapp.data.entity.User
import com.febrian.githubapp.databinding.ActivityDetailUserBinding
import com.febrian.githubapp.ui.adapter.SectionPagerAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var database: DataDao
    private lateinit var detailViewModel: DetailViewModel

    private var show = false
    private var action: Animation? = null
    private var items: Animation? = null

    private var actionClose: Animation? = null
    private var itemsClose: Animation? = null

    private var url = ""
    private var username = ""
    private var avatar = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAnimation()

        username = intent.getStringExtra(KEY_USER).toString()

        database = DatabaseRoom.getDatabase(applicationContext).dataDao()
        val exist = database.dataExist(username)
        setIcon(exist)

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        detailViewModel.getDetailUsers(username, this).observe(this, {
            showData(it)
        })

        detailViewModel.isLoading.observe(this, {
            setLoading(it)
        })

        binding.action.setOnClickListener(this)
        binding.back.setOnClickListener(this)
        binding.share.setOnClickListener(this)
        binding.favorite.setOnClickListener(this)

        val sectionsPagerAdapter = SectionPagerAdapter(this, username)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }

    private fun showData(it: User) {
        url = it.url.toString()
        avatar = it.avatar.toString()

        binding.name.text = it.name.toString()
        binding.username.text = it.username.toString()
        binding.organization.text = it.company.toString()
        binding.city.text = it.location.toString()
        binding.follower.text = it.follower.toString()
        binding.following.text = it.following.toString()
        val repositories = resources.getString(R.string.repositories)
        binding.repo.text = "${it.repository} $repositories"
        Glide.with(applicationContext).load(it.avatar.toString())
            .error(R.drawable.ic_baseline_broken_image_24).into(binding.img)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.back -> {
                back()
            }
            binding.action -> {
                setAnimation()
            }
            binding.share -> {
                shareUser()
            }
            binding.favorite -> {
                addFavorite(v)
            }
        }
    }

    private fun shareUser() {
        val mimeType = "text/plain"
        ShareCompat
            .IntentBuilder(this@DetailUserActivity)
            .setType(mimeType)
            .setChooserTitle("Link Url Github")
            .setText(url)
            .startChooser()
    }

    private fun initAnimation() {
        action = AnimationUtils.loadAnimation(applicationContext, R.anim.rotate_open_anim)
        items = AnimationUtils.loadAnimation(applicationContext, R.anim.from_bottom_anim)
        actionClose = AnimationUtils.loadAnimation(applicationContext, R.anim.rotate_close_anim)
        itemsClose = AnimationUtils.loadAnimation(applicationContext, R.anim.to_bottom_anim)
    }

    private fun setAnimation() {
        show = !show

        if (show) {
            binding.favorite.visibility = View.VISIBLE
            binding.share.visibility = View.VISIBLE
            binding.action.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.ic_baseline_clear_24
                )
            )
            binding.favorite.startAnimation(items)
            binding.share.startAnimation(items)
            binding.action.startAnimation(action)
        } else {
            binding.favorite.visibility = View.INVISIBLE
            binding.share.visibility = View.INVISIBLE
            binding.action.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.ic_baseline_add_24
                )
            )
            binding.favorite.startAnimation(itemsClose)
            binding.share.startAnimation(itemsClose)
            binding.action.startAnimation(actionClose)
        }
    }

    private fun back() {
        onBackPressed()
    }

    private fun addFavorite(v: View) {
        val exist = database.dataExist(username)
        setIcon(exist, v)
    }

    private fun setIcon(exist: Boolean, v: View) {
        if (!exist) {
            setIcon(true)
            Snackbar.make(v, getString(R.string.succes_add), Snackbar.LENGTH_SHORT).show()
            val newData = DataRoom(
                username = username,
                avatar = avatar,
            )
            database.insert(newData)
        } else {
            setIcon(false)
            Snackbar.make(v, getString(R.string.success_delete), Snackbar.LENGTH_SHORT).show()
            database.delete(username)
        }
    }

    private fun setIcon(exist: Boolean) {
        if (exist) {
            binding.favorite.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.ic_baseline_favorite_24
                )
            )
        } else {
            binding.favorite.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.ic_baseline_favorite_border_24
                )
            )
        }
    }

    private fun setLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val KEY_USER = "KEY"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }

}