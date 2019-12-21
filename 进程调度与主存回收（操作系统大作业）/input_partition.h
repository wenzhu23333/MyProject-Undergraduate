#ifndef INPUT_PARTITION_H
#define INPUT_PARTITION_H

#include <QWidget>

namespace Ui {
class input_partition;
}

class input_partition : public QWidget
{
    Q_OBJECT

public:
    explicit input_partition(QWidget *parent = 0);
    ~input_partition();

private slots:
    void on_pushButton_2_clicked();

    void on_pushButton_clicked();

private:
    Ui::input_partition *ui;
};

#endif // INPUT_PARTITION_H
