import java.io.*;
import java.util.*;

public class MacroProcessorPassI {
    // Data structures for MNT and MDT
    static HashMap<String, Integer> MNT = new HashMap<>(); // Macro Name Table
    static ArrayList<String> MDT = new ArrayList<>(); // Macro Definition Table
    static int mdtPointer = 0;

    public static void main(String[] args) throws IOException {
        // Open input and output files
        BufferedReader input = new BufferedReader(new FileReader("input.txt"));
        BufferedWriter intermediateFile = new BufferedWriter(new FileWriter("intermediate.txt"));
        String line;
        boolean isInsideMacro = false;

        while ((line = input.readLine()) != null) {
            line = line.trim();
            // Skip empty lines
            if (line.isEmpty())
                continue;

            String[] tokens = line.split("\\s+");

            // Check for MACRO definition
            if (tokens[0].equalsIgnoreCase("MACRO")) {
                isInsideMacro = true;
                // Read next line for macro prototype
                line = input.readLine().trim();
                tokens = line.split("\\s+");

                // Add to MNT with current MDT pointer
                MNT.put(tokens[0], mdtPointer);

                // Add macro prototype to MDT
                MDT.add(line);
                mdtPointer++;
                continue;
            }

            // Check for MEND
            if (tokens[0].equalsIgnoreCase("MEND")) {
                isInsideMacro = false;
                MDT.add(line);
                mdtPointer++;
                continue;
            }

            // If inside macro definition, add to MDT
            if (isInsideMacro) {
                MDT.add(line);
                mdtPointer++;
            } else {
                // If not inside macro, write to intermediate file
                intermediateFile.write(line + "\n");
            }
        }

        // Close files
        input.close();
        intermediateFile.close();

        // Write MNT and MDT to files
        writeMNTToFile();
        writeMDTToFile();

        System.out.println("Pass-I completed successfully!");
        displayTables();
    }

    // Method to write MNT to file
    private static void writeMNTToFile() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("mnt.txt"));
        for (Map.Entry<String, Integer> entry : MNT.entrySet()) {
            writer.write(entry.getKey() + " " + entry.getValue() + "\n");
        }
        writer.close();
    }

    // Method to write MDT to file
    private static void writeMDTToFile() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("mdt.txt"));
        for (int i = 0; i < MDT.size(); i++) {
            writer.write(i + " " + MDT.get(i) + "\n");
        }
        writer.close();
    }

    // Method to display MNT and MDT contents
    private static void displayTables() {
        System.out.println("\nMacro Name Table (MNT):");
        System.out.println("Macro Name\tMDT Index");
        for (Map.Entry<String, Integer> entry : MNT.entrySet()) {
            System.out.println(entry.getKey() + "\t\t" + entry.getValue());
        }

        System.out.println("\nMacro Definition Table (MDT):");
        System.out.println("Index\tDefinition");
        for (int i = 0; i < MDT.size(); i++) {
            System.out.println(i + "\t" + MDT.get(i));
        }
    }
}
