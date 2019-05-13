package com.company;

public class Timer
{

    private int hours;
    private int mins;
    Timer(int h,int m){
        hours=0;
        mins=0;
    }

    public void setTimer(int _h,int _m){
        hours=_h;
        mins=_m;
    }
    public int gethour(){
        return hours;
    }
    public int getmins(){
        return mins;
    }
    public void addTimer(int minutes){
        minutes+=mins;
        if(minutes>=60){
            hours++;
            mins=minutes%60;
        }
        else{
            mins=minutes;
        }
    }
};
