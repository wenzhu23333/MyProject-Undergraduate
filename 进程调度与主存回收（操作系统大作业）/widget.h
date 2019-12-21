#ifndef WIDGET_H
#define WIDGET_H
#include "inputjob.h"
#include "ui_inputjob.h"
#include <QWidget>
#include"input_storage.h"
#include"storage.h"
#include"QImage"
#include<QTimer>
#include<list>
namespace Ui {
class Widget;
}

class Widget : public QWidget
{
    Q_OBJECT

public:
    explicit Widget(QWidget *parent = 0);
    ~Widget();
    Ui::Widget* getMainUi()
    {
        return ui;
    }
    int JCB_record;
    //1为先来先服务，2为短作业优先，3为优先权优先（非抢占式）
    int PCB_record;
    //1为先来先服务，2为短作业优先，3为优先权调度，4为短作业优先，5为高相应比优先
    void paintEvent(QPaintEvent *);
    void draw_Rec(int const x, int const y, int const with, int const height,QColor color);
    int Storage_empty(list<partition_> *linklist,PCB *pcb);
    void delet_all(QVBoxLayout *ex);
    void erase_Rec(int const x, int const y, int const with, int const height);
    void AddNewPartition(PCB *pcb);
signals:
    void SendEraRec(PCB*Era_pcb);
public slots:
    void addjob();
    void Systime_count();
    void RecEraRec(PCB* Era_pcb);
private slots:
    void on_pushButton_2_clicked();
    void RecMessage(int);
    void RecNewPCB(PCB *new_PCB);

    void on_pushButton_clicked();

    void on_pushButton_6_clicked();

    void on_pushButton_7_clicked();

    void on_pushButton_4_clicked();

    void on_pushButton_3_clicked();

    void on_pushButton_8_clicked();

private:
    Ui::Widget *ui;
    inputjob *test;
    input_storage *input_storage_begin;
    QTimer *timer = new QTimer(this);
    QPixmap *paint_area;
    int storage;

};

#endif // WIDGET_H
