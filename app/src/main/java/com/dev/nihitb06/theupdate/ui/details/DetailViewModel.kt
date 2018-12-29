package com.dev.nihitb06.theupdate.ui.details

import androidx.lifecycle.ViewModel
import com.dev.nihitb06.theupdate.data.ArticleRepository

class DetailViewModel (repository: ArticleRepository, title: String) : ViewModel () {

    val article = repository.getArticle(title)
}