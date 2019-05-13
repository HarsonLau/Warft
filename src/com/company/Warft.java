package com.company;


import java.util.List;

public class Warft
{
    private:

    WarftMap allMap;
    Timer preTime;

    //新棋子的生成请求
    List<Request> Red_callRequest;
    List <Request> Blue_callRequest;

    //给新棋子分配的id
    int newChessId_r;
    int newChessId_b;

    //两个阵营的生命元
    int energy_r;
    int energy_b;
    public static void main(String[] args)
    {
        return;
    }

    public:
    int run(){
        //先初始化棋盘和时间，随时间增长生命元
        //分别去读两个Request的list，创建棋子
        //在地图上部署棋子。
        //遍历每个棋子，根据时间进行特殊操作。
        //
        //遍历每个棋子，调用attack()函数，在攻击函数中使用武器的use()
        //进行攻击，但是不管是否死亡均会进行攻击，如果死亡则修改live标记
        //
        //然后对于每个棋子如果死亡，则删除棋子（从list中），然后再
        //从地图上移除棋子,如果未死亡，则棋子向前移动。
        //
        //移动之后，判断胜利条件
        return 0;
    }

    int insertRequest(int color, Request rq){return 0;}	//返回是否能成功添加请求

};
