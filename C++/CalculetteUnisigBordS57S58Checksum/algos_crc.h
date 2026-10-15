#ifndef ALGOS_CRC_H_INCLUDED
#define ALGOS_CRC_H_INCLUDED

#include <cstdint>
using namespace std;

uint16_t crc16_gsm(const uint8_t* data, size_t length, bool debug=false);

uint32_t crc32_aixm(const uint8_t* data, size_t length);

uint32_t crc32_iso_hdlc(const uint8_t* data, size_t length);

uint64_t crc48_custom(const uint8_t* data, size_t length, bool debug=false);

#endif // ALGOS_CRC_H_INCLUDED
