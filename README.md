# BonaresSubapp
Dies ist die App, die die Datenpakete von den ZALF Schnittstellen abholt,
nach Rosetta ingestet und sie anschließend acknowledged.

## Setup
* git clone https://github.com/DinoAGW/BonaresSubapp.git
* unter Eclipse: Import projects... -> Maven -> Existing Maven Projects
* benötigt: ~/Rosetta_Properties.txt
* benötigt: ~/SSH-Keys/TIB\ transferserver\ Linux
* `mvn install:install-file -Dfile=lib/dps-sdk-8.1.0.jar -DgroupId=com.exlibris -DartifactId=dps-sdk -Dversion=8.1.0 -Dpackaging=jar`
