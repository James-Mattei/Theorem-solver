import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Formula
{
    Node head;
    Clauses clauses;

    public String Tokenize(String wff)
    {
        String hold = "";
        //Took this from the Piazza post
        Scanner s = new Scanner( wff );
        s.useDelimiter( "(?:\\s+)|(?<=[()])|(?=[()])" );
        while ( s.hasNext() ) {
        hold += s.next(); 
        hold+= " ";
        } // while
        return hold;
    }
    public String GetNextToken(Scanner sc)
    {
        if(sc.hasNext())
            return sc.next();
        else
            return null;
    }
    public Node parse(Scanner sc)
    {
        String token = GetNextToken(sc);
        //System.out.println("Token is: " + token);
        Node node = new Node();
        switch(token)
        {
            case "(":
                node = parse(sc);
                break;  
            case "cond":
                node.setVal(token);
                node.setLeft(parse(sc));
                node.setRight(parse(sc));
                break;
            case "not":
                node.setVal(token);
                node.setLeft(parse(sc));
                break;
            case "or":
                node.setVal(token);
                node.setLeft(parse(sc));
                node.setRight(parse(sc));
                break;
            case "and":
                node.setVal(token); 
                node.setLeft(parse(sc));
                node.setRight(parse(sc));
                break; 
            case ")":
                node = parse(sc);
                break;
            default:
                node.setVal(token);
                break;
        }
        return node;

    }
    
    public Formula()
    {
       head = null; 
       clauses = new Clauses();
    }

    public void set(String s)
    { 
        String token = Tokenize(s);
        //System.out.println("Raw Formula is: " + token);
        Scanner sc = new Scanner (token);
        head = parse(sc);
    }
    public String toString()
    {
        return toString(head);
    }
    private String toString(Node head)
    {
        String hold = "";
        
        if(head==null)
            return "";

        if(head==this.head&& head.left==null && head.right==null)
            return "(" +head.val+")";
        hold = hold + head.val+ " ";

        if(head.left!= null || head.right != null)
        {
            hold = "(" + hold + toString(head.left);

            hold = hold + toString(head.right)+ ")";
        }
        return hold;
    }

    public boolean equals(Node a, Node b)
    {
        if(a==null && b==null)
        {
            return true;
        }
        //Took this syntax for recursive call from Techie Delight page about
        //Recursive tree comparison 
        //https://www.techiedelight.com/check-if-two-binary-trees-are-identical-not-iterative-recursive/

        return (a != null && b != null) && (a.val.equals(b.val)) &&
        equals(a.left, b.left) && equals(a.right, b.right);
 
    }
    public void cnf()
    {
        while(true)
        {
            Node compare = new Node(this.head);
            Rcnf(this.head);
            //System.out.println("After one iteration: "+ this);
            if(equals(compare, this.head))
            {
                break;
            }
                
        }
    }
    private void Rcnf(Node head)
    {
        if(this.head==head)
            removeCond(head);  
        moveNot(head);
        moveDisjun(head);
        if(head.left != null)
            Rcnf(head.left);
        if(head.right!= null)
            Rcnf(head.right);
    }

    private void removeCond(Node head)
    {
        
        if(head!=null)
        {
            if(head.val.equals("cond"))
            {
                Node not = new Node("not");
                not.setLeft(head.left);
                head.setLeft(not);
                head.setVal("or");
            }

        if(head.left!=null)
        {
        removeCond(head.left);
        }
        if(head.right!=null)
            removeCond(head.right);
        }
    }

    private void moveNot(Node head)
    {
        if(head==this.head)
        {
            if(this.head.val.equals("not")&&this.head.left.val.equals("not"))
                this.head=this.head.left.left;
        }
        if(head!=null)
        { 
            if(head.val.equals("not"))
            {
                if(head.left.val.equals("or"))
                {
                    Node notL = new Node("not");
                    Node notR = new Node("not");
                    notL.setLeft(head.left.left);
                    notR.setLeft(head.left.right);
                    head.setLeft(notL);
                    head.setRight(notR);
                    head.setVal("and");
                }
                if(head.left.val.equals("and"))
                {
                    Node notL = new Node("not");
                    Node notR = new Node("not");
                    notL.setLeft(head.left.left);
                    notR.setLeft(head.left.right);
                    head.setLeft(notL);
                    head.setRight(notR);
                    head.setVal("or");
                }
            }     
            if(head.left!=null)
            {
                if(head.left.left!=null)
                {
                    if(head.left.val.equals("not") && head.left.left.val.equals("not"))
                    {
                        head.left = head.left.left.left;
                    }
                }
            }         
            if(head.right!=null)
            {
                if(head.right.left != null)
                {
                    if(head.right.val.equals("not") && head.right.left.val.equals("not"))
                    {
                        head.right = head.right.left.left;
                    }
                }
            }
        }// if head != null
    }// Move Not

    private void moveDisjun(Node head)
    {
        if(head!=null)
        {
            if(head.val.equals("or"))
            {
                if(head.left.val.equals("or"))
                {
                    moveDisjun(head.left);
                }
                if(head.right.val.equals("or"))
                {
                    moveDisjun(head.right);
                }
                if(head.left.val.equals("and"))
                {
                    Node orL = new Node("or");
                    Node orR = new Node("or");

                    orL.setRight(new Node(head.right));
                    orR.setRight(new Node(head.right));
                    orL.setLeft(new Node(head.left.left));
                    orR.setLeft(new Node(head.left.right));

                    head.setVal("and");
                    head.setLeft(orL);
                    head.setRight(orR);
                }
            
                if(head.right.val.equals("and"))
                {
                    Node orL = new Node("or");
                    Node orR = new Node("or");

                    orL.setLeft(new Node(head.left));
                    orR.setLeft(new Node(head.left));
                    orL.setRight(new Node(head.right.left));
                    orR.setRight(new Node(head.right.right));

                    head.setVal("and");
                    head.setLeft(orL);
                    head.setRight(orR);
                }
            }
        }//head!=null
    }//move disjunction

    public Clauses getClauses()
    {
        this.cnf();
        makeClauses(this.head);
        return this.clauses;
    }
    private void makeClauses(Node head)
    {
        if(head!=null)
        {
            switch(head.val)
            {
                case "and":
                    makeClauses(head.left);
                    makeClauses(head.right);
                    break;
                case "or":
                    clauses.add(getClause(head));
                    break;
                default:
                    clauses.add(getClause(head));

            }
        }
        
    }
    
    public void negate()
    {
        Node not = new Node("not");
        not.setLeft(head);
        head=not;
    }

    private Clause getClause(Node head)
    {
        Clause hold = new Clause();
        if(head==null)
            return null;
        else
        {
            switch(head.val)
            {
                case "not":
                    hold.addClause(head);
                    break;
                case "or":
                    //System.out.println("LEFT CLAUSE IS");
                    //System.out.println(head.left.val);
                    hold.combineClause(hold, getClause(head.left));
                    //System.out.println("SHOULD BE SAME AS ABOVE");
                    //System.out.println(hold);
                    hold.combineClause(hold, getClause(head.right));
                    //System.out.println("HOLD IS: ");
                    //System.out.println(hold);
                    break;
                default:
                    hold.addClause(head);
                    break;
            }
            return hold;
        }
    }

} //class def


    
