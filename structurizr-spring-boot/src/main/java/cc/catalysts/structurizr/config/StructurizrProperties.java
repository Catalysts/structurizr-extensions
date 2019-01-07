package cc.catalysts.structurizr.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;

/**
 * Configuration properties for Structurizr.
 *
 * @author Klaus Lehner, Catalysts GmbH
 */
@ConfigurationProperties("structurizr")
public class StructurizrProperties {

    /**
     * The URL of Structurizr.
     */
    private String url = "https://api.structurizr.com";

    /**
     * Object containing all properties of your workspace at Structurizr
     */
    private Workspace workspace;

    /**
     * Directory to use to archive workspaces.
     */
    private File workspaceArchiveLocation;

    /**
     * Propagates all relationships from children to their parents.
     */
    private boolean addImplicitRelationships = true;


    /**
     * Whether to put the workspace to the structurizr server after the model has been fully initialized.
     */
    private boolean performMerge = true;


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

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public static class Workspace {
        /**
         * Workspace identifier.
         */
        private long id;

        /**
         * Workspace API key.
         */
        private String key;

        /**
         * Workspace API secret.
         */
        private String secret;

        /**
         * Workspace name.
         */
        private String name;

        /**
         * Workspace description.
         */
        private String description;

        /**
         * Workspace client-side encryption passphrase
         */
        private String encryptionPassphrase;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
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

        public String getEncryptionPassphrase() {
            return encryptionPassphrase;
        }

        public void setEncryptionPassphrase(String encryptionPassphrase) {
            this.encryptionPassphrase = encryptionPassphrase;
        }
    }
}
