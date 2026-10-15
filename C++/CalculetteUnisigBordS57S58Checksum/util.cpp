#include "util.h"
#include "secrets.h"
using namespace std;

uint32_t calculer_tag_passerelle(int id_passerelle, const uint32_t Bx, const uint32_t tau, const uint32_t Rk, const uint32_t A){
    uint64_t code = (uint64_t)Rk * (uint64_t)tau;
    // cout << std::dec << "Rk * Tau = " << Rk << " * " << tau << " = " << code << " mod " << A << " = " << (code % A) << endl;
    code = code % A;
    code = (code * id_passerelle) % A;
    // cout << std::dec << "Rk * Tau * id = " << Rk << " * " << tau << " * " << Rk << " = " << code << " mod " << A << " = " << (code % A) << endl;
    // cout << "A + Bx - Rk*tau*id = " << Bx << " - " << code << " = " << ((uint64_t)Bx + A - code) << endl;
    code = (uint64_t)Bx + A - code;
    return (code % A);
}

uint32_t calculer_tag_passerelle_1(int id_passerelle){
    return calculer_tag_passerelle(id_passerelle, Bx_ident_1_C1, tau_C1, Rk_C1, A1);
}

uint32_t calculer_tag_passerelle_2(int id_passerelle){
    return calculer_tag_passerelle(id_passerelle, Bx_ident_1_C2, tau_C2, Rk_C2, A2);
}

uint32_t calculer_tag_passerelle_3(int id_passerelle){
    return calculer_tag_passerelle(id_passerelle, Bx_ident_2_C1, tau_C1, Rk_C1, A1);
}

uint32_t calculer_tag_passerelle_4(int id_passerelle){
    return calculer_tag_passerelle(id_passerelle, Bx_ident_2_C2, tau_C2, Rk_C2, A2);
}

// fonctions d'affichages
void afficher_crc48_slice(uint32_t slice){
    cout << "SLICE = " << std::dec << slice << " = 0x" << std::hex << slice << endl;
    for (int i=0; i<4; i++) {
            int octet = (slice >> 8*(3-i)) & 0xFF;
            cout << i << " -- " << std::dec << octet << endl;
        }
    cout << std::hex << endl;
}

void afficher_crc48(uint64_t crc48, uint32_t tag1, uint32_t tag2, uint32_t tag3, uint32_t tag4){
        cout << "Affichage du CRC48 sans tag = 0x" << std::hex << std::uppercase << crc48 << std::endl;

	    uint64_t crc48_47_40 = (crc48 >> 40) & 0xFF;
        uint64_t crc48_39_36 = (crc48 >> 36) & 0xF;
        uint64_t crc48_35_32 = (crc48 >> 32) & 0xF;
        uint64_t crc_48_1 = crc48_47_40 + crc48_35_32*256;

        uint64_t crc48_31_24 = (crc48 >> 24) & 0xFF;
        uint64_t crc_48_2 = (crc48_31_24 << 4) + crc48_39_36;

        uint64_t crc48_23_16 = (crc48 >> 16) & 0xFF;
        uint64_t crc48_11_08 = (crc48 >> 8) & 0xF;
        uint64_t crc_48_3 = crc48_23_16 + (crc48_11_08 << 8);

        uint64_t crc48_15_12 = (crc48 >> 12) & 0xF;
        uint64_t crc48_07_00 = crc48 & 0xFF;
        uint64_t crc_48_4 = (crc48_07_00 << 4) + crc48_15_12;

        crc_48_1 += tag1;crc_48_2 += tag2;
        crc_48_3 += tag3;crc_48_4 += tag4;


        cout << "CRC_48_4 = 0x" << crc_48_4 << endl;
        afficher_crc48_slice(crc_48_4);

        cout << "CRC_48_3 = 0x" << crc_48_3 << endl;
        afficher_crc48_slice(crc_48_3);

        cout << "CRC_48_2 = 0x" << crc_48_2 << endl;
        afficher_crc48_slice(crc_48_2);

        cout << "CRC_48_1 = 0x" << crc_48_1 << endl;
        afficher_crc48_slice(crc_48_1);
}
