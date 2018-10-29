package com.enhan.sabina.speedy.tasks;

import android.content.Context;
import android.util.Log;

import com.enhan.sabina.speedy.SpeedyApplication;
import com.enhan.sabina.speedy.callbacks.DetectActivityCallback;
import com.enhan.sabina.speedy.callbacks.GetDefinitionCallback;
import com.enhan.sabina.speedy.data.DataRepository;

import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetDefinitionTask {

    private static GetDefinitionTask INSTANCE;
    private String mWord;
    private String mWordnikApi;
    private DetectActivityCallback mCallback;

    public GetDefinitionTask(String word, String wordnikApi, DetectActivityCallback callback) {
        mWord = word;
        mWordnikApi = wordnikApi;
        mCallback = callback;
    }

    public void getDefinition(){
       String url = builUrl();
       OkHttpClient client = new OkHttpClient();
       if (url != null) {
           final Request request = new Request.Builder()
                   .url(url)
                   .build();
           Call call = client.newCall(request);
           call.enqueue(new Callback() {
               Context mContext = SpeedyApplication.getAppContext();
               @Override
               public void onFailure(Call call, IOException e) {

               }

               @Override
               public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        Log.d("getdefinition"," body " + response.body().toString());
                        mCallback.onDefinitionGotten(response.body().string());
                    }
               }
           });
       }
    }

    private String builUrl() {
        String url = null;
        try {
            HttpUrl.Builder builder = HttpUrl.parse("https://api.wordnik.com/v4/word.json/" +
                    mWord + "/definitions").newBuilder();
            builder.addQueryParameter("limit","1");
            builder.addQueryParameter("includeRelated","false");
            builder.addQueryParameter("sourceDictionaries","wiktionary");
            builder.addQueryParameter("useCanonical","true");
            builder.addQueryParameter("includeTags" ,"false");
            builder.addQueryParameter("api_key",mWordnikApi);
            url = builder.build().toString();

        } catch ( Exception e) {
            e.printStackTrace();
        }

        return url;
    }




}
