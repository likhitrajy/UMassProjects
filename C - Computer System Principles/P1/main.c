#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include "characters.h"
#include "rooms.h"
#include "items.h"




// Usage of struct to creat the character archetype- (name, item list, which room they are in)
struct Character *
character (char *name, struct Item *items, int roomX, int roomY)
{
  struct Character *thisCharacter =
    (struct Character *) malloc (sizeof (struct Character));

  //character's name
  thisCharacter->name = name;

  //characters item list
  thisCharacter->items = items;

  //character's x coordinate on the board
  thisCharacter->roomX = roomX;

  //character's y coordinate on the board
  thisCharacter->roomY = roomY;

  return thisCharacter;
}



//function to shuffle and randomize to help in different aspects of the game


void
shuffle (int *shuffleNums)
{
  for (int i = 8; i > 0; i--)
    {
      int j = rand () % (i + 1);
      int *a = &shuffleNums[i];
      int *b = &shuffleNums[j];
      int temp = *a;
      *a = *b;
      *b = temp;
    }
}


//help command
void
help_comm ()
{
  printf ("help: Lists out all the options and how to use them\n");
  printf ("list: List of names of items, rooms, and characters\n");
  printf
    ("look: Tells the player in which room they are in, shows all the possible rooms they can go into, shows the characters and the items present in the room \n");
  printf ("inventory: Shows the list of items in your inventory\n");
  printf
    ("go DIRECTION: The directions you could choose from are: NORTH, SOUTH, EAST, WEST. Typing 'go north' for example, will take the player to the room thats north to the current room\n");
  printf
    ("take ITEM: Lets the player take an item in the room and store it in their inventory. Example, 'take rope' would be the ideal command assuming there's rope in the player's room\n");
  printf
    ("drop ITEM: Drops the selected item from player's inventory and the item can be found in the room that the player dropped it in\n");
  printf
    ("clue CHARACTER: This command helps you win the game. For example, the player can guess that ---- is the answer, then the player must type 'clue ----', it must be a character's name\n");
  printf ("    The named character moves to the room the player is in\n");
  printf
    ("    If the player's guess is in the right room, with the right item and names the right character, the player wins the game.\n");
  printf
    ("    The player has a total of ten tries to guess the right answer or else, the player loses and the game creater wins.\n The game will also try to help the player if they have any of their guesses partially right (It tells the player what they got right)\n");
}

//look command
void
look_comm (struct Room rooms[3][3], struct Character *Player)
{


  printf ("Current Room: %s\n", rooms[Player->roomY][Player->roomX].name);


  if (Player->roomY - 1 >= 0)
    {
      printf ("Room North: %s\n",
	      rooms[Player->roomY - 1][Player->roomX].name);
    }
  if (Player->roomY + 1 <= 2)
    {
      printf ("Room South: %s\n",
	      rooms[Player->roomY + 1][Player->roomX].name);
    }
  if (Player->roomX + 1 <= 2)
    {
      printf ("Room East: %s\n",
	      rooms[Player->roomY][Player->roomX + 1].name);
    }
  if (Player->roomX - 1 >= 0)
    {
      printf ("Room West: %s\n",
	      rooms[Player->roomY][Player->roomX - 1].name);
    }

  printf ("\n Characters present in the room:\n");
  if (rooms[Player->roomY][Player->roomX].scarlet == 1)
    {
      printf ("    scarlet\n");
    }
  if (rooms[Player->roomY][Player->roomX].plum == 1)
    {
      printf ("    plum\n");
    }
  if (rooms[Player->roomY][Player->roomX].mustard == 1)
    {
      printf ("    mustard\n");
    }
  if (rooms[Player->roomY][Player->roomX].peacock == 1)
    {
      printf ("    peacock\n");
    }
  if (rooms[Player->roomY][Player->roomX].green == 1)
    {
      printf ("    green\n");
    }
  printf ("\n");

  printf ("Items in your current room:\n");
  struct Item *pointer = rooms[Player->roomY][Player->roomX].items;
  while (pointer != NULL)
    {
      printf ("    %s\n", pointer->name);
      pointer = pointer->next;
    }
  free (pointer);
  printf ("\n");
}


//Shows the list of items, rooms, characters to help the player
void
list_comm (char *roomNames[9], char *characterNames[5], char *itemNames[6])
{
  printf ("Rooms:\n");
  for (int i = 0; i < 9; i++)
    {
      printf ("    %s\n", roomNames[i]);
    }
  printf ("\n");
  printf ("Characters:\n");
  for (int i = 0; i < 5; i++)
    {
      printf ("    %s\n", characterNames[i]);
    }
  printf ("\n");
  printf ("Items:\n");
  for (int i = 0; i < 6; i++)
    {
      printf ("    %s\n", itemNames[i]);
    }
  printf ("\n");
}


