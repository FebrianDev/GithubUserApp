package com.febrian.githubapp.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.febrian.githubapp.R
import com.febrian.githubapp.data.database.DataDao
import com.febrian.githubapp.data.database.DataRoom
import com.febrian.githubapp.data.database.DatabaseRoom
import com.febrian.githubapp.data.entity.User
import com.febrian.githubapp.databinding.ActivityDetailUserBinding
import com.google.android.material.snackbar.Snackbar

class DetailUserActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val KEY_USER = "KEY"
    }

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var database: DataDao

    var data: User? = null

    @SuppressLint("CheckResult", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        data = intent.getParcelableExtra<User>(KEY_USER) as User

        database = DatabaseRoom.getDatabase(applicationContext).dataDao()
        val exist = database.dataExist(data!!.username.toString())
        setIcon(exist)

        val img = this.resources.getIdentifier(data?.avatar, null, this.packageName)
        Glide.with(applicationContext).load(img)
            .error(R.drawable.ic_baseline_broken_image_24).into(binding.img)
        binding.name.text = data?.name.toString()
        binding.username.text = data?.username.toString()
        binding.organization.text = data?.company.toString()
        binding.city.text = data?.location.toString()
        binding.follower.text = data?.follower.toString()
        binding.following.text = data?.following.toString()
        binding.repo.text = "${data?.repository} repositories"

        binding.btnFavorite.setOnClickListener(this)
        binding.back.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v) {
            binding.btnFavorite -> {
                addFavorite(v)
            }
            binding.back -> {
                back()
            }
        }
    }

    private fun back() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun addFavorite(v : View) {
        val exist = database.dataExist(data!!.username.toString())
        setIcon(exist, v)
    }

    private fun setIcon(exist: Boolean, v : View) {
        if (!exist) {
            setIcon(true)
            Snackbar.make(v, getString(R.string.succes_add), Snackbar.LENGTH_SHORT).show()
            val newData = DataRoom(
                name = data!!.name,
                username = data!!.username,
                repository = data!!.repository,
                avatar = data!!.avatar,
                follower = data!!.follower,
                following = data!!.following,
                location = data!!.location,
                company = data!!.company
            )
            database.insert(newData)
        } else {
            setIcon(false)
            Snackbar.make(v, getString(R.string.success_delete), Snackbar.LENGTH_SHORT).show()
            database.delete(data!!.username.toString())
        }
    }

    private fun setIcon(exist : Boolean){
        if(exist){
            binding.btnFavorite.setBackgroundResource(R.drawable.bg_btn_remove_primary)
            binding.btnFavorite.text = resources.getString(R.string.remove_from_favorite)
            binding.btnFavorite.setTextColor(resources.getColor(R.color.customColorPrimary))
        }else{
            binding.btnFavorite.setBackgroundResource(R.drawable.bg_btn_add_favorite)
            binding.btnFavorite.text = resources.getString(R.string.add_to_favorite)
            binding.btnFavorite.setTextColor(resources.getColor(R.color.customColorPrimarySecond))
        }
    }
}