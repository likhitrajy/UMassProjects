#include "data.h"
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int
convertCharToNumber (char ch)
{
  if (ch >= '0' && ch <= '9')
    {
      return ch - '0';
    }
  else if (ch >= 'A' && ch <= 'F')
    {
      return ch - 'A' + 10;
    }
  else
    {
      return -1;
    }
}

char
convertNumberToChar (int n)
{
  if (n >= 0 && n <= 9)
    {
      return n + '0';
    }
  else if (n >= 10 && n <= 15)
    {
      return n - 10 + 'A';
    }
  else
    {
      return '\0';
    }
}

/*
 * Part 1: Read and understand what's going on in this file and
 * in data.h. You'll find that main() in main.c will call these
 * functions. You'll also find some unit tests in test.cpp that are
 * built using gtest, a C++-based unit testing framework. You may
 * want to build your own tests using either C (and add them in main.c),
 * or C++ (and add them in test.cpp).
 */

Data
convert_to_base_n (Data src, unsigned char n)
{
if (src.len == 1 && src.data->number == '0')
    {
	src.base = n;
      return src;
    }

  Data new_data;
  new_data.base = n;
  new_data.sign = src.sign;
  new_data.number_bits = src.number_bits;
  new_data.data = NULL;

  int sum = 0;
  int len = 0;
  DataNode *curNode = src.data;
  while (curNode != NULL)
    {
      int temp = convertCharToNumber (curNode->number);
      sum += temp * pow (src.base, src.len - 1);
      src.len--;
      curNode = curNode->next;
    }
  DataNode *oldNode = NULL;
  while ((sum / n != 0) || (sum % n != 0))
    {
      DataNode *temp;
      temp = malloc (sizeof (struct DataNode));
      temp->next = oldNode;
      oldNode = temp;
      temp->number = convertNumberToChar (sum % n);
      sum = sum / n;
      len++;

    }
  new_data.len = len;
  new_data.data = oldNode;
  return new_data;
}

int
convert_to_int (Data src)
{
  int sum = 0;
  DataNode *curNode = src.data;
  if (src.sign == 0)
    {
      for (int i = src.len - 1; i >= 0; i--)
	{
	  sum += pow (src.base, i) * convertCharToNumber (curNode->number);
	  curNode = curNode->next;
	}
    }
  else if (src.sign == 1)
    {
      Data baseTwo = convert_to_base_n (src, 2);
      curNode = baseTwo.data;
      if (baseTwo.number_bits == baseTwo.len)
	{
	  sum =
	    -(pow (2, baseTwo.len - 1)) *
	    convertCharToNumber (curNode->number);
	  curNode = curNode->next;
	  for (int i = baseTwo.len - 2; i >= 0; i--)
	    {
	      sum += pow (2, i) * convertCharToNumber (curNode->number);
	      curNode = curNode->next;
	    }
	}
      else if (baseTwo.number_bits != baseTwo.len)
	{
	  for (int i = baseTwo.len - 1; i >= 0; i--)
	    {
	      sum += pow (2, i) * convertCharToNumber (curNode->number);
	      curNode = curNode->next;
	    }
	}
    }
  return sum;
}

Data
left_shift (Data src, int n)
{
  Data new_data;
  DataNode *newNode;
  DataNode *tail;
  new_data = convert_to_base_n (src, 2);
  DataNode *curNode = new_data.data;
  while (curNode != NULL)
    {
      if (curNode->next == NULL)
	{
	  tail = curNode;
	}
      curNode = curNode->next;
    }

  for (int i = 0; i < n; i++)
    {
      newNode = malloc (sizeof (struct DataNode));
      newNode->next = NULL;
      newNode->number = '0';
      tail->next = newNode;
      new_data.len++;
      tail = newNode;
    }
  while (new_data.len > new_data.number_bits)
    {
      new_data.data = new_data.data->next;
      new_data.len--;
    }
  while (new_data.data -> number == '0'){
      new_data.data = new_data.data->next;
      new_data.len--;
  }
    return new_data;
}



Data
right_shift (Data src, int n)
{
	Data new_data;
  
  DataNode *stl;
  new_data = convert_to_base_n (src, 2);
  DataNode *curNode = new_data.data;
  int len = new_data.len;
  if (new_data.sign == 0)
    {
      if (n > new_data.len)
	{
	  new_data.data = NULL;
	}
      for (int i = 0; i < n; i++)
	{

	  while (curNode != NULL)
	    {
	      if (curNode->next->next == NULL)
		{
		  stl = curNode;
		}
	      curNode = curNode->next;
	    }
	  if (stl->next == NULL)
	    {
	      new_data.data = NULL;
	      break;
	    }
	  stl->next = NULL;
	  len--;
	}
      new_data.len = len;
    }


return new_data;
}

