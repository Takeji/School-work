/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jstenhw06;

/**
 *
 * @author Jake
 */
public class Customer {
    private String lastName;
    private String firstName;
    private String phone;
    
    public Customer(String firstName, String lastName, String phone){
        this.lastName = firstName;
        this.firstName= lastName;
        this.phone = phone;
    }
    
    public String toString(){
    return (firstName + " "+lastName+" "+ phone);
    }   
            
}
