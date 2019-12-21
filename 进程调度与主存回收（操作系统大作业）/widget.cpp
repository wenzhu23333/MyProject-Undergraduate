#include "widget.h"
#include "ui_widget.h"
#include<QDebug>
#include"storage.h"
#include"QPaintDevice"
#include"QPixmap"
#include"QLabel"
#include"QPainter"
#include"pcb.h"
#include<queue>
#include<list>
Widget::Widget(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::Widget)
{
    ui->setupUi(this);
    connect(ui->pushButton_5,SIGNAL(clicked(bool)),this,SLOT(addjob()));
    //ui->horizontalLayout_2->addWidget(new QLabel("?????"));
    ui->comboBox->addItem("先来先服务");
    ui->comboBox->addItem("短作业优先");
    ui->comboBox->addItem("优先权调度");
    ui->comboBox_2->addItem("先来先服务");
    ui->comboBox_2->addItem("短作业优先");
    ui->comboBox_2->addItem("优先权调度");
    ui->comboBox_2->addItem("时间片轮转");
    ui->progressBar->reset();
    paint_area = new QPixmap(800,50);//建立内存图像的画板
    paint_area->fill(Qt::white);//将画板填充为黄色
    connect(this,SIGNAL(SendEraRec(PCB*)),this,SLOT(RecEraRec(PCB*)));

}
void Widget::RecEraRec(PCB* Era_pcb)
{
  erase_Rec(float(Era_pcb->storage_begin)/float(storage)*800,0,float(Era_pcb->size)/float(storage)*800,50);
}
void Widget::addjob()
{

    test=new inputjob();
    test->show();
    connect(this->test,SIGNAL(SendNewPCB(PCB*)),this,SLOT(RecNewPCB(PCB*)));

}

