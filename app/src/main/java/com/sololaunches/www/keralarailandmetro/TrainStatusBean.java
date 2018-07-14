package com.sololaunches.www.keralarailandmetro;

/**
 * Created by hp on 9/21/2017.
 */

public class TrainStatusBean {

    private String position;
    private String statusDesc;
    private String current_station;
    private String currentStation_Scheduled_arrival;
    private String currentStation_Actual_arrival;
    private String late_duration;
    private String response_code;
    private String station_context;
    private String currentNumber;
    private String station_context_sch_arrival;
    private String station_context_act_arrival;


    public String getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(String currentNumber) {
        this.currentNumber = currentNumber;
    }


    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    private String number;

    public String getStation_context() {
        return station_context;
    }

    public void setStation_context(String station_context) {
        this.station_context = station_context;
    }

    public String getStation_context_sch_arrival() {
        return station_context_sch_arrival;
    }

    public void setStation_context_sch_arrival(String station_context_sch_arrival) {
        this.station_context_sch_arrival = station_context_sch_arrival;
    }

    public String getStation_context_act_arrival() {
        return station_context_act_arrival;
    }

    public void setStation_context_act_arrival(String station_context_act_arrival) {
        this.station_context_act_arrival = station_context_act_arrival;
    }


    public String getResponse_code() {
        return response_code;
    }

    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }


    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCurrent_station() {
        return current_station;
    }

    public void setCurrent_station(String current_station) {
        this.current_station = current_station;
    }

    public String getScheduled_arrival() {
        return currentStation_Scheduled_arrival;
    }

    public void setScheduled_arrival(String scheduled_arrival) {
        this.currentStation_Scheduled_arrival = scheduled_arrival;
    }

    public String getCurrentStation_Actual_arrival() {
        return currentStation_Actual_arrival;
    }

    public void setCurrentStation_Actual_arrival(String currentStation_Actual_arrival) {
        this.currentStation_Actual_arrival = currentStation_Actual_arrival;
    }

    public String getLate_duration() {
        return late_duration;
    }

    public void setLate_duration(String late_duration) {
        this.late_duration = late_duration;
    }


}
