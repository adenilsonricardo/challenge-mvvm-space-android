package com.devpass.spaceapp.presentation.launch_list

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.accessibility.AccessibilityEventCompat.setAction
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.devpass.spaceapp.R
import com.devpass.spaceapp.databinding.ActivityLaunchListBinding
import com.devpass.spaceapp.presentation.LaunchActivity
import com.devpass.spaceapp.presentation.view_model.LaunchListViewModel
import com.devpass.spaceapp.utils.NetworkResult
import com.google.android.material.snackbar.Snackbar

class LaunchListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLaunchListBinding

    private lateinit var adapter: LaunchListAdapter

    private val viewModel by viewModels<LaunchListViewModel>()

    private val snackBarError by lazy {
        Snackbar.make(binding.root, R.string.error_message, Snackbar.LENGTH_INDEFINITE).apply {
            setAction(R.string.retry) {
                Toast.makeText(this@LaunchListActivity, "Try again clicked", Toast.LENGTH_LONG)
                    .show()
                observeLaunchList()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchListBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setupTitle()
        setupRecycleView()
        observeLaunchList()

    }

    private fun setupTitle() {
        with(binding.includeToolbar) {
            tvToolbarTitle.setText(R.string.title_toolbar_launch_list_activity)
            tvToolbarTitle.setTypeface(null, Typeface.BOLD)
            back.visibility = View.GONE
        }
    }

    private fun setupLoading() {
        with(binding) {
            lottieRocketLoading.playAnimation()
            lottieRocketLoading.visibility = View.VISIBLE
        }
    }

    private fun observeLaunchList() {
        viewModel.launches.observe(this) {
            when (it) {
                is NetworkResult.Success -> {
                    adapter.submitList(it.data)
                    binding.lottieRocketLoading.visibility = View.GONE
                }
                is NetworkResult.Error -> {
                    binding.lottieRocketLoading.visibility = View.GONE
                    snackBarError.show()
                }
                is NetworkResult.Loading -> {
                    setupLoading()
                }
            }
        }
    }

    private fun setupRecycleView() {
        adapter = LaunchListAdapter {
            Log.i(TAG, "on click $it")
            startActivity(Intent(baseContext, LaunchActivity::class.java))
        }
        binding.rvLaunches.adapter = adapter
        binding.rvLaunches.layoutManager = LinearLayoutManager(this)
    }

    companion object {
        const val TAG = "LaunchListActivity"
    }
}
