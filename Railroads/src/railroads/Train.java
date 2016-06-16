/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package railroads;

/**
 *
 * @author Jake
 */
public class Train {

    int startTime, endTime;
    String startCity, endCity;

    public Train(int s, int e, String st, String et) {
        startTime = s;
        endTime = e;
        startCity = st;
        endCity = et;
    }

    public int compareTo(Train t) {
        if (startTime != t.startTime) {
            return startTime - t.startTime;
        } else {
            return endTime - t.endTime;
        }
    }

    public String toString() {
        return startTime + " " + startCity + " " + endTime + " " + endCity;
    }
}

