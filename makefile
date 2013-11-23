COMPILER = javac

JPATH = jar
DPATH = doc
BPATH = bytecode

TETRIST_SPATH = Tetrist
TETRIST_BPATH = bytecode/Tetrist
TETRIST_DPATH = doc/Tetrist

MARVIN_SPATH = Marvin
MARVIN_BPATH = bytecode/Marvin
MARVIN_DPATH = doc/Marvin

all : jardir tetristjar

tetristjar : tetristbytecodedir
	$(COMPILER) -sourcepath $(TETRIST_SPATH) -cp $(TETRIST_BPATH) -d $(TETRIST_BPATH) $(TETRIST_SPATH)/Tetrist.java
	rmic -classpath $(TETRIST_BPATH) -d $(TETRIST_BPATH) Component.Game
	echo "Main-Class: Tetrist" > manifest
	jar cmf manifest $(JPATH)/Tetrist.jar -C bytecode/Tetrist/ . -C Tetrist/ Pictures -C Tetrist/ Fonts
	rm manifest

tetristdoc :
	@javadoc -sourcepath $(TETRIST_SPATH) -classpath $(TETRIST_BPATH) -d $(TETRIST_DPATH) $(TETRIST_SPATH)/*.java \
		-subpackages $(shell find $(TETRIST_SPATH) -mindepth 1 -type d -iname "*" -printf "%f ")

doc : docdir tetristdoc

docdir :
	@mkdir -p $(DPATH)

jardir :
	@mkdir -p $(JPATH)

bytecodedir :
	@mkdir -p $(BPATH)

tetristbytecodedir : bytecodedir
	@mkdir -p $(TETRIST_BPATH)

marvinbytecodedir :
	@mkdir -p $(TETRIST_BPATH)

clean :
	@rm -rf $(BPATH)
	@rm -rf $(JPATH)

cleandoc :
	@rm -rf $(DPATH)

cleanall : clean cleandoc
	@echo "Clean."

archive :
	tar -cvzf archive.tar.gz src makefile
