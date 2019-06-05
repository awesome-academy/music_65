package com.sun_asterisk.music_65.utils;

import com.sun_asterisk.music_65.data.model.Song;
import java.util.List;

public interface OnItemClickListener {
    void onItemRecyclerViewClick(List<Song> songs, int position);
}
