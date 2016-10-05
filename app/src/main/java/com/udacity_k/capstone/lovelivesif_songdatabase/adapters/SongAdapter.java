package com.udacity_k.capstone.lovelivesif_songdatabase.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.udacity_k.capstone.lovelivesif_songdatabase.R;
import com.udacity_k.capstone.lovelivesif_songdatabase.fragments.SongGridFragment;

/**
 * Created by Kenneth on 9/18/2016.
 */
public class SongAdapter extends RecyclerView.Adapter<SongAdapter.songImageViewHolder> {
    private Context mContext;
    private Cursor mCursor;

    public SongAdapter(Context context) {
        mContext = context;
    }

    @Override
    public songImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(parent instanceof RecyclerView) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_songimage, parent, false);
            return new songImageViewHolder(view);
        } else {
            throw new RuntimeException("Not bound to RecyclerView");
        }
    }

    @Override
    public void onBindViewHolder(songImageViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        String imageURL = mCursor.getString(SongGridFragment.COL_SONGIMAGE);

        if(null != imageURL) {
            Glide.with(mContext)
                    .load(imageURL)
                    .error(R.drawable.not_available_image)
                    .crossFade()
                    .into(holder.songImage);
        }
    }

    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    public Cursor getCursor() {
        return mCursor;
    }

    @Override
    public int getItemCount() {
        if(null == mCursor) {
            return 0;
        }
        return mCursor.getCount();
    }

    public static class songImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView songImage;

        public songImageViewHolder(View view) {
            super(view);
            songImage = (ImageView) view.findViewById(R.id.image_song);
        }
    }
}
