package com.envent.bottlesup.mvp.view.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.envent.bottlesup.R;
import com.envent.bottlesup.adapter.FoodItemAdapter;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.CheckedInVenue;
import com.envent.bottlesup.mvp.model.TableBar;
import com.envent.bottlesup.mvp.model.User;
import com.envent.bottlesup.mvp.model.server_response.fooditems.FoodItemResponse;
import com.envent.bottlesup.mvp.model.server_response.fooditems.FoodItems;
import com.envent.bottlesup.mvp.model.server_response.fooditems.SearchedItemResponse;
import com.envent.bottlesup.mvp.presenter.FoodItemPresenterImpl;
import com.envent.bottlesup.mvp.presenter.MyCartPresenterImpl;
import com.envent.bottlesup.mvp.presenter.MyPresenter;
import com.envent.bottlesup.mvp.view.fragments.base_fragment.RecyclerViewAndEmptyLayoutBaseFragment;
import com.envent.bottlesup.utils.MetaData;
import com.envent.bottlesup.utils.RecyclerItemClickListener;
import com.envent.bottlesup.utils.SessionManager;
import com.envent.bottlesup.utils.UtilityMethods;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FoodDrinkSearchActivity extends AppCompatActivity implements RecyclerItemClickListener.OnItemClickListener,MVPView.FoodItemView {



    @BindView(R.id.navigation_back)
    AppCompatImageView navigationBack;

    @BindView(R.id.search_icon)
    AppCompatImageView searchIcon;

    @BindView(R.id.search_food_drink)
    EditText searchEditBox;

    @BindView(R.id.recycler_view_search)
    RecyclerView recyclerView;



    private Unbinder unbinder;
    public CheckedInVenue checkedInVenue;
    private SessionManager sessionManager;
    private ProgressDialog progressDialog;
    private MyPresenter.FoodItemPresenter presenter;
    private List<FoodItems> foods;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.mysearch_activity_layout);
        unbinder = ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        //presenter = new MyCartPresenterImpl();
        //presenter.addView(this);

        checkedInVenue = CheckedInVenue.getCheckedInVenue();
        presenter = new FoodItemPresenterImpl();
        presenter.addView(this);

        recyclerView.setVisibility(View.GONE);

        setSearchEditTextEvent();

       // setUpProgressDialog();

    }

    private void setUpProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(MetaData.MESSAGE.PLEASE_WAIT);
        progressDialog.setCancelable(false);
    }



    @Override
    public void onFoodItemsResponse(FoodItemResponse foodItemResponse) {

    }

    @Override
    public void onSearchedFoodItemsResponse(SearchedItemResponse response) {

        if (response.getData() != null && response.getData().size() > 0) {

            recyclerView.setVisibility(View.VISIBLE);
            this.foods = response.getData();
            Log.d("list", this.foods.toString());
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new FoodItemAdapter(foods, this));
        }

    }

    private void setOnRecyclerViewItemTouchListener() {
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, (recyclerView1, view, position) -> {
            Intent detailIntent = new Intent(this, DetailFoodActivity.class);
            detailIntent.putExtra(MetaData.KEY.ITEM, foods.get(position));
            detailIntent.putExtra(MetaData.KEY.CATEGORY_ID, foods.get(position).getId());

            startActivity(detailIntent);
        }));
    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void unAuthorized() {

    }

    @Override
    public void showSwipeProgress() {

    }

    @Override
    public void hideSwipeProgress() {

    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View view, int position) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.navigation_back})
    public void onBackButtonClicked() {
        onBackPressed();
    }

    private void setSearchEditTextEvent() {
        searchEditBox.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                UtilityMethods.hideKeyboard(this, searchEditBox);
                return true;
            }
            return false;
        });

        searchEditBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int searchQueryLength = s.toString().length();
                if (searchQueryLength > 1) {

                    fireSearchQuery();
                }else {
                    recyclerView.setVisibility(View.GONE);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void fireSearchQuery() {

        String searchText = searchEditBox.getText().toString().trim();
        if (!TextUtils.isEmpty(searchText)) {
            presenter.getSearchedFood(checkedInVenue.getVenueId(), searchText, User.getUser().getAccess_token());
        }
    }
}
