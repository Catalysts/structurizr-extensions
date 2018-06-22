package cc.catalysts.structurizr;

import com.structurizr.view.ViewSet;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Nonnull;

/**
 * <p>This is the main class of the architecture application. It starts as a {@link SpringBootApplication}, thus
 * also automatically configuring {@link cc.catalysts.structurizr.config.StructurizrAutoConfiguration}.</p>
 *
 * <p>The properties are taken from <code>application.properties</code>.</p>
 *
 * <p>All {@link org.springframework.stereotype.Component}s in this package and in subpackaged are detected, they
 * build the Structurizr {@link com.structurizr.model.Model}.</p>
 *
 * <p>This class also serves as a {@link ViewProvider} and created the {@link com.structurizr.view.SystemLandscapeView}
 * or our model.</p>
 *
 * @author Klaus Lehner, Catalysts GmbH
 */
@SpringBootApplication
public class Architecture implements ViewProvider {

    @Override
    public void createViews(@Nonnull ViewSet viewSet) {
        viewSet.createSystemLandscapeView("enterprise", "Enterprise Diagram").addAllElements();
    }
}
