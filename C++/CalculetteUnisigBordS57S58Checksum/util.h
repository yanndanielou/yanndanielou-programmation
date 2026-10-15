#ifndef UTIL_INCLUDED
#define UTIL_INCLUDED

#include <cstdint>
#include <iostream>
// fonctions de calcul du TAG HARD
uint32_t calculer_tag_passerelle(int id_passerelle, const uint32_t Bx, const uint32_t tau, const uint32_t Rk, const uint32_t A);

uint32_t calculer_tag_passerelle_1(int id_passerelle);
uint32_t calculer_tag_passerelle_2(int id_passerelle);
uint32_t calculer_tag_passerelle_3(int id_passerelle);
uint32_t calculer_tag_passerelle_4(int id_passerelle);

// fonctions d'affichages de CRC
void afficher_crc48_slice(uint32_t slice);
void afficher_crc48(uint64_t crc48, uint32_t tag1=0, uint32_t tag2=0, uint32_t tag3=0, uint32_t tag4=0);

#endif // UTIL_INCLUDED
