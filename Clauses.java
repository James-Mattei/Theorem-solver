import java.util.ArrayList;

public class Clauses extends ArrayList<Clause>
{
    ArrayList<Clause> clauses;

    public Clauses()
    {
        clauses = new ArrayList<Clause>();
    } 

    public void ClausePrint()
    {
        for(int i=0; i<this.clauses.size(); i++)
        {
            System.out.println(this.clauses.get(i));
        }
    }
    public void add(ArrayList<Clause> list)
    {
        for(int i= 0; i<list.size(); i++)
        {
            this.clauses.add(list.get(i));
        }
    }
}
