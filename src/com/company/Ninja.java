package com.company;

public class Ninja extends Chess {
    Ninja(int _id,int _color){
        super();
        id=_id;
        kind=1;
        Harm=100;
        hp=maxhp=100;
        color=_color;
        switch (id%3){
            case 0:
                haveSword=true;
                arrWeapon[0]=new Sword(Harm*2/10);
                break;
            case 1:
                haveBomb=true;
                arrWeapon[1]=new Bomb();
                break;
            case 2:
                haveArrow=true;
                arrWeapon[2]=new Arrow(Harm*3/10);
        }
        switch ((id+1)%3){
            case 0:
                haveSword=true;
                arrWeapon[0]=new Sword(Harm*2/10);
                break;
            case 1:
                haveBomb=true;
                arrWeapon[1]=new Bomb();
                break;
            case 2:
                haveArrow=true;
                arrWeapon[2]=new Arrow(Harm*3/10);
        }
    }
    public Chess UseBomb (Chess opponent){
        opponent.injured(opponent.hp);
        haveBomb=false;
        return opponent;
    }

    public void captureWeapon(Chess opponent) {
        super.captureWeapon(opponent);
        /*抢自己已经用完了的武器*/
        switch(id%3){
            case 0:
                if(!haveSword&&opponent.haveArrow){
                    haveSword=true;
                    arrWeapon[0]=opponent.arrWeapon[0];
                }
                break;
            case 1:
                if(!haveBomb&&opponent.haveBomb){
                    haveBomb=true;
                    arrWeapon[1]=opponent.arrWeapon[1];
                }
                break;
            case 2:
                if(!haveArrow&&opponent.haveArrow){
                    haveArrow=true;
                    arrWeapon[2]=opponent.arrWeapon[2];
                    arrWeapon[2].setHarm(Harm*3/10);
                }
                break;
        }

        switch((id+1)%3){
            case 0:
                if(!haveSword&&opponent.haveArrow){
                    haveSword=true;
                    arrWeapon[0]=opponent.arrWeapon[0];
                }
                break;
            case 1:
                if(!haveBomb&&opponent.haveBomb){
                    haveBomb=true;
                    arrWeapon[1]=opponent.arrWeapon[1];
                }
                break;
            case 2:
                if(!haveArrow&&opponent.haveArrow){
                    haveArrow=true;
                    arrWeapon[2]=opponent.arrWeapon[2];
                    arrWeapon[2].setHarm(Harm*3/10);
                }
                break;
        }
    }

    public Chess fightBack(Chess opponent) {
        return opponent;
    }

    public int getFightBackHarm() {
        return 0;
    }
}
