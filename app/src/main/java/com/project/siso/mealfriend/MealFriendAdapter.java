package com.project.siso.mealfriend;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.project.siso.databinding.ItemMealFriendBinding;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

import static com.project.siso.mealfriend.MealFriendActivity.selectedMealFriends;

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
            itemBinding.date.setText(String.valueOf(dateTime.getYear() + "년 " + dateTime.getMonthValue() + "월 " + dateTime.getDayOfMonth() + "일 " + dateTime.getDayOfWeek() + " " + dateTime.getHour() + "시 " + dateTime.getMinute() + "분"));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (item.getCurrentNumber() >= item.getMemNumber()) {
                        Toast.makeText(context, "정원이 다 찼습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(context, MealFriendPopUpActivity.class);
                        intent.putExtra("mealFriend", String.valueOf(item.getId()));

                        context.startActivity(intent);
//                    ((Activity)context).setResult(RESULT_OK, intent);
//                    ((Activity)context).finish();
                    }
                }
            });

        }
    }
}
