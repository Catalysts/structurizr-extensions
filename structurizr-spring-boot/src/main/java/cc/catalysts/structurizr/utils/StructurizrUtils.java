package cc.catalysts.structurizr.utils;

import com.structurizr.view.ContainerView;

import javax.annotation.Nonnull;

/**
 * @author Klaus Lehner, Catalysts GmbH
 */
public class StructurizrUtils {

    /**
     * <p>Adds all {@link com.structurizr.model.Container}s of the given {@link ContainerView} as well as all external influencers, that is all
     * persons and all other software systems with incoming or outgoing dependencies.</p>
     * <p>Additionally, all relationships of external dependencies are omitted to keep the diagram clean</p>
     *
     * @param containerView
     * @deprecated use {@link ContainerView#addAllInfluencers()}
     */
    public static void addAllInfluencers(@Nonnull ContainerView containerView) {
        containerView.addAllInfluencers();
    }

    /**
     * <p>Adds all {@link com.structurizr.model.Container}s of the given {@link ContainerView} as well as all external influencers, that is all
     * persons and all other software systems with incoming or outgoing dependencies.</p>
     * <p>Additionally, all relationships of external dependencies are omitted to keep the diagram clean</p>
     *
     * @param containerView
     * @deprecated use {@link ContainerView#addAllContainersAndInfluencers()}
     */
    public static void addAllContainersAndInfluencers(@Nonnull ContainerView containerView) {
        containerView.addAllContainersAndInfluencers();
    }
}