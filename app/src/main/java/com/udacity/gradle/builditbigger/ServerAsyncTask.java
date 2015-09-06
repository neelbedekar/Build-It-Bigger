package com.udacity.gradle.builditbigger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.view.View;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.gradle.udacity.builditbigger.androidjoke.JokeActivity;
import com.udacity.builditbigger.backend.myApi.MyApi;


import java.io.IOException;

/**
 * Created by Milind Bedekar on 8/28/2015.
 */

class ServerAsyncTask extends AsyncTask<Context, Void, String> {
    private static MyApi myApiService = null;
    private Context context;
    public static AsyncTestListener mTestListener;
    @Override
    protected String doInBackground(Context... params) {
        try{
            Thread.sleep(500);
        }catch (InterruptedException i){}
        context = params[0];
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl(context.getString(R.string.backend_root_url));
            // end options for devappserver
            myApiService = builder.build();
        }


        try {
            String result = myApiService.fetchJoke().execute().getData();
            if(mTestListener != null){
                mTestListener.checkString(result);
            }
            return result;
        } catch (IOException e) {
            return e.getMessage();
        }
    }


    @Override
    protected void onPostExecute(String result) {
        Intent intent = new Intent(context, JokeActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, result);
        context.startActivity(intent);
        MainActivity.spinner.setVisibility(View.GONE);
    }
    public interface AsyncTestListener{
        void checkString(String result);
    }
}


