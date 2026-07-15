# BonaresSubapp
Dies ist die App, die die Datenpakete von den ZALF Schnittstellen abholt,
nach Rosetta ingestet und sie anschließend acknowledged.

## Setup
* git clone https://github.com/DinoAGW/BonaresSubapp.git
* unter Eclipse: Import projects... -> Maven -> Existing Maven Projects
* Java: JavaSE-1.8
* benötigt: ~/Rosetta_Properties.txt
* benötigt: ~/SSH-Keys/TIB\ transferserver\ Linux
  * Dieser Key muss mit PuttyGen z.B. als openssh Key abgespeichert und den private Key bei TIB hinterlegt werden
* `mvn install:install-file -Dfile=lib/dps-sdk-7.3.0.jar -DgroupId=com.exlibris -DartifactId=dps-sdk -Dversion=7.3.0 -Dpackaging=jar -DgeneratePom=true`
* mkdir -p ~/workspace/BonaresSubapp/
* mittels `ssh-keyscan -t rsa transfer.lza.tib.eu` kann man den Key ermitteln, diesen dann unter ~/.ssh/known_hosts eintragen
