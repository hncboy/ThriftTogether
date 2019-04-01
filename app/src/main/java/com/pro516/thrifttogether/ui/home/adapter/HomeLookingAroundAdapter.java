package com.pro516.thrifttogether.ui.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.entity.home.LookingAroundItem;

import java.util.List;

/**
 * Created by hncboy on 2019-04-01.
 */
public class HomeLookingAroundAdapter extends RecyclerView.Adapter<HomeLookingAroundAdapter.CardItemViewHolder>  {

    private Context mContext;
    private List<LookingAroundItem> mLookingAroundItems;

    @NonNull
    @Override
    public HomeLookingAroundAdapter.CardItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home_looking_around,
                viewGroup, false);
        return new CardItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeLookingAroundAdapter.CardItemViewHolder contactItemViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    static class CardItemViewHolder extends RecyclerView.ViewHolder {

        CardItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
