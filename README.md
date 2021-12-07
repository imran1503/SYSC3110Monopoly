# SYSC3110Monopoly
### Version: 4.0
### Authors:
- Mahtab Ameli: @ameli-s19 and @MahtabAmeli
- Shahrik Amin: @ShahrikAmin
- Imran Latif: @imran1503
- Muhammad Furqan: @mfurqan26
				
### Table of Contents:
- [Quick Start](#quick-start)
- [Deliverables](#deliverables)
- [Improvements](#improvements)
- [Issues](#issues)
- [User Manual](#complete-user-manual)
    * [Objective](#objective)
    * [Playing the Game](#playing-the-Game)
    * [Core Gameplay Loop](#core-gameplay-loop)
    * [Jail](#jail)
    * [Current Player Information](#current-player-information)
    * [Example Turn](#example-turn)
- [Important Design Descisions](#important-design-decisions)
- [Roadmap](#roadmap)

### Quick Start:
To play Monopoly, run the main function in BoardView.java, or if you chose to run it from the jar file type:
```
java -jar Monopoly.jar
```

### Deliverables
The deliverables for this milestone is as follows: readme file, the JUnit test files, source code in the form of a.jar file, UML diagrams, documentation. They are supposed to all in one zip file submitted on brightspace. 

### Improvements
We reviewed a variety of feedback on the last milestone. With regard to this one, this is how we have adapted:
We have made a popup window appear to show the game has ended when there is only one non bankrupt person left. Otherwise, a stack overflow would occur.
We have removed "magic numbers" from our code. As well as adding several helper functions allowing the code to be more maintainable over time. 
We have fixed the jar's ability to read in files by creating a save files folder that holds all the .XML documents in a directory marked for saves.. 

The main functionality added in this update are as follows: being able to save the game, being able to load that save and continue playing, and being able to play this game in 4 languages.
Those languages being: English, French, Arabic, and Persian. 

### Issues
One issue that can happen on occasion, the game when run with a single human player and 3 other AI players, will occasionally have the human player move after pressing pass and not rolling as if it was an AI. 
Another issue that can occur is that when 2 players are equally equipped to a very drawn out game, the game struggles to realize a tie, causing a stack overflow.  


### User Manual
#### Objective
The objective of the boardModel is to be the last one standing (and richest)by buying property and raising it costs to others in order for them to go bankrupt first. 
#### Playing the Game
In order to interact with the program, the following buttons are shown at throughout the boardModel:
'quit' - Ends the boardModel immediately.                                                                                                     // Buttons not commands
'roll' - Rolls a number die for the current player.
'purchase property' - Purchases property for the current player, the property is the position player is on.
'purchase house' or 'purchase hotel' - Purchase house/hotel, asks player to type name of house/hotel to be purchased.
'pass turn' - Current player's turn ends, passes turn to next player.
'check boardModel state' - Outputs all Players current status such as current Position, Balance, Bankrupt, Jail and Owned Property status.
'save' - Allows you to save an exact copy of all the current information of the game at that point in time. There is only one save slot that will be continuously overwritten.
'load' - Allows you to load the save if one exists and continue playing from there. 
  
###### Core Gameplay Loop                                   
1.Rolling Dice to Move.
2.Buying Property and/or Paying Rent to the Owner.
3.Build houses or hotels if someone owns all the Property of a colour.
4.Pass Turn.
###### Jail
When a player is sent to jail by landing on the designated "Go to jail" space, they are moved diagonally across the board directly to the jail cell on the 10th space. They are unable to collect go while moving to the jail cell. A player that is in jail is required to stay for a maximum of 3 turns after entering jail. While in jail, every turn they are required to roll doubles in order to leave jail early. If they are unable to roll doubles by the third turn, they are required to pay $50 to the bank in order to be released. They do not reroll for movment when they leave jail, they take the roll that got them out, or that would have if it was a double on their final turn in jail. 
         
###### The Lack of:   Speed Dice, Chance Cards, Community Chest, and Mortgaging property 
We are not able to implement these features in this version of the Monopoly. 

###### Example Turns
Players press "Start Game", and choose which language they will like to play in. Then they need to decide how many players you want by entering it in the popup that will appear upon clicking the start game button.
For each player, you will decide if they are to be either a human player or an AI, and enter their name.

The game will decide who gets to move first randomly. 
Player 1 presses "Roll" to move. 
The PLayer is moved as far as they rolled. 
Player 1 presses "Purchase Property", and the boardModel removes the money from their account but adds the Deed to the player's inventory. 
    Should the player not have enough the are required to pass their turn without purchasing the property. 
Player 1 passes their turn by pressing "Pass Turn". 

An AI will also follow the example turn described above. 


At any point, any the current player is allowed to press the save button in order to save a copy of their game in order to be played later when they click load.
If a player presses the load button and there is no file to load in, at the same directory level of the JAR, then there will be an event log telling them that there is no file found.
When a file is found, then it will load that and override the game with the save. 
### Important Design Decisions
We decided that forcing the game to come to an end once all 40 properties are purchased when there are predominantly AI players playing was necessary. 
As they could go on for a very long time before going bankrupt depending on the state of the board, and the lack of a trade feature to force colour sets to be made. . 
If all properties are purchased without there being a single winner, the first player to have a balance greater than 3000$ wins the game.
We also decided to have 4 languages in the game, allowing it to be played by more people.

