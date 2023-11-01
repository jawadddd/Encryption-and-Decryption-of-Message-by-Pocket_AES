import java.util.Scanner;

public class q1{
    public static int[][] constantMatrix = {{1, 4}, {4, 1}};
    private static final String RCON1 = "1110";
    private static final String RCON2 = "1010";
public static String inputBlock2[][]=new String[2][2];

    public static String[][] matrixMultiply(String[][] inputBlock) {
        String[][] outputBlock = new String[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                String sum = "0";
                for (int k = 0; k < 2; k++) {
                    sum = finiteFieldAddition(sum, finiteFieldMultiplication(constantMatrix[i][k], inputBlock[k][j]));
                }
                outputBlock[i][j] = sum;
            }
        }
        return outputBlock;
    }

    public static String finiteFieldMultiplication(int a, String b) {
        int aValue = a;
        int bValue = Integer.parseInt(b, 16);
        int result = 0;
        while (bValue > 0) {
            if ((bValue & 1) == 1) {
                result = result ^ aValue;
            }
            aValue = aValue << 1;
            if ((aValue & 0b10000) != 0) {
                aValue = aValue ^ 0b10011;
            }
            bValue = bValue >> 1;
        }
        return Integer.toHexString(result).toUpperCase();
    }

    public static String finiteFieldAddition(String a, String b) {
        int aValue = Integer.parseInt(a, 16);
        int bValue = Integer.parseInt(b, 16);
        int result = aValue ^ bValue;
        return Integer.toHexString(result).toUpperCase();
    }
    public static void main(String[] args) {
        String[][] inputBlock = new String[2][2];
 String[][] originalBinaryMatrix = new String[2][2];
          String[][] modifiedBinaryMatrix = new String[2][2];
           String subNibbles = "";
                   String shiftRow="";


        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a hexadecimal string (up to 4 characters): ");
        String hexInput = scanner.nextLine();

        // Ensure the input is a valid hexadecimal string
        if (isValidHexadecimal(hexInput) && hexInput.length() <= 4) {
            // Pad with leading zeros if the length is less than 4
            while (hexInput.length() < 4) {
                hexInput = "0" + hexInput;
            }

            // Create the inputBlock matrix
            inputBlock[0][0] = String.valueOf(hexInput.charAt(0));
            inputBlock[1][0] = String.valueOf(hexInput.charAt(1));

            inputBlock[0][1] = String.valueOf(hexInput.charAt(2));

            inputBlock[1][1] = String.valueOf(hexInput.charAt(3));
            

            System.out.println("Processed hexadecimal string: " + hexInput);

            // Create a 2D matrix to store binary values
           
            // Convert each character of the processed hexadecimal string to binary
            for (int i = 0; i < hexInput.length(); i++) {
                char hexChar = hexInput.charAt(i);
                int index = Integer.parseInt(String.valueOf(hexChar), 16);

                // Ensure each binary value is of size 4
                String binaryValue = Integer.toBinaryString(index);
                while (binaryValue.length() < 4) {
                    binaryValue = "0" + binaryValue;
                }

                originalBinaryMatrix[i % 2][i / 2] = binaryValue;
                modifiedBinaryMatrix[i % 2][i / 2] = binaryValue;
            }

            // Display the original binary matrix
            System.out.println("Original Binary Matrix:");
            printMatrix(originalBinaryMatrix);

            // Perform substitution in the modified matrix
            substituteMatrixValues(modifiedBinaryMatrix);

            // Print the modified matrix
            System.out.println("\nModified Binary Matrix:");
            printMatrix(modifiedBinaryMatrix);

            // Swap elements of the first row in the modified matrix
            swapFirstRowElements(originalBinaryMatrix);

            // Print the matrix after swapping
            System.out.println("\nBinary Matrix After Swapping:");
            printMatrix(modifiedBinaryMatrix);

            // Convert binary values back to hexadecimal and print the resulting matrix
           
           
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    String binaryValue = modifiedBinaryMatrix[j][i];
                    int decimalValue = Integer.parseInt(binaryValue, 2);
                    String hexValue = Integer.toHexString(decimalValue).toLowerCase();
                    subNibbles+=hexValue;
                    
                }
            }
        } else {
            System.out.println("Invalid input. Please enter a valid hexadecimal string (up to 4 characters).");
        }
        
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    String binaryValue = originalBinaryMatrix[j][i];
                    int decimalValue = Integer.parseInt(binaryValue, 2);
                    String hexValue = Integer.toHexString(decimalValue).toLowerCase();
