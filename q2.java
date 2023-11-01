import java.util.Scanner;

public class q2{
    public static int[][] constantMatrix = {{9, 2}, {2, 9}};
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

        System.out.print("Enter a hexadecimal cipher string (up to 4 characters): ");
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
            

            System.out.println("Processed hexadecimal cipher string: " + hexInput);

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

    swapFirstRowElements(originalBinaryMatrix);
    String shifted=binaryToHex(originalBinaryMatrix[0][0])+binaryToHex(originalBinaryMatrix[1][0])+binaryToHex(originalBinaryMatrix[0][1])+binaryToHex(originalBinaryMatrix[1][1]);
    System.out.println("Shifted is: "+shifted); 
  
    String result = xorHex(shifted,roundKeys[1]);
    System.out.println("Result is: "+result); 
  


    String x1=hexToBinary(Character.toString(result.charAt(0)));
    String x2=hexToBinary(Character.toString(result.charAt(1)));
    String x3=hexToBinary(Character.toString(result.charAt(2)));
    String x4=hexToBinary(Character.toString(result.charAt(3)));

    x1=subNibbles2(x1);
    x2=subNibbles2(x2);
    x3=subNibbles2(x3);
    x4=subNibbles2(x4);
     System.out.println("SubNibbles :  "+x1+x2+x3+x4);
    x1=binaryToHex(x1);
    x2=binaryToHex(x2);
    x3=binaryToHex(x3);
    x4=binaryToHex(x4);

    System.out.println("SubNibbles :  "+x1+x2+x3+x4);
    String newarr[][]=new String [2][2];
    newarr[0][0]=x1;
    newarr[1][0]=x2;
    newarr[0][1]=x3;
    newarr[1][1]=x4;
    
swapFirstRowElements(newarr);
String ans[][]=new String[2][2];
ans=matrixMultiply(newarr);
    System.out.println("Mix Column :  "+ans[0][0]+ans[1][0]+ans[0][1]+ans[1][1]);

String left=ans[0][0]+ans[1][0]+ans[0][1]+ans[1][1];
String xo=xorHex(left,roundKeys[0]);
    System.out.println("Xor :  "+xo);

 String x11=hexToBinary(Character.toString(xo.charAt(0)));
    String sub1=subNibbles2(x11);
    
 String x22=hexToBinary(Character.toString(xo.charAt(1)));
   String sub2=subNibbles2(x22);
  
 String x33=hexToBinary(Character.toString(xo.charAt(2)));
   String sub3=subNibbles2(x33);
  
 String x44=hexToBinary(Character.toString(xo.charAt(3)));
  String sub4=subNibbles2(x44);
  
String plain1=binaryToHex(sub1);
String plain2=binaryToHex(sub2);
String plain3=binaryToHex(sub3);
String plain4=binaryToHex(sub4);


    System.out.println("Plain Text :  "+plain1+plain2+plain3+plain4);
    


    scanner.close();

    }

    }
    private static String xorHex(String hexValue1, String hexValue2) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < hexValue1.length(); i++) {
            char c1 = hexValue1.charAt(i);
            char c2 = hexValue2.charAt(i);

            // Convert characters to integers and perform XOR
            int xorResult = Integer.parseInt(String.valueOf(c1), 16) ^ Integer.parseInt(String.valueOf(c2), 16);

            // Convert the XOR result back to hexadecimal
            String hexDigit = Integer.toHexString(xorResult);

            result.append(hexDigit);
        }
        return result.toString();
    }
    // Check if the input is a valid hexadecimal string
    private static boolean isValidHexadecimal(String input) {
        String hexPattern = "^[0-9A-Fa-f]*$";
        return input.matches(hexPattern);
    }

    // Helper method to print a 2D matrix
    private static void printMatrix(String[][] matrix) {
        
                System.out.print(matrix[0][0] + matrix[0][1]);
            System.out.println();

                System.out.print(matrix[1][0] + matrix[1][1]);
                
            System.out.println();
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
    private static String subNibbles2(String binary) {
       

int xx=0;
             //   String binaryValue = matrix[i][j];
             //   int index = Integer.parseInt(binaryValue, 2);
             if(binary.equals("1010")&&xx!=1)
             {
                binary="0000";
                xx=1;
             }
             if(binary.equals("0000")&&xx!=1)
             {
                binary="0001";
                xx=1;
             }
              if(binary.equals("1001")&&xx!=1)
             {
                binary="0010";
                xx=1;
             }
             if(binary.equals("1110")&&xx!=1)
             {
                binary="0011";
                xx=1;
             }
             if(binary.equals("0110")&&xx!=1)
             {
                binary="0100";
                xx=1;
             }
             if(binary.equals("0011")&&xx!=1)
             {
                binary="0101";
                xx=1;
             }
             if(binary.equals("1111")&&xx!=1)
             {
                binary="0110";
                xx=1;
             }
             if(binary.equals("0101")&&xx!=1)
             {
                binary="0111";
                xx=1;
             }
             if(binary.equals("0001")&&xx!=1)
             {
                binary="1000";
                xx=1;
             }
             if(binary.equals("1101")&&xx!=1)
             {
                binary="1001";
                xx=1;
             }
             if(binary.equals("1100")&&xx!=1)
             {
                binary="1010";
                xx=1;
             }
             if(binary.equals("0111")&&xx!=1)
             {
               binary="1011";
                xx=1;
             }  
if(binary.equals("1011")&&xx!=1)
             {
                binary="1100";
                xx=1;
             }
             if(binary.equals("0100")&&xx!=1)
             {
                binary="1101";
                xx=1;
             }
             if(binary.equals("0010")&&xx!=1)
             {
                binary="1110";
                xx=1;
             }
             if(binary.equals("1000")&&xx!=1)
             {
                binary="1111";
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

