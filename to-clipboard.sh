#!/bin/bash

(for md in *.md
do
  if [[ "$md" == "99.ignore.md" ]]; then
    : # Skip  file
  else
    echo "===== $md ======"
    cat "$md"
    echo
  fi
done) | clip
