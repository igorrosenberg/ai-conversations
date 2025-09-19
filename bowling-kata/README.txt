git diff --quiet || (
	./gradlew clean test && 
		git add . && 
		git commit -m "$(claude -p 'Based on the changes, Terse git commit message, no extra. I need to use this output as the message directly')"
)

