#include<QString>
#include<iostream>
#include<queue>
using namespace std;
static priority_queue<PCB,vector<PCB>,cmp> reserve_queue; //后备队列
class cmp
{
    bool operator ()(PCB x,PCB y)
        {
            return x.priority<y.priority; //大值优先
        }
};

class PCB
{
public:
    QString name;
    int PID;
    int time;
    int priority;
    QString state;
    int size;
    int storage_begin;
    PCB *zz;
};
