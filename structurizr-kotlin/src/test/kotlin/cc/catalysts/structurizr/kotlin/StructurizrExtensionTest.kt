package cc.catalysts.structurizr.kotlin

import com.structurizr.Workspace

/**
 * @author Klaus Lehner, Catalysts GmbH
 */
class StructurizrExtensionTest {

    fun createModel() {
        val workspace = Workspace("My Workspace", "")
        val model = workspace.model

        val softwareSystem = model.addSoftwareSystem("Software System 1", "") { withTags("Tag 1", "Tag2") }

        val container1 = softwareSystem.addContainer("Container 1", "") { withTags("Tag2") }

        val container2 = softwareSystem.addContainer("Container 2", "") {
            withTags("Tag2")
            uses(container1, "", "Soap")
        }

        val container3 = softwareSystem.addContainer("Container 3", "") {
            usedBy(container2, "", "Soap")
        }
    }
}