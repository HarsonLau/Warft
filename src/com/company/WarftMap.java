package com.company;


import java.util.HashMap;
import java.util.Map;

public class WarftMap
{
    private int [][] smap;
    private int [][] LifeElements;

    //现有两个阵营的所有棋子
    private Map<Integer,Chess> Red_Armies;
    private Map<Integer,Chess> Blue_Armies;

    //两个阵营的生命元
    private int energy_r;
    private int energy_b;

    /*构造函数*/
    WarftMap(){
        smap=new int[6][10];
        LifeElements=new int[6][10];
        for(int i=0;i<6;i++){
            for(int j=0;j<10;j++){
                smap[i][j]=0;
                LifeElements=new int[6][10];
            }
        }
        Red_Armies=new HashMap<>();
        Blue_Armies=new HashMap<>();
        energy_r=energy_b=800;
    }

    /*给坐标为(x,y)的城市增加value个生命元*/
    public void addLifeElements(int x,int y,int value){
        LifeElements[x][y]+=value;
    }

    /*获得坐标为(x,y)的城市的生命元数量*/
    public int getLifeElements(int x,int y){
        return LifeElements[x][y];
    }

    /*将坐标为(x,y)的城市的生命元设置为value*/
    public void setLifeElements(int x,int y,int value){
        LifeElements[x][y]=value;
    }

    /*把_chess武士放到指定的位置,这个函数会修改二维数组和Red_Armies/Blue_Armies里面的实例属性*/
    public int setChess(Chess _chess, int x, int y, int color,boolean ifMarch){
        //成功返回0，失败返回1
        if(color==0){//要放的棋子是红色的,所以需要设置地图相应格子的int的低16位.
            //在放置之前需要检查那个格子有没有红军,如果有红军的话就返回1,表示失败
            //在这里要想判断这个红军是不是还活着很难,索性不判断了,交给调用setChess的函数去判断.
            int mask1= 0xffff;//低16位为1
            int tmp1=(smap[x][y]&mask1);//获取低16位
            if(tmp1!=0)
                return 1;
            smap[x][y]^=tmp1;//将低16位置为0;
            smap[x][y]|=(_chess.id&mask1);//将chess 的id 与上 mask1得到chess的id的低16位,再将其和smap或起来就达到目的了.
            //修改_chess
            _chess.move(x,y);
            if(ifMarch)
                _chess.MarchEffect();
            Red_Armies.remove(_chess.id);
            Red_Armies.put(_chess.id,_chess);

            return 0;
        }
        else{//要放的棋子是蓝色的,所以需要检查,设置高16位.
            int mask2=0xffff0000;
            int tmp2=(smap[x][y]&mask2);//取高16位
            if(tmp2!=0)//不等于0,说明有蓝军
                return 1;
            smap[x][y]^=tmp2;//将高16位置为0
            smap[x][y]|=(_chess.id<<16);//将高16位置为蓝军id.
            //修改_chess对象
            _chess.move(x,y);
            if(ifMarch)
                _chess.MarchEffect();
            Blue_Armies.remove(_chess.id);
            Blue_Armies.put(_chess.id,_chess);
            return 0;
        }
    }

    /*返回坐标为(x,y),颜色为color的武士的id*/
    public int getChessId(int x, int y, int color){
        //返回该处,该颜色的棋子的id
        if(color==0){
            int mask1=0xffff;
            return smap[x][y]&mask1;
        }
        else{
            int mask2=0xffff0000;
            return (smap[x][y]&mask2)>>>16;
        }
    }

    /*将二维数组中的那个元素删掉*/
    public void removeChess(int x, int y, int color){
        int id=getChessId(x,y,color);
        if(id==0)//如果该处没有棋子,那就不操作
            return ;
        if(color==0){
            smap[x][y]&=0xffff0000;//将低16位置为0;

        }
        else{
            smap[x][y]&=0xffff;//将高16位置为0;
        }
    }

    /*让坐标为(x,y),颜色为color的武士按照红蓝双方各自的方向前进一步*/
    public void march(int x,int y,int color){
        removeChess(x,y,color);
        if(color==0){
            setChess(Red_Armies.get(getChessId(x,y,color)),x,y+1,color,true);
        }
        else
            setChess(Blue_Armies.get(getChessId(x,y,color)),x,y-1,color,true);
    }

    /*返回指定颜色和id的Chess*/
    public Chess getChess(int id,int color){
        if(color ==0){
            return Red_Armies.get(id);
        }
        else
            return Blue_Armies.get(id);
    }

    /*返回指定坐标指定颜色的Chess*/
    public Chess getChess(int x,int y,int color){
        return getChess(getChessId(x,y,color),color);
    }
};