Widget::~Widget()
{
    delete ui;
}
int Widget::Storage_empty(list<partition_> *linklist,PCB *pcb)
{
    list<partition_>::iterator listiter;
    for(listiter = linklist->begin();listiter!=linklist->end();listiter++)
    {
        if(listiter->divide_status!=new QString("Yes")&&(listiter->partition_size>pcb->size))
        {
            pcb->storage_begin = listiter->start;
            qDebug()<<listiter->partition_size;
            if(listiter->partition_size-pcb->size>Minsize)
            {
                partition_ *new_partition = new partition_();
                new_partition->divide_status = new QString("No");
                new_partition->partition_size = listiter->partition_size-pcb->size;
                new_partition->start = pcb->storage_begin + pcb->size;

                linklist->push_back(*new_partition);
                 linklist->erase(listiter);
                 //break;
            }
            else
            {
                pcb->size = listiter->partition_size ;
                linklist->erase(listiter);
                //break;
            }
            linklist->sort();
            return pcb->storage_begin;
        }
    }
    return -1;
}
//float(new_PCB->storage_begin)/float(storage)*800
//float(new_PCB->size)/float(storage)*800
void Widget::RecNewPCB(PCB *new_PCB)
{

    if(Storage_empty(&storage_linklist,new_PCB)!=-1)
    {

        //draw_Rec(float(new_PCB->storage_begin)/float(storage)*800,0,float(new_PCB->size)/float(storage)*800,50,*new QColor(rand()%256,rand()%256,rand()%256));
        new_PCB->color =new QColor(rand()%256,rand()%256,rand()%256);
        new_PCB->PID = systime+100;
        new_PCB->status = *new QString("Ready");
        ui->verticalLayout_3->addWidget(new QLabel("PID:"+QString::number(new_PCB->PID)
                                                   +"/"+"Name:"+new_PCB->name
                                                   +"/"+"Size:"+QString::number(new_PCB->size)
                                                   +"/"+"Time:"+QString::number(new_PCB->time)
                                                   +"/"+"Priority:"+QString::number(new_PCB->priority)));
        ready_queue.push_back(*new_PCB);//这个顺序非常讲究，必须将这个放最后一个，这样pcb才能够被赋上值
        qDebug()<<new_PCB->PID;
    }
    else
    {   new_PCB->color =new QColor(rand()%256,rand()%256,rand()%256);
        reverse_queue.push_back(*new_PCB);
        //ui->verticalLayout_2->addWidget(new QLabel(QString(new_PCB->name)));
    }

   // ui->verticalLayout_2->addWidget(new QLabel(new_PCB->name));
}
void Widget::on_pushButton_2_clicked()
{
    input_storage_begin= new input_storage();
    input_storage_begin->show();
    connect(this->input_storage_begin,SIGNAL(sendMessage(int )),this,SLOT(RecMessage(int )));
}
void Widget::paintEvent(QPaintEvent *)
{
    //利用Qpixmap在窗口中的局部绘图
    QPainter Painter(this);
    Painter.drawPixmap(250,710, 900, 60,*paint_area);
}
void Widget::draw_Rec(int const x, int const y, int const with, int const height,QColor color) {
    // 在QPixmap上画图
    QPainter Painter(paint_area);
    QPen pen;
    pen.setWidth(0);
    Painter.setPen(pen);
    Painter.setBrush(color);
    Painter.drawRect(x,y,with,height);
}
void Widget::erase_Rec(const int x, const int y, const int with, const int height)
{
    QPainter Painter(paint_area);
    Painter.eraseRect(x,y,with,height);
}
void Widget::RecMessage(int i)
{
    //draw_Rec(0,0,800,50,Qt::yellow);
    temp_partition = new partition_();
    temp_partition->divide_status = new QString("No");
    temp_partition->partition_size = i;
    temp_partition->start = 0;
    partition_num = 1;
    storage_linklist.push_back(*temp_partition);
    this->storage = i;
    //qDebug()<<storage;
    connect(timer, SIGNAL(timeout()), this, SLOT(Systime_count()));
    timer->start(1000);//系统定时器开始
}
void Widget::delet_all(QVBoxLayout *ex) //删除布局内的所有控件
{
    while(ex->count())
         {
            QWidget *p=ex->itemAt(0)->widget();
            p->setParent (NULL);
            ex->removeWidget(p);
            delete p;
         }
}
void Widget::Systime_count()
{
    //qDebug()<<0;
    systime++;
    if(JCB_record == 1&&!reverse_queue.empty())
    {
        delet_all(ui->verticalLayout_2);
        for(int i = 0 ; i< reverse_queue.size();i++)
        {
            ui->verticalLayout_2->addWidget(new QLabel("Name:"+reverse_queue[i].name
                                                       +"/"+"Size:"+QString::number(reverse_queue[i].size)
                                                       +"/"+"Priority:"+QString::number(reverse_queue[i].priority)
                                                       +"/"+"Time:"+QString::number(reverse_queue[i].time)));        }
    }

    else if(JCB_record == 2&&!reverse_queue.empty())
    {
        delet_all(ui->verticalLayout_2);
        sort(reverse_queue.begin(),reverse_queue.end(),CmpByTime);
        for(int i = 0 ; i< reverse_queue.size();i++)
        {
            ui->verticalLayout_2->addWidget(new QLabel("Name:"+reverse_queue[i].name
                                                       +"/"+"Size:"+QString::number(reverse_queue[i].size)
                                                       +"/"+"Priority:"+QString::number(reverse_queue[i].priority)
                                                       +"/"+"Time:"+QString::number(reverse_queue[i].time)));        }
    }
    else if(JCB_record == 3&&!reverse_queue.empty())
    {
        delet_all(ui->verticalLayout_2);
        sort(reverse_queue.begin(),reverse_queue.end(),CmpByPriority);
        for(int i = 0 ; i< reverse_queue.size();i++)
        {
            ui->verticalLayout_2->addWidget(new QLabel("Name:"+reverse_queue[i].name
                                                       +"/"+"Size:"+QString::number(reverse_queue[i].size)
                                                       +"/"+"Priority:"+QString::number(reverse_queue[i].priority)
                                                       +"/"+"Time:"+QString::number(reverse_queue[i].time)));        }
    }
/***************************************************************************/
    //作业调度
    if(!reverse_queue.empty())
    if(Storage_empty(&storage_linklist,&reverse_queue.front())!=-1)
    {
        PCB* sys_pcb = &reverse_queue.front();
        reverse_queue.erase(reverse_queue.begin());
        delet_all(ui->verticalLayout_2);
        for(int i = 0 ; i< reverse_queue.size();i++)
        {
           ui->verticalLayout_2->addWidget(new QLabel("Name:"+reverse_queue[i].name
                                                      +"/"+"Size:"+QString::number(reverse_queue[i].size)
                                                      +"/"+"Priority:"+QString::number(reverse_queue[i].priority)
                                                      +"/"+"Time:"+QString::number(reverse_queue[i].time)));
        }
        sys_pcb->PID = systime + 100;//给进程赋一个随机的PID
        sys_pcb->status = *new QString("Ready");
        ready_queue.push_back(*sys_pcb);

    }
/*************************************************************************/
    if(PCB_record == 1&&!ready_queue.empty())
    {
         delet_all(ui->verticalLayout_3);
         //PCB *running_pcb = &ready_queue.front();
         //ready_queue.erase(ready_queue.begin());

         for(int i = 1 ; i< ready_queue.size();i++)//必须从1号开始摆，因为0号处于运行状态
         {
             qDebug()<<ready_queue[i].PID;
             ui->verticalLayout_3->addWidget(new QLabel("PID:"+QString::number(ready_queue[i].PID)
                                                        +"/"+"Name:"+ready_queue[i].name
                                                        +"/"+"Size:"+QString::number(ready_queue[i].size)
                                                        +"/"+"Time:"+QString::number(ready_queue[i].time)
                                                        +"/"+"Priority:"+QString::number(ready_queue[i].priority)));
         }
         ui->progressBar->reset();
         ui->progressBar->setRange(0,ready_queue.front().time);
         delet_all(ui->verticalLayout_4);
         ui->verticalLayout_4->addWidget(new QLabel("进程名称: "+ready_queue.front().name));
         ui->verticalLayout_4->addWidget(new QLabel("PID = "+QString::number(ready_queue.front().PID)));
         ui->verticalLayout_4->addWidget(new QLabel("所占内存大小: "+QString::number(ready_queue.front().size)));
         ui->verticalLayout_4->addWidget(new QLabel("运行时间: "+QString::number(ready_queue.front().time)));
         ui->verticalLayout_4->addWidget(new QLabel("优先级: "+QString::number(ready_queue.front().priority)));
         ui->verticalLayout_4->addWidget(new QLabel("内存起址: "+QString::number(ready_queue.front().storage_begin)));
         ready_queue.front().status = *new QString("Running");
         if(running_time !=ready_queue.front().time )
         {
             running_time++;
             ui->progressBar->setValue(running_time);
         }
         else
         {
             running_time = 0;
             //emit SendEraRec(&ready_queue.front());
             AddNewPartition(&ready_queue.front());
             ready_queue.erase(ready_queue.begin());

             delet_all(ui->verticalLayout_4);
         }
    }
    else if(PCB_record == 2&&!ready_queue.empty())
    {
      delet_all(ui->verticalLayout_3);
      sort(ready_queue.begin(),ready_queue.end(),CmpByTime);
      for(int i = 1 ; i< ready_queue.size();i++)//必须从1号开始摆，因为0号处于运行状态
      {
          qDebug()<<ready_queue[i].PID;
          ui->verticalLayout_3->addWidget(new QLabel("PID:"+QString::number(ready_queue[i].PID)
                                                     +"/"+"Name:"+ready_queue[i].name
                                                     +"/"+"Size:"+QString::number(ready_queue[i].size)
                                                     +"/"+"Time:"+QString::number(ready_queue[i].time)
                                                     +"/"+"Priority:"+QString::number(ready_queue[i].priority)));
      }
      ui->progressBar->reset();
      ui->progressBar->setRange(0,ready_queue.front().time);
      delet_all(ui->verticalLayout_4);
      ui->verticalLayout_4->addWidget(new QLabel("进程名称: "+ready_queue.front().name));
      ui->verticalLayout_4->addWidget(new QLabel("PID = "+QString::number(ready_queue.front().PID)));
      ui->verticalLayout_4->addWidget(new QLabel("所占内存大小: "+QString::number(ready_queue.front().size)));
      ui->verticalLayout_4->addWidget(new QLabel("运行时间: "+QString::number(ready_queue.front().time)));
      ui->verticalLayout_4->addWidget(new QLabel("优先级: "+QString::number(ready_queue.front().priority)));
      ui->verticalLayout_4->addWidget(new QLabel("内存起址: "+QString::number(ready_queue.front().storage_begin)));
      ready_queue.front().status = *new QString("Running");
      if(running_time !=ready_queue.front().time )
      {
          running_time++;
          ui->progressBar->setValue(running_time);
      }
      else
      {
          running_time = 0;
          //emit SendEraRec(&ready_queue.front());
          AddNewPartition(&ready_queue.front());
          ready_queue.erase(ready_queue.begin());
          delet_all(ui->verticalLayout_4);
      }

    }
    else if(PCB_record == 3&&!ready_queue.empty())//动态优先级
    {
    delet_all(ui->verticalLayout_3);
    sort(ready_queue.begin(),ready_queue.end(),CmpByPriority);
    for(int i = 1 ; i< ready_queue.size();i++)//必须从1号开始摆，因为0号处于运行状态
    {
        //qDebug()<<ready_queue[i].PID;
        ui->verticalLayout_3->addWidget(new QLabel("PID:"+QString::number(ready_queue[i].PID)
                                                   +"/"+"Name:"+ready_queue[i].name
                                                   +"/"+"Size:"+QString::number(ready_queue[i].size)
                                                   +"/"+"Time:"+QString::number(ready_queue[i].time)
                                                   +"/"+"Priority:"+QString::number(ready_queue[i].priority)));
    }
    ui->progressBar->reset();
    ui->progressBar->setRange(0,5);
    delet_all(ui->verticalLayout_4);
    ui->verticalLayout_4->addWidget(new QLabel("进程名称: "+ready_queue.front().name));
    ui->verticalLayout_4->addWidget(new QLabel("PID = "+QString::number(ready_queue.front().PID)));
    ui->verticalLayout_4->addWidget(new QLabel("所占内存大小: "+QString::number(ready_queue.front().size)));
    ui->verticalLayout_4->addWidget(new QLabel("运行时间: "+QString::number(ready_queue.front().time)));
    ui->verticalLayout_4->addWidget(new QLabel("优先级: "+QString::number(ready_queue.front().priority)));
    ui->verticalLayout_4->addWidget(new QLabel("内存起址: "+QString::number(ready_queue.front().storage_begin)));
    ready_queue.front().status = *new QString("Running");
    if(running_time !=5 )
    {
        running_time++;
        ui->progressBar->setValue(running_time);
    }
    else
    {
        ready_queue.front().priority--;
        ready_queue.front().time--;

        running_time = 0;
        //emit SendEraRec(&ready_queue.front());

        if(ready_queue.front().time == 0)
        {
            AddNewPartition(&ready_queue.front());
            ready_queue.erase(ready_queue.begin());
        }
        delet_all(ui->verticalLayout_4);
    }
    }
    else if(PCB_record == 4&&!ready_queue.empty())//时间片轮转
    {
        delet_all(ui->verticalLayout_3);
        //sort(ready_queue.begin(),ready_queue.end(),CmpByPriority);
        for(int i = 1 ; i< ready_queue.size();i++)//必须从1号开始摆，因为0号处于运行状态
        {
            //qDebug()<<ready_queue[i].PID;
            ui->verticalLayout_3->addWidget(new QLabel("PID:"+QString::number(ready_queue[i].PID)
                                                       +"/"+"Name:"+ready_queue[i].name
                                                       +"/"+"Size:"+QString::number(ready_queue[i].size)
                                                       +"/"+"Time:"+QString::number(ready_queue[i].time)
                                                       +"/"+"Priority:"+QString::number(ready_queue[i].priority)));
        }
        ui->progressBar->reset();
        ui->progressBar->setRange(0,5);
        delet_all(ui->verticalLayout_4);
        ui->verticalLayout_4->addWidget(new QLabel("进程名称: "+ready_queue.front().name));
        ui->verticalLayout_4->addWidget(new QLabel("PID = "+QString::number(ready_queue.front().PID)));
        ui->verticalLayout_4->addWidget(new QLabel("所占内存大小: "+QString::number(ready_queue.front().size)));
        ui->verticalLayout_4->addWidget(new QLabel("运行时间: "+QString::number(ready_queue.front().time)));
        ui->verticalLayout_4->addWidget(new QLabel("优先级: "+QString::number(ready_queue.front().priority)));
        ui->verticalLayout_4->addWidget(new QLabel("内存起址: "+QString::number(ready_queue.front().storage_begin)));
        ready_queue.front().status = *new QString("Running");
        if(running_time !=5 )
        {
            running_time++;
            ui->progressBar->setValue(running_time);
            if(running_time == ready_queue.begin()->time)
               {
                ui->progressBar->reset();
                running_time = 0;
                /*ready_queue.push_back(ready_queue.front());
                ready_queue.erase(ready_queue.begin());*/
                delet_all(ui->verticalLayout_4);
                AddNewPartition(&ready_queue.front());
                ready_queue.erase(ready_queue.begin());
               }
        }
        else
        {
            //ready_queue.front().priority--;
            ready_queue.front().time-=5;
            running_time = 0;
            //emit SendEraRec(&ready_queue.front());
            ready_queue.push_back(ready_queue.front());
            ready_queue.erase(ready_queue.begin());
            if(ready_queue.front().time == 0)
            {
                AddNewPartition(&ready_queue.front());
                ready_queue.erase(ready_queue.begin());
            }

        }
    }
/*************************************************************************/
    //画图操作在此块
    erase_Rec(0,0,800,50);
    for(int i = 0; i<ready_queue.size();i++)
    {
        draw_Rec(float(ready_queue[i].storage_begin)/float(storage)*800,0,float(ready_queue[i].size)/float(storage)*800,50,*ready_queue[i].color);
    }
    for(int i = 0 ; i< block_queue.size();i++)
    {
        draw_Rec(float(block_queue[i].storage_begin)/float(storage)*800,0,float(block_queue[i].size)/float(storage)*800,50,*block_queue[i].color);
    }
    this->update();
/************************************************************************/
   //挂起操作在此块(显示部分）
    ui->comboBox_3->clear();
    for(int i = 1; i<ready_queue.size();i++) //第一个元素处于运行状态，所以必须从1号开始运行
    {
        ui->comboBox_3->addItem(QString::number(ready_queue[i].PID));
    }
    for(int i = 0 ; i< block_queue.size();i++)
    {
        ui->comboBox_3->addItem(QString::number(block_queue[i].PID));
    }
/************************************************************************/
    //解挂此操作块（显示部分）
    ui->comboBox_4->clear();
    for(int i = 0; i<suspended_queue.size();i++) //第一个元素处于运行状态，所以必须从1号开始运行
    {
        ui->comboBox_4->addItem(QString::number(suspended_queue[i].PID));
    }
/************************************************************************/
}

