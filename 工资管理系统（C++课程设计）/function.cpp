#include<iostream>
#include<fstream>
using namespace std;
#include<string>
#include"class.h"
ostream& operator<<(ostream&output, formal_staff&a)
{
	cout << "编号：";
	output << a.num << endl;
	cout << "姓名：";
	output << a.name << endl;
	cout << "性别：";
	output << a.sex << endl;
	cout << "年龄：";
	output << a.age << endl;
	cout << "家庭住址：";
	output << a.address << endl;
	cout << "基本职务工资:";
	output << a.ori_salary << endl;
	cout << "岗位津贴：";
	output << a.allowance << endl;
	cout << "养老金:";
	output << a.pension << endl;
	cout << "住房公积金:";
	output << a.housing_fund << endl;
	cout << "所得税:";
	output << a.tax << endl;
	cout << "医疗保险:";
	output << a.insurance << endl;
	cout << "实发工资:";
	output << a.calculate_salary() << endl;
	return output;
}
istream& operator>>(istream&input, formal_staff&a)
{
	for(;;)
	{
		cout << "编号：";
		input >> a.num;
		if (a.num>0)
			break;
		else
			cerr << "编号输入错误！请重新输入。" << endl;
	}
	cout << "姓名：";
	input >> a.name;
	for (;;)
	{
		cout << "性别：";
		input >> a.sex;
		if (a.sex == "男" || a.sex == "女")
			break;
		else
			cerr << "性别输入错误！请重新输入。" << endl;
	};
	for (;;)
	{
		cout << "年龄：";
		input >> a.age;
		if (a.age>0)
			break;
		else
			cerr << "年龄输入错误！请重新输入。" << endl;
	}
	cout << "家庭住址：";
	input >> a.address;
	for (;;)
	{
		cout << "基本职务工资:";
		input >> a.ori_salary;
		if (a.ori_salary>0)
			break;
		else
			cerr << "基本职务工资输入错误！请重新输入。" << endl;
	}
	for (;;)
	{
		cout << "岗位津贴：";
		input >> a.allowance;
		if (a.allowance>0)
			break;
		else
			cerr << "岗位津贴输入错误！请重新输入。" << endl;
	}
	for (;;)
	{
		cout << "养老金:";
		input >> a.pension;
		if (a.pension>0)
			break;
		else
			cerr << "养老金输入错误！请重新输入。" << endl;
	}
	for (;;)
	{
		cout << "住房公积金:";
		input >> a.housing_fund;
		if (a.housing_fund>0)
			break;
		else
			cerr << "住房公积金输入错误！请重新输入。" << endl;
	}
	for (;;)
	{
		cout << "所得税:";
		input >> a.tax;
		if (a.tax>0)
			break;
		else
			cerr << "所得税输入错误！请重新输入。" << endl;
	}
	for (;;)
	{
		cout << "医疗保险:";
		input >> a.insurance;
		if (a.insurance>0)
			break;
		else
			cerr << "医疗保险输入错误！请重新输入。" << endl;
	}
	
	return input;
}
ostream& operator<<(ostream&output, informal_staff&a)
{
	cout << "编号：";
	output << a.num << endl;
	cout << "姓名：";
	output << a.name << endl;
	cout << "性别：";
	output << a.sex << endl;
	cout << "年龄：";
	output << a.age << endl;
	cout << "家庭住址：";
	output << a.address << endl;
	cout << "基本职务工资:";
	output << a.ori_salary << endl;
	cout << "奖金:";
	output << a.bonus << endl;
	cout << "所得税:";
	output << a.tax << endl;
	cout << "实发工资:";
	output << a.calculate_salary() << endl;
	return output;
}
istream& operator>>(istream&input, informal_staff&a)
{
	for (;;)
	{
		cout << "编号：";
		input >> a.num;
		if (a.num>0)
			break;
		else
			cerr << "编号输入错误！请重新输入。" << endl;
	}
	cout << "姓名：";
	input >> a.name;
	while (1)
	{
		cout << "性别：";
		input >> a.sex;
		if (a.sex == "男" || a.sex == "女")
			break;
		else
			cerr << "性别输入错误！请重新输入。" << endl;
	};
	for (;;)
	{
		cout << "年龄：";
		input >> a.age;
		if (a.age>0)
			break;
		else
			cerr << "年龄输入错误！请重新输入。" << endl;
	}
	cout << "家庭住址：";
	input >> a.address;
	for (;;)
	{
		cout << "基本职务工资:";
		input >> a.ori_salary;
		if (a.ori_salary>0)
			break;
		else
			cerr << "基本职务工资输入错误！请重新输入。" << endl;
	}
	for (;;)
	{
		cout << "奖金：";
		input >> a.bonus;
		if (a.bonus>0)
			break;
		else
			cerr << "奖金输入错误！请重新输入。" << endl;
	}
	for (;;)
	{
		cout << "所得税:";
		input >> a.tax;
		if (a.tax>0)
			break;
		else
			cerr << "所得税输入错误！请重新输入。" << endl;
	}
	return input;
}
float informal_staff::calculate_salary()
{
	salary = ori_salary + bonus - tax;
	return salary;
}
float formal_staff::calculate_salary()
{
	salary = ori_salary + allowance - pension - housing_fund - tax - insurance;
	return salary;
}
//----------------------------------------------------------------------------------------
void formal_staff::modify_num_formal() {
	cin >> num;
}
void informal_staff::modify_num_informal()
{
	cin >> num;
}
//新定义的函数,用于修改num的值

