# Full game example

[Shows a bowling scorecard with 10 frames displaying rolls and cumulative scores: 1,4 | 4,5 | 5,/ | X | 0,1 | 7,/ | 6,/ | X | 2,/,6,/ with cumulative scores 5, 14, 29, 49, 60, 61, 77, 97, 117, 133]

## Legend
• / → spare
• X → strike

[Shows a diagram of frame structure with "1st roll (pins down)" and "2nd roll (pins down)" boxes above a "frame score" box]

## Tips

Write at least a class named `Game` that has two methods and corresponds to the previous rules

• `roll(pins: int)` → is called each time the player rolls a ball. The argument is the number of pins knocked down.

• `score(): int` → is called only at the very end of the game. It returns the total score for that game. For test purpose it can be called during the game, in a non ended-game.

## Bowling Score Calculator