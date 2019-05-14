package com.company;

abstract public class Weapon{

    int kind;
    int harm;
    int usedTimes;
    boolean available;
    int use(){
        return 0;
    }

    Weapon(){
        kind=harm=-1;
        usedTimes=0;
        available=true;
    }

    Weapon(int _kind,int _harm){
        kind=_kind;
        harm=_harm;
        usedTimes=0;
        available=true;
    }

    public void setHarm(int harm) {
        this.harm = harm;
    }
};