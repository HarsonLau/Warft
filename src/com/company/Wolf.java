package com.company;

public class Wolf extends Chess {
    Wolf(int _id,int _color){
        id=_id;
        kind=3;
        Harm=100;
        maxhp=hp=250;
    }

    public void captureWeapon(Chess opponent) {
        /*把敌人比我好的武器拿过来*/
        super.captureWeapon(opponent);

        /*把敌人有而我没有的武器拿过来*/
        if(!haveBomb&&opponent.haveBomb){
            arrWeapon[1]=opponent.arrWeapon[1];
            haveBomb=true;
        }
        if(!haveSword&&opponent.haveSword){
            arrWeapon[0]=opponent.arrWeapon[0];
            haveSword=true;
        }
        if(!haveArrow&&opponent.haveArrow){
            arrWeapon[2]=opponent.arrWeapon[2];
            arrWeapon[2].setHarm(Harm*3/10);
            haveArrow=true;
        }
    }

}
