#include "bits.h"
#include "cache.h"

int get_set(Cache *cache, address_type address) {
  // TODO:
  //  Extract the set bits from a 32-bit address.
  //
  unsigned int set_bits1 = (address << (32 - ((cache->set_bits) + (cache->block_bits) )));
  set_bits1 = set_bits1 >> ((cache->block_bits) + (32 - ((cache->set_bits) + (cache->block_bits))));
  

  return set_bits1;
}

int get_tag(Cache *cache, address_type address) {
  // TODO:
  // Extract the tag bits from a 32-bit address.
  //
    unsigned int tag = (address >> ( (cache->block_bits) + (cache->set_bits) ) );
  return tag;
}

int get_byte(Cache *cache, address_type address) {
  // TODO
  // Extract the block offset (byte index) bits from a 32-bit address.
  //
  unsigned int offset = (address << (32 - (cache->block_bits) ));
  offset = offset >> (32 - (cache->block_bits));
  return offset;
}
