# Structurizr Extensions for Spring Boot

You love Structurizr? You love Kotlin? Then this is your place. This tiny extension for [Structurizr](https://structurizr.com/)
lets you create your models in a fluent way:

In Java, you write code like that:

```java
Workspace workspace = new Workspace("My Workspace", "")
Model model = workspace.getModel()

SoftwareSystem softwareSystem = model.addSoftwareSystem("Software System 1", "");
softwareSystem.addTags(Tag 1", "Tag2")

Container container1 = softwareSystem.addContainer("Container 1", "");
container1.addTags("Tag2")

Container container2 = softwareSystem.addContainer("Container 2", "");
container2.addTags("Tag2")
container2.uses(container1, "", "Soap")

Container container3 = softwareSystem.addContainer("Container 3", "");
container2.uses(container3, "", "Soap")
```

To do that with Kotlin, just add the dependency with Maven or Gradle:

```groovy
dependencies {
    compile 'cc.catalysts.structurizr:structurizr-spring-kotlin:1.0.0'
}
``` 

This module adds extension functions to some Structurizr classes, you can then initialize your software systems and containers in a fluent way:

```kotlin
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
``` 

That means, you can add tags and dependencies in both directions for SoftwareSystem, Containers and Components within one single statement. 
