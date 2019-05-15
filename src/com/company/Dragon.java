package com.company;

public class Dragon extends Chess {
    Dragon(int _id,int _color){
        super();
        id=_id;
        kind=0;
        Harm=100;
        hp=maxhp=100;
        color=_color;
    }
    public Chess attack(Chess opponent){
        opponent.injured(Harm);
        return opponent;
    }

    public int AfterWin(Chess opponent, int all) {
        Harm+=10;
        return GainLifeElements(all);
    }

    public int getAttackHarm() {
        return Harm;
    }

    public void AfterDraw() {
        super.AfterDraw();
        Harm-=10;
    }
}
