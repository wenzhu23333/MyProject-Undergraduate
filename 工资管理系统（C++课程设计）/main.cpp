#include<iostream>
#include<fstream>
using namespace std;
#include<string>
#include"class.h"
int main()
{

	formal_staff* a,* m;
	m = new formal_staff;
	m->next = NULL;
	a = new formal_staff;
	//get_formal_staff(a);
	informal_staff* b,*n;
	n = new informal_staff;
	n->next = NULL;
	b = new informal_staff;
	//get_informal_staff(b);
	int i;
loop:
system("cls");
cout << "***********************************************" << endl; 
cout << "*" << "             ****员工管理系统****            " << "*" << endl;
cout << "*" << "                1.员工信息录入               " << "*" << endl;
cout << "*" << "                2.员工信息插入               " << "*" << endl;
cout << "*" << "                3.员工信息删除               " << "*" << endl;
cout << "*" << "                4.员工信息输出               " << "*" << endl;
cout << "*" << "                5.员工信息修改               " << "*" << endl;
cout << "*" << "                6.员工信息查找               " << "*" << endl;
cout << "*" << "                7.员工信息统计               " << "*" << endl; 
cout << "*" << "                8.退出系统                   " << "*" << endl; 
cout << "*" << "                9.初始化该系统               " << "*" << endl;
cout << "*" << "                10.保存到文件                " << "*" << endl;
cout << "*" << "                11.读取文件信息              " << "*" << endl;
cout << "*" << "                12.回收站                    " << "*" << endl;
cout << "***********************************************" << endl;

cout << "请选择:" << endl;
cin >> i;
switch (i)
{
case 1:
{
	mark1:
	system("cls");
	cout << "请选择录入员工类型：" << endl;
	cout << "(1)录入正式工" << endl;
	cout << "(2)录入临时工" << endl;
	cout << "请输入：";
	cin >> i;
	switch (i)
	{
	case 1:
	{
		input_formal_staff(a);
		break;
	}
	case 2:
	{
		input_informal_staff(b);
		break;
	}
	default:
	{
		cout << "输入错误！请重新输入" << endl;
		goto mark1;
	}
	}
	goto loop;
}
case 2:
{
    mark2:
	system("cls");
	cout << "请选择插入员工类型：" << endl;
	cout << "(1)插入正式工" << endl;
	cout << "(2)插入临时工" << endl;
	cout << "请输入：" ;
	cin >> i;
	switch (i)
	{
	case 1:
	{
		if (insert_formal_staff(a) == a)
			goto mark2_3;
		insert_formal_staff(a);
		mark2_3:
		cout << "请选择：" << endl;
		cout << "(1)继续插入" << endl;
		cout << "(2)返回主界面" << endl;
		mark2_1:
		cout << "请输入：" ;
		cin >> i;
		if (i == 1)
			goto mark2;
		else if (i==2)
		    break;
		else
		{
			cout << "输入错误！请重新输入。" << endl;
			goto mark2_1;
		}
	}
	case 2:
	{
		if (insert_informal_staff(b) == b)
			goto mark2_4;
		insert_informal_staff(b); 
	mark2_4:
		cout << "请选择：" << endl;
		cout << "(1)继续插入" << endl;
		cout << "(2)返回主界面" << endl;
	mark2_2:
		cout << "请输入：";
		cin >> i;
		if (i == 1)
			goto mark2;
		else if (i == 2)
			break;
		else
		{
			cout << "输入错误！请重新输入。" << endl;
			goto mark2_2;
		}
	}
	default:
	{
		cout << "输入错误！请重新输入" << endl;
		goto mark2;
	}
	}
	goto loop;
}
case 3:
{
mark3:
	system("cls");
	cout << "请选择删除员工类型：" << endl;
	cout << "(1)删除正式工" << endl;
	cout << "(2)删除临时工" << endl;
	cout << "请输入：" << endl;
	cin >> i;
	switch (i)
	{
	case 1:
	{
		if (del_formal_staff(a,m) == a)
			goto mark3_3;
	del_formal_staff(a,m); 
	mark3_3:
	cout << "请选择：" << endl;
	cout << "(1)继续删除" << endl;
	cout << "(2)返回主界面" << endl;
	mark3_1:
	cout << "请输入：";
	cin >> i;
	if (i == 1)
		goto mark3;
	else if (i == 2)
		break;
	else
	{
		cout << "输入错误！请重新输入" << endl;
		goto mark3_1;
	}
	}
	case 2:
	{
	if (del_informal_staff(b,n) == b)
		goto mark3_4;
	del_informal_staff(b,n);
	mark3_4:
	cout << "请选择：" << endl;
	cout << "(1)继续删除" << endl;
	cout << "(2)返回主界面" << endl;
mark3_2:
	cout << "请输入：";
	cin >> i;
	if (i == 1)
		goto mark3;
	else if (i == 2)
		break;
	else
	{
		cout << "输入错误！请重新输入" << endl;
		goto mark3_2;
	}
	}
	default:
	{
		cout << "输入错误！请重新输入" << endl;
		goto mark3;
	}
	}
	goto loop;

}
case 4:
{
mark4:
	system("cls");
	cout << "请选择输出员工类型：" << endl;
	cout << "(1)输出正式工" << endl;
	cout << "(2)输出临时工" << endl;
	cout << "请输入：" << endl;
	cin >> i;
	switch (i)
	{
	case 1:
	{
		system("cls");
		print_formal_staff(a);
		cout << "返回请按1" << endl;
		mark4_1:
		cout << "请输入：";
		cin >> i;
		if (i == 1)
			break;
		else
		{
			cout << "输入错误，请重新输入！" << endl;
			goto mark4_1;
		}
	}
	case 2:
	{
		print_informal_staff(b); 
		cout << "返回请按1" << endl;
	mark4_2:
		cout << "请输入：";
		cin >> i;
		if (i == 1)
			break;
		else
		{
			cout << "输入错误，请重新输入！" << endl;
			goto mark4_2;
		}
	}
	default:
	{
		cout << "输入错误！请重新输入" << endl;
		goto mark4;
	}
	}
	goto loop;
}
case 5:
{
	system("cls");
	cout << "请选择修改方式" << endl;
	cout << "(1)全部修改" << endl;
	cout << "(2)部分修改" << endl;
mark5_add:
	cout << "请输入：";
	cin >> i;
	if (i == 1)
	{
	mark5:
		system("cls");
		cout << "请选择修改员工类型：" << endl;
		cout << "(1)修改正式工" << endl;
		cout << "(2)修改临时工" << endl;
		cout << "请输入：" << endl;
		cin >> i;
		switch (i)
		{
		case 1:
		{

			modify_formal_staff(a);
		mark5_3:
			cout << "请选择：" << endl;
			cout << "(1)继续修改" << endl;
			cout << "(2)返回主界面" << endl;
		mark5_1:
			cout << "请输入：";
			cin >> i;
			if (i == 1)
				goto mark5;
			else if (i == 2)
				break;
			else
			{
				cout << "输入错误！请重新输入。" << endl;
				goto mark5_1;
			}
		}
		case 2:
		{

			modify_informal_staff(b);
		mark5_4:
			cout << "请选择：" << endl;
			cout << "(1)继续修改" << endl;
			cout << "(2)返回主界面" << endl;
		mark5_2:
			cout << "请输入：";
			cin >> i;
			if (i == 1)
				goto mark5;
			else if (i == 2)
				break;
			else
			{
				cout << "输入错误！请重新输入。" << endl;
				goto mark5_2;
			}
		}
		default:
		{
			cout << "输入错误！请重新输入" << endl;
			goto mark5;
		}
		}
		goto loop;
	}
	else if (i == 2)
	{
	mark5_10:
		system("cls");
		cout << "请选择修改员工类型：" << endl;
		cout << "(1)修改正式工" << endl;
		cout << "(2)修改临时工" << endl;
	mark20:
		cout << "请输入：" << endl;
		cin >> i;
		switch (i)
		{
		case 1:
		{
			cout << "请查找你要修改的职工：";
			formal_staff* q;
			q=seek_formal_staff(a);
			if (q == a)
				goto mark_add2;
			cout << *q;
			q->modify_formal();
		mark_add2:
			cout << "请选择：" << endl;
			cout << "(1)继续修改" << endl;
			cout << "(2)返回主界面" << endl;
		mark5_12:
			cout << "请输入：";
			cin >> i;
			if (i == 1)
				goto mark5_10;
			else if (i == 2)
				break;
			else
			{
				cout << "输入错误！请重新输入。" << endl;
				goto mark5_12;
			}
		}
		case 2:
		{
			cout << "请查找你要修改的职工：";
			informal_staff* q;
			q = seek_informal_staff(b);
			if (q == b)
				goto mark5_13;
			cout << *q;
			q->modify_informal();
		mark5_13:
			cout << "请选择：" << endl;
			cout << "(1)继续修改" << endl;
			cout << "(2)返回主界面" << endl;
		mark5_14:
			cout << "请输入：";
			cin >> i;
			if (i == 1)
				goto mark5_10;
			else if (i == 2)
				break;
			else
			{
				cout << "输入错误！请重新输入。" << endl;
				goto mark5_14;
			}
		}
		default:
		{
			cout << "输入错误，请重新输入！" << endl;
			goto mark20;
		}
		}
		goto loop;
	}
	else
	{
		cout << "输入错误，请重新输入！" << endl;
		goto mark5_add;
	}
}
case 6:
{
mark6:
	system("cls");
	cout << "请选择查找员工类型：" << endl;
	cout << "(1)查找正式工" << endl;
	cout << "(2)查找临时工" << endl;
	cout << "请输入：" << endl;
	cin >> i;
	switch (i)
	{
	case 1:
	{   
		system("cls");
		formal_staff* p;
		p = seek_formal_staff(a);
		if (p == a)
			goto mark6_3;
		cout<<*p; 
		cout << endl;
	mark6_3:
		cout << "(1)返回主界面" << endl;
		cout << "(2)继续查找" << endl;
	mark6_1:
		cout << "请输入：";
		cin >> i;
		if (i == 1)
			break;
		else if (i == 2)
			goto mark6;
		else
		{
			cout << "输入错误！请重新输入" << endl;
			goto mark6_1;
		}
	}
	case 2:
	{   
		system("cls");
		informal_staff* p;
		p = seek_informal_staff(b);
		if (p == b)
			goto mark6_4;
		cout<<*p;
		cout << endl;
	mark6_4:
		cout << "(1)返回主界面"<< endl;
		cout << "(2)继续查找" << endl;
	mark6_2:
		cout << "请输入：";
		cin >> i;
		if (i == 1)
			break;
		else if (i == 2)
			goto mark6;
		else
		{
			cout << "输入错误！请重新输入" << endl;
			goto mark6_2;
		}
	}
	default:
	{
		cout << "输入错误！请重新输入" << endl;
		goto mark6;
	}
	}
	goto loop;
}
case 7:
{
mark7:
	system("cls");
	cout << "请选择展示信息类型：" << endl;
	cout << "(1)展示正式工工资信息" << endl;
	cout << "(2)展示临时工工资信息" << endl;
	cout << "请输入：" << endl;
	cin >> i;
	switch (i)
	{
	case 1:
	{
		statistical_formal_staff(a); 
		cout << "返回主界面请按1" << endl;
	mark7_1:
		cout << "请输入：";
		cin >> i;
		if (i == 1)
			break;
		else
		{
			cout << "输入错误，请重新输入！" << endl;
			goto mark7_1;
		}
	}
	case 2:
	{
		statistical_informal_staff(b); 
		cout << "返回请按1" << endl;
	mark7_2:
		cout << "请输入：";
		cin >> i;
		if (i == 1)
			break;
		else
		{
			cout << "输入错误，请重新输入！" << endl;
			goto mark7_2;
		}
	}
	default:
	{
		cout << "输入错误！请重新输入" << endl;
		goto mark7;
	}
	}
	goto loop;
}
case 8:
{
	system("cls");
	cout << "是否保存文件？" << endl;
	cout << "(1)是" << endl;
	cout << "(2)否" << endl;
	mark8:
	cout << "请输入：";
	cin >> i;
	if (i == 1)
	{
		save_formal_staff(a);
		save_informal_staff(b);
		exit(1);
	}
	else if (i == 2)
		exit(1);
	else
	{
		cout << "输入错误！请重新输入" << endl;
		goto mark8;
	}
		
}
case 9:
{
	system("cls");
	cout << "该操作会丢失所有未保存的数据，确定初始化？" << endl;
	cout << "(1)确定" << endl;
	cout << "(2)取消" << endl;
	mark9:
	cout << "请输入：";
	cin >> i;
	if (i == 1)

	{
		delete a;
		a = new formal_staff;
		delete b;
		b = new informal_staff;
		cout << "操作成功！（按1返回主界面）" << endl;
		cin >> i;
		goto loop;

	}
	else if (i == 2)
		goto loop;
	else
	{
		cout << "输入错误！请重新输入" << endl;
		goto mark9;

	}
		
}
case 10:
{
	system("cls");
	save_formal_staff(a);
	save_informal_staff(b);
	cout << "保存成功！" << endl;
	cout << "按1返回主界面" << endl;
mark10:
	cin >> i;
	if (i == 1)
		goto loop;
	else
	cout << "输入错误!请重新输入！" << endl;
		goto mark10;
	
}
case 11:
{
	system("cls");
	get_formal_staff(a);
	get_informal_staff(b);
	cout << "读取成功！" << endl;
	cout << "按1返回主界面" << endl;
	mark11:
	cin >> i;
	if (i == 1)
		goto loop;
	else
		cout << "输入错误!请重新输入！" << endl;
	goto mark11;
}
case 12:
{
mark12:
	system("cls");
	cout << "请选择：" << endl;
	cout << "（1）展示已删除的正式工" << endl;
	cout << "（2）撤销删除操作" << endl;
	cout << "（3）展示已删除的临时工" << endl;
	cout << "（4）撤销删除操作" << endl;
	cout << "请输入：";
	cin >> i;
	switch (i)
	{
	case 1:
	{
		system("cls");
		print_formal_staff(m);
		cout << "请选择:" << endl;
		cout << "(1)返回回收站" << endl;
		cout << "(2)返回主界面" << endl;
		mark12_1:
		cout << "请输入：";
		cin >> i;
		if (i == 1)
			goto mark12;
		else if (i == 2)
			goto loop;
		else
		{
			cout << "输入错误！请重新输入。" << endl;
			goto mark12_1;
		}
	}
	case 2:
	{
		cout << "确定还原？确定为（1），取消为（2）" << endl;
	mark12_4:
		cout << "请输入：";
		cin >> i;
		if (i == 1)
		{
			formal_staff *p;
			p = a;
			while (p->next!= NULL)
			{
				p = p->next;
			}
			p->next = m->next;
			cout << "还原成功！" << endl;
			cout << "请选择" << endl;
			cout << "(1)返回回收站" << endl;
			cout << "(2)返回主界面" << endl;
		mark12_2:
			cout << "请输入：";
			cin >> i;
			if (i == 1)
				goto mark12;
			else if (i == 2)
				goto loop;
			else
			{
				cout << "输入错误！请重新输入。" << endl;
				goto mark12_2;

			}
		}
		else if (i == 2)
		{
			cout << "请选择:" << endl;
			cout << "(1)返回回收站" << endl;
			cout << "(2)返回主界面" << endl;
		mark12_3:
			cout << "请输入：";
			cin >> i;
			if (i == 1)
				goto mark12;
			else if (i == 2)
				goto loop;
			else
			{
				cout << "输入错误！请重新输入。" << endl;
				goto mark12_3;

			}
		}
		else
		{
			cout << "输入错误！请重新输入。" << endl;
			goto mark12_4;
		}
	}
	case 3:
	{
		system("cls");
		print_informal_staff(n);
		cout << "请选择:" << endl;
		cout << "(1)返回回收站" << endl;
		cout << "(2)返回主界面" << endl;
	mark12_5:
		cout << "请输入：";
		cin >> i;
		if (i == 1)
			goto mark12;
		else if (i == 2)
			goto loop;
		else
		{
			cout << "输入错误！请重新输入。" << endl;
			goto mark12_5;
		}
	}
	case 4:
	{
		cout << "确定还原？确定为（1），取消为（2）" << endl;
	mark12_6:
		cout << "请输入：";
		cin >> i;
		if (i == 1)
		{
			informal_staff *p;
			p = b;
			while (p->next != NULL)
			{
				p = p->next;
			}
			p->next = n->next;
			system("cls");
			cout << "还原成功！" << endl;
			cout << "请选择" << endl;
			cout << "(1)返回回收站" << endl;
			cout << "(2)返回主界面" << endl;
		mark12_7:
			cout << "请输入：";
			cin >> i;
			if (i == 1)
				goto mark12;
			else if (i == 2)
				goto loop;
			else
			{
				cout << "输入错误！请重新输入。" << endl;
				goto mark12_7;
			}
		}
		else if (i == 2)
		{
			cout << "请选择:" << endl;
			cout << "(1)返回回收站" << endl;
			cout << "(2)返回主界面" << endl;
		mark12_8:
			cout << "请输入：";
			cin >> i;
			if (i == 1)
				goto mark12;
			else if (i == 2)
				goto loop;
			else
			{
				cout << "输入错误！请重新输入。" << endl;
				goto mark12_8;
			}
		}
		else
		{
			cout << "输入错误！请重新输入。" << endl;
			goto mark12_6;
		}
	}
}
}
default:
{
	cout << "输入错误，请重新输入。" << endl;
	goto loop;
}
}
}
