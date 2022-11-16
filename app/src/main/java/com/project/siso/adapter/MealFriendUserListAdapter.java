package com.project.siso.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.siso.databinding.ItemDetailDiningFriendBinding;
import com.project.siso.databinding.ItemFriendBinding;
import com.project.siso.domain.UserInfoState;
import com.project.siso.mealfriend.DetailMealFriends;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MealFriendUserListAdapter extends RecyclerView.Adapter<MealFriendUserListAdapter.ViewHolder> {

    Context context;
    List<DetailMealFriends> userInfoStateList;

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public MealFriendUserListAdapter(Context context, List<DetailMealFriends> list) {
        this.context = context;
        this.userInfoStateList = list;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @NonNull
    @Override
    public MealFriendUserListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDetailDiningFriendBinding itemBinding = ItemDetailDiningFriendBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MealFriendUserListAdapter.ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItem(userInfoStateList.get(position));
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return userInfoStateList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ItemDetailDiningFriendBinding itemBinding;

        public ViewHolder(ItemDetailDiningFriendBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        void bindItem(DetailMealFriends item) {
            itemBinding.name.setText(item.getUserName());
            itemBinding.phoneNumber.setText(item.getPhoneNumber());
        }
    }
}