shiftRow+=hexValue;
                   
                }
            }
 System.out.println("\nSub Nibbles:"+subNibbles);
        System.out.println("\nShift Row:"+shiftRow);

        System.out.println("Mix Column: " +inputBlock[0][0]+inputBlock[1][0]+inputBlock[0][1]+inputBlock[1][1]);
        String[][] outputBlock = matrixMultiply(inputBlock);
        System.out.println("Output Block:");
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.print(outputBlock[i][j] + " ");
            }
            System.out.println();
        }







        System.out.print("Enter the 4-indexed master key: ");
        String masterKey = scanner.nextLine();
        scanner.close();

        while (masterKey.length() < 4) {
            masterKey = "0" + masterKey;
        }

        String[] roundKeys = generateRoundKeys(masterKey);

        System.out.println("Round Keys:");
        System.out.println("Round Key1:"+roundKeys[0]);
        System.out.println("Round Key2:"+roundKeys[1]);

    
        scanner.close();



    }

    // Check if the input is a valid hexadecimal string
    private static boolean isValidHexadecimal(String input) {
        String hexPattern = "^[0-9A-Fa-f]*$";
        return input.matches(hexPattern);
    }

    // Helper method to print a 2D matrix
    private static void printMatrix(String[][] matrix) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Helper method to perform substitution in the matrix
    private static void substituteMatrixValues(String[][] matrix) {
        String[] substitutionTable = {
            "", "0000", "1001", "1110",
            "0110", "0011", "1111", "0101",
            "0001", "1101", "1100", "0111",
            "1011", "0100", "0010", "1000"
        };
int xx=0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                xx=0;
             //   String binaryValue = matrix[i][j];
             //   int index = Integer.parseInt(binaryValue, 2);
             if(matrix[i][j].equals("0000")&&xx!=1)
             {
                matrix[i][j]="1010";
                xx=1;
             }
             if(matrix[i][j].equals("0001")&&xx!=1)
             {
                matrix[i][j]="0000";
                xx=1;
             }
              if(matrix[i][j].equals("0010")&&xx!=1)
             {
                matrix[i][j]="1001";
                xx=1;
             }
             if(matrix[i][j].equals("0011")&&xx!=1)
             {
                matrix[i][j]="1110";
                xx=1;
             }
             if(matrix[i][j].equals("0100")&&xx!=1)
             {
                matrix[i][j]="0110";
                xx=1;
             }
             if(matrix[i][j].equals("0101")&&xx!=1)
             {
                matrix[i][j]="0011";
                xx=1;
             }
             if(matrix[i][j].equals("0110")&&xx!=1)
             {
                matrix[i][j]="1111";
                xx=1;
             }
             if(matrix[i][j].equals("0111")&&xx!=1)
             {
                matrix[i][j]="0101";
                xx=1;
             }
             if(matrix[i][j].equals("1000")&&xx!=1)
             {
                matrix[i][j]="0001";
                xx=1;
             }
             if(matrix[i][j].equals("1001")&&xx!=1)
             {
                matrix[i][j]="1101";
                xx=1;
             }
             if(matrix[i][j].equals("1010")&&xx!=1)
             {
                matrix[i][j]="1100";
                xx=1;
             }
             if(matrix[i][j].equals("1011")&&xx!=1)
             {
                matrix[i][j]="0111";
                xx=1;
             }  
if(matrix[i][j].equals("1100")&&xx!=1)
             {
                matrix[i][j]="1011";
                xx=1;
             }
             if(matrix[i][j].equals("1101")&&xx!=1)
             {
                matrix[i][j]="0100";
                xx=1;
             }
             if(matrix[i][j].equals("1110")&&xx!=1)
             {
                matrix[i][j]="0010";
                xx=1;
             }
             if(matrix[i][j].equals("1111")&&xx!=1)
             {
                matrix[i][j]="1000";
                xx=1;
             }  
            }
        }
    }
    private static String xorHex(String a, String b) {
        int intValueA = Integer.parseInt(a, 16);
        int intValueB = Integer.parseInt(b, 16);
        int result = intValueA ^ intValueB;
        return String.format("%02X", result);
    }
    // Helper method to swap elements of the first row in the matrix
    private static void swapFirstRowElements(String[][] matrix) {
        String temp = matrix[0][0];
        matrix[0][0] = matrix[0][1];
        matrix[0][1] = temp;
    }
    private static String[] generateRoundKeys(String masterKey) {
        
            inputBlock2[0][0] = hexToBinary(String.valueOf(masterKey.charAt(0)));
     
            inputBlock2[1][0] = hexToBinary(String.valueOf(masterKey.charAt(1)));

            inputBlock2[0][1] = hexToBinary(String.valueOf(masterKey.charAt(2)));

            inputBlock2[1][1] = hexToBinary(String.valueOf(masterKey.charAt(3)));


        String[][] roundKey1 = new String[2][2];
        String[][] roundKey2 = new String[2][2];
String y=subNibbles(inputBlock2[1][1]);
String z=xorNibbles(y, RCON1);

roundKey1[0][0]=xorNibbles(inputBlock2[0][0],z);
roundKey1[1][0]=xorNibbles(inputBlock2[1][0],roundKey1[0][0]);
roundKey1[0][1]=xorNibbles(inputBlock2[0][1],roundKey1[1][0]);
roundKey1[1][1]=xorNibbles(inputBlock2[1][1],roundKey1[0][1]);


y=subNibbles(roundKey1[1][1]);
z=xorNibbles(y, RCON2);
roundKey2[0][0]=xorNibbles(roundKey1[0][0],z);
roundKey2[1][0]=xorNibbles(roundKey1[1][0],roundKey2[0][0]);
roundKey2[0][1]=xorNibbles(roundKey1[0][1],roundKey2[1][0]);
roundKey2[1][1]=xorNibbles(roundKey1[1][1],roundKey2[0][1]);

roundKey1[0][0]=binaryToHex(roundKey1[0][0]);
roundKey1[1][0]=binaryToHex(roundKey1[1][0]);
roundKey1[0][1]=binaryToHex(roundKey1[0][1]);
roundKey1[1][1]=binaryToHex(roundKey1[1][1]);

roundKey2[0][0]=binaryToHex(roundKey2[0][0]);
roundKey2[1][0]=binaryToHex(roundKey2[1][0]);
roundKey2[0][1]=binaryToHex(roundKey2[0][1]);
roundKey2[1][1]=binaryToHex(roundKey2[1][1]);



        String[] roundKeys = new String[2];
roundKeys[0]=roundKey1[0][0]+roundKey1[1][0]+roundKey1[0][1]+roundKey1[1][1];
roundKeys[1]=roundKey2[0][0]+roundKey2[1][0]+roundKey2[0][1]+roundKey2[1][1];



        return roundKeys;
    }


    private static String subNibbles(String binary) {
       

int xx=0;
             //   String binaryValue = matrix[i][j];
             //   int index = Integer.parseInt(binaryValue, 2);
             if(binary.equals("0000")&&xx!=1)
             {
                binary="1010";
                xx=1;
             }
             if(binary.equals("0001")&&xx!=1)
             {
                binary="0000";
                xx=1;
             }
              if(binary.equals("0010")&&xx!=1)
             {
                binary="1001";
                xx=1;
             }
             if(binary.equals("0011")&&xx!=1)
             {
                binary="1110";
                xx=1;
             }
             if(binary.equals("0100")&&xx!=1)
             {
                binary="0110";
                xx=1;
             }
             if(binary.equals("0101")&&xx!=1)
             {
                binary="0011";
                xx=1;
             }
             if(binary.equals("0110")&&xx!=1)
             {
                binary="1111";
                xx=1;
             }
             if(binary.equals("0111")&&xx!=1)
             {
                binary="0101";
                xx=1;
             }
             if(binary.equals("1000")&&xx!=1)
             {
                binary="0001";
                xx=1;
             }
             if(binary.equals("1001")&&xx!=1)
             {
                binary="1101";
                xx=1;
             }
             if(binary.equals("1010")&&xx!=1)
             {
                binary="1100";
                xx=1;
             }
             if(binary.equals("1011")&&xx!=1)
             {
               binary="0111";
                xx=1;
             }  
if(binary.equals("1100")&&xx!=1)
             {
                binary="1011";
                xx=1;
             }
             if(binary.equals("1101")&&xx!=1)
             {
                binary="0100";
                xx=1;
             }
             if(binary.equals("1110")&&xx!=1)
             {
                binary="0010";
                xx=1;
             }
             if(binary.equals("1111")&&xx!=1)
             {
                binary="1000";
                xx=1;
             }  
            
        return binary;
    }

    private static String xorNibbles(String binary1, String binary2) {

      
        String xorResult = "";
        for (int i = 0; i < binary1.length(); i++) {
            char bit1 = binary1.charAt(i);
            char bit2 = binary2.charAt(i);
            char xorBit = (bit1 == bit2) ? '0' : '1';
            xorResult += xorBit;
        }
        return xorResult;
    }

    private static String hexToBinary(String hex) {
        String binary = "";
        for (int i = 0; i < hex.length(); i++) {
            char hexChar = hex.charAt(i);
            String hexString = Character.toString(hexChar);
            int decimal = Integer.parseInt(hexString, 16);
            String binaryString = Integer.toBinaryString(decimal);
            while (binaryString.length() < 4) {
                binaryString = "0" + binaryString;
            }
            binary += binaryString;
        }
        return binary;
    }

    private static String binaryToHex(String binary) {
        String hex = "";
        for (int i = 0; i < binary.length(); i += 4) {
            String nibble = binary.substring(i, i + 4);
            int decimal = Integer.parseInt(nibble, 2);
            String hexString = Integer.toHexString(decimal).toLowerCase();
            hex += hexString;
        }
        return hex;
    }

}

