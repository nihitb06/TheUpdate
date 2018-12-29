package com.dev.nihitb06.theupdate.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dev.nihitb06.theupdate.data.ArticleRepository
import java.lang.IllegalArgumentException

class DetailViewModelFactory (
        private val repository: ArticleRepository,
        private val title: String
) : ViewModelProvider.NewInstanceFactory () {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(repository, title) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}