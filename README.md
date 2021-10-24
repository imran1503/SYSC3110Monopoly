# SYSC3110Monopoly
### Version: 1.0
### Authors:
- Mahtab Ameli: @ameli-s19
- Shahrik Amin: @ShahrikAmin
- Imran Latif: @imran1503
- Muhammad Furqan: @mfurqan26

### Table of Contents:
- [Quick Start](#quick-start)
- [Deliverables](#deliverables)
- [Issues](#issues)
- [User Manual](#complete-user-manual)
    * [Playing the Game](#playing-the-game)
    * [The Map](#the-map)
    * [Current Player Information](#current-player-information)
    * [Example Turn](#example-turn)
- [Important Design Descisions](#important-design-decisions)
- [Roadmap](#roadmap)

### Quick Start:
To play RISK, run the main function in Parser.java, or if running from the jar file type:
```
java -jar ????.jar
```

### Deliverables

### Example Gameplay
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

### User Manual

#### Playing the Game
In order to interact with the program, the following commands are shown at the beginning of the game:
   'quit' - Ends the game immediately
   'roll' - Rolls a number die for current player
   'purchase property' - Purchases property for current player, the property is the position player is on
   'purchase house' or 'purchase hotel' - Purchase house/hotel, asks player to type name of house/hotel to be purchased
   'pass turn' - Current player's turn ends, passes turn to next player
   'check game state' - Outputs all Player's current status such as current Position, Balance, Bankrupt, Jail and Owned Properties status 


### Important Design Decisions


### Roadmap
