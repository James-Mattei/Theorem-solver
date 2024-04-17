import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

public class Resolution{

    public static boolean prove(String filename)
    {
        Clauses master = new Clauses();
        try{
            File file = new File(filename);
            Scanner sc = new Scanner(file);
            String hold = "";
            while (sc.hasNextLine())
            {
                Formula f = new Formula();
                hold=sc.nextLine();
                if(hold.length()==0)
                {

                }
                else if(hold.charAt(0)=='(')
                {
                    f.set(hold);

                    System.out.println("Formula is: " + f);

                    if(!sc.hasNextLine())
                    {
                        f.negate();
                    }
                    
                    f.getClauses();
                    master.add(f.clauses);
                }
            }
        }//try for file
        catch(FileNotFoundException e)
        {
            System.out.println(e.getStackTrace());
        }
        System.out.println("Printing Master Clause list:");
        master.ClausePrint();
        //Gucci up to here


        Boolean proved = false;
        int i = 1;
        while(i<master.clauses.size()&&!proved&& i<70)
        {

            Clause comp1 = master.clauses.get(i);
            for(int j = 0; j<i; j++)
            {
                try
                {
                    Clause comp2 = master.clauses.get(j);
                    //System.out.println("About to Resolve");
                    //System.out.println(comp1);
                    //System.out.println(comp2);
                    Clause resolvent = comp1.resolve(comp2);
                    if(resolvent.toString().equals("{}"))
                    {
                        proved = true;
                        break;
                    }
                    else
                    {
                        System.out.println("Adding new Resolvent: " + resolvent);
                        master.clauses.add(resolvent);
                    }
                }
                catch(FailedToResolveException e)
                {
                    //System.out.println("Could not resolve");
                }
            }
            i+=1;
        }//While
        return proved;
    }
}