void Widget::AddNewPartition(PCB *pcb) //释放分区并合并空闲分区
{
    partition_ *new_partition;
    new_partition = new partition_();
    new_partition->divide_status = new QString("No");
    new_partition->partition_size = pcb->size;
    new_partition->start = pcb->storage_begin;
    storage_linklist.push_back(*new_partition);
    storage_linklist.sort();
    list<partition_>::iterator listiter_1;
    list<partition_>::iterator listiter_2;
    for(listiter_1 = storage_linklist.begin();listiter_1!=(storage_linklist.end());listiter_1++)
    for(listiter_2 = listiter_1;listiter_2!=(storage_linklist.end());listiter_2++)
        {
            if((listiter_1->start+listiter_1->partition_size) == listiter_2->start )
            {
                listiter_1->partition_size += listiter_2->partition_size;
                storage_linklist.erase(listiter_2);

            }
           \
        }
    storage_linklist.sort();
}
void Widget::on_pushButton_clicked()
{
    if(ui->comboBox->currentText() == new QString("先来先服务"))
    JCB_record = 1;
    if(ui->comboBox->currentText() == new QString("短作业优先"))
    JCB_record = 2;
    if(ui->comboBox->currentText() == new QString("优先权调度"))
    JCB_record = 3;
    if(ui->comboBox_2->currentText() == new QString("先来先服务"))
    PCB_record = 1;
    else if(ui->comboBox_2->currentText() == new QString("短作业优先"))
    PCB_record = 2;
    else if(ui->comboBox_2->currentText() == new QString("优先权调度"))
    PCB_record = 3;
    else if(ui->comboBox_2->currentText() == new QString("时间片轮转"))
    PCB_record = 4;
}

