#!/bin/bash

rm */index.md

for f in */*.md
do 
	echo "$f"
	ROOT=$(dirname "$f")
	LINK=$(basename "$f" .md)
	echo "[$LINK](<${LINK}.md>)" >> $ROOT/index.md	
done

cat > README.md <<EOF
# ai-conversations

Keeping track of prompts and answers.

:warning: [disclaimer](<disclaimer.md>) :warning:

EOF


for d in */
do
	echo "[$d](${d}index.md)" >> README.md
done