//修改部分正式工函数
void formal_staff::modify_formal()
{
loop_m:
	cout << "请选择要修改的项目:" << endl;
	cout << "1.编号" << endl;
	cout << "2.姓名" << endl;
	cout << "3.性别" << endl;
	cout << "4.年龄" << endl;
	cout << "5.家庭住址" << endl;
	cout << "6.基本职务工资" << endl;
	cout << "7.岗位津贴" << endl;
	cout << "8.养老金" << endl;
	cout << "9.住房公积金" << endl;
	cout << "10.所得税" << endl;
	cout << "11.医疗保险" << endl;
	int i;
	cin >> i;
	switch (i)
	{
	case 1:
		cin >> this->num; break;
	case 2:
		cin >> this->name; break;
	case 3:
		cin >> this->sex; break;
	case 4:
		cin >> this->age; break;
	case 5:
		cin >> this->address; break;
	case 6:
		cin >> this->ori_salary; break;
	case 7:
		cin >> this->allowance; break;
	case 8:
		cin >> this->pension; break;
	case 9:
		cin >> this->housing_fund; break;
	case 10:
		cin >> this->tax; break;
	case 11:
		cin >> this->insurance; break;
	default:
		cout << "输入错误！请重新输入。" << endl;
		goto loop_m;
		break;
	}
	cout << "修改成功！" << endl;
}

//修改部分非正式工
void informal_staff::modify_informal()
{
loop_n:
	cout << "请选择要修改的项目:" << endl;
	cout << "1.编号" << endl;
	cout << "2.姓名" << endl;
	cout << "3.性别" << endl;
	cout << "4.年龄" << endl;
	cout << "5.家庭住址" << endl;
	cout << "6.基本职务工资" << endl;
	cout << "7.奖金" << endl;
	cout << "8.所得税" << endl;
	int i;
	cin >> i;
	switch (i)
	{
	case 1:
		cin >> this->num; break;
	case 2:
		cin >> this->name; break;
	case 3:
		cin >> this->sex; break;
	case 4:
		cin >> this->age; break;
	case 5:
		cin >> this->address;break;
	case 6:
		cin >> this->ori_salary;break;
	case 7:
		cin >> this->bonus; break;
	case 8:
		cin >> this->tax; break;
	default:
		cout << "输入错误！请重新输入。" << endl;
		goto loop_n;
		break;
	}
	cout << "修改成功！" << endl;
}

