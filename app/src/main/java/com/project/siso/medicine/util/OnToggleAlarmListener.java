package com.project.siso.medicine.util;

import android.view.View;

import com.project.siso.medicine.model.Alarm;

public interface OnToggleAlarmListener {
    void onToggle(Alarm alarm);
    void onDelete(Alarm alarm);
    void onItemClick(Alarm alarm,View view);
}
