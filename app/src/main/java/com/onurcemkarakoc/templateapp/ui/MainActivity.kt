package com.onurcemkarakoc.templateapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.onurcemkarakoc.templateapp.R
import com.onurcemkarakoc.templateapp.databinding.ActivityMainBinding
import com.onurcemkarakoc.templateapp.ui.popularmovies.PopularMoviesFragment
import com.onurcemkarakoc.templateapp.ui.topratedmovies.TopRatedMoviesFragment
import com.trendyol.medusalib.navigator.MultipleStackNavigator
import com.trendyol.medusalib.navigator.Navigator
import com.trendyol.medusalib.navigator.NavigatorConfiguration
import com.trendyol.medusalib.navigator.transaction.NavigatorTransaction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), Navigator.NavigatorListener {
    private lateinit var binding: ActivityMainBinding
    private val rootFragmentProvider: List<() -> Fragment> =
        listOf(
            { PopularMoviesFragment.newInstance() },
            { TopRatedMoviesFragment.newInstance() }
        )

    val navigator: MultipleStackNavigator =
        MultipleStackNavigator(
            supportFragmentManager,
            R.id.fragmentContainer,
            rootFragmentProvider,
            this,
            navigatorConfiguration = NavigatorConfiguration(
                0, true, NavigatorTransaction.SHOW_HIDE
            )
        )

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_popular -> {
                    navigator.switchTab(0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_top_rated -> {
                    navigator.switchTab(1)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navigator.initialize(savedInstanceState)
        binding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onBackPressed() {
        if (navigator.canGoBack()) {
            navigator.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onTabChanged(tabIndex: Int) {
        when (tabIndex) {
            0 -> binding.navigation.selectedItemId = R.id.navigation_popular
            1 -> binding.navigation.selectedItemId = R.id.navigation_top_rated
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        navigator.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

}