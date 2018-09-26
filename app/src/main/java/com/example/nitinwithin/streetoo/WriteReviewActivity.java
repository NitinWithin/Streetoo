package com.example.nitinwithin.streetoo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nitinwithin.streetoo.Tables.RATING_AND_REVIEW;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class WriteReviewActivity extends AppCompatActivity {

    private Button submitButton;
    private EditText reviewBox;

    private String userName, userID, vendorID;
    private float rating;
    private MobileServiceClient mobileServiceClient;
    private MobileServiceTable<RATING_AND_REVIEW> mRatingReviewTable;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        submitButton = findViewById(R.id.ReviewSubmit);
        reviewBox = findViewById(R.id.ReviewEditBox);

        InsertReview();
    }

    private void InsertReview() {

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!reviewBox.getText().toString().isEmpty())
                {
                    Toast.makeText(WriteReviewActivity.this, "Inserting...",Toast.LENGTH_SHORT).show();
                    InsertDB();
                }
                else
                {
                    Toast.makeText(WriteReviewActivity.this, "Insert Fail",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void InsertDB() {
        userID = getIntent().getStringExtra("userID");
        userName = getIntent().getStringExtra("username");
        vendorID = getIntent().getStringExtra("vendorid");
        rating = getIntent().getFloatExtra("rating", 0);

        final RATING_AND_REVIEW rr = new RATING_AND_REVIEW();
        rr.setUserNameReview(userName);
        rr.setReveiw(reviewBox.getText().toString());
        rr.setRating(rating);
        rr.setVendor_id(vendorID);
        rr.setUser_id(userID);

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
                    final RATING_AND_REVIEW results = addItemInTable(rr);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            /*if(results != null)
                            {
                                Toast.makeText(WriteReviewActivity.this, "Successful",Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                            else
                            {
                                Toast.makeText(WriteReviewActivity.this, "Empty",Toast.LENGTH_SHORT).show();
                            }*/
                            Toast.makeText(WriteReviewActivity.this,"After Insert: " + String.valueOf(results.getRating()),Toast.LENGTH_SHORT).show();
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

    private AsyncTask<Void, Void, Void> runAsyncTask(AsyncTask<Void, Void, Void> task) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            return task.execute();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(WriteReviewActivity.this,VendorInfoActivity.class);
        intent.putExtra("vendorId", vendorID);
        startActivity(intent);
        finish();
        // your code.
    }

    public RATING_AND_REVIEW addItemInTable(RATING_AND_REVIEW item) throws ExecutionException, InterruptedException {
        RATING_AND_REVIEW entity = mRatingReviewTable.insert(item).get();
        return entity;
    }


}
