package com.z3t4z00k.c19meter.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.z3t4z00k.c19meter.DistrictModal;
import com.z3t4z00k.c19meter.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String TAG = "PlaceHolderFragment";

    private PageViewModel pageViewModel;

    public static PlaceholderFragment newInstance(int index, String state) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        bundle.putString("s", state);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
            Log.d(TAG, "onCreate: "+ index);
        }
        pageViewModel.setIndex(index);
        pageViewModel.setState(getArguments().getString("s"));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        final String response = sharedPreferences.getString("res", "NULL");
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        final RecyclerView recycler = root.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        if(response.equals("NULL"))
            Toast.makeText(getContext(), "Error while fetching records. Plaese try again.", Toast.LENGTH_LONG).show();
        else {
            pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<Integer>() {
                @Override
                public void onChanged(@Nullable Integer s) {
                    Log.d(TAG, "onChanged: State="+pageViewModel.getState());
                    String state = pageViewModel.getState();
                    Log.d(TAG, "onCreateView: Type=" + s);
                    String type = null;
                    switch (s) {
                        case 0:
                            type = "CoVID-19 Testing Lab";
                            break;
                        case 1:
                            type = "Free Food";
                            break;
                        case 2:
                            type = "Fundraisers";
                            break;
                        case 3:
                            type = "Hospitals and Centers";
                            break;
                        case 4:
                            type = "Delivery [Vegetables, Fruits, Groceries, Medicines, etc.]";
                            break;
                        case 5:
                            type="Government Helpline";
                            break;
                        case 6:
                            type="Community Kitchen";
                            break;
                        case 7:
                            type="Other";
                            break;
                    }
                    JSONArray resources = null;
                    try {
                        resources = new JSONArray(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(resources!=null){
                        JSONObject resource = null;
                        for (int i = 0; i < resources.length(); i++){
                            try {
                                if(resources.getJSONObject(i).getString("state").equals(state) && resources.getJSONObject(i).getString("category").equals(type)){
                                    resource = resources.getJSONObject(i);
                                    Log.d(TAG, "onChanged: resource=" + resource.getString("nameoftheorganisation"));

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
        }
        return root;
    }
}