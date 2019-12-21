#ifndef PCB_H
#define PCB_H
#include<QString>
#include<iostream>
#include<vector>
#include<QColor>
using namespace std;


class PCB
{
public:
    QString name;
    int PID;
    int time;
    int priority;
    QString status;
    int size;
    int storage_begin;
    PCB *zz;
    QColor *color; //记录内存的颜色块
};
static bool CmpByTime(const PCB& pcb_1,const PCB& pcb_2)
{
    return pcb_1.time<pcb_2.time;
}
static bool CmpByPriority(const PCB& pcb_1,const PCB& pcb_2)
{
    return pcb_1.priority>pcb_2.priority;
}
static int systime = 0;
static vector<PCB> reverse_queue;
static vector<PCB> ready_queue;
static vector<PCB> block_queue;
static vector<PCB> suspended_queue;
static int running_time = 0;
#endif // PCB_H
