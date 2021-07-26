package com.example.hamiltontevin_ce07;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NetworkTask.OnFinished {

    private NetworkTask networkTask = null;
    private ProgressDialog pDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        networkTask = new NetworkTask(this);
        downloadBooks();
    }

    // check internet connection
    private boolean isConnected(){
        ConnectivityManager mgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        if(mgr != null){
            NetworkInfo info = mgr.getActiveNetworkInfo();
            if(info != null){
                return info.isConnected();
            }
        }
        return false;
    }

    private void downloadBooks(){
        if(!isConnected()){
            Toast.makeText(this,R.string.not_connected,Toast.LENGTH_SHORT).show();
            return;
        }
        networkTask.execute("https://www.googleapis.com/books/v1/volumes?q=android;");
    }

    @Override
    public void onPre() {
        pDialog = ProgressDialog.show(this,getString(R.string.wait),getString(R.string.download_data), true);
    }

    @Override
    public void onPost(ArrayList books) {

         if (books!=null){

             BooksAdapter ba = new BooksAdapter(this , books);
             GridView mainGridView = findViewById(R.id.gridView_bookDisplay);
             mainGridView.setAdapter(ba);
             pDialog.cancel();
         }
    }

}
