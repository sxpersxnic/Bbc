#!/usr/bin/bash

# - ASCII -
# COLORS
RED='\033[38;2;255;0;0m'
BLUE='\033[38;2;0;0;255m'
GREEN='\033[38;2;0;200;0m'
YELLOW='\033[38;2;255;255;0m'
ORANGE='\033[38;2;255;100;0m'
PINK='\033[38;2;255;0;255m'
WHITE='\033[38;2;255;255;255m'
RESET='\033[0m'

# FONT
BOLD='\033[1m'
ITALIC='\033[3m'

push() {
    read -p "Enter the remote name: " remote
    echo -e "${GREEN}${BOLD}Pushing to $remote...${RESET}"
    git push $remote HEAD
}

pull() {
    read -p "Enter the remote name: " remote
    echo -e "${GREEN}${BOLD}Pulling from $remote...${RESET}"
    git pull $remote HEAD
}

get_branch() {
    echo -e "${GREEN}${BOLD}Current branch:${RESET}"
    git branch --show-current
}

create_branch() {
    read -p "Enter the name of the new branch: " branch
    echo -e "${GREEN}${BOLD}Creating new branch $branch...${RESET}"
    git branch $branch
}

delete_branch() {
    read -p "Enter the name of the branch to delete: " branch
    echo -e "${RED}${BOLD}Deleting branch $branch...${RESET}"
    git branch -d $branch
}

add_remote() {
    read -p "Enter the name of the new remote: " remote
    read -p "Enter the URL of the new remote: " url
    echo -e "${GREEN}${BOLD}Adding remote repository $remote...${RESET}"
    git remote add $remote $url
}

rename_remote() {
    read -p "Enter the current name of the remote: " old
    read -p "Enter the new name of the remote: " new
    echo -e "${YELLOW}${BOLD}Renaming remote repository $old to $new...${RESET}"
    git remote rename $old $new
}

remove_remote() {
    read -p "Enter the name of the remote to remove: " remote
    echo -e "${RED}${BOLD}Removing remote repository $remote...${RESET}"
    git remote remove $remote
}

git_config() {
    read -p "Enter your name: " name
    read -p "Enter your email: " email
    echo -e "${GREEN}${BOLD}Setting git configuration...${RESET}"
    git config --global user.name "$name"
    git config --global user.email "$email"
}

merge() {
    read -p "Enter the name of the branch to merge: " branch
    echo -e "${GREEN}${BOLD}Merging branch $branch...${RESET}"
    git merge $branch
}

checkout_branch() {
    read -p "Enter the name of the branch to checkout: " branch
    echo -e "${GREEN}${BOLD}Checking out branch $branch...${RESET}"
    git checkout $branch
}

list_branches() {
    echo -e "${GREEN}${BOLD}Listing all branches...${RESET}"
    git branch
}

list_remotes() {
    echo -e "${GREEN}${BOLD}Listing all remotes...${RESET}"
    git remote -v
}

stash_changes() {
    echo -e "${GREEN}${BOLD}Stashing changes...${RESET}"
    git stash
}

apply_stash() {
    echo -e "${GREEN}${BOLD}Applying stashed changes...${RESET}"
    git stash apply
}

list_stashes() {
    echo -e "${GREEN}${BOLD}Listing all stashes...${RESET}"
    git stash list
}

reset_hard() {
    echo -e "${RED}${BOLD}Resetting repository (hard)...${RESET}"
    git reset --hard
}

reset_soft() {
    echo -e "${YELLOW}${BOLD}Resetting repository (soft)...${RESET}"
    git reset --soft
}

show_log() {
    echo -e "${GREEN}${BOLD}Showing commit log...${RESET}"
    git log
}

fetch() {
    read -p "Enter the remote name: " remote
    echo -e "${GREEN}${BOLD}Fetching from $remote...${RESET}"
    git fetch $remote
}

rebase() {
    read -p "Enter the branch name to rebase onto: " branch
    echo -e "${GREEN}${BOLD}Rebasing onto $branch...${RESET}"
    git rebase $branch
}

cherry_pick() {
    read -p "Enter the commit hash to cherry-pick: " commit
    echo -e "${GREEN}${BOLD}Cherry-picking commit $commit...${RESET}"
    git cherry-pick $commit
}

