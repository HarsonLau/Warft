package com.company;

abstract public class Chess{
   // protected Timer bornTime;
    public int id;
    public int kind;
    public int Harm;
    public int hp;
    public int maxhp;
    public int x;//横坐标
    public int y;//纵坐标
    public int color;
    public boolean haveArrow;
    public boolean haveSword;
    public boolean haveBomb;
    public Weapon[] arrWeapon;
    public boolean alive;//是否存活
    Chess(){
        arrWeapon=new Weapon[3];
        haveArrow=haveBomb=haveSword=false;
        alive=true;
        id=kind=Harm=hp=maxhp=x=y=color=-2;
    }

    /*移动至该处，建议初始放置用WarftMap的setChess函数*/
    public void move(int _x,int _y){
        x=_x;
        y=_y;
    }

    /*主动攻击一个Chess对象*/
    public Chess attack(Chess opponent){
        if(haveSword){
            opponent.injured(arrWeapon[0].use());
        }
        return opponent;
    }

    /*还击一个Chess对象*/
    public Chess fightBack(Chess opponent){
        return attack(opponent);
    }

    /*受到伤害值为attack的伤害*/
    public void injured(int attack){
        hp-=attack;
        if(hp<0){
            hp=0;
            alive=false;
        }
    }

    /*如果对手武器比自己的好,那么拿过来*/
    public void captureWeapon(Chess opponent){

        if(haveSword&&opponent.haveSword&&opponent.arrWeapon[0].harm>arrWeapon[0].harm)
            arrWeapon[0]=opponent.arrWeapon[0];
        if(haveArrow&&opponent.haveArrow&&opponent.arrWeapon[2].usedTimes<arrWeapon[2].usedTimes){
            arrWeapon[2]=opponent.arrWeapon[2];
            arrWeapon[2].setHarm(Harm*3/10);
        }

    }

    /*战斗结束之后,检查武器,更新武器的使用情况,还有就是武器的伤害值*/
    public void checkWeapons(){
        if(haveArrow){
            haveArrow=arrWeapon[2].available;
            arrWeapon[2].setHarm(Harm*3/10);
        }
        if(haveBomb)
            haveBomb=arrWeapon[1].available;
        if(haveSword)
            haveSword=arrWeapon[0].available;
    }

    /*获胜之后获得所属城市的生命元,参数是该城市所累积的生命元,
    返回值是武士补满自己的生命元之后还剩的生命元*/
    public int GainLifeElements(int all){
        hp+=all;
        if(hp-maxhp>0)
            return hp-maxhp;
        else
            return 0;
    }

    /*武士赢了对手的反应
    * 参数是对手和这个城市的生命元
    * 返回值是给司令部的生命元
    * 龙会有一点不同,它要先改攻击力.
    * */
    public int AfterWin(Chess opponent,int all){
        captureWeapon(opponent);
        checkWeapons();
        return GainLifeElements(all);
    }

    public Chess UseBomb(Chess opponent){
        opponent.injured(opponent.hp);
        haveBomb=false;
        this.injured(this.hp);
        return opponent;
    }

    public void MarchEffect(){
        return;
    }

    /*返回attack函数将给对方造成的伤害*/
    public int getAttackHarm(){
        if(!haveSword)
            return 0;
        return arrWeapon[0].harm;
    }

    /*返回fightback函数将给对方造成的伤害*/
    public int getFightBackHarm(){
        if(!haveSword)
            return 0;
        return arrWeapon[0].harm;
    }

    /*预测自己会不会死于对手刀下*/
    public boolean willDie(Chess opponent){
        if(y%2==1){//在奇数城市,红方先发起攻击
            if(color==0){//自己是红色的,那么只有在我没把它杀死,并且它能把我杀死时,我才会死
                if(this.getAttackHarm()<opponent.hp&&opponent.getFightBackHarm()>=this.hp)
                    return true;
                return false;
            }
            else{//自己是蓝军,那么秩序考虑红军会不会杀死我
                if(this.hp<=opponent.getAttackHarm())
                    return true;
                return false;
            }
        }
        else{//在偶数城市,蓝方先发起攻击
            if(color==1){//自己是蓝色的,那么只有在我没把它杀死,并且它能把我杀死时,我才会死
                if(this.getAttackHarm()<opponent.hp&&opponent.getFightBackHarm()>=this.hp)
                    return true;
                return false;
            }
            else{//自己是红军,那么只需考虑蓝军会不会杀死我
                if(this.hp<=opponent.getAttackHarm())
                    return true;
                return false;
            }
        }
    }

    /*朝对手射箭,返回中箭后的对手的对象*/
    public Chess shoot(Chess opponent){
        if(!haveArrow)
            return opponent;
        opponent.injured(this.arrWeapon[2].use());
        return opponent;
    }
};
