import java.io.*;
import java.util.*;

public class MacroProcessorPassII {
    public static void main(String[] args) {
        try {
            // Load MNT (Macro Name Table)
            Map<String, Integer> mnt = new HashMap<>();
            BufferedReader mntReader = new BufferedReader(new FileReader("mnt.txt"));
            String line;
            while ((line = mntReader.readLine()) != null) {
                String[] parts = line.split(" ");
                mnt.put(parts[0], Integer.parseInt(parts[1]));
            }
            mntReader.close();

            // Load MDT (Macro Definition Table)
            List<String> mdt = new ArrayList<>();
            BufferedReader mdtReader = new BufferedReader(new FileReader("mdt.txt"));
            while ((line = mdtReader.readLine()) != null) {
                mdt.add(line);
            }
            mdtReader.close();

            // Process the source file and expand macros
            BufferedReader sourceReader = new BufferedReader(new FileReader("source.txt"));
            BufferedWriter outputWriter = new BufferedWriter(new FileWriter("expanded_output.txt"));
            String sourceLine;

            while ((sourceLine = sourceReader.readLine()) != null) {
                String[] tokens = sourceLine.split(" ");
                String firstToken = tokens[0];

                // Check if the first token is a macro call in the MNT
                if (mnt.containsKey(firstToken)) {
                    int mdtIndex = mnt.get(firstToken); // Get the MDT start index for the macro

                    // Create an Argument List Array (ALA) for argument substitution
                    Map<String, String> ala = new HashMap<>();
                    String[] arguments = Arrays.copyOfRange(tokens, 1, tokens.length);

                    // Map formal parameters (&ARG1, &ARG2, ...) to actual arguments (e.g., 5, 10)
                    for (int i = 0; i < arguments.length; i++) {
                        ala.put("&ARG" + (i + 1), arguments[i]);
                    }

                    // Expand macro from MDT starting at the given index
                    while (mdtIndex < mdt.size() && !mdt.get(mdtIndex).equals("MEND")) {
                        String macroLine = mdt.get(mdtIndex);

                        // Substitute parameters using the ALA
                        for (Map.Entry<String, String> entry : ala.entrySet()) {
                            macroLine = macroLine.replace(entry.getKey(), entry.getValue());
                        }

                        // Write expanded macro line to output file
                        outputWriter.write(macroLine);
                        outputWriter.newLine();

                        mdtIndex++;
                    }
                } else {
                    // If not a macro call, write the original line to the output file
                    outputWriter.write(sourceLine);
                    outputWriter.newLine();
                }
            }

            // Close all file handlers
            sourceReader.close();
            outputWriter.close();

            System.out.println("Macro expansion complete. Check 'expanded_output.txt' for output.");

        } catch (IOException e) {
            System.err.println("Error processing files: " + e.getMessage());
        }
    }
}
