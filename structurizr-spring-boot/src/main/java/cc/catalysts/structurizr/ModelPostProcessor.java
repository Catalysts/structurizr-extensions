package cc.catalysts.structurizr;

import com.structurizr.model.Model;

import javax.annotation.Nonnull;

/**
 * Implementors of this interface will be called after all spring components have been initialized but before all implicit
 * relationships are created and before all views are generated
 *
 * @author Klaus Lehner, Catalysts GmbH
 */
public interface ModelPostProcessor {

    /**
     * Do some action on the {@link Model} after the spring context has been initialized
     *
     * @param model
     */
    void postProcess(@Nonnull Model model);
}
