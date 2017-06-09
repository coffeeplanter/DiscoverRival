package ru.coffeeplanter.discoverrival;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.lucasr.twowayview.widget.DividerItemDecoration;
import org.lucasr.twowayview.widget.SpannableGridLayoutManager;
import org.lucasr.twowayview.widget.TwoWayView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Gallery fragment.
 */

public class GalleryFragment extends Fragment {

    private final static String TAG = "GalleryFragment";

    TwoWayView mGallery;
    GalleryAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.gallery_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        String[] titles = getResources().getStringArray(R.array.titles);

        mGallery = (TwoWayView) view.findViewById(R.id.gallery);

        mAdapter = new GalleryAdapter(titles);

        Drawable divider = ContextCompat.getDrawable(getActivity(), R.drawable.divider);
        mGallery.addItemDecoration(new DividerItemDecoration(divider));

        mGallery.setAdapter(mAdapter);

    }

    private class GalleryViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout mLayout;
        ImageView mImageView;
        TextView mTextView;

        public GalleryViewHolder(View view) {
            super(view);
            mLayout = (ConstraintLayout) view.findViewById(R.id.layout_gallery_item);
            mImageView = (ImageView) view.findViewById(R.id.imageview_gallery_item);
            mTextView = (TextView) view.findViewById(R.id.textview_gallery_item);
        }

    }

    private class GalleryAdapter extends RecyclerView.Adapter<GalleryViewHolder> {

        private final static String TAG = "GalleryAdapter";

        private final String SERVER_URL = "http://lorempixel.com/";
        private final String URL_DIVIDER = "/";

        private int mStrategyFirstBlock;
        private int mStrategySecondBlock;
        private int mStrategySecondInnerBlock;
        private int mStrategyThirdInnerBlock;

        List<String> mGalleryItems;

        GalleryAdapter(String[] titlesArray) {
            List<String> shortList = Arrays.asList(titlesArray);
            mGalleryItems = new ArrayList<>(shortList);
            mGalleryItems.addAll(shortList);
            mGalleryItems.addAll(shortList);
            Collections.shuffle(mGalleryItems);
            mGalleryItems.remove(mGalleryItems.size() - 1);
            mGalleryItems.remove(mGalleryItems.size() - 1);
            Picasso.with(getActivity())
                    .setLoggingEnabled(true);
            Random random = new Random();
            mStrategyFirstBlock = random.nextInt(2);
            mStrategySecondBlock = random.nextInt(2);
            mStrategySecondInnerBlock = random.nextInt(3);
            mStrategyThirdInnerBlock = random.nextInt(3);
        }

        @Override
        public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.gallery_item, parent, false);
            return new GalleryViewHolder(view);
        }

        @Override
        public void onBindViewHolder(GalleryViewHolder holder, int position) {

            holder.mTextView.setText(mGalleryItems.get(position));

            SpannableGridLayoutManager.LayoutParams layoutParams =
                    (SpannableGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();

            int colSpan = 1;
            int rowSpan = 1;
            if ((position % 8 >= 0) && (position % 8 <= 2)) { // If it's the first three positions in block.
                if (mStrategyFirstBlock == 0) { // Choose from two layout types.
                    switch (position % 8) {
                        case 0:
                            colSpan = 4;
                            rowSpan = 8;
                            break;
                        case 1:
                            colSpan = 2;
                            rowSpan = 4;
                            break;
                        case 2:
                            colSpan = 2;
                            rowSpan = 4;
                            break;
                    }
                } else {
                    switch (position % 8) {
                        case 0:
                            colSpan = 2;
                            rowSpan = 4;
                            break;
                        case 1:
                            colSpan = 4;
                            rowSpan = 8;
                            break;
                        case 2:
                            colSpan = 2;
                            rowSpan = 4;
                            break;
                    }
                }
            }
            if (mStrategySecondBlock == 0) { // Choose second block layout (two or three elements).
                if (mStrategySecondInnerBlock == 0) { // If it consists from three elements, choose layout type.
                    switch (position % 8) {
                        case 3:
                            colSpan = 2;
                            rowSpan = 4;
                            break;
                        case 4:
                            colSpan = 4;
                            rowSpan = 8;
                            break;
                        case 5:
                            colSpan = 2;
                            rowSpan = 4;
                            break;
                    }
                } else if (mStrategySecondInnerBlock == 1) {
                    switch (position % 8) {
                        case 3:
                            colSpan = 4;
                            rowSpan = 8;
                            break;
                        case 4:
                            colSpan = 2;
                            rowSpan = 4;
                            break;
                        case 5:
                            colSpan = 2;
                            rowSpan = 4;
                            break;
                    }
                } else {
                    switch (position % 8) {
                        case 3:
                            colSpan = 2;
                            rowSpan = 4;
                            break;
                        case 4:
                            colSpan = 2;
                            rowSpan = 4;
                            break;
                        case 5:
                            colSpan = 2;
                            rowSpan = 4;
                            break;
                    }
                }
                switch (position % 8) {
                    case 6:
                        colSpan = 3;
                        rowSpan = 6;
                        break;
                    case 7:
                        colSpan = 3;
                        rowSpan = 6;
                        break;
                }
            } else { // If it consists from two elements, setting two-elements layout type.
                switch (position % 8) {
                    case 3:
                        colSpan = 3;
                        rowSpan = 6;
                        break;
                    case 4:
                        colSpan = 3;
                        rowSpan = 6;
                        break;
                }
                if (mStrategyThirdInnerBlock == 0) { // And choosing from three-elements layout type.
                    switch (position % 8) {
                        case 5:
                            colSpan = 2;
                            rowSpan = 4;
                            break;
                        case 6:
                            colSpan = 4;
                            rowSpan = 8;
                            break;
                        case 7:
                            colSpan = 2;
                            rowSpan = 4;
                            break;
                    }
                } else if (mStrategyThirdInnerBlock == 1) {
                    switch (position % 8) {
                        case 5:
                            colSpan = 4;
                            rowSpan = 8;
                            break;
                        case 6:
                            colSpan = 2;
                            rowSpan = 4;
                            break;
                        case 7:
                            colSpan = 2;
                            rowSpan = 4;
                            break;
                    }
                } else {
                    switch (position % 8) {
                        case 5:
                            colSpan = 2;
                            rowSpan = 4;
                            break;
                        case 6:
                            colSpan = 2;
                            rowSpan = 4;
                            break;
                        case 7:
                            colSpan = 2;
                            rowSpan = 4;
                            break;
                    }
                }
            }

            layoutParams.colSpan = colSpan;
            layoutParams.rowSpan = rowSpan;
            holder.itemView.setLayoutParams(layoutParams);

            int imageWidth = holder.mImageView.getMeasuredWidth();
            int imageHeight = holder.mImageView.getMeasuredHeight();

            final ConstraintLayout layout = holder.mLayout;
            final ImageView imageView = holder.mImageView;
            final int adapterPosition = holder.getAdapterPosition();

            if ((imageWidth != 0) && (imageHeight != 0)) {
                loadImage(imageWidth, imageHeight, holder.mImageView);
                Log.d(TAG, "Image " + position + " loaded from onBindViewHolder()");
            } else {
                layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        int imageWidth = imageView.getMeasuredWidth();
                        int imageHeight = imageView.getMeasuredHeight();
                        loadImage(imageWidth, imageHeight, imageView);
                        Log.d(TAG, "Image " + adapterPosition + " loaded from OnGlobalLayoutListener");
                    }
                });
            }

        }

        private void loadImage(int width, int height, ImageView destination) {
            Picasso.with(getActivity())
                    .load(SERVER_URL + width + URL_DIVIDER + height)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(destination);
        }

        @Override
        public int getItemCount() {
            return mGalleryItems.size();
        }

    }

}
