package com.example.nitinwithin.streetoo;

import android.Manifest;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nitinwithin.streetoo.Tables.USER;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.ExecutionException;



/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements Serializable{

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**Azure variables*/
   MobileServiceClient mobileServiceClient;


    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private MobileServiceTable<USER> mUserTable;
    private String TAG;
    private String filePath;
    private String pathToAppFolder;

    private Boolean mLocationPermissionsGranted = false;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int PERMISSION_REQUEST_CODE = 1234;
    private static final String STORAGE_ACCESS_WRITE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final String STORAGE_ACCESS_READ = Manifest.permission.READ_EXTERNAL_STORAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        pathToAppFolder = getExternalFilesDir(null).getAbsolutePath();

        filePath = pathToAppFolder + "/user.txt";

        getLocationPermission();
        //getStoragePermission();
    }

    private void initLogin()
    {

        mEmailView = findViewById(R.id.email);

        mPasswordView =  findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                return id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL;
            }
        });

        final Button mEmailSignInButton =  findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mEmailView.getText().toString().equals(""))
                {
                    //validateSuccess(results);
                    dbconnect(mEmailView.getText().toString(),mPasswordView.getText().toString());
                }
            }
        });
    }


    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                getStoragePermission();

            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    PERMISSION_REQUEST_CODE);
        }
    }

    private void getStoragePermission() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),STORAGE_ACCESS_WRITE) == PackageManager.PERMISSION_GRANTED)
        {
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),STORAGE_ACCESS_READ) == PackageManager.PERMISSION_GRANTED)
            {
                //readUserInfo();
                initLogin();
            }
            else
            {
                ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
            }
        }
        else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our Login
                    initLogin();
                }
            }
        }
    }


    @SuppressLint("StaticFieldLeak")
    public void dbconnect(final String mail, final String pass)
    {
        try {
            mobileServiceClient =new MobileServiceClient(
                    "https://streetoo.azurewebsites.net",// Set up the login form.
                    this);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        mUserTable = mobileServiceClient.getTable(USER.class);
          AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {

                try {
                    final List<USER> results = runQuery(mail, pass);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(results != null)
                            {
                                validateSuccess(results);

                            }
                            else
                            {
                                createAndShowDialog("User Email or Password invalid", " Login Failed");
                            }
                        }
                    });
                } catch (final Exception e){
                    Log.d(TAG, "doInBackground: ERROR: " + e.toString());
                    createAndShowDialogFromTask(e, "Error");
                }

                return null;
            }
        };

        runAsyncTask(task);
    }

    private void validateSuccess(List<USER> results) {
        writeUserInfo(results);
        //createAndShowDialog("Welcome "+results.get(0).getUser_name(),"Login Success");
//        Toast.makeText(LoginActivity.this,"SUCCESS",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
        startActivity(intent);
        finish();
    }



    private void writeUserInfo(List<USER> results) {
        try {
            FileOutputStream userFile = new FileOutputStream(String.valueOf(filePath));
            ObjectOutputStream objWriter = new ObjectOutputStream(userFile);
            objWriter.writeObject(results);
            objWriter.flush();
            objWriter.close();
            userFile.close();

            } catch (Exception e) {
            Log.d(TAG, "writeUserInfo: " + e.toString());
            e.printStackTrace();
        }
    }

    private void readUserInfo()
    {
        try
        {
            FileInputStream userFileRead = new FileInputStream(String.valueOf(filePath));
            Log.d(TAG, "writeUserInfo: FILE FOUND");
            ObjectInputStream objReader = new ObjectInputStream(userFileRead);
            List<USER> userObj = (List<USER>) objReader.readObject();

            if(!(userObj == null))
            {
               dbconnect(userObj.get(0).getUser_mail(),userObj.get(0).getUser_password());
               Toast.makeText(LoginActivity.this,"Welcome back",Toast.LENGTH_SHORT).show();
            }
            else
            {
                initLogin();
            }
            Toast.makeText(LoginActivity.this, userObj.get(0).getUser_name(),Toast.LENGTH_LONG).show();

        }
        catch (Exception e)
        {
            Log.d(TAG, "readUserInfo: " + e.getMessage());
        }

    }



    private List<USER> runQuery(String mail, String pass) throws ExecutionException, InterruptedException{

        return mUserTable.where()
                .field("user_mail").eq(mail)
                .and()
                .field("user_password").eq(pass)
                .execute().get();
    }

    private void createAndShowDialogFromTask(final Exception exception, String title) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                createAndShowDialog(exception, "Error");
            }
        });
    }
    private void createAndShowDialog(Exception exception, String title) {
        Throwable ex = exception;
        if(exception.getCause() != null){
            ex = exception.getCause();
        }
        createAndShowDialog(ex.getMessage(), title);
    }

    private void createAndShowDialog(final String message, final String title) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message);
        builder.setTitle(title);
        builder.create().show();
    }

    private AsyncTask<Void, Void, Void> runAsyncTask(AsyncTask<Void, Void, Void> task) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            return task.execute();
        }
    }
}