#include <stdlib.h>
#include <string.h>
#include "items.h"
#include "characters.h"
#include "rooms.h"

//struct for the item archetype
struct Item *
item (char *name, struct Item *next)
{
  struct Item *thisItem = (struct Item *) malloc (sizeof (struct Item));

  thisItem->name = name;

  thisItem->next = next;

  return thisItem;
}

// function for the player being able to pick the item up
int
takeItem (char *itemName, struct Character *Player, struct Room *room)
{
  struct Item *pointer = room->items, *prevNode;


  struct Item *takenItem;


  if (pointer != NULL && strcmp (pointer->name, itemName) == 0)
    {
      takenItem = pointer;

      room->items = pointer->next;

      takenItem->next = Player->items;
      Player->items = takenItem;

      return 1;
    }

  while (pointer != NULL && strcmp (pointer->name, itemName) != 0)
    {
      prevNode = pointer;
      pointer = pointer->next;
    }

  if (pointer != NULL)
    {

      takenItem = pointer;

      prevNode->next = pointer->next;

      takenItem->next = Player->items;
      Player->items = takenItem;

      return 1;

    }
  else
    {
      return 0;
    }
}


//function for the player to drop an item
int
dropItem (char *itemName, struct Character *Player, struct Room *room)
{

  struct Item *pointer = Player->items, *prevNode;


  struct Item *droppedItem;


  if (pointer != NULL && strcmp (pointer->name, itemName) == 0)
    {
      droppedItem = pointer;

      Player->items = pointer->next;

      droppedItem->next = room->items;
      room->items = droppedItem;

      return 1;
    }

  while (pointer != NULL && strcmp (pointer->name, itemName) != 0)
    {
      prevNode = pointer;
      pointer = pointer->next;
    }

  if (pointer != NULL)
    {

      droppedItem = pointer;

      prevNode->next = pointer->next;

      droppedItem->next = room->items;
      room->items = droppedItem;
      return 1;

    }
  else
    {
      return 0;
    }

}
