package com.company;


import java.util.ArrayList;
import java.util.List;

public class Warft
{

    private WarftMap allMap;

    //给新棋子分配的id
    private int newChessId_r;
    private int newChessId_b;

    Warft(){
        allMap=new WarftMap();
        newChessId_b=newChessId_r=1;
    }

    public int main(String[] args)
    {
        while(true){
            allMap.allMarch();//前进
            if(judge())//如果裁判宣布游戏结束那就跳出循环
                break;
            allMap.allCityGrow();//所有城市产生生命元
            allMap.allSingleChessGain();//如果某个城市只有一个武士,该武士获得生命元
            allMap.allShoot();//放箭
            allMap.allBomb();//使用炸弹
            allMap.allFight();//战斗
            allMap.allAfterBattle();//战斗之后,赢的武士收生命元武器,整理武器,输的只整理武器
            allMap.Clean();//清除尸体
        }
        return 0;
    }
    private boolean judge(){
        int winner=allMap.getWinner();
        if(winner==-1)
            return false;
        else{
            switch(winner){//裁判宣布结果.
                case 0:
                    System.out.println("红方获胜");
                    break;
                case 1:
                    System.out.println("蓝方获胜");
                    break;
                    default:
                        System.out.println("平局");
                        break;
            }
            return true;
        }
    }
}
