package com.dev.nihitb06.theupdate.ui.list

import androidx.lifecycle.ViewModel
import com.dev.nihitb06.theupdate.data.ArticleRepository

class ListViewModel (repository: ArticleRepository, category: String?) : ViewModel () {

    val articles = if(category != null) repository.getArticlesByCategory(category, LIMIT) else repository.getArticles(LIMIT)

    companion object {
        private const val LIMIT = 20
    }
}