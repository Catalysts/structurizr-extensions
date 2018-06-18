package cc.catalysts.structurizr.model;

import com.structurizr.model.Location;
import com.structurizr.model.Model;
import com.structurizr.model.Person;
import com.structurizr.model.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Klaus Lehner, Catalysts GmbH
 */
@Component
public class Personas {

    private final Person admin;
    private final Person customer;

    @Autowired
    public Personas(Model model) {
        admin = model.addPerson(Location.Internal, "Admin", "the administrator of our system");
        admin.addTags(Tags.PERSON);
        customer = model.addPerson(Location.External, "Customer", "a customer who wants to buy items in our shop");
        customer.addTags(Tags.PERSON);
    }

    public Person getAdmin() {
        return admin;
    }

    public Person getCustomer() {
        return customer;
    }
}
