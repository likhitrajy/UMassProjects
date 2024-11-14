#ifndef __DATA
#define __DATA

typedef struct DataNode DataNode;
typedef struct Data Data;

// Represents a node in a linked list; each item represents an alphanumeric digit.
struct DataNode {
  unsigned char number;  // '0' - '9' or 'A' - 'F'
                         // Notice that the digit is stored as a char ('5'), not an int (5).
  DataNode *next;
};

// Represents a number, digit-by-digit, stored in a particular base between 2 and 16.
struct Data {
  unsigned char base;  // indicate what base this data is
  unsigned char sign;  // indicate whether this data is signed or unsigned
                       // number. 0 means unsigned and 1 means signed.
                       // 1 does not mean negative! Just that the data stored is
                       // signed.
  unsigned char number_bits;  // the number of bits that will be used to represent
                              // this number when it is converted to binary (base 2).
                              // For this assignment, at most 32.
  unsigned char len;          // length of the linked list in data
  DataNode *data;             // a linked list; each node represents a single digit
                              // of the number; the head of the list stores the most
                              // significant digit
};

int convertCharToNumber(char ch);  // return corresponding integer of ch in base 2 - 16
                                   // '0' should return 0, not 48, 'A' (or 'a')
                                   // should return 10, not 65, etc.
                                   // return -1 if ch is not a valid number

char convertNumberToChar(int n);  // return char representation of n in base 2 - 16
                                  // return '\0' if n is not a number between 0-15.
                                  // 0 should return, not 0; 10 should return 'A', not 65, etc.

Data convert_to_base_n(Data src,
                       unsigned char n);  // Return a new Data that represents src in base n

int convert_to_int(Data src);  // convert src to an C int -- we will not test this
                               // function with numbers that cannot be represented
                               // in a C int (that is, numbers that can only be
                               // represented by unsigned ints)

Data left_shift(Data src,
                int shift);  // Return a new Data *in base 2* that represents an
                             // application of binary operator left shift on src
                             // where shift is less than src.number_bits

Data right_shift(Data src,
                 int shift);  // Return a new Data in *base 2* that represents an
                              // application of binary operator right shift on
                              // src where shift is less than src.number_bits

#endif
