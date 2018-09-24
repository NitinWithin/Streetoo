/*
* Copyright (C) 2014 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.nitinwithin.streetoo.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.example.nitinwithin.streetoo.R;
import com.example.nitinwithin.streetoo.VendorInfoActivity;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;

import java.net.MalformedURLException;

import static com.example.nitinwithin.streetoo.R.string.azure_url;

/**
 * Demonstrates the use of {@link RecyclerView} with a {@link LinearLayoutManager} and a
 * {@link GridLayoutManager}.
 */
public class RecyclerViewFragment extends Fragment {

    private static final String TAG = "RecyclerViewFragment";

    protected RecyclerView mRecyclerView;
    protected CustomAdapter mAdapter;

    protected String[] mDataset;
    private MobileServiceClient mobileServiceClient;
    private Context context;
    private static final int DATASET_COUNT = 60;

    public RecyclerViewFragment() {

    }

    @SuppressLint("ValidFragment")
    public RecyclerViewFragment(Context c)
    {
        context = c;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataset();
       fetchReviewData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycler_view_frag, container, false);
        rootView.setTag(TAG);

        // BEGIN_INCLUDE(initializeRecyclerView)
        mRecyclerView =  rootView.findViewById(R.id.recyclerView);
        mAdapter = new CustomAdapter(mDataset);
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        // END_INCLUDE(initializeRecyclerView)
        return rootView;
    }




    /**
     * Generates Strings for RecyclerView's adapter. This data would usually come
     * from a local content provider or remote server.
     */
    private void fetchReviewData() {
        try {
            mobileServiceClient =new MobileServiceClient(String.valueOf(R.string.azure_url),// Set up the login form.
                    context);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    private void initDataset() {
        mDataset = new String[DATASET_COUNT];
        for (int i = 0; i < DATASET_COUNT; i++) {
            mDataset[i] = "This is element #" + i;
        }
    }

}
