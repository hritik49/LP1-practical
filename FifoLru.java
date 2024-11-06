import java.util.*;
public class FifoLru {
 public static void main(String[] args) {
 pr();
 }
 static void pr(){
 Scanner sc = new Scanner(System.in);
 System.out.println("Page Replacement : ");
 System.out.println("Enter 1 for FIFO ");
 System.out.println("Enter 2 for LRU ");
 System.out.printf("Enter Choice : ");
 int x = sc.nextInt();
 System.out.printf("Length of String : " );
 int n = sc.nextInt();
 
 int fr = 3;
 int ref[] = new int[n];
 for (int i = 0; i < n; i++){
 ref[i] = sc.nextInt();
 }
 // FIFO
 HashMap<Integer,Integer> map = new HashMap<>();
 ArrayList<ArrayList<Integer>> arr = new ArrayList<>();
 for(int i = 0 ; i <= n ; i++){
 arr.add(new ArrayList<>());
 }

 for(int i = 0 ; i < fr ; i++){
 arr.get(0).add(-1);

 }
 int ct = 1;
 int hit = 0;
 if(x == 1 && n > 0){
 int indx= 0;
 for(int i = 1 ; i <= n ; i++){
 int curr = ref[i-1];
 arr.get(i).addAll(arr.get(i-1));
 if(!map.containsKey(curr)){
 if(indx < fr) arr.get(i).set((indx),ref[i-1]);
 else{
 int min = Integer.MAX_VALUE;
 int temp = 0;
 for(int j : map.keySet()){
 if(map.get(j) < min){
 min = map.get(j);
 temp = j;
 }
 }

 for(int j = 0 ; j < fr ; j++){
 if(arr.get(i).get(j) == temp){
 arr.get(i).set(j,curr);
 break;
 }
 }
 map.remove(temp);
 }
 map.put(ref[i-1],ct++);
 indx++;
 }else{
 hit++;
 }
 }
 }else if(x == 2 && n > 0 ){

 //LRU
 int indx= 0;
 for(int i = 1 ; i <= n ; i++){
 int curr = ref[i-1];
 arr.get(i).addAll(arr.get(i-1));
 if(!map.containsKey(curr)){
 if(indx < fr) arr.get(i).set(indx,ref[i-1]);
 else{
 int min = Integer.MAX_VALUE;
 int temp = 0;
 for(int j : map.keySet()){
 if(map.get(j) < min){
 min = map.get(j);
 temp = j;
 }
 }
 for(int j = 0 ; j < fr ; j++){
 if(arr.get(i).get(j) == temp){
 arr.get(i).set(j,curr);
 break;
 }
 }
 map.remove(temp);
 }
 indx++;
 }else{
 hit++;
 }
 map.put(ref[i-1],ct++);
 }
 }
 // Output
 System.out.println();
 for(int i = 0 ; i <= n ; i++){
 for(int j = 0 ; j < fr ; j++){
 System.out.printf(arr.get(i).get(j) + " ");
 }
 System.out.println();
 }
 System.out.println("Total Page Fault : " + (n - hit));
 System.out.println("Total Page Hit : " + hit);

 sc.close();
 }
}