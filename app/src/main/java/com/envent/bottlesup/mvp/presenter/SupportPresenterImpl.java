package com.envent.bottlesup.mvp.presenter;

import com.envent.bottlesup.mvp.MVPView;

/**
 * Created by ronem on 5/20/18.
 */

public class SupportPresenterImpl implements MyPresenter.SupportPresenter {
    private MVPView.SupportView view;

    @Override
    public void cancelCall() {

    }

    @Override
    public void addView(MVPView.SupportView view) {
        this.view = view;
    }

    @Override
    public void getSupport() {

    }
}
