import kotlin.test.*

class BowlingGameTest {

    @Test
    fun testAllGutterBalls() {
        val game = Game()
        repeat(20) { game.roll(0) }
        assertEquals(0, game.score())
        assertTrue(game.isGameComplete())
    }

    @Test
    fun testAllOnes() {
        val game = Game()
        repeat(20) { game.roll(1) }
        assertEquals(20, game.score())
        assertTrue(game.isGameComplete())
    }

    @Test
    fun testSingleSpare() {
        val game = Game()
        game.roll(5)
        game.roll(5)
        game.roll(3)
        repeat(17) { game.roll(0) }
        assertEquals(16, game.score())
        assertTrue(game.isGameComplete())
    }

    @Test
    fun testSingleStrike() {
        val game = Game()
        game.roll(10)
        game.roll(3)
        game.roll(4)
        repeat(16) { game.roll(0) }
        assertEquals(24, game.score())
        assertTrue(game.isGameComplete())
    }

    @Test
    fun testPerfectGame() {
        val game = Game()
        repeat(12) { game.roll(10) }
        assertEquals(300, game.score())
        assertTrue(game.isGameComplete())
    }

    @Test
    fun testAllSpares() {
        val game = Game()
        repeat(21) { game.roll(5) }
        assertEquals(150, game.score())
        assertTrue(game.isGameComplete())
    }

    @Test
    fun testExampleFromHints() {
        val game = Game()

        game.roll(1); game.roll(4)
        game.roll(4); game.roll(5)
        game.roll(6); game.roll(4)  // spare
        game.roll(5); game.roll(5)  // spare
        game.roll(10)				// strike
        game.roll(0); game.roll(1)
        game.roll(7); game.roll(3)	// spare
        game.roll(6); game.roll(4)	// spare
        game.roll(10)				// strike
        game.roll(2); game.roll(8); game.roll(6) // ends with a spare

        assertEquals(133, game.score())
        assertTrue(game.isGameComplete())
    }

    @Test
    fun testTenthFrameStrike() {
        val game = Game()
        repeat(18) { game.roll(0) }
        game.roll(10)
        game.roll(5)
        game.roll(3)
        assertEquals(18, game.score())
        assertTrue(game.isGameComplete())
    }

    @Test
    fun testTenthFrameSpare() {
        val game = Game()
        repeat(18) { game.roll(0) }
        game.roll(5)
        game.roll(5)
        game.roll(3)
        assertEquals(13, game.score())
        assertTrue(game.isGameComplete())
    }

    @Test
    fun testTenthFrameRegular() {
        val game = Game()
        repeat(18) { game.roll(0) }
        game.roll(3)
        game.roll(4)
        assertEquals(7, game.score())
        assertTrue(game.isGameComplete())
    }

    @Test
    fun testMultipleStrikes() {
        val game = Game()
        game.roll(10)
        game.roll(10)
        game.roll(10)
        game.roll(3)
        game.roll(4)
        repeat(12) { game.roll(0) }
        assertEquals(77, game.score())
        assertTrue(game.isGameComplete())
    }

    @Test
    fun testMultipleSpares() {
        val game = Game()
        game.roll(5); game.roll(5)
        game.roll(3); game.roll(7)
        game.roll(4); game.roll(6)
        game.roll(3)
        repeat(13) { game.roll(0) }
        assertEquals(43, game.score())
        assertTrue(game.isGameComplete())
    }

    @Test
    fun testIncompleteGame() {
        val game = Game()
        game.roll(10)
        game.roll(5)
        game.roll(3)
        assertEquals(26, game.score())
        assertFalse(game.isGameComplete())
    }

    @Test
    fun testGameCompletion() {
        val game = Game()
        repeat(20) { game.roll(1) }
        assertTrue(game.isGameComplete())
    }

    @Test
    fun testTenthFrameTripleStrike() {
        val game = Game()
        repeat(18) { game.roll(0) }
        game.roll(10)
        game.roll(10)
        game.roll(10)
        assertEquals(30, game.score())
        assertTrue(game.isGameComplete())
    }

    @Test
    fun testNinthFrameStrikeTenthFrameStrike() {
        val game = Game()
        repeat(16) { game.roll(0) }
        game.roll(10)
        game.roll(10)
        game.roll(5)
        game.roll(3)
        assertEquals(43, game.score())
        assertTrue(game.isGameComplete())
    }

