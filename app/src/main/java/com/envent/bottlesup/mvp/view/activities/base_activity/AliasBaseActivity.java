package com.envent.bottlesup.mvp.view.activities.base_activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.envent.bottlesup.R;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.presenter.AliasPresenterIml;
import com.envent.bottlesup.mvp.presenter.MyPresenter;

import butterknife.BindView;

/**
 * Created by ronem on 5/7/18.
 */

public abstract class AliasBaseActivity extends SimpleBaseActivity implements MVPView.AliasView {

    public MyPresenter.AliasPresenter presenter;
    @BindView(R.id.alias_title)
    public TextView aliasTitle;
    @BindView(R.id.alias_detail)
    public TextView aliasDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        presenter = new AliasPresenterIml();
        presenter.addView(this);
    }

    protected abstract int getLayout();

}
