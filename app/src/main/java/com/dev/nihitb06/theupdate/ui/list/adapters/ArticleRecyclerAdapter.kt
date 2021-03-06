package com.dev.nihitb06.theupdate.ui.list.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.request.RequestOptions
import com.dev.nihitb06.theupdate.R
import com.dev.nihitb06.theupdate.data.database.ListArticleEntity
import com.dev.nihitb06.theupdate.ui.list.interfaces.OnItemClickListener
import kotlinx.android.synthetic.main.layout_list_item.view.*
import java.net.MalformedURLException
import java.net.URL

class ArticleRecyclerAdapter (private val onItemClickListener: OnItemClickListener)
    : RecyclerView.Adapter<ArticleRecyclerAdapter.ArticleViewHolder> () {

    private var articles: List<ListArticleEntity> = emptyList()
    fun setArticles(articles: List<ListArticleEntity>) {
        this.articles = articles
        notifyDataSetChanged()
    }

    inner class ArticleViewHolder (itemView: View) : RecyclerView.ViewHolder (itemView) {

        private val imageView = itemView.imageView
        private val tvHeader = itemView.tvHeader
        private val tvDescription = itemView.tvDescription

        fun bindView(article: ListArticleEntity, position: Int) {
            try {
                Glide.with(itemView).load(GlideUrl(URL(article.urlToImage))).apply(
                        RequestOptions()
                                .centerCrop()
                                .placeholder(R.drawable.drawable_image)
                                .error(R.drawable.drawable_image_error)
                ).into(imageView)
            } catch (e: MalformedURLException) {
                Glide.with(itemView).load(R.drawable.drawable_image_error).into(imageView)
            }

            tvHeader.text = article.title
            tvDescription.text = article.description

            itemView.transitionName = position.toString()
            itemView.setOnClickListener { onItemClickListener.onItemClick(article.title, position.toString()) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = ArticleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_list_item, parent, false))

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bindView(articles[position], position)
    }

    override fun getItemCount() = articles.size
}