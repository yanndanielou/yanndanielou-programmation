# MD2 algorithm implementation 
# execution of my article available here
# https://medium.com/@nickthecrypt/cryptography-hash-method-md2-message-digest-2-step-by-step-explanation-made-easy-with-python-10faa2e35e85
# 
# no intent to be efficient nor fancy, only for learning & 'dissection' purpose
# 
# nick@thecrypt.io
import math
import time
# <========================== interface support
class bcolors:
    HEADER = '\033[95m'
    OKBLUE = '\033[94m'
    OKCYAN = '\033[96m'
    OKGREEN = '\033[92m'
    WARNING = '\033[93m'
    FAIL = '\033[91m'
    ENDC = '\033[0m'
    BOLD = '\033[1m'
    UNDERLINE = '\033[4m'

def print_array_by_block(array, bsize, hl_start=None, hl_end=None, hl_col=None):
    print('[ '+bcolors.OKBLUE+'block number'+bcolors.ENDC+' / '+bcolors.OKCYAN+'character pos in block'+bcolors.ENDC+' ]')
    print(bcolors.OKCYAN+'\t0\t1\t2\t3\t4\t5\t6\t7\t8\t9\t10\t11\t12\t13\t14\t15'+bcolors.ENDC)
    for i in range(math.ceil(len(array) / bsize)):
        line_abb = bcolors.OKBLUE+str(i)+'\t'+bcolors.ENDC
        for j in range(bsize):
            if (hl_start is not None) & (hl_col is not None) & ((i*bsize+j) == hl_start):
                line_abb += hl_col
            line_abb += str(array[i*bsize+j])+'\t'
            if (hl_end is not None) & (hl_col is not None) & ((i*bsize+j) == hl_end):
                line_abb += bcolors.ENDC
        print(line_abb)

def print_checksum_generation(block,charpos,array):
    line_csgen = bcolors.OKBLUE+str(block)+bcolors.ENDC+':'+bcolors.OKCYAN+str(charpos).zfill(2)+bcolors.ENDC+'\t'+bcolors.OKGREEN
    for i in range(len(array)):
        line_csgen += str(array[i]).zfill(3)+'\t'
    print('\r'+line_csgen+bcolors.ENDC, end='')

def print_hash_raw(array, bsize):
    for i in range(math.ceil(len(array) / 16)):
        print(''.join(map(lambda x: str(x)+'\t',array[i:i+BLOCK_SIZE])))

# <========================== interface support

print(bcolors.WARNING+'MD2 algorithm python step by step for learning purpose =-'+bcolors.ENDC)

# <========================== algorithm constants
BLOCK_SIZE = 16
# substitution table algorithm-specific: decimals of PI 
S = [
41, 46, 67, 201, 162, 216, 124, 1, 61, 54, 84, 161, 236, 240, 6, 19,
98, 167, 5, 243, 192, 199, 115, 140, 152, 147, 43, 217, 188, 76, 130, 202,
30, 155, 87, 60, 253, 212, 224, 22, 103, 66, 111, 24, 138, 23, 229, 18,
190, 78, 196, 214, 218, 158, 222, 73, 160, 251, 245, 142, 187, 47, 238, 122,
169, 104, 121, 145, 21, 178, 7, 63, 148, 194, 16, 137, 11, 34, 95, 33,
128, 127, 93, 154, 90, 144, 50, 39, 53, 62, 204, 231, 191, 247, 151, 3,
255, 25, 48, 179, 72, 165, 181, 209, 215, 94, 146, 42, 172, 86, 170, 198,
79, 184, 56, 210, 150, 164, 125, 182, 118, 252, 107, 226, 156, 116, 4, 241,
69, 157, 112, 89, 100, 113, 135, 32, 134, 91, 207, 101, 230, 45, 168, 2,
27, 96, 37, 173, 174, 176, 185, 246, 28, 70, 97, 105, 52, 64, 126, 15,
85, 71, 163, 35, 221, 81, 175, 58, 195, 92, 249, 206, 186, 197, 234, 38,
44, 83, 13, 110, 133, 40, 132, 9, 211, 223, 205, 244, 65, 129, 77, 82,
106, 220, 55, 200, 108, 193, 171, 250, 36, 225, 123, 8, 12, 189, 177, 74,
120, 136, 149, 139, 227, 99, 232, 109, 233, 203, 213, 254, 59, 0, 29, 57,
242, 239, 183, 14, 102, 88, 208, 228, 166, 119, 114, 248, 235, 117, 75, 10,
49, 68, 80, 180, 143, 237, 31, 26, 219, 153, 141, 51, 159, 17, 131, 20]
# <========================== algorithm constants

