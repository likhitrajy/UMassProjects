#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "rooms.h"
#include "items.h"
#include "characters.h"

//struct for the room archetype
struct Room
room (char *name, struct Item *items)
{
  
  
  struct Room thisRoom;

  thisRoom.name = name;

  thisRoom.items = items;

  int player = 0;
  int scarlet = 0;
  int plum = 0;
  int mustard = 0;
  int peacock = 0;
  int green = 0;

  return thisRoom;
}

//Moving the player to a different room

int
moveDirection (char *direction, struct Character *character)
{

  if (strcmp (direction, "north") == 0)
    {

      if (character->roomY != 0)
	{

	  character->roomY = character->roomY - 1;

	  return 1;
	}
      else
	{
	  return 2;
	}

    }
  else if (strcmp (direction, "south") == 0)
    {
      if (character->roomY != 2)
	{

	  character->roomY = character->roomY + 1;

	  return 1;
	}
      else
	{
	  return 2;
	}

    }
  else if (strcmp (direction, "east") == 0)
    {
      if (character->roomX != 2)
	{
	  character->roomX = character->roomX + 1;

	  return 1;
	}
      else
	{
	  return 2;
	}

    }
  else if (strcmp (direction, "west") == 0)
    {

      if (character->roomX != 0)
	{


	  character->roomX = character->roomX - 1;

	  return 1;
	}
      else
	{
	  return 2;
	}

    }
  else
    {
      return 0;
    }
}
