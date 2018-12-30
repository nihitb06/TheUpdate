package com.dev.nihitb06.theupdate.ui.list

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.dev.nihitb06.theupdate.R
import com.dev.nihitb06.theupdate.data.database.HeaderArticleEntity
import com.dev.nihitb06.theupdate.data.database.ListArticleEntity
import com.dev.nihitb06.theupdate.ui.details.ArticleDetailsActivity
import com.dev.nihitb06.theupdate.ui.list.adapters.ArticlePagerAdapter
import com.dev.nihitb06.theupdate.ui.list.adapters.ArticleRecyclerAdapter
import com.dev.nihitb06.theupdate.ui.list.interfaces.OnItemClickListener
import com.dev.nihitb06.theupdate.utilities.InjectorUtils
import kotlinx.android.synthetic.main.fragment_list.view.*
import yalantis.com.sidemenu.interfaces.ScreenShotable
import kotlin.ClassCastException

class ListFragment : Fragment(), ScreenShotable, OnItemClickListener {

    private lateinit var container: View
    private lateinit var bitmap: Bitmap

    private lateinit var category: String
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        category = arguments?.getString(CATEGORY) ?: getString(R.string.home)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val itemView = inflater.inflate(R.layout.fragment_list, container, false)

        if(category == getString(R.string.home))
            setupPager(itemView)
        else
            setupList(itemView)

        return itemView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        container = view.container
    }

    private fun setupPager(view: View) {
        view.newsPager.visibility = View.VISIBLE

        try {
            val factory = InjectorUtils.provideListViewModelFactory(context!!.applicationContext, null)
            val viewModel = ViewModelProviders.of(this, factory).get(ListViewModel::class.java)

            viewModel.articles.observe(this, Observer { articles ->
                try {
                    @Suppress("UNCHECKED_CAST")
                    view.newsPager.setCreativeViewPagerAdapter(ArticlePagerAdapter(articles as List<HeaderArticleEntity>, this))
                } catch (e: ClassCastException) {
                    e.printStackTrace()
                } catch (e: IllegalStateException) {
                    e.printStackTrace()
                    activity?.recreate()
                }
            })
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }
    private fun setupList(view: View) {
        view.newsList.visibility = View.VISIBLE
        view.newsList.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        val adapter = ArticleRecyclerAdapter(this)
        view.newsList.adapter = adapter

        view.newsList.addItemDecoration(DividerItemDecoration(view.newsList.context, RecyclerView.VERTICAL))
        view.newsList.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                toolbar.elevation = if(recyclerView.canScrollVertically(-1)) 24f else 0f
            }
        })

        try {
            val factory = InjectorUtils.provideListViewModelFactory(context!!.applicationContext, category)
            val viewModel = ViewModelProviders.of(this, factory).get(ListViewModel::class.java)

            viewModel.articles.observe(this, Observer { articles ->
                try {
                    @Suppress("UNCHECKED_CAST")
                    adapter.setArticles(articles as List<ListArticleEntity>)
                } catch (e: ClassCastException) {
                    e.printStackTrace()
                }
            })
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }

    override fun takeScreenShot() = Thread {
        val bitmap = Bitmap.createBitmap(container.width, container.height, Bitmap.Config.ARGB_8888)
        container.draw(Canvas(bitmap))

        this.bitmap = bitmap
    }.start()

    override fun getBitmap() = bitmap

    override fun onItemClick(title: String, name: String) {
        context?.startActivity(
                Intent(context, ArticleDetailsActivity::class.java)
                        .putExtra(ArticleDetailsActivity.TITLE, title)
                        .putExtra(ArticleDetailsActivity.NAME, name)
        )
    }

    companion object {

        const val CATEGORY = "Category"

        @JvmStatic
        fun newInstance(category: String, toolbar: Toolbar) = ListFragment().apply {
            arguments = Bundle().apply {
                putString(CATEGORY, category)
            }

            this.toolbar = toolbar
        }
    }
}
