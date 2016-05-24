package com.example.uriel.ordertracker.App.Model.Dto;

import com.example.uriel.ordertracker.App.Model.Report;

/**
 * Created by Uriel on 24-May-16.
 */
public class ReportDTO extends BaseDTO {
    private Report data;

    public Report getData() {
        return data;
    }

    public void setData(Report data) {
        this.data = data;
    }
}
