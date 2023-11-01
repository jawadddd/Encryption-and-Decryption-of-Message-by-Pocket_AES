# Encryption-and-Decryption-of-Message-by-Pocket_AES
It includes code to encrypt message into cipher and again decrypt that cipher to original text using pocket AES.  
It also includes encrypting arbitrary data received from text files and also decrypting it.  
  
AES is currently the most widely used symmetric encryption algorithm. Here I have worked on a simplified and shortened version of it, let us call it PocketAES.  
  
  To start with, PocketAES takes in two inputs of 16 bits each — a plaintext and an encryption
key. It encrypts the plaintext to produce a block of ciphertext, again 16 bits in size.
A plaintext block is subdivided into four nibbles (4 bits), which are then arranged in a matrix
form. Note: the column-first ordering of nibbles!  

  
  ### Encryption 
  To perform encryption, a data block goes through several phases. Each individual stage works on a matrix of four nibbles.  
  
SubNibbles  
Add Round Key
MixColumns
ShiftRow
GenerateRoundKeys

### Decryption  
The workflow for decryption in PocketAES is to back-track the encryption process .
ShifRow is a self-inverse function, because rotating another time by 4 bits restores the
original row.  
XOR addition is also its own inverse, i.e. upon re-adding the same key you get back the
original block.  
For inverting SubNibbles, use the Table 1 but apply the opposite substitutions.  
For inverting MixColumns, multiply each column with the inverse of the given constant
matrix.  
According to GF(24) rules, the inverse of:  
1 4  
4 1   
works out to be  
9 2  
2 9  

## Encrypting arbitrary data received from text files  
The above algorithm only works on one block of 16 bits. To encrypt arbitrary data, I took
 some additional steps.  
Each character in the text file is converted to 8 bits binary (as per ASCII encoding), and the
whole bit stream is then divided into blocks of 16 bits – so every two characters become block.
If there are odd number of characters in text file, the last block will only be 8 bit long. In such a
case, data is padded with the null byte (00 hex) to make it a full block.During encryption, I am encrypting each block separately using the same key.


  Similarly, decryption is performed separately on each block. After decryption, the very last
byte of null padding, if any, is removed.
