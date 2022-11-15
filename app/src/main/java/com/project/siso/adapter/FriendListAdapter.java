package com.project.siso.adapter;

import static com.project.siso.home.HomeActivity.userInfo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.siso.MainActivity;
import com.project.siso.R;
import com.project.siso.databinding.ItemFriendBinding;
import com.project.siso.databinding.ItemVillageHallUserListBinding;
import com.project.siso.domain.UserInfoState;
import com.project.siso.villagehall.Users;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {

    Context context;
    List<UserInfoState> userInfoStateList;

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public FriendListAdapter(Context context, List<UserInfoState> list) {
        this.context = context;
        this.userInfoStateList = list;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @NonNull
    @Override
    public FriendListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFriendBinding itemBinding = ItemFriendBinding.inflate(LayoutInflater.from(context), parent, false);
        return new FriendListAdapter.ViewHolder(itemBinding);
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

        ItemFriendBinding itemBinding;

        public ViewHolder(ItemFriendBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }


        void bindItem(UserInfoState item) {
            itemBinding.name.setText(item.getUserName());
            itemBinding.phoneNumber.setText(item.getPhoneNumber());

            StringBuffer sb = new StringBuffer();
            sb.append(item.getDate());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(sb.replace(10, 11, " "), formatter);

            itemBinding.attendance.setText("[최근 출석일] " + String.valueOf(dateTime.getYear() + "년 " + dateTime.getMonthValue() + "월 " + dateTime.getDayOfMonth() + "일 "));

            if (LocalDateTime.now().minusDays(5).compareTo(dateTime) > 0) {
                itemBinding.attendance.setTextColor(Color.parseColor("#FFFF0000"));
            } else if (LocalDateTime.now().minusDays(3).compareTo(dateTime) > 0) {
                itemBinding.attendance.setTextColor(Color.parseColor("#FFFF8C00"));
            } else {
                itemBinding.attendance.setTextColor(Color.parseColor("#FF4CAF50"));
            }

        }
    }
}
