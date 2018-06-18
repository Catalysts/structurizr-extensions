package cc.catalysts.structurizr.styles;

import com.structurizr.Workspace;
import com.structurizr.model.Tags;
import com.structurizr.view.Shape;
import com.structurizr.view.Styles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Styles may be defined in a separate component
 *
 * @author Klaus Lehner, Catalysts GmbH
 */
@Component
public class StyleProvider {

    @Autowired
    public StyleProvider(Workspace workspace) {
        Styles styles = workspace.getViews().getConfiguration().getStyles();
        styles.addElementStyle(Tags.ELEMENT).width(500).height(350).color("#ffffff").fontSize(30).shape(Shape.RoundedBox);
        styles.addElementStyle(Tags.SOFTWARE_SYSTEM).background("#2A4E6E");
        styles.addElementStyle(Tags.CONTAINER).background("#007F0E");
        styles.addElementStyle(Tags.COMPONENT).background("#3059BA");
        styles.addElementStyle(Tags.PERSON).width(475).background("#728da5").shape(Shape.Person);
        styles.addRelationshipStyle(Tags.RELATIONSHIP).dashed(true).thickness(5).fontSize(40).width(400);
        styles.addRelationshipStyle(Tags.SYNCHRONOUS).dashed(false);
        styles.addRelationshipStyle(Tags.ASYNCHRONOUS).dashed(true);
    }
}
