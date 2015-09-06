package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.test.AndroidTestCase;
import android.test.ApplicationTestCase;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends AndroidTestCase implements ServerAsyncTask.AsyncTestListener {
    private String result;
    final CountDownLatch signal = new CountDownLatch(1);
    public ApplicationTest(){
        super();
        ServerAsyncTask.mTestListener = this;
    }

    public void testString() throws InterruptedException, ExecutionException{
        ServerAsyncTask task = new ServerAsyncTask();
        task.execute(getContext());
        signal.await(30,TimeUnit.SECONDS);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Override
    public void checkString(String result) {
        this.result = result;
        signal.countDown();
    }
}
