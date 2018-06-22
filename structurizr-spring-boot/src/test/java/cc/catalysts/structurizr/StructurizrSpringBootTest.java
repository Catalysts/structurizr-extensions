package cc.catalysts.structurizr;

import com.structurizr.Workspace;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * @author Klaus Lehner, Catalysts GmbH
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Architecture.class)
class StructurizrSpringBootTest {

    @Autowired
    Workspace workspace;

    @Test
    void workspaceHasViews() {
        assertFalse(workspace.getViews().isEmpty(), "there must be views in the model");
    }
}
