package com.example.uriel.ordertracker.App.Services.Interface;

import java.util.HashMap;
import java.util.List;

public interface OnTaskCompleted {

    void onTaskCompleted(List<List<HashMap<String, String>>> jsonObj);
}