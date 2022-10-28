package com.project.siso.medicine.adapter;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.siso.R;
import com.project.siso.databinding.ItemAlarmBinding;
import com.project.siso.medicine.model.Alarm;
import com.project.siso.medicine.util.DayUtil;
import com.project.siso.medicine.util.OnToggleAlarmListener;

public class AlarmViewHolder extends RecyclerView.ViewHolder {
    private TextView alarmTime;
    private ImageView alarmRecurring;
    private TextView alarmRecurringDays;
    private TextView alarmTitle;
    private Switch alarmStarted;
    private ImageButton deleteAlarm;
    private View itemView;
    private TextView alarmDay;

    public AlarmViewHolder(@NonNull ItemAlarmBinding itemAlarmBinding) {
        super(itemAlarmBinding.getRoot());
        alarmTime = itemAlarmBinding.itemAlarmTime;
        alarmStarted = itemAlarmBinding.itemAlarmStarted;
        alarmRecurring = itemAlarmBinding.itemAlarmRecurring;
        alarmRecurringDays = itemAlarmBinding.itemAlarmRecurringDays;
        alarmTitle = itemAlarmBinding.itemAlarmTitle;
        deleteAlarm = itemAlarmBinding.itemAlarmRecurringDelete;
        alarmDay = itemAlarmBinding.itemAlarmDay;
        this.itemView = itemAlarmBinding.getRoot();
    }

    public void bind(Alarm alarm, OnToggleAlarmListener listener) {
        String alarmText = String.format("%02d:%02d", alarm.getHour(), alarm.getMinute());

        alarmTime.setText(alarmText);
        alarmStarted.setChecked(alarm.isStarted());


        if (alarm.isRecurring()) {
            alarmRecurring.setImageResource(R.drawable.ic_repeat_black_24dp);
            String recurringDaysText = alarm.getRecurringDaysText();
            String days = "";

            System.out.println(alarm.getRecurringDaysText());
            if (alarm.getRecurringDaysText().contains("Mo ")) {
                days += "월 ";
            }
            if (alarm.getRecurringDaysText().contains("Tu ")) {
                days += "화 ";
            }
            if (alarm.getRecurringDaysText().contains("We ")) {
                days += "수 ";
            }
            if (alarm.getRecurringDaysText().contains("Th ")) {
                days += "목 ";
            }
            if (alarm.getRecurringDaysText().contains("Fr ")) {
                days += "금 ";
            }
            if (alarm.getRecurringDaysText().contains("Sa ")) {
                days += "토 ";
            }
            if (alarm.getRecurringDaysText().contains("Su ")) {
                days += "일 ";
            }
            alarmRecurringDays.setText(days);
        } else {
            alarmRecurring.setImageResource(R.drawable.ic_looks_one_black_24dp);
            alarmRecurringDays.setText("한 번만");
        }

        if (alarm.getTitle().length() != 0) {
            alarmTitle.setText(alarm.getTitle());
        } else {
            alarmTitle.setText("복용 알람");
        }
        if (alarm.isRecurring()) {
            String recurringDaysText = alarm.getRecurringDaysText();
            String days = "";

            if (alarm.getRecurringDaysText().contains("Mo ")) {
                days += "월 ";
            }
            if (alarm.getRecurringDaysText().contains("Tu ")) {
                days += "화 ";
            }
            if (alarm.getRecurringDaysText().contains("We ")) {
                days += "수 ";
            }
            if (alarm.getRecurringDaysText().contains("Th ")) {
                days += "목 ";
            }
            if (alarm.getRecurringDaysText().contains("Fr ")) {
                days += "금 ";
            }
            if (alarm.getRecurringDaysText().contains("Sa ")) {
                days += "토 ";
            }
            if (alarm.getRecurringDaysText().contains("Su ")) {
                days += "일 ";
            }
            alarmDay.setText(days);
        } else {
            alarmDay.setText(DayUtil.getDay(alarm.getHour(), alarm.getMinute()));
        }
        alarmStarted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isShown() || buttonView.isPressed())
                    listener.onToggle(alarm);
            }
        });

        deleteAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDelete(alarm);
            }
        });

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(alarm, view);
            }
        });
    }
}