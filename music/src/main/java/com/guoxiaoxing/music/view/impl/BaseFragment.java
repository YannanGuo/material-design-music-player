package com.guoxiaoxing.music.view.impl;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.guoxiaoxing.music.MusicApp;
import com.guoxiaoxing.music.injection.AppComponent;
import com.guoxiaoxing.music.presenter.BasePresenter;

public abstract class BaseFragment extends Fragment {
    /**
     * Is this the first start of the fragment (after onCreate)
     */
    private boolean firstStart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firstStart = true;

        injectDependencies();

        if (savedInstanceState != null) {
            final BasePresenter presenter = getBasePresenter();
            if (presenter != null) {
                presenter.onRestoreState(savedInstanceState);
            }
        }
    }

    private void injectDependencies() {
        setupComponent(((MusicApp) getActivity().getApplication()).getAppComponent());
    }

    /**
     * Get the base presenter for this view
     *
     * @return the base presenter if any, null otherwise
     */
    @Nullable
    protected abstract BasePresenter getBasePresenter();

    /**
     * Setup the injection component for this view
     *
     * @param appComponent the app component
     */
    protected abstract void setupComponent(@NonNull AppComponent appComponent);

    @Override
    public void onStart() {
        super.onStart();

        final BasePresenter presenter = getBasePresenter();
        if (presenter != null) {
            presenter.onStart(firstStart);
        }

        firstStart = false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        final BasePresenter presenter = getBasePresenter();
        if (presenter != null) {
            presenter.onSaveInstanceState(outState);
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        final BasePresenter presenter = getBasePresenter();
        if (presenter != null) {
            presenter.onStop();
        }

        super.onStop();
    }
}
