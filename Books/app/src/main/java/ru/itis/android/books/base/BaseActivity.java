package ru.itis.android.books.base;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ru.itis.android.books.R;

/**
 * Created by User on 11.12.2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        toolbar = (Toolbar) findViewById(getToolbarId());
        setSupportActionBar(toolbar);

        FragmentManager fm = getFragmentManager();
        if(fm.findFragmentById(getContainerId()) == null) {
            fm.beginTransaction()
                    .add(getContainerId(), getFragment())
                    .commit();
        }
    }

    @NonNull
    protected abstract Fragment getFragment();

    protected int getLayoutResId() {
        return R.layout.activity_base;
    }

    protected int getToolbarId() {
        return R.id.toolbar;
    }

    private int getContainerId() {
        return R.id.fragment_container;
    }

}