//开始录入正式工数据
formal_staff* input_formal_staff(formal_staff* head)
{
	formal_staff *p, *q;
	p = q = new formal_staff;
	char k = 'y';  //判断是否继续输入
	if (p == NULL || q == NULL)
	{
		cerr << "申请内存失败！";
		system("pause");
		exit(0);
	}
	cout << "开始录入数据：" << endl;
	//cin >> *p
	//cout << "录入成功！" << endl;
	int i = 1;
	while (k == 'y' || k == 'Y')
	{

		cin >> *p;
	
		if (i == 1)
			head->next = p;
		else
			q->next = p;
		q = p;
		//修改部分从此开始（下面为新添加的部分）--------------------------------------------------
		formal_staff *m = head->next;
		while (m!=q)
		{
			if (p->getnum() == m->getnum())
			{
				mark_input:
				cout << "编号不能重复！请重新输入编号！";
				cout << "请输入：";
				int i = p->getnum();
				p->modify_num_formal();//这个函数时类内新定义的函数，在本文件上面有定义。
				if (p->getnum() == i)
					goto mark_input;
				break;
			}
			m = m->next;
		}
		//结束于此（临时工同理，不再赘述）------------------------------------------------------------------------------
		p = new formal_staff;
		if (p == NULL)
		{
			cerr << "异常错误！";
			system("pause");
			exit(0);
		}

		cout << "录入成功！" << endl;
		cout << "是否继续输入？（是为y/不是为n）" << endl;
		cin >> k;
		if (k == 'y')
			i++;
		system("cls");

	}
	cout << "录入结束,共录入了：" << i << "个职工的信息" << endl;
	p->next = NULL;
	return head;
}

//开始录入临时工数据
informal_staff* input_informal_staff(informal_staff* head)
{
	informal_staff *p, *q;
	p = q = new informal_staff;
	char k = 'y';  //判断是否继续输入
	if (p == NULL || q == NULL)
	{
		cerr << "申请内存失败！";
		system("pause");
		exit(0);
	}
	cout << "开始录入数据：" << endl;
	//cin >> *p
	//cout << "录入成功！" << endl;
	int i = 1;
	while (k == 'y' || k == 'Y')
	{
		cin >> *p;
		if (i == 1)
			head->next = p;
		else
			q->next = p;
		q = p;
		//------------------------------------------------------------------------------------------------
		informal_staff *m = head->next;
		while (m != q)
		{
			if (p->getnum() == m->getnum())
			{
			mark_input2:
				cout << "编号不能重复！请重新输入编号！";
				cout << "请输入：";
				int i = p->getnum();
				p->modify_num_informal();//这个函数时类内新定义的函数，在本文件上面有定义。
				if (p->getnum() == i)
					goto mark_input2;
				break;
			}
			m = m->next;
		}
		//--------------------------------------------------------------------------------------------------
		p = new informal_staff;
		if (p == NULL)
		{
			cerr << "异常错误！";
			system("pause");
			exit(0);
		}

		cout << "录入成功！" << endl;
		cout << "是否继续输入？（是为y/不是为n）" << endl;
		cin >> k;
		if (k == 'y')
			i++;
		system("cls");

	}
	cout << "录入结束,共录入了：" << i << "个职工的信息" << endl;
	p->next = NULL;
	return head;
}

//查找某个正式工数据
formal_staff* seek_formal_staff(formal_staff* head)
{
	if (head->next == NULL)
	{
		cerr << "链表中无数据!请先返回主界面输入链表数据(或者读取文件数据)。" << endl;
		return head;

	}
	while (1)
	{
		cout << "请选择查询方式：" << endl;
		cout << "编号查询请按(1)，姓名查询请按(2)." << endl;
		int i;
		cin >> i;
		if (!(i == 1 || i == 2))
		{
			cerr << "输入错误！请重新输入" << endl;
			continue;
		}
		formal_staff *p = head->next;
		int targetnum;
		string targetname;
		switch (i)
		{
		case 1:
		{
		loop1:
			cout << "请输入编号：" << endl;
			cin >> targetnum;
			p = head->next;
			while (p->getnum() != targetnum)
			{
				p = p->next;
				if (p == NULL)
					break;
			}
			if (p == NULL)
			{
				cout << "没有这个数据，请重新输入！" << endl;
				goto loop1;
			}
			return p;
			break;
		}
		case 2:
		{
		loop2:
			cout << "请输入姓名：" << endl;
			cin >> targetname;
			p = head->next;
			while (p->getname() != targetname)
			{
				p = p->next;
				if (p == NULL)
					break;
			}
			if (p == NULL)
			{
				cout << "没有这个数据，请重新输入！" << endl;
				goto loop2;
			}
			return p;
			break;
		}
		default:
		{
			cerr << "异常错误！（请上报管理员处理）" << endl;
			system("pause");
			exit(0);
			break;

		}
		}
		break;
	}

}