void Widget::on_pushButton_6_clicked()
{
    PCB *pcb = new PCB();
    pcb = &ready_queue.front();
    block_queue.push_back(*pcb);
    ready_queue.erase(ready_queue.begin());
    //pcb->time -= running_time;
    delet_all(ui->verticalLayout_6);
    for(int i = 0 ; i< block_queue.size();i++)
    {
        //qDebug()<<ready_queue[i].PID;
        ui->verticalLayout_6->addWidget(new QLabel("PID:"+QString::number(block_queue[i].PID)
                                                   +"/"+"Name:"+block_queue[i].name
                                                   +"/"+"Size:"+QString::number(block_queue[i].size)
                                                   +"/"+"Time:"+QString::number(block_queue[i].time)
                                                   +"/"+"Priority:"+QString::number(block_queue[i].priority)));
    }
    running_time = 0;
    delet_all(ui->verticalLayout_4);
    ui->progressBar->reset();
}
void Widget::on_pushButton_7_clicked()
{
ready_queue.push_back(block_queue.front());
block_queue.erase(block_queue.begin());
delet_all(ui->verticalLayout_6);
for(int i = 0 ; i< block_queue.size();i++)
{
    //qDebug()<<ready_queue[i].PID;
    ui->verticalLayout_6->addWidget(new QLabel("PID:"+QString::number(block_queue[i].PID)
                                               +"/"+"Name:"+block_queue[i].name
                                               +"/"+"Size:"+QString::number(block_queue[i].size)
                                               +"/"+"Time:"+QString::number(block_queue[i].time)
                                               +"/"+"Priority:"+QString::number(block_queue[i].priority)));
}
}

