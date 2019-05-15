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

    private int winner;
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
        winner=-1;
    }

    /*给坐标为(x,y)的城市增加value个生命元*/
    public void addLifeElements(int x,int y,int value){
        LifeElements[x][y]+=value;
    }

    /*返回坐标为(x,y)的城市的生命元数量*/
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

    /*返回坐标为(x,y),颜色为color的武士的id,id为0表示没有该武士*/
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

    /*让坐标为(x,y),颜色为color的武士按照红蓝双方各自的方向前进一步
    * 会修改二维数组和武士本身
    * */
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

    /*让所有武士前进一步*/
    public void allMarch(){
        /*由于左边是红军司令部,所以红军的前线在右边,从右边开始动*/
        for(int j=8;j>=0;j--){
            for(int i=0;i<6;i++){
                march(i,j,0);
            }
        }
        /*蓝军前线在左边*/
        for(int j=1;j<=9;j++){
            for(int i=0;i<6;i++){
                march(i,j,1);
            }
        }
    }

    /*让所有非司令部城市的生命元增加100*/
    public void allCityGrow(){
        for(int i=0;i<6;i++){
            for(int j=1;j<=8;j++){
                addLifeElements(i,j,100);
            }
        }
    }

    /*让所有只有一个武士的城市的武士获得生命元*/
    public void allSingleChessGain(){
        for(int i=0;i<6;i++){
            for(int j=1;j<=8;j++){
                int rid=getChessId(i,j,0);
                int bid=getChessId(i,j,1);
                if(rid==0&&bid==0)
                    continue;
                else if(rid!=0&&bid!=0)
                    continue;
                else if (rid!=0){
                    Chess rChess=getChess(rid,0);
                    energy_r+=rChess.GainLifeElements(getLifeElements(i,j));
                    setLifeElements(i,j,0);
                    Red_Armies.remove(rid);
                    Red_Armies.put(rid,rChess);
                }
                else{
                    Chess bChess=getChess(bid,1);
                    energy_b+=bChess.GainLifeElements(getLifeElements(i,j));
                    setLifeElements(i,j,0);
                    Blue_Armies.remove(bid);
                    Blue_Armies.put(bid,bChess);
                }
            }
        }
    }

    /*让所有满足射箭条件的武士射箭*/
    public void allShoot(){
        for(int i=0;i<6;i++){
            for(int j=1;j<=8;j++){
                int rid=getChessId(i,j,0);
                if(rid!=0){
                    Chess rChess=getChess(rid,0);
                    if(rChess.haveArrow){
                        int tbid=getChessId(i,j+1,1);
                        if(tbid!=0){
                            Chess tbChess=getChess(tbid,1);
                            tbChess=rChess.shoot(tbChess);
                            if(tbChess.haveArrow){
                                rChess=tbChess.shoot(rChess);
                            }
                            Red_Armies.remove(rid);
                            Red_Armies.put(rid,rChess);
                            Blue_Armies.remove(tbid);
                            Blue_Armies.put(tbid,tbChess);
                        }
                    }
                }
            }
        }
    }

    /*让所有满足条件的武士使用Bomb*/
    public void allBomb(){
        for(int i=0;i<6;i++){
            for(int j=1;j<=8;j++){
                int rid=getChessId(i,j,0);
                int bid=getChessId(i,j,1);
                if(rid==0||bid==0)
                    continue;
                Chess rChess=getChess(rid,0);
                Chess bChess=getChess(bid,1);
                if(rChess.haveBomb&&rChess.willDie(bChess)){
                    bChess=rChess.UseBomb(bChess);
                }
                else if(bChess.haveBomb&&bChess.willDie(rChess)){
                    rChess=bChess.UseBomb(rChess);
                }
                Red_Armies.remove(rid);
                Red_Armies.put(rid,rChess);
                Blue_Armies.remove(bid);
                Blue_Armies.put(bid,bChess);
            }
        }
    }

    /*让所有满足发生战斗条件的城市发生战斗*/
    public void allFight(){
        for(int i=0;i<6;i++){
            for(int j=1;j<=8;j++){
                int rid=getChessId(i,j,0);
                int bid=getChessId(i,j,1);
                if(rid==0||bid==0)
                    continue;
                Chess rChess=getChess(rid,0);
                Chess bChess=getChess(bid,1);
                if(!rChess.alive||!bChess.alive)
                    continue;
                if(j%2==1){
                    bChess=rChess.attack(bChess);
                    rChess=bChess.fightBack(rChess);
                }
                else{
                    rChess=bChess.attack(rChess);
                    bChess=rChess.fightBack(bChess);
                }
                Red_Armies.remove(rid);
                Red_Armies.put(rid,rChess);
                Blue_Armies.remove(bid);
                Blue_Armies.put(bid,bChess);
            }
        }
    }

    /*根据武士伤亡情况判断是平局还是有人获胜,并做相应动作*/
    public void allAfterBattle(){
        for(int i=0;i<6;i++){
            for(int j=1;j<=8;j++){
                int rid=getChessId(i,j,0);
                int bid=getChessId(i,j,1);
                if(rid==0||bid==0)
                    continue;
                Chess rChess=getChess(rid,0);
                Chess bChess=getChess(bid,1);
                if(rChess.alive&&bChess.alive){
                    rChess.AfterDraw();
                    bChess.AfterDraw();
                }
                else if(rChess.alive&&!bChess.alive){
                    energy_r+=rChess.AfterWin(bChess,getLifeElements(i,j));
                    setLifeElements(i,j,0);
                }
                else if(!rChess.alive&&bChess.alive){
                    energy_b+=bChess.AfterWin(rChess,getLifeElements(i,j));
                    setLifeElements(i,j,0);
                }
                Red_Armies.remove(rid);
                Red_Armies.put(rid,rChess);
                Blue_Armies.remove(bid);
                Blue_Armies.put(bid,bChess);
            }
        }
    }

    /*返回这局游戏的胜利者,-1代表尚未产生,0代表红方,1代表蓝方,2代表平局*/
    public int getWinner(){
        boolean rWin=false;
        boolean bWin=false;
        for(int i=0;i<6;i++){
            int rid=getChessId(i,9,0);
            if(rid!=0){
                Chess rChess =getChess(rid,0);
                if(rChess.alive)
                    rWin=true;
            }
            if(rWin)
                break;
        }
        for (int i=0;i<6;i++){
            int bid=getChessId(i,0,1);
            if(bid!=0){
                Chess bChess=getChess(bid,1);
                if(bChess.alive)
                    bWin=true;
            }
            if(bWin)
                break;
        }
        if(bWin&&rWin)
            return 2;
        if(bWin&&!rWin)
            return 1;
        if(rWin&&!bWin)
            return 0;
        return -1;
    }

    /*清除战场上的尸体*/
    public void Clean(){
        for(int i=0;i<6;i++){
            for(int j=1;j<=8;j++){
                int rid=getChessId(i,j,0);
                int bid=getChessId(i,j,1);
                if(rid!=0){
                    Chess rChess=getChess(rid,0);
                    if(!rChess.alive){
                        removeChess(i,j,0);
                        Red_Armies.remove(rid);
                    }
                }
                if(bid!=0){
                    Chess bChess=getChess(bid,1);
                    if(!bChess.alive){
                        removeChess(i,j,1);
                        Blue_Armies.remove(bid);
                    }
                }
            }
        }
    }
};