# define the message
input = ''
print('---\r\n'+input+'\r\n---')


print('input: '+str(len(input)))

# translate the text message into an array of msg's character ascii codes
input = [ord(c) for c in input]


print("Input as an array of character ascii codes:")
print(input)

# =========================== STEP 1 - padding
# add padding to the message to ensure we have complete block (full BLOCK_SIZE)
# we fill the last block with the padding pattern: value of the gap to fill in the last block
padding_pat = BLOCK_SIZE - (len(input) % BLOCK_SIZE)
padding = padding_pat * [padding_pat]
paddedInput = input + padding


print("paddedInput")
print(paddedInput)

blocks_number = math.ceil(len(paddedInput) / BLOCK_SIZE)
print('paddedInput length: '+str(len(paddedInput)))
print('number of 16 bytes blocks: '+str(blocks_number))
print('padding pattern: '+str(padding_pat))
print('---')
print_array_by_block(paddedInput, BLOCK_SIZE,len(paddedInput)-padding_pat, len(paddedInput)-1, bcolors.WARNING)
print("paddedInput")
print(paddedInput)

# =========================== STEP 2 - calculate and happen checksum
# initialize checksum at 0 and last calculated checksum at 0 as well
# crawl through the encoded message 
print('---')
print(bcolors.HEADER+'checksum calculation and concat with message:'+bcolors.ENDC)
checksum = 16 * [0]
print("Checksum initialization:")
print(checksum)

l = 0

for block in range(blocks_number):
    for j in range(BLOCK_SIZE):
        l = S[(paddedInput[block*BLOCK_SIZE+j] ^ l)] ^ checksum[j]
        checksum[j] = l
        #print_checksum_generation(i,j,checksum)
        #time.sleep(0.1)

print("Checksum calc 1:")
print(checksum)

print("add the checksum to the processed message")
paddedInput = paddedInput + checksum

print("paddedInput:")
print(paddedInput)

blocks_number += 1

print_array_by_block(paddedInput, BLOCK_SIZE,len(paddedInput)-BLOCK_SIZE, len(paddedInput)-1, bcolors.WARNING)

# =========================== STEP 3 - initialize digest
# initialize the digest with zeros in 3 segments of 16 bytes each
md_digest = 48 * [0]

# =========================== STEP 4 - process the message digest
# for each block - it will be processed as below and in this order:
# md_digest (segment 1: [16...31]): CURRENT BLOCK
# md_digest (segment 2: [32...48]): xor(segment1, segment0)
# 18 passes on the md_digest as follow:
# initialize a temporary checksum at 0
# for each char of the digest, compute with the substitution table
print('---')
print(bcolors.HEADER+'msg processing by block ==> raw hash:'+bcolors.ENDC)
for block in range(blocks_number):
    print("block:" + str(block))
    
    print('paddedInput length: '+str(len(paddedInput)))
    print('md_digest length: '+str(len(md_digest)))
    for j in range(BLOCK_SIZE):
        md_digest[BLOCK_SIZE+j] = paddedInput[block*BLOCK_SIZE+j]
        md_digest[2*BLOCK_SIZE+j] = (md_digest[BLOCK_SIZE+j] ^ md_digest[j])

    print("md_digest:")
    print(md_digest)
    
    checktmp = 0
    for j in range(18):
        for k in range(48):
            print("checktmp:" + str(checktmp))
            print("md_digest[k]:" + str(md_digest[k]))
            print("S[checktmp]:" + str(S[checktmp]))
            checktmp = md_digest[k] ^ S[checktmp]
            md_digest[k] = checktmp
            print("Block:" + str(block)+ ", j:" + str(j) + ", k:" + str(k) + ", md_digest[" + str(k) + "] = " + str(md_digest[k]))
        checktmp = (checktmp + j) % 256
        print("j:" + str(j) + ", checktmp:" + str(checktmp))

print_hash_raw(md_digest,BLOCK_SIZE)

# =========================== STEP 5 - rendering
# simple rendering in hexadecimal of the raw hash first segment [0:16]
print('---')
print(bcolors.HEADER+'final hash:'+bcolors.ENDC)
print("".join(map(lambda x: hex(x).lstrip("0x"), md_digest[0:16])))
