package com.gve.testapplication.core;

import android.util.Log;
import android.widget.ImageView;

import com.gve.testapplication.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static com.gve.testapplication.apium.albumlist.presentation.ListAlbumActivity.TAG;

/**
 * Created by gve on 16/11/2017.
 */

public class PicassoUtils {

    public static final int RADIUS = 10;
    public static final int MARGIN = 0;
    public static final Transformation TRANSFORMATION =
            new RoundedCornersTransformation(RADIUS, MARGIN);


    public static void showImageWithPicasso(Picasso picasso, ImageView imageIV, String url) {
        if (!url.isEmpty()) {
            Log.v(TAG, url);
            picasso.load(url)
                    .placeholder(R.drawable.rounded)
                    .fit()
                    .transform(TRANSFORMATION)
                    .centerCrop()
                    .error(R.drawable.rounded)
                    .into(imageIV);
        } else {
            imageIV.setImageDrawable(imageIV.getResources()
                    .getDrawable(R.drawable.rounded));
        }
    }

}
