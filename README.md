# SYSC3110Monopoly
### Version: 2.0
### Authors:
- Mahtab Ameli: @ameli-s19 and @MahtabAmeli
- Shahrik Amin: @ShahrikAmin
- Imran Latif: @imran1503
- Muhammad Furqan: @mfurqan26
				
### Table of Contents:
- [Quick Start](#quick-start)
- [Deliverables](#deliverables)
- [Issues](#issues)
- [User Manual](#complete-user-manual)
    * [Objective](#objective)
    * [Playing the Game](#playing-the-game)
    * [Core Gameplay Loop](#core-gameplay-loop)
    * [Jail](#jail)
    * [Current Player Information](#current-player-information)
    * [Example Turn](#example-turn)
- [Important Design Descisions](#important-design-decisions)
- [Roadmap](#roadmap)

### Quick Start:
To play Monopoly, run the main function in Game.java, or if you chose to run it from the jar file type:
```
java -jar Monopoly.jar
```

### Deliverables
The deliverables for this milestone is as follows: readme file, the JUnit test files, source code in the form of a.jar file, UML diagrams, documentation. They are supposed to all in one zip file submitted on brightspace. 

### Issues
We have yet to implement a way for the player to visually recognize where they are on the board. We only hours before the deadline able to figure out why the GUI was not updating. In the next milestone the is a plan to add player pieces that are able to move around the board. 

Due to other complications, there was not enough time to allocate to JUnit's extensive corner case testing for all cases that exist in the model. 

### User Manual
#### Objective
The objective of the game is to be the last one standing (and richest)by buying property and raising it costs to others in order for them to go bankrupt first. 
#### Playing the Game
In order to interact with the program, the following buttons are shown at throughout the game:
'quit' - Ends the game immediately.                                                                                                     // Buttons not commands
'roll' - Rolls a number die for current player.
'purchase property' - Purchases property for current player, the property is the position player is on.
'purchase house' or 'purchase hotel' - Purchase house/hotel, asks player to type name of house/hotel to be purchased.
'pass turn' - Current player's turn ends, passes turn to next player.
'check game state' - Outputs all Player's current status such as current Position, Balance, Bankrupt, Jail and Owned Properties status.
  
###### Core Gameplay Loop                                   
1.Rolling Dice to Move.
2.Buying Properties and/or Paying Rent to the Owner.
3.Build houses or hotels if someone owns all the properties of a colour.
4.Pass Turn.
###### Jail
When a player is sent to jail by landing on the designated "Go to jail" space, they are moved diagonally across the board directly to the jail cell on the 10th space. They are unable to collect go while moving to the jail cell. A player that is in jail is required to stay for a maximum of 3 turns after entering jail. While in jail, every turn they are required to roll doubles in order to leave jail early. If they are unable to roll doubles by the third turn, they are required to pay $50 to the bank in order to be released. They do not reroll for movment when they leave jail, they take the roll that got them out, or that would have if it was a double on their final turn in jail. 
         
###### The Lack of:   Speed Dice, Chance Cards, Community Chest, Mortgaging property, Housing 
We are not able to implement these features in this version of the game. 

###### Example Turns
>>Players press "Start Game", and then enter their names .
>>The game decides who gets to move first. 
>>Player 1 presses "Roll" to move. 
>>The PLayer is moved as far as they rolled. 
>>Player 1 presses "Purchsae Property", and the game removes the money from their account but adds the Deed to the player's inventory. 
>>Player 1 passes their turn by pressing "Pass Turn". 

### Important Design Decisions
We decided that implementing the functionality of: Go, Jail, Railroads and utilities early would be in our best intrest. As it will save us time when implementing the harder features of the game later on.  

### Roadmap
The next steps for this project will involve us creating an AI player that can play the game by reading the state of the board. 
Much later in the projects life there will also be an save and load feature, so games can stop and pick right back up where they left off.. 
