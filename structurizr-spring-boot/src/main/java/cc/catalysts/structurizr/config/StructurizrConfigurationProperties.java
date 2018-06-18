package cc.catalysts.structurizr.config;

import com.structurizr.Workspace;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;

/**
 * @author Klaus Lehner, Catalysts GmbH
 */
@ConfigurationProperties("structurizr")
public class StructurizrConfigurationProperties {

    /**
     * The URL of Structurizr
     */
    private String url = "https://api.structurizr.com";

    /**
     * The ID of your workspace in Structurizr
     */
    private long workspaceId;

    /**
     * The API key of your workspace in Structurizr
     */
    private String key;

    /**
     * The API secret of your workspace in Structurizr
     */
    private String secret;

    /**
     * The name of your workspace
     */
    private String name;

    /**
     * The description of your workspace
     */
    private String description;

    /**
     * Set this property to a directory on your drive to archive your workspaces
     */
    private File workspaceArchiveLocation = null;

    /**
     * If true, the method {@link Workspace#getModel()#addImplicitRelationships} will be called after the model has been initialized
     */
    private boolean addImplicitRelationships = true;

    /**
     * If this property is true, the workspace will be put to the structurizr server after the model has been fully initialized
     */
    private boolean performMerge = true;

    public long getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(long workspaceId) {
        this.workspaceId = workspaceId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public File getWorkspaceArchiveLocation() {
        return workspaceArchiveLocation;
    }

    public void setWorkspaceArchiveLocation(File workspaceArchiveLocation) {
        this.workspaceArchiveLocation = workspaceArchiveLocation;
    }

    public boolean isPerformMerge() {
        return performMerge;
    }

    public void setPerformMerge(boolean performMerge) {
        this.performMerge = performMerge;
    }

    public boolean isAddImplicitRelationships() {
        return addImplicitRelationships;
    }

    public void setAddImplicitRelationships(boolean addImplicitRelationships) {
        this.addImplicitRelationships = addImplicitRelationships;
    }
}
