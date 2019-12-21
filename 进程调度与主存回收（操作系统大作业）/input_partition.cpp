#include "input_partition.h"
#include "ui_input_partition.h"
#include"storage.h"
#include<QMessageBox>
#include<QLabel>
#include<QLineEdit>
#include<QDebug>
using namespace  std;
input_partition::input_partition(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::input_partition)
{

    ui->setupUi(this);
    for(int i = 0; i<partition_num;i++)
    {
    qDebug()<<i;
    QLabel *label_1 = new QLabel(this);
    label_1->setText("起始地址：");
    QLabel *label_2 = new QLabel(this);
    label_2->setText("大小：");
    QLineEdit *lineEdit_1 = new QLineEdit(this);
    QLineEdit *lineEdit_2 = new QLineEdit(this);
    ui->verticalLayout->addWidget(label_1);
    ui->verticalLayout->addWidget(label_2);
    ui->verticalLayout_2->addWidget(lineEdit_1);
    ui->verticalLayout_2->addWidget(lineEdit_2);
    }
}

input_partition::~input_partition()
{
    delete ui;
}

void input_partition::on_pushButton_2_clicked()
{
    this->close();
}

void input_partition::on_pushButton_clicked()
{
    /*if(ui->lineEdit->text().isEmpty()||ui->lineEdit_2->text().isEmpty())
    {
        QMessageBox::warning(this, tr("警告信息"), tr("所填信息不能为空！"));
                return;
    }
    temp->start=ui->lineEdit->text().toInt();
    temp->partition_size=ui->lineEdit_2->text().toInt();
    temp->divide_status="No";
    this->close();*/
}
