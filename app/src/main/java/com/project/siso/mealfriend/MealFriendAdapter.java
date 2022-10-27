package com.project.siso.mealfriend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.project.siso.databinding.ItemMealFriendBinding;

import java.util.ArrayList;

public class MealFriendAdapter extends RecyclerView.Adapter<MealFriendAdapter.ViewHolder> {
    Context context;
    ArrayList<MealFriend> mealFriendItems;

    public MealFriendAdapter(Context context, ArrayList<MealFriend> mealFriendItems) {
        this.context = context;
        this.mealFriendItems = mealFriendItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMealFriendBinding itemBinding = ItemMealFriendBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MealFriendAdapter.ViewHolder holder, int position) {
        holder.bindItem(mealFriendItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mealFriendItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ItemMealFriendBinding itemBinding;

        public ViewHolder(ItemMealFriendBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        void bindItem(MealFriend item) {
//            itemBinding.iv.setImageResource(item.imgResId);
            itemBinding.name.setText(item.getName());
            itemBinding.address.setText(item.getAddress());
            itemBinding.personnel.setText(Integer.toString(item.getMemNumber()));
        }
    }
}
