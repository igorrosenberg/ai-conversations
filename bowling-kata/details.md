# Bowling Score Calculator - Detailed Implementation Recommendations

## Missing Components

### 1. Input Validation & Error Handling
- Validate `roll(pins)` input: 0 ≤ pins ≤ 10
- Validate frame totals don't exceed 10 pins (except 10th frame)
- Handle attempts to roll after game completion
- Prevent negative pin counts

### 2. Edge Cases Not Addressed
- Perfect game (12 strikes = 300 points)
- All gutter balls (0 points)
- Calling `score()` on incomplete game
- 10th frame complexity: strike/spare requiring exactly the right number of bonus rolls

### 3. State Management Clarity
- Game completion status tracking
- Clear definition of when 10th frame is complete
- Roll index vs frame tracking separation

## Preferred Design: Separate Frame Class

### Game Class
```
Game
├── frames: List[Frame]
├── current_frame: int
├── roll(pins: int)
├── score(): int
├── is_game_complete(): bool
└── _get_bonus_rolls(frame_index: int, bonus_count: int): List[int]
```

### Frame Class
```
Frame
├── rolls: List[int]
├── frame_number: int
├── add_roll(pins: int)
├── is_strike(): bool
├── is_spare(): bool
├── is_complete(): bool
├── get_total_pins(): int
└── calculate_score(bonus_rolls: List[int]): int
```

### TenthFrame Class (inherits from Frame)
```
TenthFrame
├── max_rolls: int (3)
├── is_complete(): bool (override)
└── needs_bonus_rolls(): bool
```

## Implementation Details

### Frame Scoring Logic
- **Regular Frame**: Base score = sum of rolls
- **Strike**: Base score = 10 + next 2 rolls
- **Spare**: Base score = 10 + next 1 roll
- **10th Frame**: Special rules for bonus rolls

### 10th Frame Special Logic
The 10th frame requires special handling:
- **Strike**: Player gets 2 more rolls (up to 3 total)
- **Spare**: Player gets 1 more roll (exactly 3 total)
- **Regular**: No extra rolls (2 rolls max)
- **Maximum**: 3 total rolls allowed

### State Management
- Game tracks current frame (1-10)
- Each frame tracks its own rolls and completion status
- Clear separation between frame logic and game orchestration

### Bonus Calculation Strategy
- Game class handles looking ahead for bonus rolls
- Frame class calculates its own score given bonus rolls
- Clean separation of concerns

### Validation Rules
1. **Roll Validation**: 0 ≤ pins ≤ 10
2. **Frame Validation**: Total pins per frame ≤ 10 (except 10th)
3. **Game State**: No rolls after game completion
4. **10th Frame**: Maximum 3 rolls, proper bonus handling

### Testing Strategy
- Design for incremental testing during incomplete games
- Clear separation between frame scoring and bonus calculation
- Mock-able components for unit testing
- Edge case coverage (perfect game, gutter game, incomplete games)

### Error Handling
- InvalidRollException for invalid pin counts
- GameCompleteException for rolls after game end
- Clear error messages for debugging

## Benefits of Separate Frame Class Design

1. **Single Responsibility**: Each class has a clear, focused purpose
2. **Testability**: Frame logic can be tested independently
3. **Maintainability**: Changes to frame rules don't affect game orchestration
4. **Extensibility**: Easy to add new frame types or rules
5. **Clarity**: 10th frame complexity is encapsulated in its own class