package com.envent.bottlesup.mvp.view.fragments.base_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.envent.bottlesup.R;
import com.envent.bottlesup.mvp.model.TutorialData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ronem on 5/6/18.
 */

public class FragmentTutorial extends Fragment {

    @BindView(R.id.imageThumb)
    ImageView imageThumb;
    @BindView(R.id.descriptionTV)
    TextView descriptionTv;

    private Unbinder unbinder;

    public static FragmentTutorial createNewInstance(TutorialData tutorialData) {
        Bundle box = new Bundle();
        box.putInt("image", tutorialData.getImg());
        box.putString("description", tutorialData.getDescription());
        FragmentTutorial fragmentTutorial = new FragmentTutorial();
        fragmentTutorial.setArguments(box);
        return fragmentTutorial;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tutorial, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle box = getArguments();
        int image = box.getInt("image");
        String description = box.getString("description");
        imageThumb.setImageResource(image);
        descriptionTv.setText(description);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
