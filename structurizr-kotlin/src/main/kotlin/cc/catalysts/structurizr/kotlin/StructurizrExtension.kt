package cc.catalysts.structurizr.kotlin

import com.structurizr.model.*

/**
 * @author Klaus Lehner, Catalysts GmbH
 */
fun Model.addSoftwareSystem(name: String, description: String, init: ElementConfiguration.() -> Unit): SoftwareSystem {
    return this.addSoftwareSystem(Location.Unspecified, name, description, init)
}

fun Model.addSoftwareSystem(location: Location = Location.Unspecified, name: String, description: String, init: ElementConfiguration.() -> Unit): SoftwareSystem {
    val softwareSystem = this.addSoftwareSystem(location, name, description);
    val config: ElementConfiguration = ElementConfiguration().apply(init)
    config.tags.forEach { t -> softwareSystem.addTags(t) }
    config.uses.forEach({ d ->
        when (d.element) {
            is SoftwareSystem -> softwareSystem.uses(d.element, d.description, d.technology)
            is Container -> softwareSystem.uses(d.element, d.description, d.technology)
            is Component -> softwareSystem.uses(d.element, d.description, d.technology)
        }
    })
    config.usedBy.forEach({ d ->
        when (d.element) {
            is SoftwareSystem -> d.element.uses(softwareSystem, d.description, d.technology)
            is Container -> d.element.uses(softwareSystem, d.description, d.technology)
            is Component -> d.element.uses(softwareSystem, d.description, d.technology)
            is Person -> d.element.uses(softwareSystem, d.description, d.technology)
        }
    })
    return softwareSystem;
}

fun SoftwareSystem.addContainer(name: String, description: String, vararg technologies: String, init: ElementConfiguration.() -> Unit): Container {
    val container = this.addContainer(name, description, technologies.joinToString())
    val config: ElementConfiguration = ElementConfiguration().apply(init)
    config.tags.forEach { t -> container.addTags(t) }
    config.uses.forEach({ d ->
        when (d.element) {
            is SoftwareSystem -> container.uses(d.element, d.description, d.technology)
            is Container -> container.uses(d.element, d.description, d.technology)
            is Component -> container.uses(d.element, d.description, d.technology)
        }
    })
    config.usedBy.forEach({ d ->
        when (d.element) {
            is SoftwareSystem -> d.element.uses(container, d.description, d.technology)
            is Container -> d.element.uses(container, d.description, d.technology)
            is Component -> d.element.uses(container, d.description, d.technology)
            is Person -> d.element.uses(container, d.description, d.technology)
        }
    })
    return container
}

fun Container.addComponent(name: String, description: String, vararg technologies: String, init: ElementConfiguration.() -> Unit): Component {
    val component = this.addComponent(name, description, technologies.joinToString())
    val config: ElementConfiguration = ElementConfiguration().apply(init)
    config.tags.forEach { t -> component.addTags(t) }
    config.uses.forEach({ d ->
        when (d.element) {
            is SoftwareSystem -> component.uses(d.element, d.description, d.technology)
            is Container -> component.uses(d.element, d.description, d.technology)
            is Component -> component.uses(d.element, d.description, d.technology)
        }
    })
    config.usedBy.forEach({ d ->
        when (d.element) {
            is SoftwareSystem -> d.element.uses(component, d.description, d.technology)
            is Container -> d.element.uses(component, d.description, d.technology)
            is Component -> d.element.uses(component, d.description, d.technology)
            is Person -> d.element.uses(component, d.description, d.technology)
        }
    })
    return component
}


class ElementConfiguration {
    var tags: MutableList<String> = ArrayList()
    var uses: MutableList<Dependency> = ArrayList()
    var usedBy: MutableList<Dependency> = ArrayList()

    fun withTags(vararg tag: String) {
        this.tags.addAll(tag)
    }

    fun uses(element: Element, description: String, vararg technologies: String) {
        this.uses.add(Dependency(element, description, concat(technologies)))
    }

    fun usedBy(element: Element, description: String, vararg technologies: String) {
        this.usedBy.add(Dependency(element, description, concat(technologies)))
    }

    private fun concat(values: Array<out String>): String {
        return values.joinToString()
    }
}

data class Dependency(val element: Element, val description: String, val technology: String)