    @Test
    fun testNinthFrameSpare() {
        val game = Game()
        repeat(16) { game.roll(0) }
        game.roll(5)
        game.roll(5)
        game.roll(10)
        game.roll(2)
        game.roll(3)
        assertEquals(35, game.score())
        assertTrue(game.isGameComplete())
    }

    @Test
    fun testInvalidPinCountNegative() {
        val game = Game()
        val exception = assertFailsWith<IllegalArgumentException> {
            game.roll(-1)
        }
        assertEquals("Number of pins cannot be negative", exception.message)
    }

    @Test
    fun testInvalidPinCountTooHigh() {
        val game = Game()
        val exception = assertFailsWith<IllegalArgumentException> {
            game.roll(11)
        }
        assertEquals("Number of pins cannot exceed 10", exception.message)
    }

    @Test
    fun testInvalidFrameTotal() {
        val game = Game()
        game.roll(5)
        val exception = assertFailsWith<IllegalArgumentException> {
            game.roll(6)
        }
        assertEquals("Frame has 5, adding 6 makes a total > 10", exception.message)
    }

    @Test
    fun testRollAfterGameComplete() {
        val game = Game()
        repeat(20) { game.roll(1) }
        val exception = assertFailsWith<IllegalStateException> {
            game.roll(1)
        }
        assertEquals("Game is already complete", exception.message)
    }

    @Test
    fun testTenthFrameTooManyRolls() {
        val game = Game()
        repeat(18) { game.roll(0) }
        game.roll(10)
        game.roll(5)
        game.roll(3)
        val exception = assertFailsWith<IllegalStateException> {
            game.roll(1)
        }
        assertEquals("Game is already complete", exception.message)
    }

    @Test
    fun testInvalidFrameTotalEightPlusEight() {
        val game = Game()
        game.roll(8)
        val exception = assertFailsWith<IllegalArgumentException> {
            game.roll(8)
        }
        assertEquals("Frame has 8, adding 8 makes a total > 10", exception.message)
    }

    @Test
    fun testStrikeFrame() {
        val frame = Frame(1)
        frame.addRoll(10)
        assertTrue(frame.isStrike())
        assertFalse(frame.isSpare())
        assertTrue(frame.isComplete())
        assertEquals(10, frame.getTotalPins())
    }

    @Test
    fun testSpareFrame() {
        val frame = Frame(1)
        frame.addRoll(7)
        frame.addRoll(3)
        assertFalse(frame.isStrike())
        assertTrue(frame.isSpare())
        assertTrue(frame.isComplete())
        assertEquals(10, frame.getTotalPins())
    }

    @Test
    fun testRegularFrame() {
        val frame = Frame(1)
        frame.addRoll(3)
        frame.addRoll(4)
        assertFalse(frame.isStrike())
        assertFalse(frame.isSpare())
        assertTrue(frame.isComplete())
        assertEquals(7, frame.getTotalPins())
    }

    @Test
    fun testIncompleteFrame() {
        val frame = Frame(1)
        frame.addRoll(3)
        assertFalse(frame.isStrike())
        assertFalse(frame.isSpare())
        assertFalse(frame.isComplete())
        assertEquals(3, frame.getTotalPins())
    }

    @Test
    fun testTenthFrameStrikeCompletion() {
        val frame = TenthFrame()
        frame.addRoll(10)
        assertFalse(frame.isComplete())
        frame.addRoll(5)
        assertFalse(frame.isComplete())
        frame.addRoll(3)
        assertTrue(frame.isComplete())
    }

    @Test
    fun testTenthFrameSpareCompletion() {
        val frame = TenthFrame()
        frame.addRoll(5)
        assertFalse(frame.isComplete())
        frame.addRoll(5)
        assertFalse(frame.isComplete())
        frame.addRoll(3)
        assertTrue(frame.isComplete())
    }

    @Test
    fun testTenthFrameRegularCompletion() {
        val frame = TenthFrame()
        frame.addRoll(3)
        assertFalse(frame.isComplete())
        frame.addRoll(4)
        assertTrue(frame.isComplete())
    }

    @Test
    fun testFrameScoreWithBonus() {
        val frame = Frame(1)
        frame.addRoll(10)
        val score = frame.calculateScore(listOf(5, 3))
        assertEquals(18, score)
    }

