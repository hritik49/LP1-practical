import java.io.*;
import java.util.*;

class AssemblerPassI {
    public static void main(String args[]) throws Exception {
        int loc = 0, sym = 0, lit = 0;
        Map<String, Integer> symbolTable = new HashMap<>();
        List<String[]> literalTable = new ArrayList<>();
        
        FileReader f1 = new FileReader("in.txt");
        BufferedReader b1 = new BufferedReader(f1);

        FileWriter f3 = new FileWriter("symboltab.txt");
        BufferedWriter b3 = new BufferedWriter(f3);

        FileWriter f4 = new FileWriter("intermediate.txt");
        BufferedWriter b4 = new BufferedWriter(f4);

        FileWriter f5 = new FileWriter("literaltab.txt");
        BufferedWriter b5 = new BufferedWriter(f5);

        String s;
        String[] s1 = new String[4];
        while ((s = b1.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(s);
            int m = 0;
            while (st.hasMoreTokens()) {
                s1[m] = st.nextToken();
                m++;
            }

            if (s1[1].equals("START")) {
                b4.write("AD 01 C " + s1[2]);
                loc = Integer.parseInt(s1[2]);
                b4.newLine();
            } else if (s1[1].equals("END")) {
                for (String[] literal : literalTable) {
                    b4.write(loc + " AD 02 C " + literal[0]);
                    loc++;
                    b4.newLine();
                }
                break;
            } else {
                b4.write(loc + " ");
                
                // Handling Label
                if (!s1[0].equals("-")) {
                    if (!symbolTable.containsKey(s1[0])) {
                        symbolTable.put(s1[0], loc);
                        b3.write(s1[0] + " " + loc);
                        b3.newLine();
                    }
                }
                
                // Checking if Opcode is in the operation table
                FileReader f2 = new FileReader("op.txt");
                BufferedReader b2 = new BufferedReader(f2);
                boolean opFound = false;
                String sl;
                while ((sl = b2.readLine()) != null) {
                    String[] s2 = sl.split(" ");
                    if (s1[1].equals(s2[0])) {
                        b4.write("IS " + s2[1]);
                        opFound = true;
                        break;
                    }
                }
                b2.close();
                
                // If not found, write an error message or handle as needed
                if (!opFound) {
                    System.out.println("Error: Opcode " + s1[1] + " not found.");
                    continue;
                }
                
                // Handle Operand (Symbol or Literal)
                if (s1[2].matches("[a-zA-Z]+")) {
                    if (!symbolTable.containsKey(s1[2])) {
                        symbolTable.put(s1[2], -1); // Placeholder until defined
                        sym++;
                    }
                    b4.write(" S " + sym);
                } else if (s1[2].contains("=")) {
                    String literal = s1[2];
                    boolean found = false;
                    for (String[] litEntry : literalTable) {
                        if (litEntry[1].equals(literal)) {
                            found = true;
                            b4.write(" L " + litEntry[0]);
                            break;
                        }
                    }
                    if (!found) {
                        literalTable.add(new String[]{String.valueOf(++lit), literal});
                        b4.write(" L " + lit);
                    }
                }
                loc++;
                b4.newLine();
            }
        }

        // Write Symbol Table
        for (Map.Entry<String, Integer> entry : symbolTable.entrySet()) {
            b3.write(entry.getKey() + " " + entry.getValue());
            b3.newLine();
        }

        // Write Literal Table
        int litLoc = loc;
        for (String[] literal : literalTable) {
            b5.write(literal[0] + " " + literal[1] + " " + litLoc++);
            b5.newLine();
        }

        b1.close();
        b3.close();
        b4.close();
        b5.close();
    }
}
