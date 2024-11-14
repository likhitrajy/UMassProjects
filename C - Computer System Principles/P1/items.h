struct Item
{
 
 
  char *name;
  struct Item *next;

  struct Character *character;
  struct Room *room;
};


struct Item *item (char *name, struct Item *next);

int takeItem (char *, struct Character *, struct Room *);

int dropItem (char *, struct Character *, struct Room *);
