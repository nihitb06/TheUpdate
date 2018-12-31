package com.dev.nihitb06.theupdate.ui.list.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.request.RequestOptions
import com.dev.nihitb06.theupdate.R
import com.dev.nihitb06.theupdate.data.database.HeaderArticleEntity
import com.dev.nihitb06.theupdate.ui.list.interfaces.OnItemClickListener
import com.tbuonomo.creativeviewpager.adapter.CreativePagerAdapter
import kotlinx.android.synthetic.main.layout_content_item.view.*
import kotlinx.android.synthetic.main.layout_header_item.view.*
import java.net.URL

class ArticlePagerAdapter (
        private var articles: List<HeaderArticleEntity>,
        private val onItemClickListener: OnItemClickListener
) : CreativePagerAdapter {

    fun setArticles(articles: List<HeaderArticleEntity>) {
        this.articles = articles
    }

    override fun instantiateHeaderItem(inflater: LayoutInflater, container: ViewGroup, position: Int): View
            = inflater.inflate(R.layout.layout_content_item, container, false).apply {
        val article = articles[position]
        Glide.with(context).load(GlideUrl(URL(article.urlToImage))).apply(
                RequestOptions().centerCrop()
        ).into(imageView)

        tvHeader.text = article.title
        tvDescription.text = article.description
        tvPublishedAt.text = article.publishedAt.replace('T', ' ').replace('Z', '.')

        transitionName = position.toString()
        setOnClickListener { onItemClickListener.onItemClick(article.title, position.toString()) }
    }

    override fun instantiateContentItem(inflater: LayoutInflater, container: ViewGroup, position: Int): View
            = inflater.inflate(R.layout.layout_header_item, container, false).apply {
        iconCategory.setImageResource(getImageResource(articles[position].category.toLowerCase()))
    }

    override fun getCount() = articles.size

    private fun getImageResource(category: String) = when(category) {
        "business" -> R.drawable.ic_business
        "entertainment" -> R.drawable.ic_entertainment
        "health" -> R.drawable.ic_health
        "science" -> R.drawable.ic_science
        "sports" -> R.drawable.ic_sports
        "technology" -> R.drawable.ic_technology
        else -> -1
    }
}