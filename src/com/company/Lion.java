package com.company;

public class Lion extends Chess {
    int loyaty;
    Lion(int _id,int _color){
        super();
        id=_id;
        kind=2;
        Harm=100;
        hp=maxhp=250;
        loyaty=60;
        color=_color;
        haveArrow=haveBomb=haveSword=true;
        arrWeapon[0]=new Sword(Harm);
        arrWeapon[1]=new Bomb();
        arrWeapon[2]=new Arrow(Harm);
    }

    public void MarchEffect() {
        loyaty-=10;
        if(loyaty==0)
            alive=false;
    }

    public void captureWeapon(Chess opponent) {
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
