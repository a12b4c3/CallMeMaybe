# CallMeMaybe
A Java based <b>call</b> graph generator for Java made by <b>me</b>(us), that is at best, only <b>MAYBE</b>, somewhat accurate.

PS. actually more of a sequence graph generator than call graph generator.

## Usage
1. clone the repo
2. import as maven project
3. in App.java, change the 3 static final variables to indicate the method name, method list of params, and class name of the method. 
4. run the node server for sequence diagram generator (not included in the repo, request from contributors.)
5. run the program.
6. image is generated at the source root of the project. 

## Acknowledgements
We thank the developers of the following projects. This project would not be possible without them. In no particular order.
* JavaParser and JavaSymbolSolver (www.javaparser.org) - used to generate AST trees of Java source code.
* SequenceDiagram.org (www.sequenceDiagram.org) - used to generate the sequence diagrams, based on their provided DSL (domain specific language).
