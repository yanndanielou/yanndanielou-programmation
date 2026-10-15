/*
 * main.cpp
 *
 * Outil de calcul de CRC (CRC16 GSM, CRC32 ISO-HDLC, CRC48 custom)
 * et de calcul des tags passerelle associés.
 */

#include <iostream>
#include <string>

#include <cstdint>
#include <cstddef>

#include "util.h" // PSC
#include "algos_crc.h" // CRC

using namespace std;

int main()
{
    cout << "Calcul de CRC !!" << endl << endl;

    const int data_ref_size = 9;
    uint8_t* data_ref = new uint8_t[data_ref_size];
    for (int i=0; i<data_ref_size; i++) data_ref[i]= 49+i;

    const int data_size = 5;
    uint8_t* data = new uint8_t[data_size];

    data[0]= 0x48; // H
    data[1]= 0x45; // E
    data[2]= 0x4C; // L
    data[3]= 0x4C; // L
    data[4]= 0x4F; // O


    const int ref_data_size = 13;
    uint8_t* ref_data = new uint8_t[ref_data_size];
    ref_data[0]= 0x0E; // Data Length (implicit) // si on retire les donnees implictes cela fait 13-5=8 et il faut ajouter les 6 octets de CRC.=> length = 14 = 0x0E
    ref_data[1]= 0x7F; // Receiver_address (implicit)
    ref_data[2]= 0x53; // Sender_address (implicit)
    ref_data[3]= 0x21; // DSAP (implicit)
    ref_data[4]= 0x21; // SSAP (implicit)
    ref_data[5]= 0x03; // Configuration data prefix X
    ref_data[6]= 0x00; // Configuration data prefix Y
    ref_data[7]= 0x00; // Configuration data prefix Z
    ref_data[8]= 0x8D; // Command
    ref_data[9] = 0xEF; // Sequence_Number (low word, low byte)
    ref_data[10]= 0xCD; // Sequence_Number (low word, high byte)
    ref_data[11]= 0xAB; // Sequence_Number (high word, low byte)
    ref_data[12]= 0x89; // Sequence_Number (high word, high byte)

    const int test_cas1_data_size = 8;
    uint8_t* test_cas1_data = new uint8_t[test_cas1_data_size];
    // sec_crc_psc_unisig_56_57_test : cas 1 : /  0 =>  241 /  1 =>  22 /  2 =>  33 /  3 =>  44 /  4 =>  55 /  5 =>  122 /  6 =>  133 /  7 =>  144
    test_cas1_data[0]= 241;
    test_cas1_data[1]= 22;
    test_cas1_data[2]= 33;
    test_cas1_data[3]= 44;
    test_cas1_data[4]= 55;
    test_cas1_data[5]= 122;
    test_cas1_data[6]= 133;
    test_cas1_data[7]= 144;

    const int test_cas2_data_size = 8;
    uint8_t* test_cas2_data = new uint8_t[test_cas2_data_size];
    // sec_crc_psc_unisig_56_57_test : Cas_3 : 8 octets avec valeurs 00
    for(int i=0;i<8; ++i) test_cas2_data[i]=0;

    const int test_cas3_data_size = 8;
    uint8_t* test_cas3_data = new uint8_t[test_cas3_data_size];
    // sec_crc_psc_unisig_56_57_test : Cas_3 : 8 octets avec valeurs FF
    for(int i=0;i<8; ++i) test_cas3_data[i]=255;

    const int test_cas4_data_size = 21;
    uint8_t* test_cas4_data = new uint8_t[test_cas4_data_size];
    // sec_crc_psc_unisig_56_57_test : Cas_4 : 21 octets avec valeurs quelconques
    //  0 =>  128 /  1 =>  132 /  2 =>  0 /  3 =>  0 /  4 =>  128 /  5 =>  5 /  6 =>  0 /  7 =>  0 /  8 =>  0 /  9 =>  0 /  10 =>  9 /  11 =>  8 /  12 =>  0 /  13 =>  0 /  14 =>  2 /  15 =>  85 /  16 =>  0 /  17 =>  0 /  18 =>  1 /  19 =>  103 /  20 =>  63
    test_cas4_data[0] =  128;
    test_cas4_data[1] =  132;
    test_cas4_data[2] =  0;
    test_cas4_data[3] =  0;
    test_cas4_data[4] =  128;
    test_cas4_data[5] =  5;
    test_cas4_data[6] =  0;
    test_cas4_data[7] =  0;
    test_cas4_data[8] =  0;
    test_cas4_data[9] =  0;
    test_cas4_data[10] =  9;
    test_cas4_data[11] =  8;
    test_cas4_data[12] =  0;
    test_cas4_data[13] =  0;
    test_cas4_data[14] =  2;
    test_cas4_data[15] =  85;
    test_cas4_data[16] =  0;
    test_cas4_data[17] =  0;
    test_cas4_data[18] =  1;
    test_cas4_data[19] =  103;
    test_cas4_data[20] =  63;

    const int test_data_size = 8;
    uint8_t* test_data = new uint8_t[test_data_size];
    // sec_crc_psc_unisig_56_57_test : cas 1 : /  0 =>  241 /  1 =>  22 /  2 =>  33 /  3 =>  44 /  4 =>  55 /  5 =>  122 /  6 =>  133 /  7 =>  144
    test_data[0]= 241;
    test_data[1]= 22;
    test_data[2]= 33;
    test_data[3]= 44;
    test_data[4]= 55;
    test_data[5]= 122;
    test_data[6]= 133;
    test_data[7]= 144;
    // for(int i=0;i<4; ++i) {uint8_t mem=test_data[i]; test_data[i]=test_data[7-i]; test_data[7-i]=mem;}

    const int test0_data_size = 45;
    uint8_t* test0_data = new uint8_t[test0_data_size];
    // CC1_cucp_a:0:7:09.174:Recv>   [00 41 12 01] 2E 7F 02 27 27 03 00 00 8D 9E 01 00 00 FF 1C 08 06 68 00 25 C5 20 0F 18 0F 18 00 18 00 00 00 00 00 00 00 00 00 00 00 00 00 A4 B8 04 00 [CRC + tag : 00 7C 7D CA 00 04 12 F1 00 20 D3 9F 00 05 20 A9]
    // CC1_cucp_a:0:7:11.709:Recv>    Data (hex) : 2E 7F 02 27 27 03 00 00 8D 9E 01 00 00 FF 1C 08 06 68 00 25 C5 20 0F 18 0F 18 00 18 00 00 00 00 00 00 00 00 00 00 00 00 00 A4 B8 04 00 [CRC + tag  + date]
    test0_data[0]= 0x2E; // Data Length (implicit) // 45 - 5 donnee implicites + 6 bits de CRC = 46 = 0x2E
    test0_data[1]= 0x7F; // Receiver_address (implicit)
    test0_data[2]= 0x02; // Sender_address (implicit)
    test0_data[3]= 0x27; // DSAP (implicit)
    test0_data[4]= 0x27; // SSAP (implicit)
    test0_data[5]= 0x03; // Configuration data prefix X
    test0_data[6]= 0x00; // Configuration data prefix Y
    test0_data[7]= 0x00; // Configuration data prefix Z
    test0_data[8]= 0x8D; // Commande MULTICAST (range 8Dh, A0h-BFh) 8Dh = "Application Data (multicast)" (see Subset 56)
    test0_data[9] = 0x9E; // Sequence_Number (low word, low byte)
    test0_data[10]= 0x01; // Sequence_Number (low word, high byte)
    test0_data[11]= 0x00; // Sequence_Number (high word, low byte)
    test0_data[12]= 0x00; // Sequence_Number (high word, high byte)
    // Net data : FF 1C 08 06 68 00 25 C5 20 0F 18 0F 18 00 18 ...
    test0_data[13]= 0xFF;
    test0_data[14]= 0x1C;
    test0_data[15]= 0x08;
    test0_data[16]= 0x06;
    test0_data[17]= 0x68;
    test0_data[18]= 0x00;
    test0_data[19]= 0x25;
    test0_data[20]= 0xC5;
    test0_data[21]= 0x20;
    test0_data[22]= 0x0F;
    test0_data[23]= 0x18;
    test0_data[24]= 0x0F;
    test0_data[25]= 0x18;
    test0_data[26]= 0x00;
    test0_data[27]= 0x18;
    // 00 00 00 00 00 00 00 00 00 00 00 00 00 A4 B8 04 00
    for (int i=1; i<=13; ++i) test0_data[27+i]= 0x00;
    test0_data[41]= 0xA4;
    test0_data[42]= 0xB8;
    test0_data[43]= 0x04;
    test0_data[44]= 0x00;


    const int test1_data_size = 24;
    uint8_t* test1_data = new uint8_t[test1_data_size];
    // Data (hex) : 19 7F 02 20 20 03 00 00 A1 78 03 00 00 03 00 00 78 03 00 00 36 B8 04 00 [ CRC apres tag hard : 00 7C 81 62 00 04 06 DF 00 A2 D9 51 00 A4 0A DC ]
                 // [ 00 2C 13 01 ] 19 7F 02 20 20 03 00 00 A1 78 03 00 00 03 00 00 78 03 00 00 36 B8 04 00 [ CRC ]
                 // Header :
                   // Msg_length = 0x002C = 44 = 4 octets de header + 24 octets de message (data impicite incluse) + 16 octets CRC
                   // Msg_Type = 0x13 = 19 =  Srv_Temporal_Tag_Types.C_SL4_STL_SyncAndReferenceTime
                   // No_Cabine = 0x01 = 1
    test1_data[0]= 0x19; // Data Length (implicit) // 24 - 5 donnee implicites + 6 bits de CRC = 25 = 0x19
    test1_data[1]= 0x7F; // Receiver_address (implicit)
    test1_data[2]= 0x02; // Sender_address (implicit)
    test1_data[3]= 0x20; // DSAP (implicit)
    test1_data[4]= 0x20; // SSAP (implicit)
    test1_data[5]= 0x03; // Configuration data prefix X
    test1_data[6]= 0x00; // Configuration data prefix Y
    test1_data[7]= 0x00; // Configuration data prefix Z
    test1_data[8] = 0xA1; // Commande MULTICAST (range 8Dh, A0h-BFh) A1h = Sync and Reference Time (see Subset 56)
    test1_data[9] = 0x78; // Sequence_Number (low word, low byte)
    test1_data[10]= 0x03; // Sequence_Number (low word, high byte)
    test1_data[11]= 0x00; // Sequence_Number (high word, low byte)
    test1_data[12]= 0x00; // Sequence_Number (high word, high byte)
    // Net Data : 03 00 00 78 03 00 00 36 B8 04 00 (Sync and Reference Time)
    test1_data[13]= 0x03; // Configuration data prefix X
	test1_data[14]= 0x00; // Configuration data prefix Y
    test1_data[15]= 0x00; // Configuration data prefix Z
    test1_data[16]= 0x78; // Reference Sync[n] ("The messages number. (unsigned integer).")
    test1_data[17]= 0x03; // Reference Sync[n] ("The messages number. (unsigned integer).")
    test1_data[18]= 0x00; // Reference Sync[n] ("The messages number. (unsigned integer).")
    test1_data[19]= 0x00; // Reference Sync[n] ("The messages number. (unsigned integer).")
    test1_data[20]= 0x36; // Reference Time[n-1]
    test1_data[21]= 0xB8; // Reference Time[n-1]
    test1_data[22]= 0x04; // Reference Time[n-1]
    test1_data[23]= 0x00; // Reference Time[n-1]

    const int test2_data_size = 24;
    uint8_t* test2_data = new uint8_t[test2_data_size];
    // Data (hex)  : 19 7F 02 27 27 03 00 00 8D 9F 01 00 00 FF 07 09 01 28 F1 50 D0 B9 04 00 [CRC]
    // [00 2C 12 01] 19 7F 02 27 27 03 00 00 8D 9F 01 00 00 FF 07 09 01 28 F1 50 D0 B9 04 00 [CRC : 00 7C 7D 6D 00 04 16 12 00 20 DB 44 00 05 1F F0]
                 // Header :
                   // Msg_length = 0x002C = 44 = 4 octets de header + 24 octets de message (data impicite incluse) + 16 octets CRC
                   // Msg_Type = 0x12 = 18 =  Srv_Temporal_Tag_Types.C_SL4_ApplicationDataModeMulticast
                   // No_Cabine = 0x01 = 1
    test2_data[0]= 0x19; // Data Length (implicit) // 24 - 5 donnee implicites + 6 bits de CRC = 25 = 0x19
    test2_data[1]= 0x7F; // Receiver_address (implicit)
    test2_data[2]= 0x02; // Sender_address (implicit)
    test2_data[3]= 0x27; // DSAP (implicit)
    test2_data[4]= 0x27; // SSAP (implicit)
    test2_data[5]= 0x03; // Configuration data prefix X
    test2_data[6]= 0x00; // Configuration data prefix Y
    test2_data[7]= 0x00; // Configuration data prefix Z
    test2_data[8] = 0x8D; // Commande MULTICAST (range 8Dh, A0h-BFh) 8Dh = "Application Data (multicast)" (see Subset 56)
    test2_data[9] = 0x9F; // Sequence_Number (low word, low byte)
    test2_data[10]= 0x01; // Sequence_Number (low word, high byte)
    test2_data[11]= 0x00; // Sequence_Number (high word, low byte)
    test2_data[12]= 0x00; // Sequence_Number (high word, high byte)
    // Net Data : FF 07 09 01 28 F1 50 D0 B9 04 00
    test2_data[13]= 0xFF;
	test2_data[14]= 0x07;
    test2_data[15]= 0x09;
    test2_data[16]= 0x01;
    test2_data[17]= 0x28;
    test2_data[18]= 0xF1;
    test2_data[19]= 0x50;
    test2_data[20]= 0xD0;
    test2_data[21]= 0xB9;
    test2_data[22]= 0x04;
    test2_data[23]= 0x00;

    const int test3_data_size = 45;
	uint8_t* test3_data = new uint8_t[test3_data_size];
    // Data (hex)  : 2E 7F 02 27 27 03 00 00 8D A0 01 00 00 FF 1C 08 06 68 00 25 CE 80 0F D8 0F D8 00 18 00 00 00 00 00 00 00 00 00 00 00 00 00 D1 B9 04 00 [CRC+tag : 00 7C 8A 31 00 04 10 20 00 80 F0 27 00 9F 15 EC]
	// [00 41 12 01] 2E 7F 02 27 27 03 00 00 8D A0 01 00 00 FF 1C 08 06 68 00 25 CE 80 0F D8 0F D8 00 18 00 00 00 00 00 00 00 00 00 00 00 00 00 D1 B9 04 00   [CRC+tag+date : 00 7C 8A 31 00 04 10 20 00 20 DA 2E 00 05 1D 85]
                 // Header :
                   // Msg_length = 0x0041 = 65 = 4 octets de header + 45 octets de message (data impicite incluse) + 16 octets CRC
                   // Msg_Type = 0x12 = 18 =  Srv_Temporal_Tag_Types.C_SL4_ApplicationDataModeMulticast
                   // No_Cabine = 0x01 = 1
    test3_data[0]= 0x2E; // Data Length (implicit) // 45 - 5 donnee implicites + 6 bits de CRC = 46 = 0x2E
    test3_data[1]= 0x7F; // Receiver_address (implicit)
    test3_data[2]= 0x02; // Sender_address (implicit)
    test3_data[3]= 0x27; // DSAP (implicit)
    test3_data[4]= 0x27; // SSAP (implicit)
    test3_data[5]= 0x03; // Configuration data prefix X
    test3_data[6]= 0x00; // Configuration data prefix Y
    test3_data[7]= 0x00; // Configuration data prefix Z
    test3_data[8] = 0x8D; // Commande MULTICAST (range 8Dh, A0h-BFh) 8Dh = "Application Data (multicast)" (see Subset 56)
    test3_data[9] = 0xA0; // Sequence_Number (low word, low byte)
    test3_data[10]= 0x01; // Sequence_Number (low word, high byte)
    test3_data[11]= 0x00; // Sequence_Number (high word, low byte)
    test3_data[12]= 0x00; // Sequence_Number (high word, high byte)
    // Net Data : FF 1C 08 06 68 00 25 CE 80 0F D8 0F D8 00 18 00 00 00 00 00 00 00 00 00 00 00 00 00 D1 B9 04 00
    test3_data[13]= 0xFF;
	test3_data[14]= 0x1C;
	test3_data[15]= 0x08;
    test3_data[16]= 0x06;
    test3_data[17]= 0x68;
    test3_data[18]= 0x00;
    test3_data[19]= 0x25;
    test3_data[20]= 0xCE;
    test3_data[21]= 0x80;
    test3_data[22]= 0x0F;
    test3_data[23]= 0xD8;
    test3_data[24]= 0x0F;
	test3_data[25]= 0xD8;
	test3_data[26]= 0x00;
	test3_data[27]= 0x18;
	for (int i=28; i<=40; i++) test3_data[i]= 0x00;
	test3_data[41]= 0xD1;
	test3_data[42]= 0xB9;
	test3_data[43]= 0x04;
	test3_data[44]= 0x00;

    const int test_hc_data_size = 45;
	uint8_t* test_hc_data = new uint8_t[test_hc_data_size];
    //  Data (hex) : 2E 7F 02 27 27 03 00 00 8D A2 01 00 00 FF 1C 08 06 68 00 25 E1 40 11 58 11 58 00 18 00 00 00 00 00 00 00 00 00 00 00 00 00 28 BC 04 00 [CRC+tag+date : 00 7C 80 B3 00 04 07 DF 01 01 6A F0 00 8B 55 32]
	// [00 41 12 01] 2E 7F 02 27 27 03 00 00 8D A2 01 00 00 FF 1C 08 06 68 00 25 E1 40 11 58 11 58 00 18 00 00 00 00 00 00 00 00 00 00 00 00 00 28 BC 04 00      [CRC+tag : 00 7C 80 B3 00 04 07 DF 00 20 DB C0 00 05 27 42]
                 // Header :
                   // Msg_length = 0x0041 = 65 = 4 octets de header + 45 octets de message (data impicite incluse) + 16 octets CRC
                   // Msg_Type = 0x12 = 18 =  Srv_Temporal_Tag_Types.C_SL4_ApplicationDataModeMulticast
                   // No_Cabine = 0x01 = 1

// CC1_cucp_a:0:7:14.209:Recv>Message before Timestamping (after transcode) :           9
// CC1_cucp_a:0:7:14.209:Recv>   Status transcodage : True
// CC1_cucp_a:0:7:14.279:Recv>   Value :        16#2#       16#0#      16#30#       16#0#       16#4#      16#BC#      16#28#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#      16#18#       16#0#      16#58#      16#11#      16#58#      16#11#      16#40#      16#E1#      16#25#       16#0#      16#68#       16#6#       16#8#      16#1C#      16#FF#       16#0#       16#0#       16#1#      16#A2#      16#8D#       16#0#       16#0#       16#3#      16#27#      16#27#       16#2#      16#7F#      16#2E#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#
// CC1_cucp_a:0:7:14.281:Recv>   Redond (hex) : ( 16#194C5E1# ,  16#D5097F# )
// CC1_cucp_a:0:7:14.282:Recv>   Redond after timestamping (hex) : ( 16#24FC04#, 16#61D82B#)
// CC1_cucp_a:0:7:14.283:Recv>    | Heure_Reception | Num_Sous_Cycle | Previous_Local_Time_Val | Previous_Local_Time_Redond |
// CC1_cucp_a:0:7:14.284:Recv>    | --------------  | -------------- | ----------------------- | -------------------------- |
// CC1_cucp_a:0:7:14.285:Recv>    |      107044   |            44   |      107000 | (  -27861557,  -58339726) |
// CC1_cucp_a:0:7:14.286:Recv>    Heure CKD timestamping :     1089666

// CC1_cucp_a:0:7:17.200:Recv>   MESSAGE HORS-CODE !!!!! *************
// CC1_cucp_a:0:7:17.200:Recv>   Value :        16#2#   16#1A224#      16#30#       16#0#       16#4#      16#BC#      16#28#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#      16#18#       16#0#      16#58#      16#11#      16#58#      16#11#      16#40#      16#E1#      16#25#       16#0#      16#68#       16#6#       16#8#      16#1C#      16#FF#       16#0#       16#0#       16#1#      16#A2#      16#8D#       16#0#       16#0#       16#3#      16#27#      16#27#       16#2#      16#7F#      16#2E#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#       16#0#
// CC1_cucp_a:0:7:17.201:Recv>   Redond (hex) : (  16#24FC04# ,  16#61D82B# )

    test_hc_data[0] = 0x2E; // Data Length (implicit) // 45 - 5 donnee implicites + 6 bits de CRC = 46 = 0x2E
    test_hc_data[1] = 0x7F; // Receiver_address (implicit)
    test_hc_data[2] = 0x02; // Sender_address (implicit)
    test_hc_data[3] = 0x27; // DSAP (implicit)
    test_hc_data[4] = 0x27; // SSAP (implicit)
    test_hc_data[5] = 0x03; // Configuration data prefix X
    test_hc_data[6] = 0x00; // Configuration data prefix Y
    test_hc_data[7] = 0x00; // Configuration data prefix Z
    test_hc_data[8] = 0x8D; // Commande MULTICAST (range 8Dh, A0h-BFh) 8Dh = "Application Data (multicast)" (see Subset 56)
    test_hc_data[9] = 0xA2; // Sequence_Number (low word, low byte)
    test_hc_data[10]= 0x01; // Sequence_Number (low word, high byte)
    test_hc_data[11]= 0x00; // Sequence_Number (high word, low byte)
    test_hc_data[12]= 0x00; // Sequence_Number (high word, high byte)
    // Net Data : FF 1C 08 06 68 00 25 E1 40 11 58 11 58 00 18 00 00 00 00 00 00 00 00 00 00 00 00 00 28 BC 04 00
    test_hc_data[13]= 0xFF;
	test_hc_data[14]= 0x1C;
	test_hc_data[15]= 0x08;
    test_hc_data[16]= 0x06;
    test_hc_data[17]= 0x68;
    test_hc_data[18]= 0x00;
    test_hc_data[19]= 0x25;
    test_hc_data[20]= 0xE1;
    test_hc_data[21]= 0x40;
    test_hc_data[22]= 0x11;
    test_hc_data[23]= 0x58;
    test_hc_data[24]= 0x11;
	test_hc_data[25]= 0x58;
	test_hc_data[26]= 0x00;
	test_hc_data[27]= 0x18;
	for (int i=28; i<=40; i++) test_hc_data[i]= 0x00;
	test_hc_data[41]= 0x28;
	test_hc_data[42]= 0xBC;
	test_hc_data[43]= 0x04;
	test_hc_data[44]= 0x00;

    const int test4_data_size = 10;
    uint8_t* test4_data = new uint8_t[test4_data_size];
    // Data (hex) : 08 63 02 25 25 00 00 3D 3D 86 (+ CRC : 00 7C 86 3C / 00 04 11 5E / 00 C6 01 90 / 01 01 82 CC)
    test4_data[0]= 0x08; // Data Length (implicit) // 10 - 8 donnee implicites + 6 bits de CRC = 8 = 0x08
    test4_data[1]= 0x63; // Receiver_address (implicit)
    test4_data[2]= 0x02; // Sender_address (implicit)
    test4_data[3]= 0x25; // DSAP (implicit)
    test4_data[4]= 0x25; // SSAP (implicit)
    test4_data[5]= 0x00; // ?? "Higher_order_24bits_of_sequence_number (low word, high byte)" (implicit)
    test4_data[6]= 0x00; // ?? "Higher_order_24bits_of_sequence_number (high word, low byte)" (implicit)
    test4_data[7]= 0x3D; // ?? "Higher_order_24bits_of_sequence_number (high word, high byte)" (implicit)
    test4_data[8]= 0x3D; // lowest order byte sequence number
    test4_data[9]= 0x86; // Command (idle message) 86h [SIL4] or 06h [SIL2] or C6h [SIL0]

    const int test_idle_data_size = 10;
    uint8_t* test_idle_data = new uint8_t[test_idle_data_size];
    // [99:33 <= 10:33] Received PROFIBUS message [num:657][mode:SDA][len:8] 84 86 d7 bc 6b 68 c5 30
    // [CbtcMessage][bim:33210][ack:41][id:13][from:110][to:5] 00 1e 1c 01 08 63 0a 21 21 00 00 00 84 86 00 7c 7d ef 00 04 0b c2 00 20 d2 d2 00 05 24 6f
    test_idle_data[0]= 0x08; // Data Length (implicit) // 10 - 8 donnee implicites + 6 bits de CRC = 8 = 0x08
    test_idle_data[1]= 0x63; // 99 Receiver_address / Receive_address (implicit)  
    test_idle_data[2]= 0x0A; // 10 Sender_address / Send_address (implicit)
    test_idle_data[3]= 0x21; // DSAP / Partner_SAP (implicit)
    test_idle_data[4]= 0x21; // SSAP / Local_SAP (implicit)
    // Connect Confirm Telegram (pour avoir le num�ro de sequence initial)
    // [99:33 <= 10:33] Received PROFIBUS message [num:499][mode:SDA][len:29] 7a 82 7a 05 06 f2 88 13 03 00 00 00 03 00 00 e8 03 00 00 0a 00 00 00 ac e1 db cd 53 b7
    // [=> 239.192.112.101:61440] Sending CBTC message  [num:263][rt:624][CbtcMessage][bim:33030][ack:5][id:13][from:110][to:5] 00 33 18 01 1d 63 0a 21 21 00 00 00 7a 82 7a 05 06 f2 88 13 03 00 00 00 03 00 00 e8 03 00 00 0a 00 00 00 00 7c 86 58 00 04 0a 27 00 20 d9 d5 00 05 19 44
    test_idle_data[5]= 0x05; // "Higher_order_24bits_of_sequence_number (low word, high byte) / Authentication number (low word, low byte)" (implicit)
    test_idle_data[6]= 0x06; // "Higher_order_24bits_of_sequence_number (high word, low byte) / Authentication number (low word, high byte)" (implicit)
    test_idle_data[7]= 0xF2; // "Higher_order_24bits_of_sequence_number (high word, high byte) / Authentication number (high word, low byte) " (implicit)
    test_idle_data[8]= 0x84; // lowest order byte sequence number
    test_idle_data[9]= 0x86; // Command (idle message) 86h [SIL4] or 06h [SIL2] or C6h [SIL0]

    uint64_t test_idle_data_crc48_computed= crc48_custom(
        test_idle_data, // reinterpret_cast<const uint8_t*>(msg_ref.data()),
        test_idle_data_size, //msg_ref.size()
        false // debug
    );

    cout << "CRC48(test_idle_data) = 0x" << std::hex << std::uppercase << test_idle_data_crc48_computed << std::endl;
    cout << "CRC48 attendu = d7 bc 6b 68 c5 30" << endl;


    const int test_sn_data_size = 10;
    uint8_t* test_sn_data = new uint8_t[test_sn_data_size];
    // [99:33 <= 10:33] Received PROFIBUS message [num:657][mode:SDA][len:8] sn 86 [CRC]
    test_sn_data[0]= 0x00; // Data Length (implicit) // 10 - 8 donnee implicites + 6 bits de CRC = 8 = 0x08
    test_sn_data[1]= 0x00; // ?? Receiver_address (implicit)
    test_sn_data[2]= 0x00; // ?? Sender_address (implicit)
    test_sn_data[3]= 0x00; // DSAP (implicit)
    test_sn_data[4]= 0x00; // SSAP (implicit)
    test_sn_data[5]= 0x00; // "Higher_order_24bits_of_sequence_number (low word, high byte)" (implicit)
    test_sn_data[6]= 0x00; // "Higher_order_24bits_of_sequence_number (high word, low byte)" (implicit)
    test_sn_data[7]= 0x00; // "Higher_order_24bits_of_sequence_number (high word, high byte)" (implicit)
    test_sn_data[8]= 0x00; // lowest order byte sequence number
    test_sn_data[9]= 0x00; // Command (idle message) 86h [SIL4] or 06h [SIL2] or C6h [SIL0]

    string msg = "HELLO";
    string msg_ref = "123456789";

    uint16_t crc16_ref = crc16_gsm(
        data_ref, // reinterpret_cast<const uint8_t*>(msg_ref.data()),
        data_ref_size//msg_ref.size()
    );

    uint16_t crc16 = crc16_gsm(
        data, //reinterpret_cast<const uint8_t*>(msg.data()),
        data_size // msg.size()
    );

     uint32_t crc32_ref = crc32_iso_hdlc(
        reinterpret_cast<const uint8_t*>(msg_ref.data()),
        msg_ref.size()
    );

    uint32_t crc32 = crc32_iso_hdlc(
        reinterpret_cast<const uint8_t*>(msg.data()),
        msg.size()
    );


    uint64_t crc48_ref = crc48_custom(
        ref_data, // reinterpret_cast<const uint8_t*>(msg_ref.data()),
        ref_data_size, //msg_ref.size()
        false // debug
    );

    uint64_t crc48 = crc48_custom(
        test_hc_data,
        test_hc_data_size,
        false // debug
    );



    cout << "CRC16_ref = " << std::hex << "0x" << std::uppercase << crc16_ref << std::endl;
    cout << "CRC16(" << msg << ") = 0x" << std::hex << std::uppercase << crc16 << std::endl;

    cout << endl;

    cout << "CRC32_ref = " << std::hex << "0x" << std::uppercase << crc32_ref << std::endl;
    cout << "CRC32(" << msg << ") = 0x" << std::hex << std::uppercase << crc32 << std::endl;

    cout << endl;



    cout << "CRC48(exemple SIL4 du Sub57) = 0x" << std::hex << std::uppercase << crc48_ref << std::endl;
    cout << "CRC48 attendu = 97h E4h C3h C1h 6Ah 30h" << endl;

    cout << endl;

    cout << "Calculs de tag id_passerelle" << endl;
    cout << "----------------------------" << endl << endl;

    uint32_t ident[4];

    for (int id=1; id<2; id++)
    {
        cout << "id_passerelle = " << std::dec << id << endl;
        ident[0]= calculer_tag_passerelle_1(id);
        ident[1]= calculer_tag_passerelle_2(id);
        ident[2]= calculer_tag_passerelle_3(id);
        ident[3]= calculer_tag_passerelle_4(id);


        for (int i=0; i<4; ++i){
            cout << "tag_" << (i+1) << " = " << ident[i] << endl;
        }

        cout << endl;
        cout << endl;

        //cout << "Calcul de CRC sans tag :" << endl;
        //afficher_crc48(crc48, 0, 0, 0, 0);

        cout << endl;
        cout << "Calcul de CRC avec tag id_passerelle :" << endl;
        afficher_crc48(crc48, ident[0], ident[1], ident[2], ident[3]);
        cout << endl << endl;
    }

    return 0;
}
