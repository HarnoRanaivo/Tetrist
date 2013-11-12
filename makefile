COMPILER = javac

SPATH = src
BPATH = bytecode
DPATH = doc

vpath %.java $(SPATH)/
vpath %.class $(BPATH)/

all : | bytecodedir
	@$(COMPILER) -sourcepath $(SPATH) -cp $(BPATH) -d $(BPATH) $(SPATH)/Main.java

%.class : %.java | bytecodedir
	@$(COMPILER) -sourcepath $(SPATH) -cp $(BPATH) -d $(BPATH) $(SPATH)/$<

doc : cleandoc
	@javadoc -sourcepath $(SPATH) -classpath $(BPATH) -d $(DPATH) $(SPATH)/*.java \
		-subpackages $(shell find $(SPATH) -mindepth 1 -type d -iname "*" -printf "%f ")

bytecodedir :
	@mkdir -p $(BPATH)

clean :
	@rm -rf $(BPATH)

cleandoc :
	@rm -rf $(DPATH)

cleanall : clean cleandoc
	@echo "Clean."

archive :
	tar -cvzf archive.tar.gz src makefile
