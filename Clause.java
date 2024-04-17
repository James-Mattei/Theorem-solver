import java.util.Scanner;
import java.util.Arrays;

public class Clause
{
    Node head;
    
    public Clause()
    {
        head = new Node();
    }
    public Clause(Clause c)
    {
        this.head = new Node(c.head);
    }

    public void addClause(Node bit)
    {
        Node temp = head;
        while(temp.right!=null)
        {
            temp=temp.right;
        }
        if(bit.val.equals("not"))
        {
            Node hold = new Node(bit);
            hold.right=null;
            //System.out.println("Not value is: ");
            //System.out.println(bit.left.val);
            temp.setRight(hold);
        }
        else{
            Node hold = new Node(bit.val);
            temp.setRight(hold);
        }
    }

    public void combineClause(Clause a, Clause b)
    {
        Node temp = b.head;

        while(temp.right!=null)
        {
            temp=temp.right;
            a.addClause(temp);
        }
        
    }

    private void removeDup()
    {
        Node temp = this.head;  
        while(temp.right!=null)
        {
            Node check = temp.right;
            if(temp.right.val.equals("not"))
            {
                while(check.right!=null)
                {
                    if(check.right.val.equals("not") && temp.right.left.val.equals(check.right.left.val))
                    {
                        check.setRight(check.right.right);
                    }
                    check=check.right;
                    if(check==null)
                        break;
                }
            }
            else
            {
                while(check.right!=null)
                {
                    if(check.right.val.equals(temp.right.val))
                    {
                        check.setRight(check.right.right);
                    }
                    check=check.right;
                    if(check==null)
                        break;
                }
            }
            temp=temp.right;
        }
    }

    public Clause resolve(Clause clause)
    {
        Clause copythis = new Clause(this);
        Clause copyclause = new Clause(clause);
        Node tempa = copythis.head;

        while(tempa.right!=null)
        {
            Node holdb = copyclause.head;

            if(tempa.right.val.equals("not"))
            {
                while(holdb.right!=null)
                {
                    if(holdb.right.val.equals(tempa.right.left.val))
                    {
                        tempa.setRight(tempa.right.right);
                        holdb.setRight(holdb.right.right);
                        copythis.combineClause(copythis, copyclause);
                        copythis.removeDup();
                        return copythis;
                    }
                    holdb=holdb.right;
                }
            }
            else
            {
                while(holdb.right!=null)
                {
                    if(holdb.right.val.equals("not"))
                    {
                        if(holdb.right.left.val.equals(tempa.right.val))
                        {
                            tempa.setRight(tempa.right.right);
                            holdb.setRight(holdb.right.right);
                            copythis.combineClause(copythis, copyclause);
                            copythis.removeDup();
                            return copythis;
                        }
                    }
                    holdb=holdb.right;
                }  
            }
            tempa=tempa.right;
        }//While tempa.right
        throw new FailedToResolveException();
    }//Resolve

    public void set(String clause)
    {
        String[] literals = clause.split(",");
        
        for(int i= 0; i<literals.length; i++)
        {
            String hold = literals[i];
            if(hold.charAt(0)!='{' && hold.charAt(0)!= '(')
            {
                if(hold.charAt(hold.length()-1)=='}')
                {
                    hold=hold.substring(0, hold.length()-1);
                    Node literal = new Node(hold);
                    this.addClause(literal);
                }
                else
                {
                    Node literal = new Node(hold);
                    this.addClause(literal);
                }
                
            }
            else if(hold.charAt(0)=='{')
            {
                if(hold.charAt(1)=='(')
                {
                    Node not = new Node("not");
                    if(hold.charAt(hold.length()-1)=='}')
                    {
                        hold = hold.substring(6, hold.length()-2);
                    }
                    else
                    {
                        hold = hold.substring(6, hold.length()-1);
                    }
                    Node literal = new Node(hold);
                    not.setLeft(literal);
                    this.addClause(not);
                    
                }
                else
                {
                    if(hold.charAt(hold.length()-1)=='}')
                        hold = hold.substring(1, hold.length()-1);
                    else
                        hold = hold.substring(1, hold.length());
                    Node literal = new Node(hold);
                    this.addClause(literal);
                }
            }
            else if(hold.charAt(0)=='(')
            {
                Node not = new Node("not");
                if(hold.charAt(hold.length()-1)=='}')
                {
                    hold=hold.substring(5, hold.length()-2);
                }
                else
                {
                    hold=hold.substring(5, hold.length()-1);
                }
                not.setLeft(new Node(hold));
                this.addClause(not);
            }
        }
    }


    public String toString()
    {
        String hold = "{";
        Node temp = head.right;
        if(temp==null)
            return"{}";
        while(temp!=null)
        {
            //System.out.println("We in the LOOP");
            //System.out.println(temp.val);
            if(temp.val.equals("not"))
            {
                //System.out.println("We in the not");
                hold += "(" + "not " +temp.left.val + "),";
                temp=temp.right;
            }
            else
            {
                //System.out.println("We in the regular");
                //System.out.println("value is: " + temp.val);
                hold += temp.val +",";
                temp = temp.right;
            }
        }
        
        hold = hold.substring(0, hold.length()-1);
        return hold+"}";
    }
    
}