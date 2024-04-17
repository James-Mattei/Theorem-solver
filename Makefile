JC = javac
JFLAGS =
SRCS = ${wildcard *.java}
# SRCS = Main.java
OBJS = ${SRCS:.java=.class}
LIBS =

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

all: $(OBJS)

submit:
	zip submit.zip $(SRCS) Makefile HONOR Beggars.txt Example.txt Summer.txt Work.txt Transcript.txt

clean:
	rm -f *.class

