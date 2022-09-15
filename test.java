import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class test {
	/**
	 * binds angle from -pi to pi
	 * @param theta radians
	 * @return angle radians
	 */
	public static double bindAngle(double theta) {
		
		while (theta<-Math.PI) {
			theta += Math.PI*2;
		}
	
		while (theta>Math.PI) {
			theta -= Math.PI*2;
		}
		
		return theta;
	}
    public static double calcAngle(double x, double y) {
    	if (x<0) {
    		return bindAngle(Math.atan(y / x) + (Math.PI/2.0)); // + Math.PI - MATH.PI/2
    	}
        if (x == 0) {
            if (y >= 0)
                return 0.0;
            else
                return Math.PI;
        }
        return bindAngle(Math.atan(y / x) - (Math.PI / 2.0));
    }

    public static void main(String[] args) {
    	String file_name = "file";
        try {
            boolean first = true;
            boolean second = true;
            double relX, relY, relTheta, firstTheta, runningTheta;
            relX=relY=relTheta=firstTheta=runningTheta=0.0;
            File myObj = new File(file_name+".txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file_name+"_out.txt"));
            
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                

                if (first == false) {
                    String data = myReader.nextLine();
                    String values[] = data.split(",");

                    double X = Double.parseDouble(values[0]);
                    double Y = Double.parseDouble(values[1]);
                    double tanX = Double.parseDouble(values[2]);
                    double tanY = Double.parseDouble(values[3]);

                    if (second == true) {
                        relX = X;
                        relY = Y;
                        firstTheta = calcAngle(tanX, tanY);
                        relTheta = calcAngle(tanX, tanY);
                        System.out.println("RELTHETA: " + (relTheta+Math.PI/2));
                        writer.write(""+0.0+","+0.0+","+0.0+'\n');
                        second = false;
                        continue;
                    }

                    double newX, newY, newTheta;
                    newX = (Y - relY) * Math.sin(firstTheta) + (X - relX) * Math.cos(firstTheta);
                    newY = -(X - relX) * Math.sin(firstTheta) + (Y - relY) * Math.cos(firstTheta);
                    System.out.println("Calc: "+calcAngle(tanX, tanY));
                    newTheta = bindAngle(calcAngle(tanX, tanY)-relTheta);
                    runningTheta+=newTheta;
                    if (newX==-0.0) newX = 0.0;
                    if (newY==-0.0) newY = 0.0;
                    if (newTheta==-0.0) newTheta = 0.0;
                    System.out.println("NEW X: " + newX);
                    System.out.println("NEW Y: " + newY);
                    System.out.println("NEW THETA: "+ runningTheta);
                    System.out.println();
                    writer.write(""+newX+","+newY+","+runningTheta+'\n');
                    relTheta = calcAngle(tanX, tanY);

                } else
                {
                    myReader.nextLine();
                    first = false;
                }
                   
            }
            myReader.close();
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        catch(IOException e) {
        	System.out.println("An error occurred.");
            e.printStackTrace();
        };

    }

}
