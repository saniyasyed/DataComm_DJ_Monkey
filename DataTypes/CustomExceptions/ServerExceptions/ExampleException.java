package DataTypes.CustomExceptions.ServerExceptions;

public class ExampleException extends Exception {

    public ExampleException(String errorMessage, Throwable err)
    {
        super(errorMessage, err);
    }
}
