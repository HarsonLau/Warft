package com.company;

abstract public class Chess{
    protected Timer bornTime;
    public int id;
    public int kind;
    public int atk;
    public int def;
    public int hp;
    public int maxhp;
    public int x;//横坐标
    public int y;//纵坐标
    public int color;
    public Weapon[] arrWeapon;
    //int weaponPoint; 当前使用武器的序号，可能不需要
    public boolean live;//是否存活
    Chess(){
        arrWeapon=new Weapon[10];
    }


    public void move(int _x,int _y){//移动至该处，建议初始放置setChess函数用WarftMap的
        x=_x;
        y=_y;
    }
    public void attack(WarftMap mp);
    public void injured(int attack){
        hp-=attack;
        if(hp<0){
            hp=0;
            live=false;
        }
    }
    public void captureWeapon(Chess opponent);
};
