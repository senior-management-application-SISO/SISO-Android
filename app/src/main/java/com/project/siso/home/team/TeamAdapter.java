package com.project.siso.home.team;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.siso.databinding.ItemTeamBinding;
import com.project.siso.home.DetailSignUpActivity;

import java.util.ArrayList;

import static com.project.siso.home.team.TeamPopUpActivity.selectedTeamPopup;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {

    Context context;
    ArrayList<Teams> teams;

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public TeamAdapter(Context context, ArrayList<Teams> list) {
        this.context = context;
        this.teams = list;
    }


    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @NonNull
    @Override
    public TeamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTeamBinding itemBinding = ItemTeamBinding.inflate(LayoutInflater.from(context), parent, false);
        return new TeamAdapter.ViewHolder(itemBinding);
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(@NonNull TeamAdapter.ViewHolder holder, int position) {
        holder.bindItem(teams.get(position));
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return teams.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ItemTeamBinding itemBinding;

        public ViewHolder(ItemTeamBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        void bindItem(Teams item) {
            itemBinding.name.setText(item.getTeamName());
            itemBinding.address.setText(item.getTeamAddress());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailSignUpActivity.selectedTeam = item;
                    selectedTeamPopup = item;
                }
            });
        }
    }
}
