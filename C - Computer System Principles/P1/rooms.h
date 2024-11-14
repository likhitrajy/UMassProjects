struct Room
{
  char *name;
  struct Item *items;
  struct Character *character;


  int player;
  int scarlet;
  int plum;
  int mustard;
  int peacock;
  int green;
};


struct Room room (char *name, struct Item *items);

int moveDirection (char *direction, struct Character *character);
