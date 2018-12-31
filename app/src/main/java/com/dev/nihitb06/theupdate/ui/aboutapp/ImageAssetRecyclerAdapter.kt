package com.dev.nihitb06.theupdate.ui.aboutapp

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.nihitb06.theupdate.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.layout_image_asset.view.*
import java.net.MalformedURLException

class ImageAssetRecyclerAdapter (
        private val context: Context,
        private val assets: List<ImageAsset>
) : RecyclerView.Adapter<ImageAssetRecyclerAdapter.ImageAssetViewHolder> () {

    inner class ImageAssetViewHolder (itemView: View) : RecyclerView.ViewHolder (itemView) {

        private val icon = itemView.icon

        fun onBind(asset: ImageAsset) {
            Glide.with(icon).load(asset.image).into(icon)

            itemView.setOnClickListener {
                try {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(asset.url)))
                } catch (e: MalformedURLException) {
                    e.printStackTrace()
                    Snackbar.make(itemView, "Something Went Wrong", Snackbar.LENGTH_SHORT).show()
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                    Snackbar.make(itemView, "No Activity Found to Handle Action", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = ImageAssetViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_image_asset, parent, false))

    override fun onBindViewHolder(holder: ImageAssetViewHolder, position: Int) {
        holder.onBind(assets[position])
    }

    override fun getItemCount() = assets.size
}