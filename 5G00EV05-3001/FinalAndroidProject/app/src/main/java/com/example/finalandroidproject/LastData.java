package com.example.finalandroidproject;

import com.fasterxml.jackson.annotation.*;

public class LastData {
    private Double[][] dataSeries;

    @JsonProperty("dataSeries")
    public Double[][] getDataSeries() { return dataSeries; }
    @JsonProperty("dataSeries")
    public void setDataSeries(Double[][] value) { this.dataSeries = value; }

}
