package com.dev.nihitb06.theupdate.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dev.nihitb06.theupdate.BuildConfig
import com.dev.nihitb06.theupdate.R
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_about.*
import java.net.MalformedURLException

class AboutActivity : AppCompatActivity() {

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
        tvOSS.setOnClickListener { startActivity(Intent(this, OssLicensesMenuActivity::class.java)) }
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