//查找某个临时工的数据
informal_staff* seek_informal_staff(informal_staff* head)
{
	if (head->next == NULL)
	{
		cerr << "链表中无数据!请先返回主界面输入链表数据(或者读取文件数据)。" << endl;
		return head;
	}
	while (1)
	{
		cout << "请选择查询方式：" << endl;
		cout << "编号查询请按(1)，姓名查询请按(2)." << endl;
		int i;
		cin >> i;
		if (!(i == 1 || i == 2))
		{
			cerr << "输入错误！请重新输入" << endl;
			continue;
		}
		informal_staff *p = head->next;
		int targetnum;
		string targetname;
		switch (i)
		{
		case 1:
		{
		loop1:
			cout << "请输入编号：" << endl;
			cin >> targetnum;
			p = head->next;
			while (p->getnum() != targetnum)
			{
				p = p->next;
				if (p == NULL)
					break;
			}
			if (p == NULL)
			{
				cout << "没有这个数据，请重新输入！" << endl;
				goto loop1;
			}
			return p;
			break;
		}
		case 2:
		{
		loop2:
			cout << "请输入姓名：" << endl;
			cin >> targetname;
			p = head->next;
			while (p->getname() != targetname)
			{
				p = p->next;
				if (p == NULL)
					break;
			}
			if (p == NULL)
			{
				cout << "没有这个数据，请重新输入！" << endl;
				goto loop2;
			}
			return p;
			break;
		}
		default:
		{
			cerr << "异常错误！（请上报管理员处理）" << endl;
			system("pause");
			exit(0);
			break;

		}
		}
		break;
	}

}

//插入某个正式工的数据
formal_staff* insert_formal_staff(formal_staff* head)
{
	if (head->next == NULL)
	{
		cerr << "链表中无数据!请先返回主界面输入链表数据(或者读取文件数据)。" << endl;
		return head;
	}
	formal_staff *p, *q;

	cout << "请输入一个新的正式职工数据：" << endl;
	p = new formal_staff;
	/*if (p->next == NULL)
	{
	cerr << "异常错误！(上报管理员处理)" << endl;
	system("pause");
	exit(0);
	}*/
	cin >> *p;
	system("cls");
	cout << "录入成功！" << endl;
	cout << "请问插入在哪里之后?" << endl;
	q = seek_formal_staff(head);
	p->next = q->next;
	q->next = p;
	system("cls");
	cout << "插入成功！";
	return head;
}

//插入某个临时工的数据
informal_staff* insert_informal_staff(informal_staff* head)
{
	if (head->next == NULL)
	{
		cerr << "链表中无数据!请先返回主界面输入链表数据(或者读取文件数据)。" << endl;
		return head;
	}
	informal_staff *p, *q;

	cout << "请输入一个新的临时工数据：" << endl;
	p = new informal_staff;
	/*if (p->next == NULL)
	{
	cerr << "异常错误！(上报管理员处理)" << endl;
	system("pause");
	exit(0);
	}*/
	cin >> *p;
	cout << "录入成功！" << endl;
	cout << "请问插入在哪里之后" << endl;
	q = seek_informal_staff(head);
	p->next = q->next;
	q->next = p;
	system("cls");
	cout << "插入成功！";
	return head;
}

