/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package banksimjsten;

import java.util.*;
import java.io.*;


/*
 * Bank simulation. 
 * Using Queues, simulates the time customers take in a bank.
 * Configure "Customers.txt" To change event times.
 */
public class BankSimJsten {
    //Initilization
    private int Time = 0;
    private int Wait = 0;
    private int customers = 0;
    private final int Head = 0;
    private Queue<Customer> line = new LinkedList();
    private final File f = new File("Customers.txt");
    private final ArrayList<Event> EventList = new ArrayList();
    //Constructor
    BankSimJsten(){
        createEvents();
    }
    
    public static void main(String[] args) {
       BankSimJsten bs = new BankSimJsten();
        System.out.println("EventList: ");
        System.out.println(bs.EventList);
        bs.simulate();
        
    }
    
    public void simulate(){
        System.out.println("Simulation starting");
        //While we have events
        while(!EventList.isEmpty()){
            Event curr = EventList.get(Head);
            Time = curr.getBegin();
            //Arrivals
            if(curr.getType().matches("A"))
            {
                System.out.println("Arrial event at: \t"+Time);
                Time = curr.getBegin();
                Customer c = new Customer (curr.getBegin(),curr.getDuration());
                line.add(c);
                customers++;
                //Departures
            }else{
                System.out.println("Departure event at: \t"+Time);
                Customer c = line.peek();
                c.setDeparture(curr.getEnd());
                c.setWait(Time-c.getArrival()-c.getTrans());
                line.remove();
                Wait += Time - c.getArrival()-c.getTrans();
                
                
            }
            EventList.remove(Head);
        }
        System.out.println("Total Customers : "+customers);
        System.out.println("Avg time"+ ((double)Wait/(double)customers));
        System.out.println("Fin");
    }
    /*
     * Constructor for events
     * Creates Arrivals and Departures 
     * and adds them into EventList
     * Txt file "Customers.txt"
     * Arrival - Wait time
     */
    private void createEvents(){
        try{
            Scanner keys = new Scanner(f);
            
            while(keys.hasNext()){
              int a = keys.nextInt();
              int t = keys.nextInt();
              
              if(EventList.isEmpty()){
                  EventList.add(new Event(a, t));
                  EventList.add(new Event(a + t));
              }else{
                  if (EventList.get(EventList.size()-1).getBegin()<a){
                      EventList.add(new Event(a,t));
                      EventList.add(new Event(a + t));
                  }else{
                      EventList.add(new Event(a, t));
                      Event e1 = EventList.get(EventList.size()-2);
                      EventList.add(new Event(e1.getEnd()+t));
                  }
              }
            }
            Collections.sort(EventList);
        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
}
class Customer {

    //Initilization
    private int arrival;
    private int trans;
    private int departure;
    private int wait;
    
    
    //Constructor
    Customer(int a, int t) {
        arrival = a;
        trans = t;
    }
    //Getters/Setters
    public int getArrival() {
        return arrival;
    }

    public void setArrival(int arrival) {
        this.arrival = arrival;
    }

    public int getTrans() {
        return trans;
    }

    public void setTrans(int trans) {
        this.trans = trans;
    }

    public int getDeparture() {
        return departure;
    }

    public void setDeparture(int departure) {
        this.departure = departure;
    }

    public int getWait() {
        return wait;
    }

    public void setWait(int wait) {
        this.wait = wait;
    }

}
//Changed compareTo
class Event implements Comparable<Event> {
    //Intialization
    final private String Type;
    final private int Begin;
    final private int End;
    final private int Duration;
    
    //Constructors
    Event(int b, int d) {
        Begin = b;
        Duration = d;
        Type = "A";
        End = b + d;
    }

    Event(int d) {
        Begin = d;
        Duration = -1;
        Type = "D";
        End = d;
    }

   //Getters and Setters
    public String getType() {
        return Type;
    }

    public int getBegin() {
        return Begin;
    }

    public int getEnd() {
        return End;
    }

    public int getDuration() {
        return Duration;
    }
    //Override original Compareto to compare Events
@Override
    public int compareTo(Event o) {
        int e1 = this.Begin;
        int e2 = o.Begin;
        return (e1 < e2) ? -1 : (e1 > e2) ? 1 : 0;
    }
    //Overrided toString
    @Override
    public String toString() {
        if (Type.matches("A")) {
            return "\nArrival Event:\n"
                    + "Arrival: " + Begin + "\tTransaction: " + Duration + "\n";
        } else {
            return "\nDeparture Event:\n"
                    + "Departure: " + End + "\n";
        }
    }
}
