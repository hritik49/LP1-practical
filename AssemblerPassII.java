import java.io.*;
import java.util.*;

public class AssemblerPassII {
    public static void main(String[] args) {
        try {
            // Load the Symbol Table
            Map<String, Integer> symbolTable = new HashMap<>();
            BufferedReader symbolReader = new BufferedReader(new FileReader("symboltab.txt"));
            String line;
            while ((line = symbolReader.readLine()) != null) {
                String[] parts = line.split(" ");
                symbolTable.put(parts[0], Integer.parseInt(parts[1]));
            }
            symbolReader.close();

            // Load the Literal Table
            Map<String, Integer> literalTable = new HashMap<>();
            BufferedReader literalReader = new BufferedReader(new FileReader("literaltab.txt"));
            while ((line = literalReader.readLine()) != null) {
                String[] parts = line.split(" ");
                literalTable.put(parts[1], Integer.parseInt(parts[2]));
            }
            literalReader.close();

            // Process the Intermediate Code to Generate Final Code
            BufferedReader intermediateReader = new BufferedReader(new FileReader("intermediate.txt"));
            BufferedWriter outputWriter = new BufferedWriter(new FileWriter("final_code.txt"));

            while ((line = intermediateReader.readLine()) != null) {
                String[] tokens = line.trim().split(" ");
                
                StringBuilder machineCode = new StringBuilder();
                
                for (String token : tokens) {
                    if (token.equals("AD")) {
                        // Ignore assembler directives in Pass 2
                        break;
                    } else if (token.equals("IS")) {
                        // Instruction
                        machineCode.append(tokens[1]).append(" ");
                    } else if (token.equals("L")) {
                        // Literal - fetch from literal table
                        String literalIndex = tokens[2];
                        machineCode.append(literalTable.get(literalIndex)).append(" ");
                    } else if (token.equals("S")) {
                        // Symbol - fetch from symbol table
                        String symbolIndex = tokens[2];
                        machineCode.append(symbolTable.get(symbolIndex)).append(" ");
                    } else {
                        // Append remaining tokens as part of machine code
                        machineCode.append(token).append(" ");
                    }
                }

                // Write final machine code for the line
                if (machineCode.length() > 0) {
                    outputWriter.write(machineCode.toString().trim());
                    outputWriter.newLine();
                }
            }

            // Close all file handlers
            intermediateReader.close();
            outputWriter.close();

            System.out.println("Assembler Pass 2 complete. Check 'final_code.txt' for output.");

        } catch (IOException e) {
            System.err.println("Error processing files: " + e.getMessage());
        }
    }
}
