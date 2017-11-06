package com.gve.testapplication.apium.albumdetail.presentation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gve.testapplication.R;
import com.gve.testapplication.apium.albumdetail.data.Song;
import com.gve.testapplication.core.injection.qualifiers.ForActivity;
import com.gve.testapplication.core.recyclerview.DisplayableItem;
import com.gve.testapplication.core.recyclerview.ViewHolderBinder;
import com.gve.testapplication.core.recyclerview.ViewHolderFactory;

import javax.inject.Inject;

public class SongViewHolder extends RecyclerView.ViewHolder {

    private TextView songNameTV;
    private TextView durationTV;

    public SongViewHolder(final View itemView) {
        super(itemView);
        songNameTV = itemView.findViewById(R.id.song_row_title);
        durationTV = itemView.findViewById(R.id.song_row_duration);
    }

    private void bind(@NonNull final Song song) {
        songNameTV.setText(durationTV.getResources().getString(R.string.song_detail_name,
                song.getName()));
        durationTV.setText(durationTV.getResources().getString(R.string.song_detail_duration,
                Song.Companion.timeFromMillis(song.getDuration())));
    }

    static class SongCardHolderFactory extends ViewHolderFactory {

        @Inject
        SongCardHolderFactory(@NonNull @ForActivity final Context context) {
            super(context);
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder createViewHolder(@NonNull final ViewGroup parent) {
            return new SongViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.song_recycler_row, parent, false));
        }
    }

    static class SongCardHolderBinder implements ViewHolderBinder {

        @Inject
        SongCardHolderBinder() { }

        @Override
        public void bind(@NonNull final RecyclerView.ViewHolder viewHolder,
                         @NonNull final DisplayableItem item) {
            SongViewHolder castedViewHolder = SongViewHolder.class.cast(viewHolder);
            Song viewEntity = Song.class.cast(item.model());
            castedViewHolder.bind(viewEntity);
        }
    }
}
