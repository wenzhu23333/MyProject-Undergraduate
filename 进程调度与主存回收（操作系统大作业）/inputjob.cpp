#include "inputjob.h"
#include "ui_inputjob.h"
#include<QMessageBox>
#include<QDebug>
inputjob::inputjob(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::inputjob)
{
    ui->setupUi(this);
}

inputjob::~inputjob()
{
    delete ui;
}

void inputjob::on_pushButton_clicked()
{
    if(ui->lineEdit->text().isEmpty()||ui->lineEdit_2->text().isEmpty()||ui->lineEdit_3->text().isEmpty()||ui->lineEdit_4->text().isEmpty())
    {
        QMessageBox::warning(this, tr("警告信息"), tr("所填信息不能为空！"));
                return;
    }
    input_PCB = new PCB();
    input_PCB->name = ui->lineEdit->text();
    input_PCB->time = ui->lineEdit_3->text().toInt();
    input_PCB->priority = ui->lineEdit_4->text().toInt();
    input_PCB->size = ui->lineEdit_2->text().toInt();
    emit SendNewPCB(input_PCB);
    this->close();
}

void inputjob::on_pushButton_2_clicked()
{
this->close();
}
