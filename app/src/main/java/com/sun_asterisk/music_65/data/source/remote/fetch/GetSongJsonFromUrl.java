package com.sun_asterisk.music_65.data.source.remote.fetch;

import android.os.AsyncTask;
import com.sun_asterisk.music_65.data.model.Song;
import com.sun_asterisk.music_65.data.source.remote.OnFetchDataJsonListener;
import org.json.JSONArray;
import org.json.JSONException;

public class GetSongJsonFromUrl extends AsyncTask<String, Void, String> {

    private OnFetchDataJsonListener<Song> mListener;
    private Exception mMyException;

    GetSongJsonFromUrl(OnFetchDataJsonListener<Song> listener) {
        mListener = listener;
    }

    @Override
    protected String doInBackground(String... strings) {
        String data = "";
        try {
            ParseDataWithJson parseDataWithJson = new ParseDataWithJson();
            data = parseDataWithJson.getJsonFromUrl(strings[0]);
        } catch (Exception e) {
            mMyException = e;
        }
        return data;
    }

    @Override
    protected void onPostExecute(String data) {
        super.onPostExecute(data);
        if (mMyException != null) {
            mListener.onError(mMyException);
        }
        if (data != null) {
            try {
                JSONArray jsonArray = new JSONArray(data);
                mListener.onSuccess(new ParseDataWithJson().parseJsonToData(jsonArray));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
