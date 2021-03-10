package cc.catalysts.structurizr.service;

import cc.catalysts.structurizr.ModelPostProcessor;
import cc.catalysts.structurizr.ViewProvider;
import cc.catalysts.structurizr.config.StructurizrProperties;
import com.structurizr.Workspace;
import com.structurizr.api.StructurizrClient;
import com.structurizr.api.StructurizrClientException;
import com.structurizr.model.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static cc.catalysts.structurizr.service.StructurizrService.ORDER;

/**
 * @author Klaus Lehner, Catalysts GmbH
 */
@Order(ORDER)
public class StructurizrService implements CommandLineRunner {

    public static final int ORDER = 0;

    private static final Logger LOG = LoggerFactory.getLogger(StructurizrService.class);

    private final StructurizrClient structurizrClient;
    private final Workspace workspace;
    private final StructurizrProperties properties;
    private final ApplicationContext applicationContext;

    public StructurizrService(StructurizrClient structurizrClient, Workspace workspace, StructurizrProperties properties, ApplicationContext applicationContext) {
        this.structurizrClient = structurizrClient;
        this.workspace = workspace;
        this.properties = properties;
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(String... args) {
        List<ModelPostProcessor> postProcessors = new ArrayList<>(applicationContext
                .getBeansOfType(ModelPostProcessor.class).values());

        AnnotationAwareOrderComparator.sort(postProcessors);

        for (ModelPostProcessor postProcessor : postProcessors) {
            postProcessor.postProcess(workspace.getModel());
        }

        if (properties.isAddImplicitRelationships()) {
            final Set<Relationship> relationships = workspace.getModel().addImplicitRelationships();
            LOG.info("Added {} implicit relationships.", relationships.size());
        }

        applicationContext
                .getBeansOfType(ViewProvider.class)
                .values()
                .forEach(vp -> vp.createViews(workspace.getViews()));


        if (properties.isPerformMerge()) {
            try {
                structurizrClient.putWorkspace(properties.getWorkspace().getId(), workspace);
            } catch (StructurizrClientException e) {
                LOG.error("Could not put workspace.", e);
                throw new RuntimeException(e);
            }
        }

    }
}
