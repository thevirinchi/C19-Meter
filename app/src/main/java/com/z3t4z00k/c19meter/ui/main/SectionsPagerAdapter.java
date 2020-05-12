package com.z3t4z00k.c19meter.ui.main;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.z3t4z00k.c19meter.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3, R.string.tab_text_4, R.string.tab_text_5, R.string.tab_text_6, R.string.tab_next_7, R.string.tab_next_8};
    private final Context mContext;
    private String TAG = "SectionsPagerAdapter";
    private String state;

    public SectionsPagerAdapter(Context context, FragmentManager fm, String state) {
        super(fm);
        mContext = context;
        this.state = state;
    }
    

    @NonNull
    @Override
    public Fragment getItem(final int position) {
        return PlaceholderFragment.newInstance(position, state);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 8;
    }
}