//删除某个正式工的数据
formal_staff* del_formal_staff(formal_staff *head, formal_staff* del_head)
{
	if (head->next == NULL)//判断缓存中是否有数据  
	{
		cerr << "链表中无数据!请先返回主界面输入链表数据(或者读取文件数据)。" << endl;
		return head;
	}
	formal_staff *p, *q;
loop:
	cout << "请选择删除的方式：" << endl;
	cout << "通过编号删除（1），通过姓名删除（2）" << endl;
	int i;
	cin >> i;
	if (!(i == 1 || i == 2))
	{
		cout << "输入错误！请重新输入。" << endl;
		goto loop;
	}
	p = head->next;
	q = head;
	int targetNum;
	string targetName;
	switch (i)
	{
	case 1:
	{
	mark1:
		p = head->next;
		q = head;
		cout << "请输入编号：" << endl;
		cin >> targetNum;
		while (p->getnum() != targetNum)
		{

			q = p;
			p = p->next;
			if (p == NULL)
				break;

		}
		if (p == NULL)
		{
			cerr << "没有这个数据！请重新输入！" << endl;
			goto mark1;
		}
		break;
	}
	case 2:
	{
	mark2:
		p = head->next;
		q = head;
		cout << "请输入姓名：" << endl;
		cin >> targetName;
		while (p->getname() != targetName)
		{
			q = p;
			p = p->next;
			if (p == NULL)
				break;

		}
		if (p == NULL)
		{
			cerr << "没有这个数据！请重新输入！" << endl;
			goto mark2;
		}
		break;
	}
	default:
	{
		cerr << "异常错误！（请上报管理员处理）" << endl;
		system("pause");
		exit(0);
		break;
	}
	}
	i = 0;
	while (1)
	{
		cout << "是否确定删除？是请按（1）， 否请按（2）" << endl;
		cin >> i;
		if (i == 1)
		{
			q->next = p->next;
			cout << "删除成功！" << endl;
			break;
		}
		else if (i == 2)
		{
			cout << "取消成功！" << endl;
			break;
			return head;
		}
		else
		{
			cout << "输入错误！请重新输入。" << endl;
			continue;
		}

	}

	q = del_head;
	while (q->next != NULL)
	{
		q = q->next;
	}
	q->next = p;
	p->next = NULL;

	return head;

}

//删除某个临时工的数据
informal_staff* del_informal_staff(informal_staff *head, informal_staff* del_head)
{
	if (head->next == NULL)//判断缓存中是否有数据  
	{
		cerr << "链表中无数据!请先返回主界面输入链表数据(或者读取文件数据)。" << endl;
		return head;
	}
	informal_staff *p, *q;
loop:
	cout << "请选择删除的方式：" << endl;
	cout << "通过编号删除（1），通过姓名删除（2）" << endl;
	int i;
	cin >> i;
	if (!(i == 1 || i == 2))
	{
		cout << "输入错误！请重新输入。" << endl;
		goto loop;
	}
	p = head->next;
	q = head;
	int targetNum;
	string targetName;
	switch (i)
	{
	case 1:
	{
	mark1:
		p = head->next;
		q = head;
		cout << "请输入编号：" << endl;
		cin >> targetNum;
		while (p->getnum() != targetNum)
		{
			q = p;
			p = p->next;
			if (p == NULL)
				break;

		}
		if (p == NULL)
		{
			cerr << "没有这个数据！请重新输入！" << endl;
			goto mark1;
		}
		break;
	}
	case 2:
	{
	mark2:
		p = head->next;
		q = head;
		cout << "请输入姓名：" << endl;
		cin >> targetName;
		while (p->getname() != targetName)
		{
			q = p;
			p = p->next;
			if (p == NULL)
				break;

		}
		if (p == NULL)
		{
			cerr << "没有这个数据！请重新输入！" << endl;
			goto mark2;
		}
		break;
	}
	default:
	{
		cerr << "异常错误！（请上报管理员处理）" << endl;
		system("pause");
		exit(0);
		break;
	}
	}
	i = 0;
	while (1)
	{
		cout << "是否确定删除？是请按（1）， 否请按（2）" << endl;
		cin >> i;
		if (i == 1)
		{
			q->next = p->next;
			cout << "删除成功！" << endl;
			break;
		}
		else if (i == 2)
		{
			cout << "取消成功！" << endl;
			break;
		}
		else
		{
			cout << "输入错误！请重新输入。" << endl;
			continue;
		}
	}

	q = del_head;
	while (q->next != NULL)
	{
		q = q->next;
	}
	q->next = p;
	p->next = NULL;
	return head;

}

//输出所有正式工数据
void print_formal_staff(formal_staff *head)
{
	formal_staff  *p = head->next;
	if (head->next == NULL)
	{
		cerr << "错误!（链表中无数据）" << endl;
		cerr << "请联系管理员处理！" << endl;
	}
	while (p != NULL)
	{
		cout << *p << endl;
		p = p->next;
	}
}

//输出所有临时工数据
void print_informal_staff(informal_staff *head)
{
	informal_staff  *p = head->next;
	if (head->next == NULL)
	{
		cerr << "错误!（链表中无数据）" << endl;
		cerr << "请联系管理员处理！" << endl;
	}
	while (p != NULL)
	{
		cout << *p << endl;
		p = p->next;
	}
}

