/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jstenhw06;

import java.io.*;
import java.util.*;

/*
 * Program by Jake Stensrud
 * 
 * This program takes in a specificly formatted list of DvD's
 * and acts as an inventory system.
 * 
 * When you are done, it'll save it in the same format to a specific file.
 * 
 */

public class JstenHW06 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //create inventory list   
        LinkedList<Movie> inventory = new LinkedList();
        
        /*
         * manual input
         inventory.add(new Movie("Apples",6,9));
         inventory.add(new Movie("Cars",1,0));
         inventory.add(new Movie("Beauty and the Beast",0,5));
                
         Collections.sort(inventory,Movie.MovieTitleComparator);
                
         System.out.println(inventory.toString());
         */
        
        //Start of reading file
        BufferedReader br = null;
        try {
            String title;
            String temp;
            int want;
            int have;
            String contact;
            String last;
            String first;
            String phone;
            String sCurrentLine;
            //Reading the first file in args
            br = new BufferedReader(new FileReader(args[0]));

             //Read until there is nothing left
            while ((sCurrentLine = br.readLine()) != null) {
                //Get information from the file, and save it into inventory

                title = sCurrentLine;
                temp = br.readLine();
                StringTokenizer st = new StringTokenizer(temp);
                want = Integer.parseInt(st.nextElement().toString());
                temp = br.readLine();
                st = new StringTokenizer(temp);
                have = Integer.parseInt(st.nextElement().toString());
                System.out.println(title + " " + want + " " + have);
                Movie m = new Movie(title, want, have);

                LinkedList waiting = new LinkedList();
                //Check if each movie in file has wait list
                while (!(sCurrentLine = br.readLine()).matches("#")) {
                    contact = sCurrentLine;
                    //Retrieve customer info
                    st = new StringTokenizer(contact);
                    first = st.nextElement().toString();
                    last = st.nextElement().toString();
                    phone = st.nextElement().toString();

                    //Create a customer, and add it to the waiting list

                    m.addToWaitingList(first, last, phone);

                }
                //Create a movie with given information
                m.setWant(want);
                m.setHave(have);
                m.setTitle(title);
                //Add each movie to inventory
                inventory.add(m);
                System.out.println("added " + m.toString());
            }
            //Catch exception
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }




       
        //Start prompt
        Scanner keys = new Scanner(System.in);
        System.out.print("Welcome to the Movie Inventory\nEnter the letter \"H\" to get help on available commands: ");
        String command = keys.nextLine();
        
        //Sorts all of the movies
        Collections.sort(inventory, Movie.MovieTitleComparator);
        
        //Check
        while (!command.toUpperCase().matches("H")) {
            System.out.print("Please enter \"H\" again: ");
            command = keys.nextLine();
        }
        //Always run program until they save to quit
        while (true) {

            System.out.print("\nAvailable Commands:\n"
                    + "I <title>\tDisplays inventory info for the title.\n"
                    + "L\t\tList entire inventory alphabetically.\n"
                    + "A <title>\tAdds title to inventory. Will ask for want value.\n"
                    + "M <title>\tModify want value for the title.\n"
                    + "D\t\tTake delivery of a shipment of DVDs.\n"
                    + "O\t\tOrder additional DVDs. Raises have value to want value for all.\n"
                    + "R\t\tWrite a return order. Lowers have value to want value for all.\n"
                    + "S <title>\tSell single DVD of the title. Add name to waitlist if sold out.\n"
                    + "Q\t\tSave inventory and wishlists to a file and quit.\n"
                    + "Please enter command (letter only): ");
            command = keys.nextLine();
            //Valid input check
            while (!command.toUpperCase().matches("I|L|A|M|D|O|R|S|Q")) {
                System.out.print("Please enter valid command: ");
                command = keys.nextLine();
            }
            //Displays inventory info for specific titles
            if (command.toUpperCase().matches("I")) {
                System.out.print("Enter title name: ");
                String title = keys.nextLine();
                Movie a = find(inventory, title);
                if (a == null) {
                    System.out.println("Sorry, could not find your movie");
                } else {
                    System.out.println(a.toString());
                }
                
                //Displays the inventory
            } else if (command.toUpperCase().matches("L")) {
                //Alphabetizes inventory
                Collections.sort(inventory, Movie.MovieTitleComparator);
                System.out.println(inventory.toString());
                
                //Adds a title
            } else if (command.toUpperCase().matches("A")) {
                System.out.println("Enter title name: ");
                String title = keys.nextLine();
                System.out.println("Please add initial want value");
                int w = keys.nextInt();
                inventory.add(new Movie(title, w, 0));
                Collections.sort(inventory, Movie.MovieTitleComparator);
                //Modifying want value
            } else if (command.toUpperCase().matches("M")) {
                System.out.print("Enter title name: ");
                String title = keys.nextLine();
                for (Movie s : inventory) {
                    int i = s.getTitle().compareToIgnoreCase(title);
                    if (i == 0) {
                        System.out.println("Found " + s.getTitle());
                        System.out.println("Please input new want value");
                        int w = keys.nextInt();
                        s.setWant(w);
                        System.out.println("Want value has been modified.");
                    }
                }

            } else if (command.toUpperCase().matches("D")) {
                //Raises have value
            } else if (command.toUpperCase().matches("O")) {
                for (Movie a : inventory) {
                    if (a.getWant() > a.getHave()) {
                        System.out.println("Ordered " + a.getTitle() + " by " + (a.getWant() - a.getHave()) + " more");
                        a.setHave(a.getWant());
                    }
                }
                //Lowers have to want
            } else if (command.toUpperCase().matches("R")) {
                for (Movie a : inventory) {
                    if (a.getHave() < a.getWant()) {
                        System.out.println("Returned " + a.getTitle() + " by " + (a.getWant() - a.getHave()) + " many");
                        a.setHave(a.getWant());
                    }
                }
                //Sells a Dvd, otherwise adds to waitlist
            } else if (command.toUpperCase().matches("S")) {
                System.out.print("Enter title name: ");
                String title = keys.nextLine();
                for (Movie s : inventory) {
                    int i = s.getTitle().compareToIgnoreCase(title);
                    if (i == 0) {
                        if ((s.getHave() == 0) || (s.getHave() == s.getWant())) {
                            System.out.println("Sorry we don't have anymore of " + title + " you will be added to the wait list");
                            System.out.println("Your first name?");
                            String f = keys.nextLine();
                            System.out.println("Your last name?");
                            String l = keys.nextLine();
                            System.out.println("Your phone number?");
                            String p = keys.nextLine();
                            System.out.println("You have been added to the wait list for " + title);
                            s.addToWaitingList(f, l, p);
                        } else {
                            s.setHave(s.getHave() - 1);
                            System.out.println("Sold one of" + title);
                        }
                    }
                }

                //Saves current inventory into a file
            } else if (command.toUpperCase().matches("Q")) {
                      //create temp customer.
                      LinkedList<Customer> c = new LinkedList<Customer>();
                      //Designated file name
                      File file = new File("Saved.txt");
                     
                    try {
                    FileWriter fileWriter = new FileWriter(file);
                    BufferedWriter bW = new BufferedWriter(fileWriter);
                    
                    for (int i = 0; i < inventory.size(); i++) {
                    Movie m = inventory.get(i);
                    
                       //File formatting, so you can read it through same program
                        String s = m.getTitle();                   
                        bW.write(s);
                        bW.newLine();
                        int j = m.getWant();
                        s = ""+j;                     
                        bW.write(s);
                        bW.newLine();
                        j = m.getHave();
                        s = ""+j;
                        bW.write(s);
                        bW.newLine();
                        c = m.getWaitingList();
                        //more formatting for waiting list customers
                        for(int k = 0; k<c.size();k++){
                           Customer q = c.get(k);
                           bW.write(q.toString());
                           bW.newLine();
                        }
                        bW.write("#");
                        bW.newLine();
                       
                           
                      
                    }
                    bW.close();
                    }catch(IOException e){
                        System.out.println(e.getMessage());
                    }

                
                
                System.out.println("Invetory saved in "+file);
                System.out.println("Good bye!");
                
                System.exit(0);
            }
        }
    }
        //Find function for Movie titles.
    public static Movie find(LinkedList<Movie> m, String s) {
        for (Movie a : m) {
            int i = a.getTitle().compareToIgnoreCase(s);
            if (i == 0) {
                return a;
            }
        }
        return null;
    }
}
