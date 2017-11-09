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
* Add documentation for your static variables and methods (official documentation format TBA)
# Github Formalities
* Commit often, push/pull seldom
* Always pull new code to your fork before pulling code on your fork to Master
* Change (commit/pull/push) titles should be Initialcased and end with a period
	* EX: Added this thing.
	* EX: Destroyed everything.
	* EX: Changed this. Blame Kurt.   (second sentences are acceptable if they explain something necessary.)
	
* Avoid using multiple statements to describe multiple items in a commit title
	* If you're doing that then it should be two seperate commits.
* Multiple statements for multiple items is acceptable for push/pull
	* EX: Worked on this. Added that.
