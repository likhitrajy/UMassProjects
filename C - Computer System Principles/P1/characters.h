
struct Character
{


  char *name;

  struct Item *items;

  int roomX;
  int roomY;
};


struct Character *character (char *name, struct Item *items, int roomX,
			     int roomY);
