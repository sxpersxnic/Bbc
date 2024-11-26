# - ASCII -
# COLORS
$RED = "`e[38;2;255;0;0m"
$BLUE = "`e[38;2;0;0;255m"
$GREEN = "`e[38;2;0;200;0m"
$YELLOW = "`e[38;2;255;255;0m"
$ORANGE = "`e[38;2;255;100;0m"
$PINK = "`e[38;2;255;0;255m"
$WHITE = "`e[38;2;255;255;255m"
$RESET = "`e[0m"

# FONT
$BOLD = "`e[1m"
$ITALIC = "`e[3m"

function push {
    $remote = Read-Host "Enter the remote name: "
    Write-Host "Pushing to $remote..."
    git push $remote HEAD
}

function pull {
    Write-Host "Pulling..."
    git pull
}

function get_branch {
    Write-Host "Current branch:"
    git branch --show-current
}

function create_branch {
    $branch = Read-Host "Enter the name of the new branch: "
    Write-Host "Creating new branch $branch..."
    git branch $branch
}

function delete_branch {
    $branch = Read-Host "Enter the name of the branch to delete: "
    Write-Host "Deleting branch $branch..."
    git branch -d $branch
}

function add_remote {
    $remote = Read-Host "Enter the name of the new remote"
    $url = Read-Host "Enter the URL of the new remote"
    Write-Host "Adding remote repository $remote..."
    git remote add $remote $url
}

function rename_remote {
    $old = Read-Host "Enter the current name of the remote"
    $new = Read-Host "Enter the new name of the remote"
    Write-Host "Renaming remote repository $old to $new..."
    git remote rename $old $new
}

function remove_remote {
    $remote = Read-Host "Enter the name of the remote to remove"
    Write-Host "Removing remote repository $remote..."
    git remote remove $remote
}

function git_config {
    $name = Read-Host "Enter your name"
    $email = Read-Host "Enter your email"
    Write-Host "Setting git configuration..."
    git config --global user.name "$name"
    git config --global user.email "$email"
}

function merge {
    $branch = Read-Host "Enter the name of the branch to merge"
    Write-Host "Merging branch $branch..."
    git merge $branch
}

function checkout_branch {
    $branch = Read-Host "Enter the name of the branch to checkout"
    Write-Host "Checking out branch $branch..."
    git checkout $branch
}

function list_branches {
    Write-Host "Listing all branches..."
    git branch
}

function list_remotes {
    Write-Host "Listing all remotes..."
    git remote -v
}

function stash_changes {
    Write-Host "Stashing changes..."
    git stash
}

function apply_stash {
    Write-Host "Applying stashed changes..."
    git stash apply
}

function list_stashes {
    Write-Host "Listing all stashes..."
    git stash list
}

function reset_hard {
    Write-Host "Resetting repository (hard)..."
    git reset --hard
}

function reset_soft {
    Write-Host "Resetting repository (soft)..."
    git reset --soft
}

function show_log {
    Write-Host "Showing commit log..."
    git log
}

function fetch {
    $remote = Read-Host "Enter the remote name"
    Write-Host "Fetching from $remote..."
    git fetch $remote
}

function rebase {
    $branch = Read-Host "Enter the branch name to rebase onto"
    Write-Host "Rebasing onto $branch..."
    git rebase $branch
}

function cherry_pick {
    $commit = Read-Host "Enter the commit hash to cherry-pick"
    Write-Host "Cherry-picking commit $commit..."
    git cherry-pick $commit
}

function show_diff {
    Write-Host "Showing diff..."
    git diff
}

function show_status {
    Write-Host "Showing status..."
    git status
}

function commit {
    $message = Read-Host "Enter the commit message"
    Write-Host "Committing changes..."
    git commit -m "$message"
}

function add {
    $file = Read-Host "Enter the file or directory to add (use . to add all changes)"
    Write-Host "Adding $file..."
    git add $file
}

function revert {
    $commit = Read-Host "Enter the commit hash to revert"
    Write-Host "Reverting commit $commit..."
    git revert $commit
}

function tag {
    $tag = Read-Host "Enter the tag name"
    Write-Host "Creating tag $tag..."
    git tag $tag
}

function list_tags {
    Write-Host "Listing all tags..."
    git tag
}

function delete_tag {
    $tag = Read-Host "Enter the tag name to delete"
    Write-Host "Deleting tag $tag..."
    git tag -d $tag
}

function show_commit {
    $commit = Read-Host "Enter the commit hash"
    Write-Host "Showing commit $commit..."
    git show $commit
}

$shortcuts = @{}

function add_shortcut {
    $name = Read-Host "Enter the name of the shortcut"
    $commands = Read-Host "Enter the commands for the shortcut (separated by &&)"
    $shortcuts[$name] = $commands
    Write-Host "Shortcut $name added."
}

function execute_shortcut {
    $name = Read-Host "Enter the name of the shortcut to execute"
    if ($null -eq $shortcuts[$name]) {
        Write-Host "Shortcut $name does not exist."
    } else {
        Write-Host "Executing shortcut $name..."
        Invoke-Expression $shortcuts[$name]
    }
}

function show_tree {
    Write-Host "Showing git tree graph..." -ForegroundColor $GREEN
    git log --all --decorate --oneline --graph
}

# -- main --

while ($true) {
    Write-Host "Please select an option:"
    Write-Host "1. Push"
    Write-Host "2. Pull"
    Write-Host "3. Get current branch"
    Write-Host "4. Create branch"
    Write-Host "5. Delete branch"
    Write-Host "6. Add remote"
    Write-Host "7. Rename remote"
    Write-Host "8. Remove remote"
    Write-Host "9. Set git config"
    Write-Host "10. Merge"
    Write-Host "11. Checkout branch"
    Write-Host "12. List branches"
    Write-Host "13. List remotes"
    Write-Host "14. Stash changes"
    Write-Host "15. Apply stash"
    Write-Host "16. List stashes"
    Write-Host "17. Reset hard"
    Write-Host "18. Reset soft"
    Write-Host "19. Show log"
    Write-Host "20. Fetch"
    Write-Host "21. Rebase"
    Write-Host "22. Cherry-pick"
    Write-Host "23. Show diff"
    Write-Host "24. Show status"
    Write-Host "25. Commit"
    Write-Host "26. Add"
    Write-Host "27. Revert"
    Write-Host "28. Tag"
    Write-Host "29. List tags"
    Write-Host "30. Delete tag"
    Write-Host "31. Show commit"
    Write-Host "32. Add shortcut"
    Write-Host "33. Execute shortcut"
    Write-Host "34. Exit"
    $CHOICE = Read-Host "Enter your choice:"
    switch ($CHOICE) {
        1 { push }
        2 { pull }
        3 { get_branch }
        4 { create_branch }
        5 { delete_branch }
        6 { add_remote }
        7 { rename_remote }
        8 { remove_remote }
        9 { git_config }
        10 { merge }
        11 { checkout_branch }
        12 { list_branches }
        13 { list_remotes }
        14 { stash_changes }
        15 { apply_stash }
        16 { list_stashes }
        17 { reset_hard }
        18 { reset_soft }
        19 { show_log }
        20 { fetch }
        21 { rebase }
        22 { cherry_pick }
        23 { show_diff }
        24 { show_status }
        25 { commit }
        26 { add }
        27 { revert }
        28 { tag }
        29 { list_tags }
        30 { delete_tag }
        31 { show_commit }
        32 { add_shortcut }
        33 { execute_shortcut }
        34 { exit }
        default { Write-Host "Invalid option." }
    }
}
