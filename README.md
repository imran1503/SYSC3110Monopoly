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
The deliverables for this milestone is as follows: readme file, source code in the form of a.jar file, UML diagrams, documentation. They are supposed to all in one zip file submitted on brightspace. 

### Issues
An issue that is currently in our game is that the JLabels responsible for showing each players inventory are not updating, despite the JLabels updating to the correct value. For example the first label "Name: " would change to the 

### User Manual
#### Objective
The objective of the game is to be the last one standing (and richest)by buying property and raising it costs to others in order for them to go bankrupt first. 
#### Playing the Game
In order to interact with the program, the following commands are shown at the beginning of the game:
'quit' - Ends the game immediately.
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
         
###### The Lack of:   Speed Dice, Chance Cards, Community Chest, Mortgaging property 
We are not able to implement these features in this version of the game. 

###### Example Turns
Welcome to the game of Monopoly!
Type 'help' if you ever need a command list with explanation.

Enter the total number of Players playing
```
2
```
Enter the name of Player 1
```
p1
```
Enter the name of Player 2
```
p2
```
All commands are below with brief explanation:
'quit' - Ends the game immediately
'roll' - Rolls a number die for current player
'purchase property' - Purchases property for current player, the property is the position player is on
'purchase house' or 'purchase hotel' - Purchase house/hotel, asks player to type name of house/hotel to be purchased
'pass turn' - Current player's turn ends, passes turn to next player
'check game state' - Outputs all Player's current status such as current Position, Balance, Bankrupt, Jail and Owned Properties status 

Player 1 goes first, begin by typing roll command
```
roll
```
p1 rolled a 6, landed on Oriental Avenue
```
purchase
```
No such command exists!
```
purchase property
```
p1 purchased Oriental Avenue, remaining Balance:1400
```
pass turn
```
It's Now p2 turn.
```
roll
```
p2 rolled a 11, landed on St. Charles Place
```
purchase property
```
p2 purchased St. Charles Place, remaining Balance:1360
```
pass turn
```
It's Now p1 turn.
```
check game state
```
******
p1 position is currently at Oriental Avenue
Balance is 1400
Bankrupt Status = false
In Jail Status = false
List of Owned Properties:
- Oriental Avenue
*****
******
p2 position is currently at St. Charles Place
Balance is 1360
Bankrupt Status = false
In Jail Status = false
List of Owned Properties:
- St. Charles Place
*****
### Important Design Decisions
We decided that implementing the functionality of: Go, Jail, Railroads and utilities early would be in our best intrest. As it will save us time when implementing the harder features of the game later on.  

### Roadmap
The next steps for this project will involve us moving from the console based interactions to a graphical user interface approach. 
Much later in the projects life there will also be an option to add an AI player into your game. 
