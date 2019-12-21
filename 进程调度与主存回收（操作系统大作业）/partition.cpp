#include "partition.h"
#include "ui_partition.h"
#include "storage.h"
#include<QMessageBox>
partition::partition(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::partition)
{
    ui->setupUi(this);
}

partition::~partition()
{
    delete ui;
}

void partition::on_pushButton_clicked()
{
    if(ui->lineEdit->text().isEmpty()||ui->lineEdit_2->text().isEmpty())
    {
        QMessageBox::warning(this, tr("警告信息"), tr("所填信息不能为空！"));
               return;
    }
    temp->start = ui->lineEdit->text().toInt();
    temp->partition_size = ui->lineEdit_2->text().toInt();
    temp->divide_status = "NO";
    this->close();
}

void partition::on_pushButton_2_clicked()
{
    this->close();
}
