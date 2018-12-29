package com.dev.nihitb06.theupdate.ui.list

import android.animation.Animator
import android.content.res.Configuration
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateInterpolator
import com.dev.nihitb06.theupdate.R
import com.dev.nihitb06.theupdate.ui.list.utilities.MyActionBarDrawerToggle
import kotlinx.android.synthetic.main.activity_main.*
import yalantis.com.sidemenu.interfaces.Resourceble
import yalantis.com.sidemenu.interfaces.ScreenShotable
import yalantis.com.sidemenu.model.SlideMenuItem
import yalantis.com.sidemenu.util.ViewAnimator

class MainActivity : AppCompatActivity(), ViewAnimator.ViewAnimatorListener {

    private lateinit var listFragment: ListFragment

    private val menuList by lazy {
        List(NAMES.size, init = { index ->  SlideMenuItem(getString(NAMES[index]), IMAGES[index]) })
    }

    private val viewAnimator by lazy {
        ViewAnimator(this, menuList, listFragment, drawerLayout, this)
    }

    private val drawerToggle by lazy {
        MyActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close,
                appDrawer,
                viewAnimator
        )
    }

    private lateinit var currentCategory: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        currentCategory = savedInstanceState?.getString(CURRENT_CATEGORY) ?: getString(R.string.home)

        switchFragment(currentCategory, null, -1)

        setActionBar()
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        drawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        drawerToggle.onConfigurationChanged(newConfig)
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState?.putString(CURRENT_CATEGORY, currentCategory)
    }

    private fun setActionBar() {
        setSupportActionBar(toolbar)

        toolbar.setNavigationIcon(R.drawable.ic_menu)
        toolbar.setNavigationOnClickListener { drawerLayout.openDrawer(drawerLayout) }

        drawerLayout.addDrawerListener(drawerToggle)
    }

    private fun switchFragment(category: String, fragment: ScreenShotable?, topPosition: Int): ScreenShotable {
        if(topPosition >= 0) {
            val animator = ViewAnimationUtils.createCircularReveal(
                    contentFrame,
                    0,
                    topPosition,
                    0f,
                    Math.max(contentFrame.width, contentFrame.height).toFloat()
            )
            animator.interpolator = AccelerateInterpolator()
            animator.duration = ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION.toLong()

            animator.addListener(object: Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                    //Do Nothing
                }

                override fun onAnimationRepeat(animation: Animator?) {
                    //Do Nothing
                }

                override fun onAnimationEnd(animation: Animator?) {
                    contentOverlay.visibility = View.INVISIBLE
                }

                override fun onAnimationCancel(animation: Animator?) {
                    //Do Nothing
                }
            })

            contentOverlay.background = BitmapDrawable(resources, fragment?.bitmap)
            contentOverlay.visibility = View.VISIBLE

            animator.start()
        }

        currentCategory = category
        listFragment = ListFragment.newInstance(category, toolbar)
        supportFragmentManager.beginTransaction().replace(R.id.contentFrame, listFragment).commit()

        return listFragment
    }

    override fun onSwitch(
            slideMenuItem: Resourceble?, screenShotable: ScreenShotable?, position: Int
    ) = when(slideMenuItem?.name) {
        getString(R.string.close) -> screenShotable
        else -> switchFragment(slideMenuItem!!.name, screenShotable, position)
    }

    override fun addViewToContainer(view: View) {
        appDrawer.addView(view)
    }

    override fun enableHomeButton() {
        //Do Nothing
    }

    override fun disableHomeButton() {
        //Do Nothing
    }

    companion object {

        private const val CURRENT_CATEGORY = "CurrentCategory"

        private val NAMES = arrayOf(
                R.string.close,
                R.string.home,
                R.string.business,
                R.string.entertainment,
                R.string.health,
                R.string.science,
                R.string.sports,
                R.string.technology
        )

        private val IMAGES = arrayOf(
                R.drawable.ic_close_black_24dp,
                R.drawable.ic_home,
                R.drawable.ic_business,
                R.drawable.ic_entertainment,
                R.drawable.ic_health,
                R.drawable.ic_science,
                R.drawable.ic_sports,
                R.drawable.ic_technology
        )
    }
}
