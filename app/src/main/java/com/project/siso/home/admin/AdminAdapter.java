package com.project.siso.home.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.siso.databinding.ItemAdminBinding;
import com.project.siso.databinding.ItemTeamBinding;
import com.project.siso.home.DetailSignUpActivity;
import com.project.siso.home.team.Teams;

import java.util.ArrayList;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder>{

    Context context;
    ArrayList<Admin> admins;

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public AdminAdapter(Context context, ArrayList<Admin> list) {
        this.context = context;
        this.admins = list;
    }


    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @NonNull
    @Override
    public AdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAdminBinding itemBinding = ItemAdminBinding.inflate(LayoutInflater.from(context), parent, false);
        return new AdminAdapter.ViewHolder(itemBinding);
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(@NonNull AdminAdapter.ViewHolder holder, int position) {
        holder.bindItem(admins.get(position));
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return admins.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ItemAdminBinding itemBinding;

        public ViewHolder(ItemAdminBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        void bindItem(Admin item) {
            itemBinding.name.setText(item.getAdminName());
            itemBinding.phoneNumber.setText(item.getAdminPhoneNumber());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailSignUpActivity.selectedAdmin = item;
                }
            });
        }
    }

}
