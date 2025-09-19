# Bowling Score Calculator - Program Structure

## Core Components

### 1. Game Class
**Responsibility**: Main orchestrator for bowling game scoring
- `roll(pins: int)`: Records each roll and the number of pins knocked down
- `score(): int`: Calculates and returns the total game score

### 2. Frame Management
**Responsibility**: Handle individual frame scoring logic
- Track rolls within each frame (1-2 rolls normally, up to 3 in 10th frame)
- Identify strikes and spares
- Calculate frame scores including bonuses

### 3. Score Calculation Engine
**Responsibility**: Apply bowling scoring rules
- **Normal scoring**: Sum of pins knocked down
- **Spare bonus**: Next roll value added to frame score
- **Strike bonus**: Next two rolls added to frame score
- **10th frame special rules**: Allow extra rolls for strikes/spares

## Data Structure

### Game State
- Current frame number (1-10)
- Roll history (sequence of pin counts)
- Frame completion status

### Frame Types
- **Regular Frame**: 1-2 rolls, max 10 pins total
- **Strike**: 1 roll, 10 pins
- **Spare**: 2 rolls, 10 pins total
- **10th Frame**: Special case allowing up to 3 rolls

## Implementation Flow

1. **Roll Recording**: Store each roll in sequence
2. **Frame Detection**: Determine frame boundaries and types
3. **Bonus Calculation**: Look ahead for strike/spare bonuses
4. **Score Accumulation**: Sum frame scores progressively
5. **Game Completion**: Validate 10 frames completed

## Key Rules to Implement

- 10 frames per game
- Strike = 10 pins on first roll → bonus = next 2 rolls
- Spare = 10 pins on 2 rolls → bonus = next 1 roll
- 10th frame allows extra rolls to complete bonuses
- Maximum 3 rolls in 10th frame
- Progressive scoring (cumulative totals)