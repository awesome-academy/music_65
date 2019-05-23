package com.sun_asterisk.music_65.data.source.remote.fetch;

import com.sun_asterisk.music_65.data.model.Song;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseDataWithJson {

    private static final int TIME_OUT = 60000;
    private static final String METHOD_GET = "GET";

    public String getJsonFromUrl(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setConnectTimeout(TIME_OUT);
        httpURLConnection.setReadTimeout(TIME_OUT);
        httpURLConnection.setRequestMethod(METHOD_GET);
        httpURLConnection.connect();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        bufferedReader.close();
        httpURLConnection.disconnect();
        return stringBuilder.toString();
    }

    public List<Song> parseJsonToData(JSONArray jsonArray) {
        List<Song> songList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Song song = parseJsonToSong(jsonObject);
                songList.add(song);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return songList;
    }

    private Song parseJsonToSong(JSONObject jsonObject) {
        Song song = new Song();
        try {
            song = new Song.SongBuilder().id(jsonObject.getString(Song.SongEntry.ID))
                    .title(jsonObject.getString(Song.SongEntry.TITLE))
                    .duration(jsonObject.getString(Song.SongEntry.DURATION))
                    .genre(jsonObject.getString(Song.SongEntry.GENRE))
                    .user(jsonObject.getJSONObject(Song.SongEntry.USER))
                    .artworkUrl(jsonObject.getString(Song.SongEntry.ARTWORKURL))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return song;
    }
}
