## Overview

By default the test runs using the embeeded driver. If you want to browse the store trough the UI, use the http driver instead.
### Running Neo4j in a Docker Container
 
 ```
 docker run -p 7474:7474 -p 7687:7687 -v $HOME/Development/mount/neo4j -e NEO4J_AUTH=none neo4j
 ```
 
### Using the Http Driver
 
Replace the properties in: 

_/test/resources/application-test.properties_ 

with the following key values:

```
 wfg.neo4j.driver-classname=org.neo4j.ogm.drivers.http.driver.HttpDriver
 wfg.neo4j.uri=http://localhost:7474
```