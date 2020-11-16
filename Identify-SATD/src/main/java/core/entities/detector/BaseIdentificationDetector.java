package core.entities.detector;

import core.entities.Component;

import java.util.ArrayList;
import java.util.List;

public class BaseIdentificationDetector implements SATDDetector
{

    @Override
    public List<Component> detectSATD() throws ImpossibleIdentification
    {
        return new ArrayList<>();
    }

}
