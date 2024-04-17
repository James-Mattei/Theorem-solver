public class FailedToResolveException extends RuntimeException{
    /**
     *
     */

    //VS Code gave me this quick fix for some sort of warning
    private static final long serialVersionUID = 1L;

    FailedToResolveException() {
        super();
    }
    
    FailedToResolveException(String s){
        super(s);
    }
}