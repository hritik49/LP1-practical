import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class Optimal {
public static void main(String[] args) throws IOException
{
BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
int rl, fr, pt = 0, hit = 0, fault = 0;
boolean isFull = false;
int buffer[];
int reference[];
int mem_layout[][];
System.out.println("\nENTER THE NUMBER OF FRAMES: ");
fr = Integer.parseInt(br.readLine());
System.out.println("\nENTER THE LENGTH OF REFERENCE STRING: ");
rl = Integer.parseInt(br.readLine());
reference = new int[rl];
mem_layout = new int[rl][fr];
buffer = new int[fr];
for(int j = 0; j < fr; j++)
buffer[j] = -1;
System.out.println("\nENTER THE REFERENCE STRING: ");
for(int i = 0; i < rl; i++)
{
reference[i] = Integer.parseInt(br.readLine());
}
System.out.println();
for(int i = 0; i < rl; i++)
{
int search = -1;
for(int j = 0; j < fr; j++)
{
if(buffer[j] == reference[i])
{
search = j;
hit++;
break;
}
}
if(search == -1)
{
if(isFull)
{
int index[] = new int[fr];
boolean index_flag[] = new boolean[fr];
for(int j = i + 1; j < rl; j++)
{
for(int k = 0; k < fr; k++)
{
if((reference[j] == buffer[k]) && (index_flag[k] == false))
{
index[k] = j;
index_flag[k] = true;
break;
}
}
}
int max = index[0];
pt = 0;
if(max == 0)
max = 200;
for(int j = 0; j < fr; j++)
{
if(index[j] == 0)
index[j] = 200;
if(index[j] > max)
{
max = index[j];
pt = j;
}
}
}
buffer[pt] = reference[i];
fault++;
if(!isFull)
{
pt++;
if(pt == fr)
{
pt = 0;
isFull = true;
}
}
}
for(int j = 0; j < fr; j++)
mem_layout[i][j] = buffer[j];
}
for(int i = 0; i < fr; i++)
{
for(int j = 0; j < rl; j++)
System.out.printf("%3d ",mem_layout[j][i]);
System.out.println();
}
System.out.println("\nTOTAL NUMBER OF HIT: " + hit);
System.out.println("\nHIT RATIO: " + (float)((float)hit/rl));
System.out.println("\nTOTAL NUMBER OF PAGE FAULT: " + fault);
}
}
