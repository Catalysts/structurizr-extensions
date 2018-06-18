package cc.catalysts.structurizr.model;

import com.structurizr.model.Location;
import com.structurizr.model.Model;
import com.structurizr.model.SoftwareSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Klaus Lehner, Catalysts GmbH
 */
@Component
public class Sap {

    private final SoftwareSystem sap;

    @Autowired
    public Sap(Model model) {
        sap = model.addSoftwareSystem(Location.External, "SAP", "");
    }

    public SoftwareSystem getSap() {
        return sap;
    }
}
