package core.entities.detector;

public class ImpossibleDetection extends RuntimeException
{

    public ImpossibleDetection()
    {
        this("There was an error. Impossible to pursue the detection.");
    }


    public ImpossibleDetection(String message)
    {
        super(message);
    }

}
