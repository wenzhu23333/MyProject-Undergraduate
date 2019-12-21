#include<iostream>
#include<bits/stdc++.h>
#include<stdio.h>
#include<stdlib.h>
#include<string.h>
using namespace std;

unsigned char Sbox[256];

void swap(unsigned char data[],int i ,int j){
    unsigned char temp=data[i];
    data[i]=data[j];
    data[j]=temp;
}

void initialSbox(unsigned char *key,int size)
{
    for(int i = 0;i<256;i++)
    {
        Sbox[i] = (unsigned char) i;
    }
    int j = 0;
    for(int i = 0;i<256;i++)
    {
        j = (j+Sbox[i]+key[i%size])%256;
        swap(Sbox,i,j);
    }
}

void RC4(unsigned char plainText[],unsigned char Cipher[],unsigned char key[],int size)
{
    int i=0,j=0;
    for( int k =0;k<size;k++)
    {
        i = (i+1)%256;
        j = (j+Sbox[i])%256;
        swap(Sbox,i,j);
        key[k] = Sbox[(Sbox[i]+Sbox[j])%256];
        Cipher[k] = key[k]^plainText[k];
    }
}
void InvRC4(unsigned char output[],unsigned char key[],unsigned char OriginText[],int  size)
{
    for(int i = 0;i<size;i++)
        OriginText[i] = output[i]^key[i];
}
void print(unsigned char* state)
{
    int i;
    for(i=0; i<16; i++)
    {
        printf("%s%X ",state[i]>15 ? "" : "0", state[i]);//自动补0
    }
    printf("\n");
}
int main(int argc, char* argv[])
{

    unsigned char input[] =
    {
        0x11, 0x22, 0x33, 0x44,
        0x55, 0x66, 0x77, 0x88,
        0x99,0x00,0xAA, 0xBB,
        0xCC,0xDD, 0xEE,  0xFF
    };
    unsigned char key[] =
    {
        0x13,  0x57,  0x9B,  0xDF,
        0x02, 0x46,  0x8A, 0xCE,
        0x12,  0x34,  0x56, 0x78,
        0x90, 0xAB,  0xCD, 0xEF
    };
    unsigned char GenerateKey[16];
    unsigned char output[16];
    unsigned char OriginText[16];
    initialSbox(key,16);
    RC4(input,output,GenerateKey,16);
    InvRC4(output,GenerateKey,OriginText,16);
    cout<<"明文:";
    print(input);
    cout<<"密文:";
    print(output);
    cout<<"密钥:";
    print(GenerateKey);
    cout<<"解密:";
    print(OriginText);

}
