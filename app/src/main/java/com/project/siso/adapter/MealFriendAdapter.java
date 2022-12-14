package com.project.siso.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.project.siso.databinding.ItemMealFriendBinding;
import com.project.siso.home.HomeActivity;
import com.project.siso.mealfriend.MealFriendPopUpActivity;
import com.project.siso.mealfriend.MealFriends;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MealFriendAdapter extends RecyclerView.Adapter<MealFriendAdapter.ViewHolder> {
    Context context;
    ArrayList<MealFriends> mealFriendsItems;

    public MealFriendAdapter(Context context, ArrayList<MealFriends> mealFriendsItems) {
        this.context = context;
        this.mealFriendsItems = mealFriendsItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMealFriendBinding itemBinding = ItemMealFriendBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MealFriendAdapter.ViewHolder holder, int position) {
        holder.bindItem(mealFriendsItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mealFriendsItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ItemMealFriendBinding itemBinding;

        public ViewHolder(ItemMealFriendBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        void bindItem(MealFriends item) {
//            itemBinding.iv.setImageResource(item.imgResId);
            itemBinding.name.setText(item.getName());
            itemBinding.address.setText(item.getAddress());
            itemBinding.personnel.setText(item.getCurrentNumber() + "/" + item.getMemNumber());

            StringBuffer sb = new StringBuffer();
            sb.append(item.getTime());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(sb.replace(10, 11, " "), formatter);
            itemBinding.date.setText(String.valueOf(dateTime.getYear() + "??? " + dateTime.getMonthValue() + "??? " + dateTime.getDayOfMonth() + "??? " + dateTime.getDayOfWeek() + " " + dateTime.getHour() + "??? " + dateTime.getMinute() + "???"));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (HomeActivity.userDFId != -1L) {
                        Toast.makeText(context, "?????? ?????? ????????? ???????????? ????????????.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (item.getCurrentNumber() >= item.getMemNumber()) {
                            Toast.makeText(context, "????????? ??? ????????????.", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(context, MealFriendPopUpActivity.class);
                            intent.putExtra("mealFriend", String.valueOf(item.getId()));

                            context.startActivity(intent);
//                    ((Activity)context).setResult(RESULT_OK, intent);
//                    ((Activity)context).finish();
                        }
                    }
                }
            });

        }
    }
}
