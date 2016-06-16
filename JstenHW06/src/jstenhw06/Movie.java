/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jstenhw06;

import java.util.*;


public class Movie implements Comparable {
    private String title;
    private int have, want;
    private LinkedList<Customer>waitingList;
    
     Movie(String t, int w, int h, LinkedList<Customer> waitingList){
    this.title=t;
    this.have=w;
    this.want=h;
    this.waitingList = waitingList;
}
     Movie(String t, int w, int h, Customer l) {
        this.title = t;
        this.have = h;
        this.want = w;
        this.waitingList.add(l);
    }
    
     Movie(String t, int w, int h) {
        this.title = t;
        this.have = h;
        this.want = w;
        this.waitingList = new LinkedList();
    }
    public void addToWaitingList(String firstName, String lastName, String phone){
        this.waitingList.add(new Customer(firstName, lastName, phone));
    }
    public void setTitle(String title){
        this.title = title;
    }
   
    public String getTitle(){
        return title;
        
    }
    public void setHave(int have){
        this.have = have;
    }
    public int getHave(){
            return have;
        }
    public void setWant(int want){
        this.want = want;
    }
    public int getWant(){
        return want;
    }
    public LinkedList<Customer> getWaitingList(){
        return waitingList;
    }
 
      public static Comparator<Movie> MovieTitleComparator = new Comparator<Movie>() {

        @Override
        public int compare(Movie o1,Movie o2) {
            String title1 = o1.getTitle();
            String title2 = o2.getTitle();

            return title1.compareTo(title2);
        }

    };
       @Override
    public String toString() {
       String movie = "\nTitle: " + title + ", want: " + want + ", have: " + have + ",";

        if (waitingList.isEmpty()) {
            return movie += " No people in the waiting list\n";
            
        }
        else{
            for (Customer p : waitingList) {
                movie += p;
            }
            return movie += "\n";
            

        }
    }

    @Override
    public int compareTo(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }





}
