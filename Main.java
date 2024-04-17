import java.util.ArrayList;
import java.util.Scanner;

public class Main{

    public static void main(String[] args) 
    {
        if(Resolution.prove(args[0]))
        {
            System.out.println("WE HAVE RESOLVED OUR PROOF");
        }
        else{
            System.out.println("WE COULD NOT RESOLVE THE PROOF");
        }
    }
        
} //main}
