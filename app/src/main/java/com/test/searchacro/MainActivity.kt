package com.test.searchacro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.searchacro.databinding.ActivityMainBinding
import com.test.searchacro.di.DI
import com.test.searchacro.repository.AcronymRepo
import com.test.searchacro.repository.AcronymRepository

class MainActivity : AppCompatActivity() {

    val searchListAdapter by lazy {
        MainAdapter(searchActivityViewModel)
    }

    val searchActivityViewModel by lazy {
        ViewModelProvider(this, SearchViewModelFactory(DI.acronymRepo))
            .get(SearchActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            activityViewModel = searchActivityViewModel
        }

        val linearLayoutManager = LinearLayoutManager(this)
        binding.acronymList?.apply {
            layoutManager = linearLayoutManager
            adapter = searchListAdapter
        }

        searchActivityViewModel.searchData.observe(this, Observer {
            searchListAdapter.setData(it ?: emptyList())
        })
    }
}

class SearchViewModelFactory(private val repo: AcronymRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchActivityViewModel::class.java)) {
            return SearchActivityViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}