//修改正式工的数据
formal_staff* modify_formal_staff(formal_staff *head)
{
	formal_staff *p, *q;
	cout << "请查找你要修改的正式工：" << endl;
	p = seek_formal_staff(head);
	if (p == head)
		return head;
	cout << *p;
	q = new formal_staff;
	q->next = p->next;
	if (q == NULL)
	{
		cerr << "错误!(分配内存失败)" << endl;
		cerr << "请联系管理员解决！" << endl;
		system("pause");
		exit(0);
	}

	//---待修改
	/*cout << "请选择修改方式：" << endl;
	cout << "1.全部修改" << endl;
	cout << "2.部分修改" << endl;*/

	cout << "请修改：" << endl;
	cin >> *q;
	int i = 0;
mark:
	cout << "确定修改请按（1），取消请按（2）" << endl;
	cin >> i;
	if (i == 1)
	{
		*p = *q;
		system("cls");
		cout << "修改成功！" << endl;
	}
	else if (i == 2)
	{
		cout << "取消成功！" << endl;
		delete p;
	}
	else
	{
		cout << "输入有误！请重新输入！" << endl;
		goto mark;
	}


}

//修改临时工的数据
informal_staff* modify_informal_staff(informal_staff *head)
{
	informal_staff *p, *q;
	cout << "请查找你要修改的临时工：" << endl;
	p = seek_informal_staff(head);
	if (p == head)
		return head;
	q = new informal_staff;
	q->next = p->next;
	if (q == NULL)
	{
		cerr << "错误!(分配内存失败)" << endl;
		cerr << "请联系管理员解决！" << endl;
		system("pause");
		exit(0);
	}

	//---待修改
	/*cout << "请选择修改方式：" << endl;
	cout << "1.全部修改" << endl;
	cout << "2.部分修改" << endl;*/

	cout << "请修改：" << endl;
	cin >> *q;
	int i = 0;
mark:
	cout << "确定修改请按（1），取消请按（2）" << endl;
	cin >> i;
	if (i == 1)
	{
		*p = *q;
		system("cls");
		cout << "修改成功！" << endl;
	}
	else if (i == 2)
	{
		cout << "取消成功！" << endl;
		delete p;
	}
	else
	{
		cout << "输入有误！请重新输入！" << endl;
		goto mark;
	}


}

//统计正式工的数据
formal_staff* statistical_formal_staff(formal_staff *head)
{

	if (head->next == NULL)
	{
		cerr << "错误！（链表中无数据，请返回主界面输入数据或者读取数据）" << endl;
		return head;
	}
	formal_staff *p = head->next;
	static float sum_formal_salary = 0;
	static int count_formal = 0;
	static float formal_average_salary;
	while (p != NULL)
	{
		sum_formal_salary += (p->calculate_salary());
		count_formal++;
		p = p->next;
	}
	formal_average_salary = sum_formal_salary / count_formal;
	cout << "现有正式工工资总数为：" << sum_formal_salary << endl;
	cout << "现有正式工人数为：" << count_formal << endl;
	cout << "正式工平均工资为：" << formal_average_salary << endl;
}

//统计临时工的数据
informal_staff* statistical_informal_staff(informal_staff *head)
{

	if (head->next == NULL)
	{
		cerr << "错误！（链表中无数据，请返回主界面输入数据或者读取数据）" << endl;
		return head;

	}
	informal_staff *p = head->next;
	static float sum_informal_salary = 0;
	static int count_informal = 0;
	static float informal_average_salary;
	while (p != NULL)
	{
		sum_informal_salary += (p->calculate_salary());
		count_informal++;
		p = p->next;
	}
	informal_average_salary = sum_informal_salary / count_informal;
	cout << "现有临时工工资总数为：" << sum_informal_salary << endl;
	cout << "现有临时工人数为：" << count_informal << endl;
	cout << "临时工平均工资为：" << informal_average_salary << endl;
}

