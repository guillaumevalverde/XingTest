package com.gve.testapplication.apium.albumlist.presentation;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gve.testapplication.R;
import com.gve.testapplication.apium.albumdetail.presentation.ListSongActivity;
import com.gve.testapplication.apium.albumlist.data.Album;
import com.gve.testapplication.apium.albumlist.data.ConstItunes;
import com.gve.testapplication.core.PicassoUtils;
import com.gve.testapplication.core.recyclerview.DisplayableItem;
import com.gve.testapplication.core.recyclerview.ViewHolderBinder;
import com.gve.testapplication.core.recyclerview.ViewHolderFactory;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class AlbumViewHolder extends RecyclerView.ViewHolder {

    private TextView albumNameTV;
    private TextView artistNameTV;
    private TextView trackCountTV;
    private ImageView imageIV;
    private Picasso picasso;
    private View card;
    private Activity activity;


    public AlbumViewHolder(final View itemView, Activity activity, Picasso picasso) {
        super(itemView);
        card = itemView;
        albumNameTV = itemView.findViewById(R.id.album_card_title);
        artistNameTV = itemView.findViewById(R.id.album_card_artist_name);
        trackCountTV = itemView.findViewById(R.id.album_card_count_track);
        imageIV = itemView.findViewById(R.id.album_card_image);
        this.picasso = picasso;
        this.activity = activity;
    }

    private void bind(@NonNull final Album album) {
        albumNameTV.setText(album.getName());
        artistNameTV.setText(album.getArtistName());
        trackCountTV.setText("" + album.getTrackCount());
        PicassoUtils.showImageWithPicasso(picasso, imageIV, album.getThumbnail());

        card.setOnClickListener(click -> startNextActivity(album));
    }

    private void startNextActivity(Album album) {
        Intent intent = new Intent(card.getContext(), ListSongActivity.class);

        intent.putExtra(ConstItunes.ALBUM_TYPE_ID, album.getId());
        intent.putExtra(ConstItunes.ALBUM_TYPE_NAME, album.getName());
        intent.putExtra(ConstItunes.ALBUM_TYPE_ARTIST_NAME, album.getArtistName());
        intent.putExtra(ConstItunes.ALBUM_TYPE_TRACK, album.getTrackCount());
        intent.putExtra(ConstItunes.ALBUM_TYPE_THUMBNAIL, album.getThumbnail());

        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(activity, (View) imageIV, "profile");
        card.getContext().startActivity(intent, options.toBundle());
    }

    static class AlbumCardHolderFactory extends ViewHolderFactory {

        private Picasso picasso;
        private Activity activity;

        @Inject
        AlbumCardHolderFactory(@NonNull final Activity activity, Picasso picasso) {
            super(activity.getBaseContext());
            this.picasso = picasso;
            this.activity = activity;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder createViewHolder(@NonNull final ViewGroup parent) {
            return new AlbumViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.album_recycler_row, parent, false), activity, picasso);
        }
    }

    static class AlbumCardHolderBinder implements ViewHolderBinder {

        @Inject
        AlbumCardHolderBinder() { }

        @Override
        public void bind(@NonNull final RecyclerView.ViewHolder viewHolder,
                         @NonNull final DisplayableItem item) {
            AlbumViewHolder castedViewHolder = AlbumViewHolder.class.cast(viewHolder);
            Album viewEntity = Album.class.cast(item.model());
            castedViewHolder.bind(viewEntity);
        }
    }
}
