package com.dev.nihitb06.theupdate.ui.list

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.dev.nihitb06.theupdate.R
import kotlinx.android.synthetic.main.fragment_list.view.*
import yalantis.com.sidemenu.interfaces.ScreenShotable

class ListFragment : Fragment(), ScreenShotable {

    private lateinit var container: View
    private lateinit var bitmap: Bitmap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        container = view.container
    }

    override fun takeScreenShot() = Thread {
        val bitmap = Bitmap.createBitmap(container.width, container.height, Bitmap.Config.ARGB_8888)
        container.draw(Canvas(bitmap))

        this.bitmap = bitmap
    }.start()

    override fun getBitmap() = bitmap

    companion object {

        @JvmStatic
        fun newInstance() = ListFragment()
    }
}