    @Test
    fun testTenthFrameScore() {
        val frame = TenthFrame()
        frame.addRoll(10)
        frame.addRoll(5)
        frame.addRoll(3)
        val score = frame.calculateScore(emptyList())
        assertEquals(18, score)
    }

    @Test
    fun testConsecutiveStrikes() {
        val game = Game()
        repeat(10) { game.roll(10) }
        game.roll(10)
        game.roll(10)
        assertEquals(300, game.score())
        assertTrue(game.isGameComplete())
    }

    @Test
    fun testAlternatingStrikesAndSpares() {
        val game = Game()
        game.roll(10)
        game.roll(5); game.roll(5)
        game.roll(10)
        game.roll(3); game.roll(7)
        repeat(12) { game.roll(0) }
        assertEquals(70, game.score())
        assertTrue(game.isGameComplete())
    }

    @Test
    fun testStatusFromHints() {
        val game = Game()

        game.roll(1); game.roll(4)
        game.roll(4); game.roll(5)
        game.roll(6); game.roll(4)  // spare
        game.roll(5); game.roll(5)  // spare
        game.roll(10)				// strike
        game.roll(0); game.roll(1)
        game.roll(7); game.roll(3)	// spare
        game.roll(6); game.roll(4)	// spare
        game.roll(10)				// strike
        game.roll(2); game.roll(8); game.roll(6) // ends with a spare

        val status = game.status()

        assertEquals("1,4 | 4,5 | 6/ | 5/ | X | 0,1 | 7/ | 6/ | X | 2,8,6", status.frames)
        assertEquals("5 | 14 | 29 | 49 | 60 | 61 | 77 | 97 | 117 | 133", status.cumulativeScore)
        assertEquals(133, status.totalScore)
        assertEquals(Progress.COMPLETED, status.progress)
        assertTrue(game.isGameComplete())
    }

    @Test
    fun testGameStateNotStarted() {
        val game = Game()
        assertEquals(Progress.NOT_STARTED, game.getGameState())

        val status = game.status()
        assertEquals(Progress.NOT_STARTED, status.progress)
    }

    @Test
    fun testGameStateInProgress() {
        val game = Game()
        game.roll(5)
        assertEquals(Progress.IN_PROGRESS, game.getGameState())

        val status = game.status()
        assertEquals(Progress.IN_PROGRESS, status.progress)
    }

    @Test
    fun testGameStateInProgressMultipleFrames() {
        val game = Game()
        repeat(10) { game.roll(5) }
        assertEquals(Progress.IN_PROGRESS, game.getGameState())

        val status = game.status()
        assertEquals(Progress.IN_PROGRESS, status.progress)
    }

    @Test
    fun testGameStateCompleted() {
        val game = Game()
        repeat(20) { game.roll(1) }
        assertEquals(Progress.COMPLETED, game.getGameState())

        val status = game.status()
        assertEquals(Progress.COMPLETED, status.progress)
    }

    @Test
    fun testGameStateCompletedPerfectGame() {
        val game = Game()
        repeat(12) { game.roll(10) }
        assertEquals(Progress.COMPLETED, game.getGameState())

        val status = game.status()
        assertEquals(Progress.COMPLETED, status.progress)
    }

    @Test
    fun testGameStateCompletedWithTenthFrameSpare() {
        val game = Game()
        repeat(18) { game.roll(0) }
        game.roll(5)
        game.roll(5)
        game.roll(3)
        assertEquals(Progress.COMPLETED, game.getGameState())

        val status = game.status()
        assertEquals(Progress.COMPLETED, status.progress)
    }

    @Test
    fun testGameStateTransition() {
        val game = Game()

        assertEquals(Progress.NOT_STARTED, game.getGameState())

        game.roll(5)
        assertEquals(Progress.IN_PROGRESS, game.getGameState())

        repeat(19) { game.roll(1) }
        assertEquals(Progress.COMPLETED, game.getGameState())
    }

    @Test
    fun testTenthFrameInvalidPinCountNegative() {
        val frame = TenthFrame()
        val exception = assertFailsWith<IllegalArgumentException> {
            frame.addRoll(-1)
        }
        assertEquals("Number of pins cannot be negative", exception.message)
    }

    @Test
    fun testTenthFrameInvalidPinCountTooHigh() {
        val frame = TenthFrame()
        val exception = assertFailsWith<IllegalArgumentException> {
            frame.addRoll(11)
        }
        assertEquals("Number of pins cannot exceed 10", exception.message)
    }
}