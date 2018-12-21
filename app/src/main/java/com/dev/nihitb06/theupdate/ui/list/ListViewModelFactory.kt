package com.dev.nihitb06.theupdate.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dev.nihitb06.theupdate.data.ArticleRepository

class ListViewModelFactory (
        private val repository: ArticleRepository,
        private val category: String?
) : ViewModelProvider.NewInstanceFactory () {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(ListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListViewModel(repository, category) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}