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

TESTS_SPATH = Tests
TESTS_BPATH = bytecode/Tests
TESTS_DPATH = doc/Tests

all : tetrist marvin

tetrist : tetristbytecodedir
	@$(COMPILER) -sourcepath $(TETRIST_SPATH) -cp $(TETRIST_BPATH) -d $(TETRIST_BPATH) $(TETRIST_SPATH)/Tetrist.java
	@cp -R $(TETRIST_SPATH)/Fonts $(TETRIST_BPATH)
	@cp -R $(TETRIST_SPATH)/Pictures $(TETRIST_BPATH)
	@rmic -classpath $(TETRIST_BPATH) -d $(TETRIST_BPATH) Component.Game

marvin : marvinbytecodedir tetrist
	@$(COMPILER) -sourcepath $(MARVIN_SPATH) -cp $(MARVIN_BPATH) -d $(MARVIN_BPATH) $(MARVIN_SPATH)/Marvin.java
	@cp $(TETRIST_BPATH)/Component/Game_Stub.class $(MARVIN_BPATH)/Component/Game_Stub.class

tests : testbytecodedir
	@$(COMPILER) -sourcepath $(TESTS_SPATH) -cp $(TESTS_BPATH) -cp /usr/share/java/junit.jar -d $(TESTS_BPATH) $(TESTS_SPATH)/TestAll.java

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

marvinbytecodedir : bytecodedir
	@mkdir -p $(MARVIN_BPATH)

testbytecodedir :
	@mkdir -p $(TESTS_BPATH)

clean :
	@rm -rf $(BPATH)
	@rm -rf $(JPATH)

cleandoc :
	@rm -rf $(DPATH)

cleanall : clean cleandoc
	@echo "Clean."

archive :
	tar -cvzf archive.tar.gz src makefile