void Widget::on_pushButton_4_clicked()
{
   vector<PCB>::iterator queue_iterator;
   for(queue_iterator = ready_queue.begin();queue_iterator != ready_queue.end();queue_iterator++)
   {
       if(queue_iterator->PID == ui->comboBox_3->currentText().toInt())
       {

           suspended_queue.push_back(*queue_iterator);
           AddNewPartition(&*queue_iterator);
           ready_queue.erase(queue_iterator);
           break;
       }
   }
   for(queue_iterator = block_queue.begin();queue_iterator != block_queue.end();queue_iterator++)
   {
       if(queue_iterator->PID == ui->comboBox_3->currentText().toInt())
       {

           suspended_queue.push_back(*queue_iterator);
           AddNewPartition(&*queue_iterator);
           block_queue.erase(queue_iterator);
           delet_all(ui->verticalLayout_6);
           break;
       }
   }
   delet_all(ui->verticalLayout_5);
   for(int i= 0; i<suspended_queue.size();i++)
   {
       ui->verticalLayout_5->addWidget(new QLabel("Name:"+suspended_queue[i].name+" PID:"+QString::number((suspended_queue[i].PID))));
   }
}

void Widget::on_pushButton_3_clicked()
{
    vector<PCB>::iterator queue_iterator;
    for(queue_iterator = suspended_queue.begin();queue_iterator != suspended_queue.end();queue_iterator++)
    {
        if(queue_iterator->PID == ui->comboBox_4->currentText().toInt())
        {
            RecNewPCB(&*queue_iterator);
            suspended_queue.erase(queue_iterator);
            break;
        }
    }
    delet_all(ui->verticalLayout_5);
    for(int i= 0; i<suspended_queue.size();i++)
    {
        ui->verticalLayout_5->addWidget(new QLabel("Name:"+suspended_queue[i].name+" PID:"+QString::number((suspended_queue[i].PID))));
    }

}

