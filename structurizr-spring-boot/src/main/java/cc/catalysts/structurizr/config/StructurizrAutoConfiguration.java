package cc.catalysts.structurizr.config;

import cc.catalysts.structurizr.service.StructurizrService;
import com.structurizr.Workspace;
import com.structurizr.api.StructurizrClient;
import com.structurizr.model.Model;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Klaus Lehner, Catalysts GmbH
 */
@Configuration
@ComponentScan(basePackageClasses = StructurizrService.class)
@EnableConfigurationProperties(value = {StructurizrProperties.class})
public class StructurizrAutoConfiguration {

    @Bean
    public StructurizrService structurizrService(StructurizrClient structurizrClient, Workspace workspace,
                                                 StructurizrProperties properties) {
         return new StructurizrService(structurizrClient, workspace, properties);
    }

    @Bean
    public Model model(Workspace workspace) {
        return workspace.getModel();
    }

    @Bean
    public Workspace workspace(StructurizrProperties config) {
        return new Workspace(config.getName(), config.getDescription());
    }

    @Bean
    public StructurizrClient structurizrClient(StructurizrProperties config) {
        StructurizrClient structurizrClient = new StructurizrClient(config.getUrl(), config.getKey(), config.getSecret());
        structurizrClient.setWorkspaceArchiveLocation(config.getWorkspaceArchiveLocation());
        return structurizrClient;
    }

}
