Important Setting:
1) Must have dowloaded JavaFx
2) Must use Java 17
3) In Eclipse, under arguments/vm arguments add something simlar:
      --module-path /Users/abrahamlimon/Downloads/javafx-sdk-21.0.1/lib --add-modules=javafx.controls
4) Make sure to uncheck the following:
   Use the -XX:+ShowCodeDetailsInExceptionMessages argument when launching

# Scaryville
 In this asylum escape game, navigate as the guard through corridors, avoiding lunatics with strategic movements. Win by reaching the exit or lose if caught. The game offers a resettable, immersive experience.

• Developed an Object-Oriented program with encapsulation using Java, featuring Guard, Lunatic, and Walls objects for a grid-based escape game set in an insane asylum.
• Utilized a two-dimensional array data structure in the driver class to track object locations within the dynamic game environment.
• Implemented logic where the Lunatic chases the Guard if they share the same row or column, or else moves randomly using the
Math class.
• Ensured collision prevention by restricting Guard and Lunatic movement through walls with well-crafted conditional statements.
• Designed the game's graphical user interface using JavaFX and CSS, employing multiple classes for a polished user experience.

PS: This game was created and ran on Mac device with intel chip. Some settings may differ in other applications.
