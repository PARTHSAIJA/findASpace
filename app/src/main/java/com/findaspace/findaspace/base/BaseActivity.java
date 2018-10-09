package com.findaspace.findaspace.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.findaspace.findaspace.app.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/*
 */

public abstract class BaseActivity<V extends BaseView, P extends BasePresenter> extends AppCompatActivity implements BaseView {

    private P mPresenter;
    private Unbinder mBind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showActionBarBackButton();
        // set XML layout
        BaseActivity.this.setContentView(getLayoutId());
        // use ButterKnife
        mBind = ButterKnife.bind(this);
        mPresenter = setPresenter();
        if (mPresenter != null) {
            //
            mPresenter.attachView(this);
        }
        initView();
        initEvent();
    }

    /**
     * initiate Event
     */
    protected abstract void initEvent();

    /**
     * initiate  View
     */
    protected abstract void initView();

    /**
     * set Presenter
     */
    protected abstract P setPresenter();

    protected P getPresenter() {
        return mPresenter;
    }

    /**
     * get layout Id
     */
    protected abstract int getLayoutId();

    /**
     * show ActionBar backButton
     */
    private void showActionBarBackButton() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // click ActionBarbutton，finish this page
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * set action title
     */
    protected void setActionBarTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {

            mPresenter.detachView();
        }
        super.onDestroy();
        if (mBind != null) {

            mBind.unbind();
        }
    }
}
