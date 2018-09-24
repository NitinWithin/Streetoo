package com.example.nitinwithin.streetoo;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionBarContainer;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nitinwithin.streetoo.Tables.RATING_AND_REVIEW;
import com.example.nitinwithin.streetoo.Tables.VENDOR;
import com.example.nitinwithin.streetoo.recyclerview.RecyclerViewFragment;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.microsoft.windowsazure.mobileservices.table.query.QueryOperations.val;

public class VendorInfoActivity extends AppCompatActivity {

    String vendorinfo;
    private MobileServiceTable<VENDOR> mVendorTable;
    private MobileServiceTable<RATING_AND_REVIEW> mRatingReviewTable;
    private MobileServiceClient mobileServiceClient;

    private String TAG = "vendorInfoActivity";

    /**UI COMPONENT DECLARATIONS*/
    private TextView statusTextView, ratingTextView, cuisineTextView, rateUsTextView, avgCost;
    private TextView vendorOwnerTextView, vendorContactTextView, vendorDescriptionView;
    private RatingBar vendorRatingbar;
    private Button OnlineOrderButton;
    private Toolbar toolbar;

    CollapsingToolbarLayout toolbarLayout;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_info);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        vendorinfo = intent.getStringExtra("vendorId");
        Toast.makeText(VendorInfoActivity.this,"New Activity: " + vendorinfo, Toast.LENGTH_LONG).show();

        statusTextView = findViewById(R.id.textViewStatus);
        ratingTextView = findViewById(R.id.textViewRating);
        cuisineTextView = findViewById(R.id.textViewCuisine);
        vendorRatingbar = findViewById(R.id.VendorRating);
        OnlineOrderButton = findViewById(R.id.OnlineOrderButton);
        toolbarLayout = findViewById(R.id.toolbar_layout);
        vendorOwnerTextView = findViewById(R.id.OwnerNametextView);
        vendorContactTextView = findViewById(R.id.OwnerContacttextView);
        rateUsTextView = findViewById(R.id.RatingtextView);
        vendorDescriptionView = findViewById(R.id.vendorDescriptionView);
        avgCost = findViewById(R.id.avgView);

        try {
            mobileServiceClient =new MobileServiceClient(
                    getString(R.string.azure_url),// Set up the login form.
                    VendorInfoActivity.this);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        fetchVendorInfo();
        ratingBarChange();
        onlineFoodOrder();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(VendorInfoActivity.this, MapsActivity.class);
        startActivity(intent);
        finish();
        // your code.
    }

    @SuppressLint("StaticFieldLeak")
    private void fetchVendorInfo() {

        mVendorTable = mobileServiceClient.getTable(VENDOR.class);
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {

                try {
                    final List<VENDOR> results = runQuery();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(results != null)
                            {
                                for(VENDOR item : results)
                                {
                                    Log.d(TAG, "run: RECORD FOUND : " + item.getVendorName());
                                    statusTextView.setText("Open");
                                    statusTextView.setTextColor(getColor(R.color.colorVendorStatusOpen));
                                    cuisineTextView.setText("Cuisine: " + item.getVendorCuisine());
                                    Toast.makeText(VendorInfoActivity.this,item.getVendorName(),Toast.LENGTH_SHORT).show();
                                    toolbarLayout.setTitle(item.getVendorName());
                                    vendorContactTextView.setText("Mobile: " + item.getVendorContact());
                                    vendorOwnerTextView.setText("Owner Name: " + item.getVendorOwner());
                                    vendorDescriptionView.setText(item.getVendorDescription());
                                    avgCost.setText("Cost for 2 : " + String.valueOf(item.getVendorAvgCost()));
                                    ratingTextView.setText("Rating : " + String.valueOf(item.getVendorAvgRating()));
                                }
                            }
                            else
                            {
                                Toast.makeText(VendorInfoActivity.this, "Empty",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (final Exception e){
                    Log.d(TAG, "doInBackground: ERROR: " + e.toString());
                    //createAndShowDialogFromTask(e, "Error");
                }

                return null;
            }
        };

        runAsyncTask(task);
    }

    private void ratingBarChange()
    {
        vendorRatingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rateUsTextView.setText("Thank you for Rating us!!");
                if(CheckUserRating())
                {}
            }
        });
    }

    private boolean CheckUserRating() {
        try {
            mobileServiceClient =new MobileServiceClient(
                    getString(R.string.azure_url),// Set up the login form.
                    this);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        mRatingReviewTable = mobileServiceClient.getTable(RATING_AND_REVIEW.class);
        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {

                try {
                    final List<RATING_AND_REVIEW> results = runQuery3();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(results != null)
                            {
                                for(RATING_AND_REVIEW item : results)
                                {


                                }
                            }
                            else
                            {
                                Toast.makeText(VendorInfoActivity.this, "Empty",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (final Exception e){
                    Log.d(TAG, "doInBackground: ERROR: " + e.toString());
                    //createAndShowDialogFromTask(e, "Error");
                }

                return null;
            }
        };

        runAsyncTask(task);
        return false;
    }

    private List<RATING_AND_REVIEW> runQuery3() {
        return null;
    }


    private AsyncTask<Void, Void, Void> runAsyncTask(AsyncTask<Void, Void, Void> task) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            return task.execute();
        }
    }

    private List<VENDOR> runQuery() throws ExecutionException, InterruptedException {
        return mVendorTable.where()
                .field("id")
                .eq(val(vendorinfo))
                .execute().get();
    }


    private void onlineFoodOrder() {
        startActivity(new Intent(VendorInfoActivity.this, OnlineOrderActivity.class));
    }

}
