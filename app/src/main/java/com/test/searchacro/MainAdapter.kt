package com.test.searchacro

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.test.searchacro.databinding.SearchItemBinding

data class SearchViewModel(val data: String)

class MainAdapter(val searchActivityViewModel: SearchActivityViewModel): RecyclerView.Adapter<SearchListViewHolder>() {

    val searchViewModels = mutableListOf<SearchViewModel>()

    fun setData(searchViewModel: List<SearchViewModel>) {
        with(searchViewModels) {
            clear()
            addAll(searchViewModel)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<SearchItemBinding>(layoutInflater, R.layout.search_item, parent, false)
        return SearchListViewHolder(binding, searchActivityViewModel)
    }

    override fun getItemCount(): Int {
        return searchViewModels.size
    }

    override fun onBindViewHolder(holder: SearchListViewHolder, position: Int) {
        holder.bind(searchViewModels[position] as SearchViewModel)
    }
}

class SearchListViewHolder(private val itemBinding: SearchItemBinding,
                            private val searchActivityViewModel: SearchActivityViewModel): RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(productViewModel: SearchViewModel) {
        itemBinding.viewModel = productViewModel
        itemBinding.executePendingBindings()
    }
}