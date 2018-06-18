package cc.catalysts.structurizr.model;

import cc.catalysts.structurizr.ViewProvider;
import com.structurizr.model.Container;
import com.structurizr.model.Location;
import com.structurizr.model.Model;
import com.structurizr.model.SoftwareSystem;
import com.structurizr.view.ContainerView;
import com.structurizr.view.ViewSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

/**
 * @author Klaus Lehner, Catalysts GmbH
 */
@Component
public class WebShop implements ViewProvider {

    private final SoftwareSystem webShop;

    @Autowired
    public WebShop(Model model, Personas personas, Sap sap) {
        webShop = model.addSoftwareSystem(Location.Internal, "WebShop", "Web-based application to buy our products");

        final Container webServer = webShop.addContainer("WebShop", "The web application of the web shop", "Tomcat");
        personas.getCustomer().uses(webServer, "Buys products", "Browser");
        personas.getAdmin().uses(webServer, "enters new products", "Browser");

        webServer.uses(sap.getSap(), "places orders", "WebService");
    }

    @Override
    public void createViews(@Nonnull ViewSet viewSet) {
        final ContainerView containerView = viewSet.createContainerView(webShop, "webshop", "WebShop");
        containerView.addAllElements();
    }
}
