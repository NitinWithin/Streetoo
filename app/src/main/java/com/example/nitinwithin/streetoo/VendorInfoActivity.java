package com.example.nitinwithin.streetoo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.example.nitinwithin.streetoo.Tables.VENDOR;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class VendorInfoActivity extends AppCompatActivity {

    String vendorinfo;
    private MobileServiceTable<VENDOR> mVendorTable;
    MobileServiceClient mobileServiceClient;

    private String TAG = "vendorInfoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        vendorinfo = intent.getStringExtra("vendorId");
        Toast.makeText(VendorInfoActivity.this,"New Activity: " + vendorinfo, Toast.LENGTH_LONG).show();

        //fetchVendorInfo();
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
                                //validateSuccess();
                            }
                            else
                            {
                                //createAndShowDialog("User Email or Password invalid", " Login Failed");
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
                .eq(vendorinfo)
                .execute().get();
    }
}