void
inventory_comm (struct Character *Player)
{
  printf ("Inventory items:\n");

  struct Item *pointer = Player->items;
  while (pointer != NULL)
    {
      printf ("    %s\n", pointer->name);
      pointer = pointer->next;
    }
  free (pointer);
  printf ("\n");
}




int
main ()
{

  srand (time (NULL));


  //Initializing items
  struct Item *candlestick = item ("candlestick", NULL);
  struct Item *rope = item ("rope", NULL);
  struct Item *pipe = item ("pipe", NULL);
  struct Item *knife = item ("knife", NULL);
  struct Item *wrench = item ("wrench", NULL);
  struct Item *revolver = item ("revolver", NULL);



  //Used to randomize numbers and apply them to items, rooms, characters
  int shuffleNo[9] = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
  shuffle (shuffleNo);


  char *roomName[9] = { "kitchen", "ballroom", "conservatory", "dining room",
    "cellar", "billiard room", "library", "lounge", "study"
  };


  //creating rooms with certain directions for easier reading in the future
  struct Room roomN = room (roomName[shuffleNo[0]], NULL);
  struct Room roomE = room (roomName[shuffleNo[2]], NULL);
  struct Room roomS = room (roomName[shuffleNo[4]], NULL);
  struct Room roomW = room (roomName[shuffleNo[6]], NULL);
  struct Room roomMid = room (roomName[shuffleNo[8]], NULL);
  struct Room roomNW = room (roomName[shuffleNo[7]], NULL);
  struct Room roomNE = room (roomName[shuffleNo[1]], NULL);
  struct Room roomSE = room (roomName[shuffleNo[3]], NULL);
  struct Room roomSW = room (roomName[shuffleNo[5]], NULL);


  //Creating the board
  struct Room board[3][3] = { roomNW, roomN, roomNE,
    roomW, roomMid, roomE,
    roomSW, roomS, roomSE
  };



  //Placing items in different rooms
  struct Room *roomsRandom[9] =
    { &board[0][0], &board[1][0], &board[2][0], &board[0][1], &board[1][1],
    &board[2][1], &board[0][2], &board[1][2], &board[2][2]
  };

  shuffle (shuffleNo);
  roomsRandom[shuffleNo[0]]->items = knife;
  roomsRandom[shuffleNo[1]]->items = candlestick;
  roomsRandom[shuffleNo[2]]->items = pipe;
  roomsRandom[shuffleNo[3]]->items = wrench;
  roomsRandom[shuffleNo[4]]->items = rope;
  roomsRandom[shuffleNo[5]]->items = revolver;



  //Character initialization
  int x;
  int y;

  x = rand () % 3;
  y = rand () % 3;
  struct Character *Player = character ("Player", NULL, x, y);
  board[y][x].player = 1;

  x = rand () % 3;
  y = rand () % 3;
  struct Character *Scarlet = character ("Scarlet", NULL, x, y);
  board[y][x].scarlet = 1;

  x = rand () % 3;
  y = rand () % 3;
  struct Character *Plum = character ("Plum", NULL, x, y);
  board[y][x].plum = 1;

  x = rand () % 3;
  y = rand () % 3;
  struct Character *Mustard = character ("Mustard", NULL, x, y);
  board[y][x].mustard = 1;

  x = rand () % 3;
  y = rand () % 3;
  struct Character *Peacock = character ("Peacock", NULL, x, y);
  board[y][x].peacock = 1;

  x = rand () % 3;
  y = rand () % 3;
  struct Character *Green = character ("Green", NULL, x, y);
  board[y][x].green = 1;




  //Generating a randomized answer for each run
  char *charName[5] = { "Scarlet", "Plum", "Mustard", "Peacock", "Green" };
  char *itemName[6] =
    { "knife", "candlestick", "pipe", "wrench", "rope", "revolver" };

  char *ansRoom = roomName[rand () % 9];
  char *ansChar = charName[rand () % 5];
  char *ansItem = itemName[rand () % 6];


  //Player gets 10 tries to win
  int guess = 10;


  //Printing the answer for easy code checks (comment out if you like)
  printf ("Answer Room: %s\n", ansRoom);
  printf ("Answer Character: %s\n", ansChar);
  printf ("Answer Item: %s\n", ansItem);
  printf ("\n");

  printf
    ("Likhit Raj's Clue game-- Your goal is to guess the correct room, item and the character\n\n");

  //command for the player to type
  char command[100];

  while (1)
    {

      //get's user input
      printf
	("Player's move: (look, help, list, inventory, go ----, take ----, drop ----, clue ---- ) \n");

      scanf ("%[^\n]%*c", command);
      printf ("\n");

      //Player's strtok input 
      char *token;
      token = strtok (command, " ");

      // Reading different commands

      if (strcmp (token, "help") == 0)
	{
	  help_comm ();
	}

      else if (strcmp (token, "look") == 0)
	{
	  look_comm (board, Player);
	}


      else if (strcmp (token, "list") == 0)
	{
	  list_comm (roomName, charName, itemName);
	}

      else if (strcmp (token, "inventory") == 0)
	{
	  inventory_comm (Player);
	}

      else if (strcmp (token, "go") == 0)
	{


	  token = strtok (NULL, " ");

	  int validDirection = moveDirection (token, Player);

	  if (validDirection == 2)
	    {
	      printf ("You cant go there. Retype command\n\n");
	    }
	  if (validDirection == 0)
	    {
	      printf ("Retype command\n\n");
	    }
	}


      else if (strcmp (token, "take") == 0)
	{


	  token = strtok (NULL, " ");

	  if (takeItem (token, Player, &board[Player->roomY][Player->roomX])
	      == 0)
	    {
	      printf ("Item not found. Retype command\n");
	    }
	  else
	    {
	      printf ("The player picked %s up\n\n", token);
	    }
	}



      else if (strcmp (token, "drop") == 0)
	{

	  token = strtok (NULL, " ");

	  if (dropItem (token, Player, &board[Player->roomY][Player->roomX])
	      == 0)
	    {
	      printf ("Item not found\n");
	    }
	  else
	    {
	      printf ("%s has been dropped from your inventory\n\n", token);
	    }
	}






      else if (strcmp (token, "clue") == 0)
	{


	  token = strtok (NULL, " ");

	  //boolean for checking if the chracter name arguent exists
	  int charExists = 1;

	  if (strcmp (token, "plum") == 0)
	    {
	      board[Plum->roomY][Plum->roomX].plum = 0;
	      Plum->roomX = Player->roomX;
	      Plum->roomY = Player->roomY;
	      board[Plum->roomY][Plum->roomX].plum = 1;
	    }
	  else if (strcmp (token, "scarlet") == 0)
	    {
	      board[Scarlet->roomY][Scarlet->roomX].scarlet = 0;
	      Scarlet->roomX = Player->roomX;
	      Scarlet->roomY = Player->roomY;
	      board[Scarlet->roomY][Scarlet->roomX].scarlet = 1;
	    }
	  else if (strcmp (token, "mustard") == 0)
	    {
	      board[Mustard->roomY][Mustard->roomX].mustard = 0;
	      Mustard->roomX = Player->roomX;
	      Mustard->roomY = Player->roomY;
	      board[Mustard->roomY][Mustard->roomX].mustard = 1;
	    }
	  else if (strcmp (token, "peacock") == 0)
	    {
	      board[Peacock->roomY][Peacock->roomX].peacock = 0;
	      Peacock->roomX = Player->roomX;
	      Peacock->roomY = Player->roomY;
	      board[Peacock->roomY][Peacock->roomX].peacock = 1;
	    }
	  else if (strcmp (token, "green") == 0)
	    {
	      board[Green->roomY][Green->roomX].green = 0;
	      Green->roomX = Player->roomX;
	      Green->roomY = Player->roomY;
	      board[Green->roomY][Green->roomX].green = 1;
	    }
	  else
	    {
	      printf ("Character not found\n\n");
	      charExists = 0;
	    }



	  //If the character name is part of the game
	  if (charExists == 1)
	    {

	      //Using this to check how many matches are right

	      int matches = 0;


	      //if room matches with answer room
	      if (strcmp (board[Player->roomY][Player->roomX].name, ansRoom)
		  == 0)
		{
		  printf ("Room Match\n");
		  matches = matches + 1;
		}

	      //if character matches with answer character
	      if (strcmp (token, ansChar) == 0)
		{
		  printf ("Character Match\n");
		  matches = matches + 1;
		}

	      //if item matches with answer item

	      struct Item *pointer =
		board[Player->roomY][Player->roomX].items;
	      while (pointer != NULL)
		{
		  if (strcmp (pointer->name, ansItem) == 0)
		    {
		      printf ("Item Match\n");
		      matches = matches + 1;
		      break;
		    }

		  pointer = pointer->next;
		}
	      pointer = Player->items;
	      while (pointer != NULL)
		{
		  if (strcmp (pointer->name, ansItem) == 0)
		    {
		      printf ("Item Match\n");
		      matches = matches + 1;
		      break;
		    }

		  pointer = pointer->next;
		}
	      free (pointer);
	      printf ("\n");

	      //Ending game. winning scene

	      if (matches == 3)
		{
		  printf ("Player wins. Great job!");
		  break;
		}

	      guess = guess - 1;

	      //Ending game. losing scene
	      if (guess == 0)
		{
		  printf ("Try again next time. You lose.");
		  break;
		}

	      printf ("Guesses left: %i\n\n", guess);
	    }
	}


      else
	{
	  printf ("Unknown command\n\n");
	}
    }
  return 0;
}