//保存正式工数据到文件
void save_formal_staff(formal_staff *head)
{
	formal_staff *p = head->next;
	ofstream outfile("正式工信息.txt", ios::out);
	if (!outfile)
	{
		cerr << "文件打开失败！" << endl;
		cerr << "请联系管理员处理！" << endl;
		system("pause");
		exit(0);
	}
	while (p != NULL)
	{
		outfile << endl;
		outfile << p->getnum() << " "<<p->getname()<<" "<<p->getsex()
			<<" "<<p->getage()<<" "<<p->getori_salary()<<" "<<p->getaddress()
			<<" "<<p->getallowance()<<" "<<p->getpension()<<" "<<p->gethousing_fund()
			<<" "<<p->gettax()<<" "<<p->getinsurance()<<" "<<p->getsalary();
		p = p->next;
	}
	outfile.close();
	p = head->next;
	ofstream outfile_dat("正式工二进制信息.dat", ios::out | ios::binary);
	if (!outfile_dat)
	{
		cerr << "文件打开失败!" << endl;
		cerr << "请联系管理员处理" << endl;
		system("pause");
		exit(0);
	}
	while (p != NULL)
	{
		outfile_dat.write((char*)p, sizeof(*p));
		p = p->next;

	}
	outfile_dat.close();
	cout << "保存正式工成功！" << endl;
}

//保存临时工数据到文件
void save_informal_staff(informal_staff *head)
{
	informal_staff *p = head->next;
	ofstream outfile("临时工信息.txt", ios::out);
	if (!outfile)
	{
		cerr << "文件打开失败！" << endl;
		cerr << "请联系管理员处理！" << endl;
		system("pause");
		exit(0);
	}
	while (p != NULL)
	{
	outfile << endl;
	outfile << p->getnum() << " " << p->getname() << " " << p->getsex()
		<< " " << p->getage() << " " << p->getori_salary() << " " << p->getaddress()
		<< " " << p->getbonus() << " " << p->gettax() << " " << p->getsalary();
		p = p->next;
	}
	outfile.close();
	p = head->next;
	ofstream outfile_dat("临时工二进制信息.dat", ios::out | ios::binary);
	if (!outfile_dat)
	{
		cerr << "文件打开失败!" << endl;
		cerr << "请联系管理员处理" << endl;
		system("pause");
		exit(0);
	}
	while (p != NULL)
	{
		outfile_dat.write((char*)p, sizeof(*p));
		p = p->next;

	}
	outfile_dat.close();
	cout << "保存临时工成功！" << endl;
}

//读取正式工数据到内存
formal_staff* get_formal_staff(formal_staff *head)
{
	ifstream infile("正式工二进制信息.dat", ios::in | ios::out);
	if (!infile)
	{
		cerr << "未找到文件！请先输入和保存数据" << endl;
		//待定

	}
	infile.seekg(0, ios::beg);
	formal_staff *p, *q;
	p = q = new formal_staff;
	infile.read((char*)p, sizeof(*p));
	int i = 1;
	while (!infile.eof())
	{
		if (i == 1)
		{
			head->next = p;
		}
		else
		{
			q->next = p;

		}
		q = p;
		p = new formal_staff;
		if (p == NULL)//申请内存失败操作  
		{
			cerr << "异常错误！（请上报管理员处理）" << endl;
			system("pause");
			exit(0);
		}
		infile.read((char*)p, sizeof(*p));
		i++;
	}
	q->next = NULL;
	infile.close();
	return head;
}

//读取临时工数据到内存
informal_staff* get_informal_staff(informal_staff *head)
{
	ifstream infile("临时工二进制信息.dat", ios::in | ios::out);
	if (!infile)
	{
		cerr << "未找到文件！请先输入和保存数据" << endl;
		//待定

	}
	infile.seekg(0, ios::beg);
	informal_staff *p, *q;
	p = q = new informal_staff;
	infile.read((char*)p, sizeof(*p));
	int i = 1;
	while (!infile.eof())
	{
		if (i == 1)
		{
			head->next = p;
		}
		else
		{
			q->next = p;

		}
		q = p;
		p = new informal_staff;
		if (p == NULL)//申请内存失败操作  
		{
			cerr << "异常错误！（请上报管理员处理）" << endl;
			system("pause");
			exit(0);
		}
		infile.read((char*)p, sizeof(*p));
		i++;
	}
	q->next = NULL;
	infile.close();
	return head;
}

//记得修改数据到内存的格式 不换行