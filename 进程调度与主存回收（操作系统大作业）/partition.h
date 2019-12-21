#ifndef PARTITION_H
#define PARTITION_H

#include <QWidget>

namespace Ui {
class partition;
}

class partition : public QWidget
{
    Q_OBJECT

public:
    explicit partition(QWidget *parent = 0);
    ~partition();

private slots:
    void on_pushButton_clicked();

    void on_pushButton_2_clicked();

private:
    Ui::partition *ui;
};

#endif // PARTITION_H
