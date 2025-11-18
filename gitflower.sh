#!/usr/bin/env bash

# Facilita el hacer mergear cosas en la feature y volver inmediatamente a la personal
_flowmerge() {
  local cur branches
  cur="${COMP_WORDS[COMP_CWORD]}"
  branches=$(git branch --format='%(refname:short)' 2>/dev/null)
  COMPREPLY=( $(compgen -W "$branches" -- "$cur") )
}
if [[ "${BASH_SOURCE[0]}" == "${0}" ]]; then
  complete -F _flowmerge ./gitflower.sh
fi
git checkout "$1";git merge "$2";git checkout "$2"
