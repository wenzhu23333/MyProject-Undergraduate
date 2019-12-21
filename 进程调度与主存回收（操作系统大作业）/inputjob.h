#ifndef INPUTJOB_H
#define INPUTJOB_H
#include"pcb.h"
#include <QDialog>

namespace Ui {
class inputjob;
}

class inputjob : public QDialog
{
    Q_OBJECT

public:
    explicit inputjob(QWidget *parent = 0);
    ~inputjob();
signals:
    void SendNewPCB(PCB *new_PCB);

private slots:
    void on_pushButton_clicked();

    void on_pushButton_2_clicked();

private:
    Ui::inputjob *ui;
    PCB *input_PCB;
};

#endif // INPUTJOB_H
