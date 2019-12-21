#ifndef STORAGE_H
#define STORAGE_H
#include<QString>
#include<list>
#include<algorithm>
using namespace std;
//static int storage;
static int partition_num = 0;
class partition_
{
public:
    int start;
    int partition_size;
    QString *divide_status;
    const bool operator < (const partition_ &partition) const
    {
        return start<partition.start;
    }
};
static partition_ *temp_partition;
static list<partition_> storage_linklist;

static int Minsize = 5;
#endif // STORAGE_H
