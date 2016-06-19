package com.dmitrymalkovich.android.builditbigger;

import android.os.AsyncTask;

import com.dmitrymalkovich.android.builditbigger.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

public class RetrieveJokeTask extends AsyncTask<Void, Void, String> {

    @SuppressWarnings("unused")
    public static String LOG_TAG = RetrieveJokeTask.class.getSimpleName();
    private final Listener mListener;
    private static MyApi myApiService = null;

    /**
     * Interface definition for a callback to be invoked when reviews are loaded.
     */
    interface Listener {
        void onJokeLoaded(String joke);
    }

    public RetrieveJokeTask(Listener listener) {
        mListener = listener;
    }

    @Override
    protected String doInBackground(Void... params) {
        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?>
                                                       abstractGoogleClientRequest)
                                throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        try {
            return myApiService.randomJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String joke) {
        mListener.onJokeLoaded(joke);
    }
}