void Widget::on_pushButton_8_clicked()
{
    for(int i = 1;i<16;i++)
    {
        PCB *new_PCB = new PCB();
        new_PCB->name = QString::number(i);
        new_PCB->size = i;
        new_PCB->time = i;
        new_PCB->priority = rand();
        new_PCB->PID = systime+100+i;
        if(Storage_empty(&storage_linklist,new_PCB)!=-1)
        {


            //draw_Rec(float(new_PCB->storage_begin)/float(storage)*800,0,float(new_PCB->size)/float(storage)*800,50,*new QColor(rand()%256,rand()%256,rand()%256));
            new_PCB->color =new QColor(rand()%256,rand()%256,rand()%256);
           // new_PCB->PID = systime+100;
            new_PCB->status = *new QString("Ready");
            ui->verticalLayout_3->addWidget(new QLabel("PID:"+QString::number(new_PCB->PID)
                                                       +"/"+"Name:"+new_PCB->name
                                                       +"/"+"Size:"+QString::number(new_PCB->size)
                                                       +"/"+"Time:"+QString::number(new_PCB->time)
                                                       +"/"+"Priority:"+QString::number(new_PCB->priority)));
            ready_queue.push_back(*new_PCB);//这个顺序非常讲究，必须将这个放最后一个，这样pcb才能够被赋上值
           // qDebug()<<new_PCB->PID;
        }
        else
        {   new_PCB->color =new QColor(rand()%256,rand()%256,rand()%256);
            reverse_queue.push_back(*new_PCB);
            //ui->verticalLayout_2->addWidget(new QLabel(QString(new_PCB->name)));
        }
    }
}
