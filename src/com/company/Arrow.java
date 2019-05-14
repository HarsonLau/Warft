package com.company;

public class Arrow extends Weapon {
    Arrow(){
        super();
        kind=2;
    }
    Arrow(int _harm){
        super(2,_harm);
    }
    int use(){
        if(!available)
            return 0;
        usedTimes++;
        if(usedTimes==3)
            available=false;
        return harm;
    }
}
