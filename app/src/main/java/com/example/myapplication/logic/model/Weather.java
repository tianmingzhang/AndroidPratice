package com.example.myapplication.logic.model;

public class Weather {
    private RealtimeResponse realtimeResponse;
    private DailyRespose dailyRespose;

    public RealtimeResponse getRealtimeResponse() {
        return realtimeResponse;
    }

    public void setRealtimeResponse(RealtimeResponse realtimeResponse) {
        this.realtimeResponse = realtimeResponse;
    }

    public DailyRespose getDailyRespose() {
        return dailyRespose;
    }

    public void setDailyRespose(DailyRespose dailyRespose) {
        this.dailyRespose = dailyRespose;
    }
}
