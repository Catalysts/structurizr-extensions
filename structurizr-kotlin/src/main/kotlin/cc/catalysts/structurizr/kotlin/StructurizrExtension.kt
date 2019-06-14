package cc.catalysts.structurizr.kotlin

import com.structurizr.model.*

/**
 * Adds a new [SoftwareSystem] to this [Model] and returns the new [SoftwareSystem]. Use the [init] function to add further [Container]s.
 */
fun Model.addSoftwareSystem(name: String, description: String, init: ElementConfiguration.() -> Unit): SoftwareSystem {
    return this.addSoftwareSystem(Location.Unspecified, name, description, init)
}

/**
 * Adds a new [SoftwareSystem] to this [Model] and returns the new [SoftwareSystem]. Use the [init] function to add further [Container]s.
 */
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

/**
 * Adds a new [Container] with the name [name] to this SoftwareSystem and returns new [Container]. Use the [init] function to add further [Component]s.
 */
fun SoftwareSystem.addContainer(name: String, description: String, vararg technologies: String, init: ElementConfiguration.() -> Unit): Container {
    val container = this.addContainer(name, description, technologies.joinToString())
    val config: ElementConfiguration = ElementConfiguration().apply(init)
    config.tags.forEach { t -> container.addTags(t) }
    config.uses.forEach({ d ->
        when (d.element) {
            is SoftwareSystem -> container.uses(d.element, d.description, d.technology, d.interactionStyle)
            is Container -> container.uses(d.element, d.description, d.technology, d.interactionStyle)
            is Component -> container.uses(d.element, d.description, d.technology, d.interactionStyle)
        }
    })
    config.usedBy.forEach({ d ->
        when (d.element) {
            is SoftwareSystem -> d.element.uses(container, d.description, d.technology, d.interactionStyle)
            is Container -> d.element.uses(container, d.description, d.technology, d.interactionStyle)
            is Component -> d.element.uses(container, d.description, d.technology, d.interactionStyle)
            is Person -> d.element.uses(container, d.description, d.technology, d.interactionStyle)
        }
    })
    return container
}

/**
 * Adds a new [Component] with the name [name] to this container and returns new [Component]
 */
fun Container.addComponent(name: String, description: String, vararg technologies: String, init: ElementConfiguration.() -> Unit): Component {
    val component = this.addComponent(name, description, technologies.joinToString())
    val config: ElementConfiguration = ElementConfiguration().apply(init)
    config.tags.forEach { t -> component.addTags(t) }
    config.uses.forEach({ d ->
        when (d.element) {
            is SoftwareSystem -> component.uses(d.element, d.description, d.technology, d.interactionStyle)
            is Container -> component.uses(d.element, d.description, d.technology, d.interactionStyle)
            is Component -> component.uses(d.element, d.description, d.technology, d.interactionStyle)
        }
    })
    config.usedBy.forEach({ d ->
        when (d.element) {
            is SoftwareSystem -> d.element.uses(component, d.description, d.technology, d.interactionStyle)
            is Container -> d.element.uses(component, d.description, d.technology, d.interactionStyle)
            is Component -> d.element.uses(component, d.description, d.technology, d.interactionStyle)
            is Person -> d.element.uses(component, d.description, d.technology, d.interactionStyle)
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

    fun uses(element: Element, description: String, vararg technologies: String, interactionStyle: InteractionStyle? = null) {
        this.uses.add(Dependency(element, description, concat(technologies), interactionStyle))
    }

    fun usedBy(element: Element, description: String, vararg technologies: String, interactionStyle: InteractionStyle? = null) {
        this.usedBy.add(Dependency(element, description, concat(technologies), interactionStyle))
    }

    private fun concat(values: Array<out String>): String {
        return values.joinToString()
    }
}

data class Dependency(val element: Element, val description: String, val technology: String, val interactionStyle: InteractionStyle? = null)