show_diff() {
    echo -e "${GREEN}${BOLD}Showing diff...${RESET}"
    git diff
}

show_status() {
    echo -e "${GREEN}${BOLD}Showing status...${RESET}"
    git status
}

commit() {
    read -p "Enter the commit message: " message
    echo -e "${GREEN}${BOLD}Committing changes...${RESET}"
    git commit -m "$message"
}

add() {
    read -p "Enter the file or directory to add (use . to add all changes): " file
    echo -e "${GREEN}${BOLD}Adding $file...${RESET}"
    git add $file
}

revert() {
    read -p "Enter the commit hash to revert: " commit
    echo -e "${GREEN}${BOLD}Reverting commit $commit...${RESET}"
    git revert $commit
}

tag() {
    read -p "Enter the tag name: " tag
    echo -e "${GREEN}${BOLD}Creating tag $tag...${RESET}"
    git tag $tag
}

list_tags() {
    echo -e "${GREEN}${BOLD}Listing all tags...${RESET}"
    git tag
}

delete_tag() {
    read -p "Enter the tag name to delete: " tag
    echo -e "${RED}${BOLD}Deleting tag $tag...${RESET}"
    git tag -d $tag
}

show_commit() {
    read -p "Enter the commit hash: " commit
    echo -e "${GREEN}${BOLD}Showing commit $commit...${RESET}"
    git show $commit
}

declare -A shortcuts

add_shortcut() {
    read -p "Enter the name of the shortcut: " name
    read -p "Enter the commands for the shortcut (separated by &&): " commands
    shortcuts[$name]=$commands
    echo -e "${GREEN}${BOLD}Shortcut $name added.${RESET}"
}

execute_shortcut() {
    read -p "Enter the name of the shortcut to execute: " name
    if [ -z "${shortcuts[$name]}" ]; then
        echo -e "${RED}${BOLD}Shortcut $name does not exist.${RESET}"
    else
        echo -e "${GREEN}${BOLD}Executing shortcut $name...${RESET}"
        eval "${shortcuts[$name]}"
    fi
}

show_tree() {
    echo -e "${GREEN}${BOLD}Showing git tree graph...${RESET}"
    git log --all --decorate --oneline --graph
}

# -- main --

while true; do
echo -e "${GREEN}${BOLD}Please select an option:${RESET}"
echo "1. Push"
echo "2. Pull"
echo "3. Get current branch"
echo "4. Create branch"
echo "5. Delete branch"
echo "6. Add remote"
echo "7. Rename remote"
echo "8. Remove remote"
echo "9. Set git config"
echo "10. Merge"
echo "11. Checkout branch"
echo "12. List branches"
echo "13. List remotes"
echo "14. Stash changes"
echo "15. Apply stash"
echo "16. List stashes"
echo "17. Reset hard"
echo "18. Reset soft"
echo "19. Show log"
echo "20. Fetch"
echo "21. Rebase"
echo "22. Cherry-pick"
echo "23. Show diff"
echo "24. Show status"
echo "25. Commit"
echo "26. Add"
echo "27. Revert"
echo "28. Tag"
echo "29. List tags"
echo "30. Delete tag"
echo "31. Show commit"
echo "32. Add shortcut"
echo "33. Execute shortcut"
echo "34. Exit"
echo -e "${GREEN}Enter your choice: ${RESET}"; read CHOICE
    case ${CHOICE} in
        1) push;;
        2) pull;;
        3) get_branch;;
        4) create_branch;;
        5) delete_branch;;
        6) add_remote;;
        7) rename_remote;;
        8) remove_remote;;
        9) git_config;;
        10) merge;;
        11) checkout_branch;;
        12) list_branches;;
        13) list_remotes;;
        14) stash_changes;;
        15) apply_stash;;
        16) list_stashes;;
        17) reset_hard;;
        18) reset_soft;;
        19) show_log;;
        20) fetch;;
        21) rebase;;
        22) cherry_pick;;
        23) show_diff;;
        24) show_status;;
        25) commit;;
        26) add;;
        27) revert;;
        28) tag;;
        29) list_tags;;
        30) delete_tag;;
        31) show_commit;;
        32) add_shortcut;;
        33) execute_shortcut;;
        34) break;;
        *) echo -e "${RED}${BOLD}Invalid option.${RESET}";;
    esac
done