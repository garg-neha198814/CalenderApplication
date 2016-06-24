package com.shivam.testing.calenderapplication.ui.activities;

import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.shivam.testing.calenderapplication.R;
import com.shivam.testing.calenderapplication.ui.fragments.CalenderFragment;
import com.shivam.testing.calenderapplication.ui.fragments.PasswordStrength;
import com.shivam.testing.calenderapplication.ui.fragments.PwdFragment;
import com.shivam.testing.calenderapplication.ui.fragments.TrackerFragment;


public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private Toolbar mToolBar;
    private DrawerLayout mDrawerLayout;
    private ImageView mToolBarBack, mToolBarMenu, toolBarSetting;
    private TextView mToolBarTitle, mCalender, mTracker, mSetting, mLogout, mUserName;
    private View navView = null;
    private SimpleDraweeView mUserImage;
    private static final int TIME_INTERVAL = 3000;
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_home);
        initViews();
        //  getFragmentManager().beginTransaction().add(R.id.main,new MapFragment(),"map").addToBackStack(null).commit();

        getSupportFragmentManager().beginTransaction().add(R.id.main, new PwdFragment(), "calender").commit();

    }

    private void initViews() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mToolBarBack = (ImageView) mToolBar.findViewById(R.id.toolbar_back);
        mToolBarTitle = (TextView) mToolBar.findViewById(R.id.toolbar_title);
        mToolBarMenu = (ImageView) mToolBar.findViewById(R.id.toolbar_menu);
        toolBarSetting = (ImageView) mToolBar.findViewById(R.id.toolbar_setting);
        setActionBarTitle("Calender");

        initNavigationDrawer();
    }

    private void initNavigationDrawer() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navView == null)
            navView = navigationView.inflateHeaderView(R.layout.drawer_header);
        mCalender = (TextView) navView.findViewById(R.id.calender);
        mTracker = (TextView) navView.findViewById(R.id.tracker);
        mSetting = (TextView) navView.findViewById(R.id.setting);
        mLogout = (TextView) navView.findViewById(R.id.logout);
        mUserName = (TextView) navView.findViewById(R.id.user_name);
        mUserImage = (SimpleDraweeView) navView.findViewById(R.id.user_image);
        setClickListeners();
    }

    private void setClickListeners() {
        mToolBarMenu.setVisibility(View.VISIBLE);
        mToolBarBack.setVisibility(View.GONE);
        mToolBarMenu.setOnClickListener(this);
        mToolBarBack.setOnClickListener(this);
        mCalender.setOnClickListener(this);
        mTracker.setOnClickListener(this);
        mSetting.setOnClickListener(this);
        mLogout.setOnClickListener(this);
        //   mDrawerLayout.openDrawer(GravityCompat.START);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.toolbar_back:
                break;
            case R.id.toolbar_menu:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.calender:

                mDrawerLayout.closeDrawers();

                getSupportFragmentManager().beginTransaction().replace(R.id.main, new CalenderFragment(), "calender").commit();
                break;
            case R.id.tracker:

                mDrawerLayout.closeDrawers();

                getSupportFragmentManager().beginTransaction().replace(R.id.main, new TrackerFragment(), "tracker").commit();
                break;
            case R.id.setting:

                mDrawerLayout.closeDrawers();

                getSupportFragmentManager().beginTransaction().replace(R.id.main, new PwdFragment(), "setting").commit();
                break;
            case R.id.logout:
                break;


        }
    }

    public void clearBackStack() {

        FragmentManager fm = getSupportFragmentManager();

        for (int i = 1; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        if (fm.getBackStackEntryCount() == 1) {

        }
    }

    public void showDrawer() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }


    public void hideDrawer() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public void setActionBarTitle(String title) {
        mToolBarTitle.setText(title);
    }

    @Override
    public void onBackPressed() {
        System.out.println("back pressed() called of productlist-----------------------------------");
        System.out.println("backstack count>>>"+getSupportFragmentManager().getBackStackEntryCount());
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();


        } else {

            System.out.println("current time " + System.currentTimeMillis());
            if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
                super.onBackPressed();
                return;
            } else {
                showToast("Press Back Button Again In Order To Exit");

            }
            mBackPressed = System.currentTimeMillis();

        }
    }
}
