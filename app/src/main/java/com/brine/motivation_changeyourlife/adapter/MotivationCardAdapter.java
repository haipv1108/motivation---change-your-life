package com.brine.motivation_changeyourlife.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.brine.motivation_changeyourlife.R;
import com.brine.motivation_changeyourlife.model.MotivationCard;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

import static com.brine.motivation_changeyourlife.model.MotivationCard.STATUS_DONE;
import static com.brine.motivation_changeyourlife.model.MotivationCard.STATUS_PROCESSING;
import static com.brine.motivation_changeyourlife.model.MotivationCard.STATUS_WAIT_YOU;

/**
 * Created by phamhai on 15/03/2017.
 */

public class MotivationCardAdapter extends RecyclerView.Adapter<MotivationCardAdapter.ViewHolder> {

    private Context mContext;
    private List<MotivationCard> mMotivationCards;
    private MotivationCardCallback mCallback;

    public interface MotivationCardCallback{
        void onClickCard(MotivationCard motivationCard);
    }

    public MotivationCardAdapter(Context _context, List<MotivationCard> _motivationCards,
                                 MotivationCardCallback _callback){
        this.mContext = _context;
        this.mMotivationCards = _motivationCards;
        this.mCallback = _callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.motivation_card_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final MotivationCard motivationCard = mMotivationCards.get(position);
        holder.motivationId.setText(String.valueOf(motivationCard.getId()));
        holder.motivationTitle.setText(motivationCard.getTitle());
        int res = mContext.getResources().getIdentifier(
                motivationCard.getImage(),
                "drawable",
                mContext.getPackageName());
        Glide.with(mContext)
                .load(res)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(400, 500) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Drawable drawable = new BitmapDrawable(resource);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            holder.motivationImage.setBackground(drawable);
                        }
                    }
                });
        switch (motivationCard.getStatus()){
            case STATUS_WAIT_YOU:
                int res_label_wait_you = mContext.getResources().getIdentifier(
                        "label_wait_you",
                        "drawable",
                        mContext.getPackageName());
                Glide.with(mContext)
                        .load(res_label_wait_you)
                        .asBitmap()
                        .into(holder.motivationStatus);
                break;
            case STATUS_PROCESSING:
                int res_label_processing = mContext.getResources().getIdentifier(
                        "label_processing",
                        "drawable",
                        mContext.getPackageName());
                Glide.with(mContext)
                        .load(res_label_processing)
                        .asBitmap()
                        .into(holder.motivationStatus);
                break;
            case STATUS_DONE:
                int res_label_done = mContext.getResources().getIdentifier(
                        "label_done",
                        "drawable",
                        mContext.getPackageName());
                Glide.with(mContext)
                        .load(res_label_done)
                        .asBitmap()
                        .into(holder.motivationStatus);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mMotivationCards.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView motivationId;
        TextView motivationTitle;
        RelativeLayout motivationImage;
        ImageView motivationStatus;

        ViewHolder(View view){
            super(view);
            motivationId = (TextView) view.findViewById(R.id.motivation_id);
            motivationTitle = (TextView) view.findViewById(R.id.motivation_title);
            motivationImage = (RelativeLayout) view.findViewById(R.id.motivation_image);
            motivationStatus = (ImageView) view.findViewById(R.id.motivation_status);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            MotivationCard motivationCard = mMotivationCards.get(position);
            mCallback.onClickCard(motivationCard);
        }
    }
}
