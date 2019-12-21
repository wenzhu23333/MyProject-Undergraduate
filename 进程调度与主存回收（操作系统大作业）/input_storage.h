#ifndef INPUT_STORAGE_H
#define INPUT_STORAGE_H
#include<QWidget>
#include<storage.h>
#include"input_partition.h"
namespace Ui {
class input_storage;
}

class input_storage : public QWidget
{
    Q_OBJECT

public:
    explicit input_storage(QWidget *parent = 0);
    ~input_storage();
signals:
    void sendMessage(int );
private slots:
    void on_pushButton_clicked();

    void on_pushButton_2_clicked();

private:
    Ui::input_storage *ui;
};

#endif // INPUT_STORAGE_H
