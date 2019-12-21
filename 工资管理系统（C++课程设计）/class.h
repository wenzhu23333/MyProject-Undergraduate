#include<iostream>
#include<fstream>
using namespace std;
#include<string>
class staff
{
public:
	staff *next;
	staff() {};
	~staff() {};
	void display()
	{
		cout << num;
	}
protected:
	int num;
	string name;
	string sex;
	int age;
	float ori_salary;
	string address;
};
class formal_staff :public staff
{
public:
	formal_staff *next;
	formal_staff() {};
	~formal_staff() {};
	//友元重载运算符
	friend istream& operator>>(istream&, formal_staff&);
	friend ostream& operator<<(ostream&, formal_staff&);
	int getnum()const { return num; }
	string getname()const { return name; }
	string getsex()const { return sex; }
	int getage()const { return age; }
	float getori_salary()const { return ori_salary; }
	string getaddress()const { return address; }
	float getallowance()const { return  allowance; }
	float getpension()const { return   pension; }
	float gethousing_fund()const { return   housing_fund; }
	float gettax()const { return   tax; }
	float getinsurance()const { return   insurance; }
	float getsalary()const { return   salary; }
	float calculate_salary();
	void modify_formal();
	void modify_num_formal();
protected:
	static float sum_formal_salary;
	static int count_formal;
	static float formal_average_salary;
	float allowance;
	float pension;
	float housing_fund;
	float tax;
	float insurance;
	float salary;
};
class informal_staff :public staff
{
public:
	informal_staff *next;
	//类的输入输出
	friend istream& operator>>(istream&, informal_staff&);
	friend ostream& operator<<(ostream&, informal_staff&);
	int getnum()const { return num; }
	string getname()const { return name; }
	string getsex()const { return sex; }
	int getage()const { return age; }
	float getori_salary()const { return ori_salary; }
	string getaddress()const { return address; }
	float getbonus()const { return bonus; }
	float gettax()const { return tax; }
	float getsalary()const { return salary; }
	float calculate_salary();
	void modify_informal();
	void modify_num_informal();
protected:
	static float sum_informal_salary;
	static int count_informal;
	static float informal_average_salary;
	float bonus;
	float tax;
	float salary;

};

ostream& operator<<(ostream&output, formal_staff&a);
istream& operator>>(istream&input, formal_staff&a);
ostream& operator<<(ostream&output, informal_staff&a);
istream& operator>>(istream&input, informal_staff&a);
formal_staff* input_formal_staff(formal_staff* head);
informal_staff* input_informal_staff(informal_staff* head);
formal_staff* seek_formal_staff(formal_staff* head);
informal_staff* seek_informal_staff(informal_staff* head);
formal_staff* insert_formal_staff(formal_staff* head);
informal_staff* insert_informal_staff(informal_staff* head);
formal_staff* del_formal_staff(formal_staff *head, formal_staff* del_head);
informal_staff* del_informal_staff(informal_staff *head, informal_staff* del_head);
void print_formal_staff(formal_staff *head);
void print_informal_staff(informal_staff *head);
formal_staff* modify_formal_staff(formal_staff *head);
informal_staff* modify_informal_staff(informal_staff *head);
informal_staff* statistical_informal_staff(informal_staff *head);
formal_staff* statistical_formal_staff(formal_staff *head);
void save_formal_staff(formal_staff *head);
void save_informal_staff(informal_staff *head);
formal_staff* get_formal_staff(formal_staff *head);
informal_staff* get_informal_staff(informal_staff *head);
