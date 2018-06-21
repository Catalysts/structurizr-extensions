# Structurizr Extensions for Spring Boot

This small module combines two of the greatest frameworks that have been provided to the Java community:
[Spring Boot](http://projects.spring.io/spring-boot/) and [Structurizr](https://structurizr.com/). 

While Spring Boot will be well-known by most of you, Structurizr isn't yet that popular, but in our opinion
it's just a matter time to change that.

> "Structurizr is a collection of tooling to help you visualise, document and explore your software architecture."

With Structurizr you can create a model made of [Java code](https://structurizr.com/help/tutorials/java) to describe
your architecture. The [example code snippets](https://github.com/structurizr/java-starter/blob/master/src/main/java/com/mycompany/mysystem/Structurizr.java) look something like that:


```java
Workspace workspace = new Workspace("My model", "This is a model of my software system.");
Model model = workspace.getModel();
ViewSet viewSet = workspace.getViews();
Styles styles = viewSet.getConfiguration().getStyles();

// add some elements to your software architecture model
Person user = model.addPerson("User", "A user of my software system.");
SoftwareSystem softwareSystem = model.addSoftwareSystem("Software System", "My software system.");
user.uses(softwareSystem, "Uses");

// define some views (the diagrams you would like to see)
SystemContextView contextView = viewSet.createSystemContextView(softwareSystem, "Context", "A description of this diagram.");
contextView.addAllSoftwareSystems();
contextView.addAllPeople();
contextView.setPaperSize(PaperSize.A5_Landscape);

// optionally, add some styling
styles.addElementStyle(Tags.SOFTWARE_SYSTEM).background("#1168bd").color("#ffffff");
styles.addElementStyle(Tags.PERSON).background("#08427b").color("#ffffff");
```

While this is fine for smaller projects, this kind of code quickly gets hard to maintain when the software systems
you want to describe are getting bigger. And has we're having dozens of big software projects (some of them running with more than 
1MLoC), we thought of a way to modularize that kind of code.

This is when Spring Boot came into the game, utilizing it's Dependency-Management. It's as easy as that:

## How it works

Create a new Java project and embed the library

With Gradle that would look like that:

```groovy
apply plugin: 'java'

sourceCompatibility = 1.8

repositories {
    jcenter()
}

dependencies {
    compile 'cc.catalysts.structurizr:structurizr-spring-boot:1.0.0'
    compile "org.springframework.boot:spring-boot-starter:2.0.3.RELEASE"
}
```

Now create your main class, which is a simple SpringBootApplication:

```java
@SpringBootApplication
public class Architecture {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Architecture.class, args);
    }

}
```

No more imports required here, CatBootStructurizr will be picked up automatically by Spring Boot's AutoConfiguration-mechanism.

Configuration of your structurizr workspace is done in ``application.yaml``:

```yaml
structurizr:
  workspace:
    id: 26741
    name: Cat-Boot-Structurizr 
    description: This is a sample workspace demonstrating cat-boot-structurizr
    key: e21bee9d-e5af-4153-bbf4-d44aa3a8e6bd 
    secret: secret
```

That's all you need to do to create an empty model and to push it to Structurizr.

To add elements to your Structurizr-Model now create Java classes, mark them as Spring Components, let the Structurizr Model
be injected and create elements inside the constructur of your classes:

```java
@Component
public class Personas {

    private final Person admin;
    private final Person customer;

    @Autowired
    public Personas(Model model) {
        admin = model.addPerson(Location.Internal, "Admin", "the administrator of our system");
        admin.addTags(Tags.PERSON);
        customer = model.addPerson(Location.External, "Customer", "a customer who wants to buy items in our shop");
        customer.addTags(Tags.PERSON);
    }

    public Person getAdmin() {
        return admin;
    }

    public Person getCustomer() {
        return customer;
    }
}
```

In our case you have a Java class here where you encapsulate the creation of all personas of your software system.
The big advantage compared to the ``Person`` objects from Structurizr here is, that this is typesafe, and you can inject
the class ``Personas`` in to other parts of the model:

```java
@Component
public class WebShop {

    private final SoftwareSystem webShop;

    @Autowired
    public WebShop(Model model, Personas personas) {
        webShop = model.addSoftwareSystem(Location.Internal, "WebShop", "Web-based application to buy our products");

        final Container webServer = webShop.addContainer("WebShop", "The web application of the web shop", "Tomcat");
        personas.getCustomer().uses(webServer, "Buys products", "Browser");
        personas.getAdmin().uses(webServer, "enters new products", "Browser");
    }

}
```

That way you can divide your architecture model into several classes, divide it by persons, software systems, containers, or whatever
seems suitable.

Now there's one important part missing, the creation of Structurizr Views.

We found out that it's important to create Views only after the whole model is initialized, that is way we created the 
interface ``ViewProvider`` which is called by Cat Boot Structurizr after all java classes have been initialized (i.e. model
construction is complete).

If you want to create Views for your WebShop, simple implement that interface and its method ``createViews(ViewSet)``:

```java
@Component
public class WebShop implements ViewProvider {

    private final SoftwareSystem webShop;

    @Autowired
    public WebShop(Model model, Personas personas, Sap sap) {
        webShop = model.addSoftwareSystem(Location.Internal, "WebShop", "Web-based application to buy our products");

        final Container webServer = webShop.addContainer("WebShop", "The web application of the web shop", "Tomcat");
        personas.getCustomer().uses(webServer, "Buys products", "Browser");
        personas.getAdmin().uses(webServer, "enters new products", "Browser");

        webServer.uses(sap.getSap(), "places orders", "WebService");
    }

    @Override
    public void createViews(ViewSet viewSet) {
        final ContainerView containerView = viewSet.createContainerView(webShop, "webshop", "WebShop");
        containerView.addAllElements();
    }
}
```

That's all you need to do. You can find the full example code under [https://github.com/Catalysts/cat-boot-structurizr-sample](https://github.com/Catalysts/cat-boot-structurizr-sample).  

The corresponding workspace of the sample code is public on Structurizr.com and can be viewed at [https://structurizr.com/share/39399](https://structurizr.com/share/39399)