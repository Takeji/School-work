/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package railroads;

import java.util.*;

public class Railroads {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner keys = new Scanner(System.in);

        int i = keys.nextInt();
        keys.nextLine();//This is to flush scanner, you'll see this a lot.

        //# of simulations j
        for (int j = 1; j <= i; j++) {
            
            Hashtable<String, Integer> cityMap = new Hashtable<String, Integer>();
            ArrayList<Train> trainList = new ArrayList<Train>();

            int k = keys.nextInt();
            keys.nextLine();

            //Number of cities k
            for (int l = 0; l < k; l++) {
                String cityName = keys.nextLine();
                cityMap.put(cityName, l);
            }
            int timeLimit = 0;


            int n = keys.nextInt();
            keys.nextLine();

            //Number of trains l
            for (int l = 0; l < n; l++) {

                int stops = keys.nextInt();
                keys.nextLine();

                int timeA, timeB;
                String cityA, cityB;

                if (stops > 0) {
                    timeA = keys.nextInt();
                    cityA = keys.nextLine().trim();
                    stops--;
                    while (stops > 0) {
                        timeB = keys.nextInt();
                        cityB = keys.nextLine().trim();
                        //setting latest train
                        if (timeB > timeLimit) {
                            timeLimit = timeB;
                        }
                        trainList.add(new Train(timeA, timeB, cityA, cityB));
                        stops--;

                    }
                }

            }
            int startTime = keys.nextInt();
            keys.nextLine();
            int start;
            int end;

            String startCity = keys.nextLine();
            start = cityMap.get(startCity);
            String endCity = keys.nextLine();
            end = cityMap.get(endCity);
            

               //Parameters for matrix,
               //X = the number of cities
               //Y = the max time limit 
            int[][] matrixA = new int[k][timeLimit];
            for (int x = 0; x < k; x++) {
                for (int y = 0; y < timeLimit; y++) {
                    matrixA[x][y] = -1;//placeholder for invalid
                }
            }
            
           //itterate through all Trains in trainList
            for (Train t : trainList) {
                int startInd = cityMap.get(t.startCity);
                int endInd = cityMap.get(t.endCity);
                 //if the beginning city is the same as the city being compared
                 //and if its starting time is within the threshold
                if ((startInd == start) && (t.startTime >= startTime)) {    
                    for (int a = t.endTime - 1; a < timeLimit; a++) {
                        matrixA[endInd][a] = Math.max(matrixA[endInd][a], t.startTime);
                    }
                    //search for end time and store it at that time

                } else if (matrixA[startInd][t.startTime] >= 0) {
                    for (int a = t.endTime - 1; a < timeLimit; a++) {
                        matrixA[endInd][a] = Math.max(matrixA[endInd][a],
                                matrixA[startInd][t.startTime - 1]);
                    }
                }
            }
            System.out.println("Scenario " + j);

            Boolean connection = false;
            for (int q = 0; q < timeLimit; q++) {
                if (matrixA[end][q] >= 0) {
                    if((q+1)>=2400){
                        break;
                    }
                    connection = true;
                    System.out.println("Departure  " + matrixA[end][q] + " " + startCity);
                    System.out.println("Arrival    " + (q + 1) + " " + endCity);
                    break;
                }
            }
            if (!connection) {
                System.out.println("No connection");
            }
            System.out.println();
        }
    }
}
