package com.project.siso.adapter;

import static com.project.siso.home.DetailSignUpActivity.RESULT_OK_SELECTED_ADMIN;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.siso.databinding.ItemAdminBinding;
import com.project.siso.home.DetailSignUpActivity;
import com.project.siso.home.admin.AdminCountyOffice;

import java.util.ArrayList;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder>{

    Context context;
    ArrayList<AdminCountyOffice> admins;

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public AdminAdapter(Context context, ArrayList<AdminCountyOffice> list) {
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

        void bindItem(AdminCountyOffice item) {
            itemBinding.name.setText(item.getAdminName());
            itemBinding.phoneNumber.setText(item.getAdminPhoneNumber());
            itemBinding.county.setText(item.getOfficeName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailSignUpActivity.selectedAdmin = item;

                    Intent intent = new Intent();
                    intent.putExtra("adminName", DetailSignUpActivity.selectedAdmin.getAdminName());
                    ((Activity)context).setResult(RESULT_OK_SELECTED_ADMIN, intent);

                    ((Activity)context).finish();

                }
            });
        }
    }

}
