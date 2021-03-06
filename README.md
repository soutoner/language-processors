Language Processors
===================

Personal work on the subject of language Processors. Contains original exercises, test cases and reference binaries (none of these made by me).

---

### Content:

#### Skeleton of the repository

* {version}**C**: each version has its own features.
	* {version}**-bin**: binary reference files for this compiler.
	* {version}**-test**: some example code for testing purposes.
	* {version}**.pdf**: original exercise. 
	* {version}**C.cup**: CUP file for the compiler (analyzer).
	* {version}**C.flex**: JFlex file for the compiler (tokenizer).
	* {version}**C.java**: main Java class.
	* **compile.sh**: shell script with the three compile steps (improve workflow).
	* **runtest.sh**: shell script that runs all the test case and compare the provided solution with the reference one.
	* **auxiliary files**: commented in the next section.
* **ctd-bin**: binary files of the three-address code interpreter (intermediate code that is generated by the compiler). 

#### Other files inside each folder:

 * **PLC**:  PL compiler.
	 * **Condition**.java: manage conditions
	 * **Printer.java**: generating (printing) code purposes.
	 
 * **PLXC**: PL+ compiler (support more features).
	 * **Condition**.java: manage conditions.
	 * **Printer.java**: generating (printing) code purposes.
	 * **SymbolTable.java**: auxiliary Java class that mantains the Symbol Table (variables scopes).

 * **PLXC+**: PLX+ compiler. Support more features than the PDF: switch case, and ask operator. Backwards compatible.
     * **Condition**.java: manage conditions.
     * **Printer.java**: generating (printing) code purposes.
     * **SymbolTable.java**: maintains the Symbol Table (variables scopes).
     * **Occurrence.java**: holds the inner structure of the SymbolTable [scope, type, size] of an identifier.

 * **JPLC**: Java "ByteCode" compiler (very reduced).
	 * **Condition**.java: auxiliary Java class for conditions.
	 * **Printer.java**: auxiliary Java class for printing purposes.
	 * **SymbolTable.java**: auxiliary Java class that mantains the Symbol Table (variables scopes).
	 * **jplcore.j**: auxiliary file needed by the compile process.
	 * **jasmin.jar**: jbytecode (intermediate language) compiler.
	 * Not implemented at all: Perfect limitation of the operational stack size.  

### Requirements

* JFlex: http://jflex.de/
* CUP: http://www2.cs.tum.edu/projects/cup/
* Java: http://www.java.com/en/

### How to run PL*C projects

#### Choose one of the project

e.g. PLC

#### Choose the correct binaries

Put inside the folder of the project the necessary binary files that goes with your architecture. `ctd` binaries are inside `/ctd-bin`, `pl*c` are in `/pl*c/pl*c-bin`.

#### Change directory to the project (if not done)

e.g. `cd PLC`

#### `./compile.sh`

#### `./runtest.sh`

>**Note:** credits to the University of Málaga for original exercises, test cases and reference binaries. Material uploaded for educational purposes.

