# cities  

A program to determine if two cities are connected, based on an arbitrary input file, src/main/resources/city.txt

## Software used  

* Java 8
* Spring Boot 2.0.1
* JaCoCo 0.7.9.2
* JUnit 4

## Setup Requirements

As with any Spring Boot project, the software is bundled with an application server (Apache Tomcat) and can be run as a jar, or from an IDE of your choosing.

Make sure you have JDK 8 installed and in your classpath prior to running/building this software.

## Build & Run

Assuming you've cloned this repo and you've set up your dev environment, navigate to the space in the file directory where this repo is.

Run `./gradlew build && java -jar build/libs/cities-1.0-SNAPSHOT.jar`

## Running unit tests

Run `./gradlew test` to execute the unit tests via JUnit.

## Running code coverage

Run `./gradlew test jacocoTestReport` to execute code coverage report generation via JaCoCo.

## Assumptions

* The problem statement is a disjointed set problem, which I solved using the Union Find algorithm. I used an implementation of Union Find by Sedgewick and Wayne:
https://algs4.cs.princeton.edu/15uf/WeightedQuickUnionUF.java.html

* The algorithm implemented assumes that a vertex is connected to itself, and a vertex with no incident edges is itself a connected component, and so if the origin and the destination were the same city (i.e Toronto) the program would return true.

* The origin and destination inputs are valid city names (i.e Washington D.C, W4sh1ngt0n D.C, etc.,)

## Extra Credit

* Code coverage of 93%
* Searching in log (n) time for a given city