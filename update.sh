#!/bin/bash

rm -- */index.md

for f in */*.md
do 
	echo "$f"
	ROOT=$(dirname "$f")
	LINK=$(basename "$f" .md)
	echo "* [$LINK](<${LINK}.md>)" >> "$ROOT/index.md"
done

cat > README.md <<EOF
# ai-conversations

Keeping track of prompts and answers.

Find here a set of Markdown files, which track AI-prompts and their AI-answers.
Why ?
- come back to previous AI conversations to understand a result's provenance
- start again from a previous context

:warning: **THIS MIGHT NOT BE USEFUL TO THE GENERAL PUBLIC** :warning:

EOF


for d in */
do
	echo "* [$d](${d}index.md)" >> README.md
done
