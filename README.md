# Pebble mini game

Placed 16 pebbles on the fields of a board consisting of 4 x 4 fields, one on each field.
Two players can alternately take pebbles from the board, at least 1 and at most 4 pieces.
You can only remove pebbles that are in a row and/or a column and there are no empty spaces between them.
The last person to move loses.


## 1. Functional requirements
• Includes functionality (business logic) that can be tested, and unit tests had been prepared for it.
• Has a meaningful graphical user interface, the implementation of which had been done using JavaFX.
• The implementation is the model-view-controller (MVC) architectural pattern according to which it acts.
• Data storage in XML, it runs on JDOM.
    • Manages both input and output of the program.

## 2. Java-specific requirements
• Uses JDK 17

## 3. Apache Maven-specific requirements
    In the pom.xml file, the following elements are mandatory and corresponding
substitute:
• description
• developers (with at least the developer's name and email address)
• properties/project.build.sourceEncoding
• (properties/maven.compiler.source and properties/maven.compiler.target)
or properties/maven.compiler.release
• properties/exec.mainClass

    For the presentation of the project website, the following extensions are used:
• Maven Javadoc Plugin
• Maven JXR Plugin
• Maven Checkstyle Plugin1
• Maven Surefire Report Plugin
• JaCoCo Maven Plugin
• Checkstyle extension used exclusively for checking the API documentation.

## 4. Portability
• The project is translatable and functional in an environment where the JDK and the appropriate version of Apache Maven are available. 
    For example, the command line execution of the mvn site command in the project directory in the Linux, macOS and Windows environments also displays the website.

• Only US-ASCII characters can be used in usernames.

## 5. Documentary
• The documentation must be checked with the Checkstyle extension.
• A package-info.java file must be placed in each package.

    It is not necessary to document:
• the unit tests,
• classes of the graphical user interface,
• the main(String[] args) method,
• non-publicly visible members.

## 6. Logging
• Logging is done with the tynylog 2 program library.

## 7. Unit tests
• For classes providing the main functions of the program in the JUnit 5 framework unit tests had been prepared.
• The coverage of the tests reported using the JaCoCo Maven Plugin so that the objective can be checked in an objective way.

## 8. Resource management
• Resorces can only be accessed during class transfer.
    For example, the following is prohibited:
        File file = new File("src/main/resources/file.txt");

## 9. JAR file
• During the execution of the package life cycle phase, an executable JAR containing the dependencies had been created with the help of the Maven Shade Plugin.
• Running the JAR results in the application starting.