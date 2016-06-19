package com.dmitrymalkovich.android.jokes.viewer;

import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.ButterKnife;

public class JokeViewerActivity extends AppCompatActivity {

    public static final String EXTRA_JOKE = "EXTRA_JOKE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_viewer);

        if (getIntent() != null && getIntent().hasExtra(EXTRA_JOKE)) {
            TextView jokeTextView = ButterKnife.findById(this, R.id.joke);
            jokeTextView.setText(getIntent().getStringExtra(EXTRA_JOKE));
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case (android.R.id.home):
                default:
                    onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
