#include <arpa/inet.h>
#include <ctype.h>
#include <netdb.h>
#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <strings.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <unistd.h>

#define MAXLINE 8192 /* Max text line length */

int open_clientfd(char *hostname, int port) {
  // The client's socket file descriptor.
  int clientfd;

  // The hostent struct is used to get the IP address of the server
  // using DNS.
  //
  // struct hostent {
  //   char *h_name;        // official domain name of host
  //   char **h_aliases;    // null-terminated array of domain names
  //   int  h_addrtype;     // host address type (AF_INET)
  //   int  h_length;       // length of an address, in bytes
  //   char **h_addr_list;  // null-terminated array of in_addr structs
  // };
  struct hostent *hp;

  // serveraddr is used to record the server information (IP address
  // and port number).
  //
  // struct sockaddr_in {
  //   short            sin_family;   // e.g. AF_INET
  //   unsigned short   sin_port;     // e.g. htons(3490)
  //   struct in_addr   sin_addr;     // see struct in_addr, below
  //   char             sin_zero[8];  // zero this if you want to
  // };
  struct sockaddr_in serveraddr;

  printf("Client is creating a socket.\n");

  // First, we create the socket file descriptor with the given
  // protocol and protocol family.
  if ((clientfd = socket(AF_INET, SOCK_STREAM, 0)) < 0) return -1;

  // Query DNS for the host (server) information.
  if ((hp = gethostbyname(hostname)) == NULL) return -2;

  // The socket API requires that you zero out the bytes!
  bzero((char *)&serveraddr, sizeof(serveraddr));

  // Record the protocol family we are using to connect.
  serveraddr.sin_family = AF_INET;

  // Copy the IP address provided by DNS to our server address
  // structure.
  bcopy((char *)hp->h_addr_list[0], (char *)&serveraddr.sin_addr.s_addr,
        hp->h_length);

  // Convert the port from host byte order to network byte order and
  // store this in the server address structure.
  serveraddr.sin_port = htons(port);

  printf("Client is trying to connect to %s (%s:%d).\n", hostname,
         inet_ntoa(serveraddr.sin_addr), port);

  // Establish a connection with the server.
  if (connect(clientfd, (struct sockaddr *)&serveraddr, sizeof(serveraddr)) < 0)
    return -1;

  printf("Client connected.\n");

  // Return the connected file descriptor.
  return clientfd;
}

int main(int argc, char **argv) {
  // The client socket file descriptor.
  int clientfd;

  // The port number.
  int port;

  // Variable to store the host/server domain name.
  char *host;

  // A buffer to receive data from the server.
  char buff[MAXLINE];

  // First, we check the program arguments:
  if (argc != 4) {
    fprintf(stderr, "usage: %s <netID> <host> <port>\n", argv[0]);
    exit(0);
  }

  // First argument is netID, second is port, third is host:
  char *netID = argv[1];
  port = atoi(argv[2]);
  host = argv[3];
  
  // Open the client socket file descriptor given the host and port:
  clientfd = open_clientfd(host, port);

  // Print "type: " and fflush to stdout:
  sprintf(buff, "cs230 HELLO %s\n", netID);
  send(clientfd, buff, strlen(buff), 0);
  
  
  int recv_len = recv(clientfd, buff, sizeof(buff), 0);
  buff[recv_len] = '\0';
  char min[] = "min";
  char max[] = "max";
  char med[] = "median";
 
  while(strstr(buff, "BYE") != "BYE") {
    char cs[MAXLINE];
    char status[MAXLINE];
    char op[MAXLINE];
    int n1, n2, n3, n4, n5;

    sscanf(buff, "%s %s %s %d %d %d %d %d", cs, status, op, &n1, &n2, &n3, &n4, &n5);
    int num[5] = {n1, n2, n3, n4, n5};
    int res = 0;
    if(strcmp(op, min) == 0){
      int i = 0;
      int z = 0;
      for(z = 1; z < 5; z++){
        if(num[z] < num[i]){
          i = z;
        }
      }
      res = num[i];
    }
    else if(strcmp(op, max) == 0){
      int i = 0;
      int z = 0;
      for(z = 1; z < 5; z++){
        if(num[z] > num[i]){
          i = z;
        }
      }
      res = num[i];
    }
    else if(strcmp(op, med) == 0){
      int i = 0;
      int j = 0;
      int z = 0;

      for(i=0 ; i<5 ; i++){
        for(j=0 ; j<4 ; j++){
          if(num[j]>num[j+1]){
              z = num[j];
              num[j] = num[j+1];
              num[j+1] = z;
          }
        }
      }
      res = num[2];
    }

    sprintf(cs, "cs230 %d\n", res);
    send(clientfd, cs, strlen(cs), 0);
    int recv_len = recv(clientfd, buff, sizeof(buff), 0);
    buff[recv_len] = '\0';
  }
  // Close the file descriptor:
  close(clientfd);
  exit(0);
}