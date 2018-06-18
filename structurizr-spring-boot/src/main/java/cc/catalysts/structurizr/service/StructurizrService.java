package cc.catalysts.structurizr.service;

import cc.catalysts.structurizr.ModelPostProcessor;
import cc.catalysts.structurizr.ViewProvider;
import cc.catalysts.structurizr.config.StructurizrConfigurationProperties;
import com.structurizr.Workspace;
import com.structurizr.api.StructurizrClient;
import com.structurizr.api.StructurizrClientException;
import com.structurizr.model.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static cc.catalysts.structurizr.service.StructurizrService.ORDER;

/**
 * @author Klaus Lehner, Catalysts GmbH
 */
@Service
@Order(ORDER)
public class StructurizrService implements ApplicationListener<ContextRefreshedEvent> {

    public static final int ORDER = 0;

    private static final Logger LOG = LoggerFactory.getLogger(StructurizrService.class);

    private final StructurizrClient structurizrClient;
    private final Workspace workspace;
    private final StructurizrConfigurationProperties config;

    @Autowired
    public StructurizrService(StructurizrClient structurizrClient, Workspace workspace, StructurizrConfigurationProperties config) {
        this.structurizrClient = structurizrClient;
        this.workspace = workspace;
        this.config = config;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<ModelPostProcessor> postProcessors = event.getApplicationContext()
                .getBeansOfType(ModelPostProcessor.class).values().stream().collect(Collectors.toList());

        AnnotationAwareOrderComparator.sort(postProcessors);

        for (ModelPostProcessor postProcessor : postProcessors) {
            postProcessor.postProcess(workspace.getModel());
        }

        if (config.isAddImplicitRelationships()) {
            final Set<Relationship> relationships = workspace.getModel().addImplicitRelationships();
            LOG.info("Added {} implicit relationships.", relationships.size());
        }

        event.getApplicationContext()
                .getBeansOfType(ViewProvider.class)
                .values().stream()
                .forEach(vp -> vp.createViews(workspace.getViews()));


        if (config.isPerformMerge()) {
            try {
                structurizrClient.putWorkspace(config.getWorkspaceId(), workspace);
            } catch (StructurizrClientException e) {
                LOG.error("Could not put workspace.", e);
                throw new RuntimeException(e);
            }
        }
    }
}
