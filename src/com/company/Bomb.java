package com.company;

public class Bomb extends Weapon {
    Bomb(){
        super(1,0xfffffff);
    }

    int use() {
        if(!available)
            return 0;
        else{
            available=false;
            return harm;
        }
    }

}
