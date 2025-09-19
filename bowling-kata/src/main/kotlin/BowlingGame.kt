enum class Progress {
    NOT_STARTED,
    IN_PROGRESS,
    COMPLETED
}

data class FullStatus(
    val frames: String,
    val cumulativeScore: String,
    val totalScore: Int,
    val progress: Progress
)

class Game {
    private val frames = mutableListOf<Frame>()

    fun roll(pins: Int) {
        if (isGameComplete()) {
            throw IllegalStateException("Game is already complete")
        }

        if (frames.isEmpty() || frames.last().isComplete()) {
            if (frames.size < 9) {
                frames.add(Frame(frames.size + 1))
            } else {
                frames.add(TenthFrame())
            }
        }

        frames.last().addRoll(pins)
    }

    fun score(): Int {
        var totalScore = 0

        for (i in frames.indices) {
            val frame = frames[i]
            val bonusRolls = getBonusRolls(i)
            totalScore += frame.calculateScore(bonusRolls)
        }

        return totalScore
    }

    fun isGameComplete(): Boolean {
        return frames.size == 10 && frames.last().isComplete()
    }

    fun getGameState(): Progress {
        return when {
            frames.isEmpty() -> Progress.NOT_STARTED
            isGameComplete() -> Progress.COMPLETED
            else -> Progress.IN_PROGRESS
        }
    }

    fun status(): FullStatus {
        val framesStr = frames.map { frame ->
            frame.getDisplayString()
        }.joinToString(" | ")

        val cumulativeScores = mutableListOf<Int>()
        var runningTotal = 0
        for (i in frames.indices) {
            val frame = frames[i]
            val bonusRolls = getBonusRolls(i)
            runningTotal += frame.calculateScore(bonusRolls)
            cumulativeScores.add(runningTotal)
        }
        val cumulativeStr = cumulativeScores.joinToString(" | ")

        return FullStatus(framesStr, cumulativeStr, runningTotal, getGameState())
    }


    private fun getBonusRolls(frameIndex: Int): List<Int> {
        val frame = frames[frameIndex]
        val bonusRolls = mutableListOf<Int>()

		if (frameIndex < 9) {
			when {
				frame.isStrike() -> {
                    addNextRolls(frameIndex, 2, bonusRolls)
				}
				frame.isSpare() -> {
                    addNextRolls(frameIndex, 1, bonusRolls)
				}
			}
        }

        return bonusRolls
    }

    private fun addNextRolls(frameIndex: Int, count: Int, bonusRolls: MutableList<Int>) {
        var rollsNeeded = count
        var nextFrameIndex = frameIndex + 1

        while (rollsNeeded > 0 && nextFrameIndex < frames.size) {
            val nextFrame = frames[nextFrameIndex]
            for (roll in nextFrame.getAllRolls()) {
                if (rollsNeeded > 0) {
                    bonusRolls.add(roll)
                    rollsNeeded--
                }
            }
            nextFrameIndex++
        }
    }
}

open class Frame(protected val frameNumber: Int) {
    protected val rolls = mutableListOf<Int>()

    open fun addRoll(pins: Int) {
        if (pins < 0) {
            throw IllegalArgumentException("Number of pins cannot be negative")
        }
        if (pins > 10) {
            throw IllegalArgumentException("Number of pins cannot exceed 10")
        }
        canAddPins(pins) 
        rolls.add(pins)
    }

    open fun isStrike(): Boolean {
        return rolls.isNotEmpty() && rolls[0] == 10
    }

    open fun isSpare(): Boolean {
        return !isStrike() && rolls.size == 2 && rolls.sum() == 10
    }

    open fun isComplete(): Boolean {
        return isStrike() || rolls.size == 2
    }

    fun getTotalPins(): Int {
        return rolls.sum()
    }

    open fun calculateScore(bonusRolls: List<Int>): Int {
        return getTotalPins() + bonusRolls.sum()
    }

    fun getRoll(index: Int): Int? {
        return if (index >= 0 && index < rolls.size) rolls[index] else null
    }

    fun getRollCount(): Int {
        return rolls.size
    }

    fun hasRolls(): Boolean {
        return rolls.isNotEmpty()
    }

    fun getFirstRoll(): Int? {
        return getRoll(0)
    }

    fun getSecondRoll(): Int? {
        return getRoll(1)
    }

    fun getAllRolls(): List<Int> {
        return rolls.toList()
    }

    open fun getDisplayString(): String {
        return when {
            isStrike() -> "X"
            isSpare() -> "${getFirstRoll()}/"
            getRollCount() == 2 -> "${getFirstRoll()},${getSecondRoll()}"
            getRollCount() == 1 -> "${getFirstRoll()}-"
            else -> "--"
        }
    }

    open fun canAddPins(pins: Int){
        if (isComplete()) {
			throw IllegalArgumentException("Frame already complete with ${getDisplayString()} so $pins can't be added")
		}
		if (getTotalPins() + pins > 10) {
            throw IllegalArgumentException("Frame has ${getTotalPins()}, adding $pins makes a total > 10")			
        }
    }
}

class TenthFrame : Frame(10) {

    override fun isComplete(): Boolean {
        return when {
            rolls.size == 2 -> !isStrike() && !isSpare()
            rolls.size == 3 -> true
            else -> false
        }
    }


    override fun isSpare(): Boolean {
        return rolls.size >= 2 && !isStrike() && rolls[0] + rolls[1] == 10
    }

    override fun calculateScore(bonusRolls: List<Int>): Int {
        return getTotalPins()
    }

    fun getThirdRoll(): Int? {
        return getRoll(2)
    }

    override fun getDisplayString(): String {
        return getAllRolls().joinToString(",")
    }

    override fun canAddPins(pins: Int) {
        if (rolls.size >= 3)
					throw IllegalStateException("Cannot add more than 3 rolls to 10th frame")

    }
}