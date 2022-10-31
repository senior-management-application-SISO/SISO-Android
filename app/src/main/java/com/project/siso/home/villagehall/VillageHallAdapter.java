package com.project.siso.home.villagehall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.siso.databinding.ItemTeamBinding;
import com.project.siso.databinding.ItemVillageHallBinding;
import com.project.siso.home.DetailSignUpActivity;
import com.project.siso.home.team.TeamAdapter;
import com.project.siso.home.team.Teams;

import java.util.ArrayList;

public class VillageHallAdapter extends RecyclerView.Adapter<VillageHallAdapter.ViewHolder>{

    Context context;
    ArrayList<VillageHall> villageHalls;

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public VillageHallAdapter(Context context, ArrayList<VillageHall> list) {
        this.context = context;
        this.villageHalls = list;
    }


    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @NonNull
    @Override
    public VillageHallAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemVillageHallBinding itemBinding = ItemVillageHallBinding.inflate(LayoutInflater.from(context), parent, false);
        return new VillageHallAdapter.ViewHolder(itemBinding);
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(@NonNull VillageHallAdapter.ViewHolder holder, int position) {
        holder.bindItem(villageHalls.get(position));
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return villageHalls.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ItemVillageHallBinding itemBinding;

        public ViewHolder(ItemVillageHallBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        void bindItem(VillageHall item) {
            itemBinding.name.setText(item.getHallName());
            itemBinding.address.setText(item.getAddress());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailSignUpActivity.selectedVillageHall = item;
                }
            });
        }
    }
}
