# Naming Standards
* camelCase
 	- Methods
 	- Variables
* BumpyCase
 	- Classes
* CAPS_LOCK
	- Static Variables
 	- Enumeration Values
* lowercase
 	- Folder Names
	
# Recurring Structure
```
* [Competition Name]-[year]
	- src
		- org
			- usfirst
				- frc
					- team3695
						- robot
							- commands
							- enumeration
							- subsystems
							- util
							- Constants.java
							- Controller.java
							- OI.java
							- Robot.java
						- util
							- Tomes of Knowledge
								- Standards.md
								- Competition.md
								- Concept.md
							- layout.smart.xml
	- README.md
```						
# Code Formalities
* Use tabs to offset, not spaces
* Add documentation for your static variables and methods
	* put /** javadoc */ descriptions above each method and class
	* use // single line comments above variables that need to be documented
	* use // single line comments to comment out single lines of code
	* use // single line comments for, well, single line comments (notes and stuff)
	* use /\*\* multi-line comments \*\*/ to comment out blocks of code
	* use /\*\* multi-line comments \*\*/ for datatables (mainly for Constants use)

# Github Formalities
* Commit often, push/pull seldom
* Always pull new code to your fork before pulling code on your fork to Master
* Commit messages should be *Initial cased* and end with a period
	* EX: Added this thing.
	* EX: Destroyed everything.
	* EX: Changed this. Blame Kurt.   (second sentences are acceptable if they explain something necessary, but are not preferred)
* Push/pull messages should be *Initial cased* and end with a period
	* Push/pull messages can have multiple sentences since they have multiple commits
	* EX: Broke literally everything.
	* EX: Fixed autonomous? (question marks are okay if you aren't sure about it yet)
	* EX: Added GRIP code. Worked on vision. 
