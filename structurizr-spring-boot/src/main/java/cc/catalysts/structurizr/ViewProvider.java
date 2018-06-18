package cc.catalysts.structurizr;

import com.structurizr.view.ViewSet;

import javax.annotation.Nonnull;

/**
 * Implement this interface if you want to create {@link com.structurizr.view.View}s for your {@link com.structurizr.model.Model}.
 * The reason why this is done in a separate method is to ensure that all {@link org.springframework.stereotype.Component}s which
 * might manipulate the model during startup phase have been initialized and that the {@link com.structurizr.model.Model} is complete.
 *
 * @author Klaus Lehner, Catalysts GmbH
 */
public interface ViewProvider {

    /**
     * <p>This method is called automatically after the whole {@link org.springframework.context.ApplicationContext} is refreshed,
     * thus the {@link com.structurizr.model.Model} definition should be complete.</p>
     * <p>Implementations of this method might create one or more {@link com.structurizr.view.View} inside this method</p>
     *
     * @param viewSet the {@link ViewSet} of the {@link com.structurizr.Workspace} after the whole {@link com.structurizr.model.Model}
     *                has been initialized
     */
    void createViews(@Nonnull ViewSet viewSet);
}
