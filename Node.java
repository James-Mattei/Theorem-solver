public class Node
{
    String val;
    Node left;
    Node right;

    public void setLeft(Node child)
    {
        this.left = child;
    }
    public void setRight(Node child)
    {
        this.right = child;
    }
    
    public void setVal(String s)
    {
        this.val = s;
    }

    public Node(String s)
    {
        this.val = s;
        this.left = null;
        this.right = null;
    }
    public Node()
    {
        this.val = "";
        this.left = null;
        this.right = null;
    }
    public Node(Node copy)
    {
        this.val = copy.val;
        if(copy.left!=null)
            this.left = new Node(copy.left);
        else   
            this.left=null;
        if(copy.right!=null)
            this.right = new Node(copy.right);
        else
            this.right=null;
    }
    public void toNull()
    {
        this.left=null;
        this.right=null;
    }
    public String getVal()
    {
        return this.val;
    }
}