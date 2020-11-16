package core.usecases.identifySATD;

import core.entities.Component;
import core.entities.detector.BaseIdentificationDetector;

import java.util.Collection;

public class IdentifySATDInteractor
{

    public Collection<Component> execute()
    {
        BaseIdentificationDetector baseIdentificationDetector = new BaseIdentificationDetector();

        return baseIdentificationDetector.detectSATD();
    }

}
