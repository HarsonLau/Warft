public class Warft
{
private:
	WarftMap allMap;
	Timer preTime;

	//新棋子的生成请求
	List <Request> Red_callRequest;
	List <Request> Blue_callRequest;

	//现有两个阵营的所有棋子
	List <Chess> Red_Armies;
	List <Chess> Blue_Armies;

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
	};

	int insertRequest(int color, Request rq);	//返回是否能成功添加请求

};

public class WarftMap
{
private:
	int [][] smap;

public:
	int setChess(Chess _chess, int x, int y, int color);//成功返回0，失败返回1
	Chess getChess(int x, int y, int color);
	int removeChess(int x, int y, int color);//注意返回值可能有的各种状态

};

abstract public class Chess{
protected:
	Timer bornTime;
	void born(int _kind,int id);
public:
	int id;
	int kind;
	int atk;
	int def;
	int hp;
	int maxhp;
	int x;//横坐标
	int y;//纵坐标
	int color;
	Weapon[10] arrWeapon;
	//int weaponPoint; 当前使用武器的序号，可能不需要
	int live;//是否存活


	void move(int x,int y);//移动至该处，建议初始放置也用这个函数
	void attack(WarftMap mp);
	void injured(int attack);//受到如此攻击力的攻击
	void captureWeapon(Chess opponent);
};

abstract public class Weapon{
protected:
	Chess owner;
	int kind;
	int id;
	void use(){};
};

public class Timer
{
private:
	int hours;
	int mins;
public:
	void setTimer(int _h,int _m);
	int gethour();
	int getmins();
	void addTimer(int minutes);
};

public class Request
{
	int x;//只能决定横向位置
	int kind;
};