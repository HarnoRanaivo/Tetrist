Tetrist
=======

T'es triste.
Tetrist, projet de POO2, L3S5 Informatique, Automne 2013, Université de Strasbourg.

Pré-requis
----------

- ```make```
- ```javac````

#### Ubuntu
```bash
$ apt-get install build-essential git openjdk-7-jdk
```

#### Fedora
```bash
$ yum groupinstall "Development Tools"
$ yum install git java-1.7.0-openjdk java-1.7.0-openjdk-devel java-1.7.0-javadoc
```

### Optionnel
Pour les tests, il vous faudra également [JUnit 4](http://junit.org).
Sur Fedora:
```bash
$ yum install junit
```

Récupérer Tetrist
-----------------
Pour récupérer le projet dans le dossier :
```bash
$ git clone https://github.com/remove/Tetrist.git
```

Pour récupérer les derniers changements, s'il y en a :
```bash
$ cd <Tetrist>
$ git pull
```

Compiler et lancer le programme
-------------------------------
```bash
$ make
$ ./tetrist
```

Branches
--------
Les versions stables se trouvent dans la branche ```master```. La branche ```dev``` contient la version de développement du projet.

License
-------
Copyright © 2013 MEYER Jérémy, RAZANAJATO Harenome

Ce projet est libre. Vous pouvez le redistribuer ou le modifier selon les termes de la license « Do What The Fuck You Want To Public License >, Version 2, comme publiée par Sam Hocevar. Pour de plus amples et extrêmement détaillées informations sur les tenants et aboutissants de cette license, veuillez vous référer au fichier COPYING, ou bien au site http://www.wtfpl.net/.
