# SYSC3110Monopoly
### Version: 3.0
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
    * [Playing the BoardModel](#playing-the-boardModel)
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
We revieved a variety of feedback on the last milestone. In regards to this one, this is how we have adapted:
We added a proper BoardController to help us stay with in the MVC pattern.
We have gone through and cleaned up some of the less refined code. Particularlly in BoardView. 
We have tackled the list of bugs that were appearing in the previous version, such as the game not endeding when there are no players left for the winner to compete against. 
As we as we have fixed the bug with removing properties when a player is bankrupted. 
In the game previously we had the functionality of houses and hotels complete however they were not added onto the GUI as of the last milestone, so this update we added that functionality to the game. 


### Issues

### User Manual
#### Objective
The objective of the boardModel is to be the last one standing (and richest)by buying property and raising it costs to others in order for them to go bankrupt first. 
#### Playing the BoardModel
In order to interact with the program, the following buttons are shown at throughout the boardModel:
'quit' - Ends the boardModel immediately.                                                                                                     // Buttons not commands
'roll' - Rolls a number die for current player.
'purchase property' - Purchases property for current player, the property is the position player is on.
'purchase house' or 'purchase hotel' - Purchase house/hotel, asks player to type name of house/hotel to be purchased.
'pass turn' - Current player's turn ends, passes turn to next player.
'check boardModel state' - Outputs all Player's current status such as current Position, Balance, Bankrupt, Jail and Owned Properties status.
  
###### Core Gameplay Loop                                   
1.Rolling Dice to Move.
2.Buying Properties and/or Paying Rent to the Owner.
3.Build houses or hotels if someone owns all the properties of a colour.
4.Pass Turn.
###### Jail
When a player is sent to jail by landing on the designated "Go to jail" space, they are moved diagonally across the board directly to the jail cell on the 10th space. They are unable to collect go while moving to the jail cell. A player that is in jail is required to stay for a maximum of 3 turns after entering jail. While in jail, every turn they are required to roll doubles in order to leave jail early. If they are unable to roll doubles by the third turn, they are required to pay $50 to the bank in order to be released. They do not reroll for movment when they leave jail, they take the roll that got them out, or that would have if it was a double on their final turn in jail. 
         
###### The Lack of:   Speed Dice, Chance Cards, Community Chest, Mortgaging property, Housing 
We are not able to implement these features in this version of the boardModel. 

###### Example Turns
Players press "Start BoardView", and then decide how many players you want by entering it in the popup that will appear upon clicking the start game button. F
For each player, you will decide if they are to be either a human player or a AI, and if they are a human player, enter their name.

The game will decide who gets to move first randomly. 
Player 1 presses "Roll" to move. 
The PLayer is moved as far as they rolled. 
Player 1 presses "Purchsae Property", and the boardModel removes the money from their account but adds the Deed to the player's inventory. 
    Should the player not have enough the are required to pass their turn without purchasing the property. 
Player 1 passes their turn by pressing "Pass Turn". 

An AI will also follow the example turn described above. 
### Important Design Decisions
We decided that implementing the functionality of: Go, Jail, Railroads and utilities early would be in our best intrest. As it will save us time when implementing the harder features of the boardModel later on.  

### Roadmap
The next step of this project will be implementing 2 main features. The first will be the ability to save and load the game to the local computer. 
The second main feature is adding support for multiple languages in the game. 
