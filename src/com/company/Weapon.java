package com.company;

abstract public class Weapon{

    protected Chess owner;
    protected int kind;
    protected int id;
    protected void use(){}
    Weapon(Chess _owner,int _kind,int _id){
        owner=_owner;
        kind=_kind;
        id=_id;
    }
};