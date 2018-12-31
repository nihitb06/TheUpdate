package com.dev.nihitb06.theupdate.ui.aboutapp

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.nihitb06.theupdate.BuildConfig
import com.dev.nihitb06.theupdate.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_about.*
import java.net.MalformedURLException

class AboutActivity : AppCompatActivity() {

    private val assets by lazy {
        ArrayList<ImageAsset>().apply {
            add(ImageAsset(R.drawable.ic_launcher_foreground, "https://www.flaticon.com/authors/smashicons"))
            add(ImageAsset(R.drawable.ic_home, "https://www.flaticon.com/authors/vectors-market"))
            add(ImageAsset(R.drawable.ic_business, "https://www.flaticon.com/authors/dimitry-miroliubov"))
            add(ImageAsset(R.drawable.ic_entertainment, "https://www.freepik.com/"))
            add(ImageAsset(R.drawable.ic_health, "https://www.freepik.com/"))
            add(ImageAsset(R.drawable.ic_science, "https://www.freepik.com/"))
            add(ImageAsset(R.drawable.ic_sports, "https://www.flaticon.com/authors/smashicons"))
            add(ImageAsset(R.drawable.ic_technology, "https://www.flaticon.com/authors/ddara"))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        setActionBar()

        tvVersion.text = getString(R.string.app_version, BuildConfig.VERSION_NAME)

        appLink.setOnClickListener {
            try {
                startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(appLink.text.toString())))
            } catch (e: MalformedURLException) {
                e.printStackTrace()
                Snackbar.make(rootView, "Something Went Wrong", Snackbar.LENGTH_SHORT).show()
            } catch (e: ActivityNotFoundException) {
                Snackbar.make(rootView, "No Activity can Handle the Action", Snackbar.LENGTH_SHORT).show()
            }
        }

        tvAssets.setOnClickListener {
            val dialog = Dialog(this)

            dialog.setContentView(R.layout.layout_image_asset_dialog)

            val recyclerView = dialog.findViewById<RecyclerView>(R.id.rvImageAssets)
            recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            recyclerView.adapter = ImageAssetRecyclerAdapter(this, assets)

            recyclerView.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))

            dialog.show()
        }
    }

    private fun setActionBar() {
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)

            setDisplayShowTitleEnabled(false)
        }
    }
}
