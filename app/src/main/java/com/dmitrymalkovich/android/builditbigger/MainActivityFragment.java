package com.dmitrymalkovich.android.builditbigger;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dmitrymalkovich.android.jokes.viewer.JokeViewerActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivityFragment extends Fragment {

    private Unbinder mUnBinder;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        mUnBinder = ButterKnife.bind(this, root);

        Button button = ButterKnife.findById(root, R.id.button_show_joke);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new RetrieveJokeTask(new RetrieveJokeTask.Listener() {

                    @Override
                    public void onJokeLoaded(String joke) {
                        Intent intent = new Intent(getContext(), JokeViewerActivity.class);
                        intent.putExtra(JokeViewerActivity.EXTRA_JOKE, joke);
                        startActivity(intent);
                    }
                }).execute();
            }
        });

        AdView mAdView = ButterKnife.findById(root, R.id.ad_view);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }
}
