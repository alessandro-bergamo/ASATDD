package core.entities.detector;

public class ImpossibleIdentification extends RuntimeException
{

    public ImpossibleIdentification()
    {
        this("There was an error. Impossible to pursue the identification.");
    }


    public ImpossibleIdentification(String message)
    {
        super(message);
    }

}
