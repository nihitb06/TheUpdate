package com.dev.nihitb06.theupdate.ui.details

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.request.RequestOptions
import com.dev.nihitb06.theupdate.R
import com.dev.nihitb06.theupdate.data.database.ArticleEntity
import com.dev.nihitb06.theupdate.utilities.InjectorUtils
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_article_details.*
import java.net.MalformedURLException
import java.net.URL

class ArticleDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_details)

        setActionBar()

        val title = intent.extras?.getString(TITLE) ?: ""
        val name = intent.extras?.getString(NAME) ?: ""

        if (name.isNotEmpty())
            imageView.transitionName = name

        if(title.isNotEmpty()) {
            val factory = InjectorUtils.provideDetailViewModelFactory(this, title)
            val viewModel = ViewModelProviders.of(this, factory).get(DetailViewModel::class.java)

            bindArticle(viewModel.article)
        }
    }

    private fun setActionBar() {
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun bindArticle(article: LiveData<ArticleEntity>) {
        article.observe(this, Observer { articleEntity ->
            try {
                Glide.with(this).applyDefaultRequestOptions(
                        RequestOptions()
                                .placeholder(R.drawable.drawable_image)
                                .error(R.drawable.drawable_image_error)
                ).load(GlideUrl(URL(articleEntity.urlToImage))).into(imageView)
            } catch (e: MalformedURLException) {
                Glide.with(this).load(R.drawable.drawable_image_error).into(imageView)
            }

            tvHeader.text = articleEntity.title
            tvAuthor.text = articleEntity.author
            tvCategory.text = articleEntity.category
            tvDescription.text = articleEntity.description
            tvPublishedAt.text = articleEntity.publishedAt.replace('T', ' ').replace('Z', '.')
            tvContent.text = articleEntity.content
            tvUrl.text = articleEntity.url

            tvUrl.setOnClickListener {
                try {
                    startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(articleEntity.url)))
                } catch (e: MalformedURLException) {
                    e.printStackTrace()
                    Snackbar.make(rootView, "Something Went Wrong", Snackbar.LENGTH_SHORT).show()
                } catch (e: ActivityNotFoundException) {
                    Snackbar.make(rootView, "No Activity can Handle the Action", Snackbar.LENGTH_SHORT).show()
                }
            }
        })
    }

    companion object {
        const val TITLE = "Title"
        const val NAME = "Name"
    }
}
