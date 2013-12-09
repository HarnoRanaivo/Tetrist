Tetrist
=======

T'es triste.

```Tetrist``` est un jeu de tétris développé dans le cadre de l'Unité d'enseignement Programmation Orientée Objet 2 du Semestre 5, Licence 3 d'informatique de l'Université de Strasbourg.

### Conventions de noms
Le nom « Tetrist » est un calembour. Nul besoin de s'étendre là-dessus. Avec ```Tetrist``` vient avec une humble tentative d'élaborer une intelligence artificielle capable de jouer à ```Tetrist```. Celle-ci est sobrement nommée ```Marvin```. Le nom « Marvin » vient quant à lui du nom du robot dans « Le guide du voyageur galactique ». Ce robot étant quelque peu dépréssif, il nous paraissait tout à fait approprié de nommer ainsi une intelligence artificielle jouant à un jeu triste.

Pré-requis
----------

- ```make```
- ```javac```

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
Pour récupérer le projet :
```bash
$ git clone https://github.com/remove/Tetrist.git
```

Pour récupérer les derniers changements, s'il y en a :
```bash
$ git pull
```

Compiler et lancer le programme
-------------------------------
Premièrement, il est nécessaire de compiler le projet :
```bash
$ cd Tetrist
$ make
```
Ensuite, pour jouer manuellement à ```Tetrist``` :
```bash
$ ./tetrist
```
Pour lancer l'intelligence artificielle ```Marvin``` :
```bash
$ ./marvin
```

Pour lancer les tests (qui sont très loin d'être exhaustifs et complets) :
```bash
$ cd Tetrist
$ make tests
$ ./runtests
```


Branches
--------
Les versions stables se trouvent dans la branche ```master```. La branche ```dev``` contient la version de développement du projet.

License
-------
Copyright © 2013 MEYER Jérémy, RAZANAJATO Harenome
Tetrist, projet de POO2, L3S5 Informatique, Automne 2013, Université de Strasbourg.

Ce projet est libre. Vous pouvez le redistribuer ou le modifier selon les termes de la license « Do What The Fuck You Want To Public License >, Version 2, comme publiée par Sam Hocevar. Pour de plus amples et extrêmement détaillées informations sur les tenants et aboutissants de cette license, veuillez vous référer au fichier COPYING, ou bien au site http://www.wtfpl.net/.
