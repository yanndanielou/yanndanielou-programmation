#include "algos_crc.h"

#include <iostream>
using namespace std;

uint16_t crc16_gsm(const uint8_t* data, size_t length, bool debug)
{
    uint16_t crc = 0x0000;          // Init GSM
    const uint16_t poly = 0x1021;

    for (size_t i = 0; i < length; ++i)
    {
        crc ^= static_cast<uint16_t>(data[i]) << 8;

        if (debug) cout << "data["<< i <<"]=" << (int)(data[i]) << endl;

        for (int bit = 0; bit < 8; ++bit)
        {
            if (crc & 0x8000)
                crc = (crc << 1) ^ poly;
            else
                crc <<= 1;
        }
    }

    return crc ^ 0xFFFF;  // XOR final GSM
}


uint32_t crc32_aixm(const uint8_t* data, size_t length)
{
    uint32_t crc = 0xFFFFFFFF;

    for (size_t i = 0; i < length; ++i)
    {
        crc ^= data[i];

        for (int bit = 0; bit < 8; ++bit)
        {
            if (crc & 1)
                crc = (crc >> 1) ^ 0xEDB88320;
            else
                crc >>= 1;
        }
    }

    return crc ^ 0xFFFFFFFF;
}


uint32_t crc32_iso_hdlc(const uint8_t* data, size_t length)
{
    uint32_t crc = 0xFFFFFFFF;

    for (size_t i = 0; i < length; ++i)
    {
        crc ^= data[i];
        for (int bit = 0; bit < 8; ++bit)
        {
            if (crc & 1)
                crc = (crc >> 1) ^ 0xEDB88320; // poly = x^32 + x^26 + ... + x^2 + x + 1
            else
                crc >>= 1;
        }
    }

    return crc ^ 0xFFFFFFFF;
}


uint64_t crc48_custom(const uint8_t* data, size_t length, bool debug)
{
    const uint64_t POLY = 0x0000D28DB3FA4AADULL; // Polynomial: D2h 8Dh B3h FAh 4Ah Adh
    const uint64_t MASK = 0x0000FFFFFFFFFFFFULL; // 48 bits

    uint64_t crc = 0;// 0x0000FFFFFFFFFFFFULL; // init 48 bits

    cout << "crc_init=" << crc << endl;

    for (size_t i = 0; i < length; ++i)
    {
        // Aligner l'octet sur le MSB (bit 47)
        crc ^= static_cast<uint64_t>(data[i]) << 40;

        if (debug) {
            cout << "data["<< i <<"]=" << (int)(data[i]) << endl;
            cout << "crc_temp_pre=" << crc << endl;
            }

        for (int bit = 0; bit < 8; ++bit)
        {
            if (crc & 0x0000800000000000ULL){ // bit 47
                if (debug) cout << "crc_temp0(bit"<< bit <<")=" << crc << endl;
                crc = (crc << 1) ^ POLY;
                //crc = crc << 1 ;
                //if (debug) cout << "crc_temp1(bit"<< bit <<")=" << crc << endl;
                //crc = crc ^ POLY;
                //if (debug) cout << "crc_temp2(bit"<< bit <<")=" << crc << endl;
                }
            else {
                crc <<= 1;
                }

            crc &= MASK; // forcer 48 bits
            if (debug) cout << "crc_temp(bit"<< bit <<")=" << crc << endl;
        }
        if (debug) cout << "crc_temp_new=" << crc << endl;
    }

    return crc; // pas de XOR final ici
}
