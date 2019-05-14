package com.company;

public class Sword extends Weapon {
    Sword(int _harm){
        super(0,_harm);
    }
    int use(){
        if(!available)
            return 0;
        usedTimes++;
        int res=harm;
        harm=harm*2/10;
        if(harm==0)
            available=false;
        return res;
    }
}
