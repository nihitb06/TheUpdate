package com.dev.nihitb06.theupdate.ui.list.utilities;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import yalantis.com.sidemenu.util.ViewAnimator;

public class MyActionBarDrawerToggle extends ActionBarDrawerToggle {

    private LinearLayout appDrawer;
    private ViewAnimator viewAnimator;

    public MyActionBarDrawerToggle(
            Activity activity,
            DrawerLayout drawerLayout,
            Toolbar toolbar,
            int openDrawerContentDescRes,
            int closeDrawerContentDescRes,
            LinearLayout appDrawer,
            ViewAnimator viewAnimator
    ) {
        super(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes);

        this.appDrawer = appDrawer;
        this.viewAnimator = viewAnimator;
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        super.onDrawerSlide(drawerView, slideOffset);

        if(slideOffset > 0.6 && appDrawer.getChildCount() == 0)
            viewAnimator.showMenuContent();
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        super.onDrawerClosed(drawerView);

        appDrawer.removeAllViews();
        appDrawer.invalidate();
    }
}
