#include"input_partition.h"
#include"input_storage.h"
#include"ui_input_storage.h"
#include"inputjob.h"
#include"input_storage.h"
#include<QString>
#include<QWidget>
#include<QMessageBox>
#include<QDebug>
#include<widget.h>
input_storage::input_storage(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::input_storage)
{
    ui->setupUi(this);
}

input_storage::~input_storage()
{
    delete ui;
}

void input_storage::on_pushButton_clicked()
{
    if(ui->lineEdit->text().isEmpty())
    {
        QMessageBox::warning(this, tr("警告信息"), tr("所填信息不能为空！"));
                return;
    }
    //storage = ui->lineEdit->text().toInt();
    //qDebug()<<storage;
    this->close();
    emit sendMessage(ui->lineEdit->text().toInt());
}

void input_storage::on_pushButton_2_clicked()
{
    this->close();
}
