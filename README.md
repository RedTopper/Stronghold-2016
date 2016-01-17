# Stronghold 2016 EXP Code Base
Code for FRC Robotics team 3695.

# Tutorials
### How to use GitHub Presentation
[drive.google.com](https://docs.google.com/presentation/d/1c2I2dRhcj-hvn5ArNstm2tBm8RZz-9yyLwL01LYOvZs/edit?usp=sharing)

### How to set up FRC's development kit
[wpilib.screenstepslive.com](http://wpilib.screenstepslive.com/s/4485/m/13503/l/145002-installing-eclipse-c-java)

### How to import the project into Eclipse
* Coming soon!

### Setting up your Classpath
* When the Java project has been imported into Eclipse, follow these steps to obtain the correct classpath variables.
* It is required that the above tutorials have been followed before proceeding! 

1. Window > Preferences > Java > Build path > Classpath variables > New
2. For the "name" field, type in "wpilib" (without quotes, all lowercase) and press File...
3. Then go to "C:\Users\<YOUR USERNAME>\wpilib\java\current\lib" (Should be installed in "How to set up FRC's dev kit") and select wpilib.jar. Press OK
4. Press New again. Type in "networktables" this time. Press File... and select networktables.jar. Press OK.
5. Finally, press New again. Type in "wpilib-source". Press File... and select wpilib-source.jar. Press OK.

### Linking sources
This step facilitates development with the robot. While optional, it allows you to hover over classes and methods to see how they work. It's pretty easy to set up.

1. Find source that isn't set up yet. for example, when you hover over the word "Talon" on the line "private Talon left", it'll say something along the lines of "no source found"
2. Go to "Open declaration"
3. It'll now show a big, confusing white page with some form of error. Simply go to "link source" and select wpilib-source.
4. It'll now show a bunch of code! Yay! DO NOT EDIT THIS CODE HOWEVER, simply close out and try hovering over the word "Talon" again. You will now see "Cross the Road Electronics (CTRE) Talon and Talon SR Speed Controller". You can hover over other FRC methods too!

# Code
### Code Style and Format
- Methods should be in camelCase.
- Variables should be in camelCase.
- Classes should be in BumpyCase.
- Make sure that your IDE uses tabs, not spaces.
- Static variables should be in CAPS_LOCK.
- Please make sure to add Javadoc to your methods and static variables. 
- Example:
```Java
/**
 * I/O number for the arm of the robot.
 */
public static final int ROBOT_ARM = 10;

/**
 * This method kills the robot. Do not attempt to
 * kill the robot.
 */
public boolean doMagicalThing(int magic) {
  Robot r = new Robot();
  r.kill();
  r.haltAndCatchFire(true);
  return magic > 0;
}
```

# Contributors (Program Team 2016)
- AJ Walter
- Kurtis Bowen
- Grant Walter
