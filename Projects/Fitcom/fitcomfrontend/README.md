# FitCom

## Project setup

### Git

#### Initial commit + Adding remote repositories
```sh

# Setup new local repository
git init # npx create-expo-app creates local repository
git branch -M main # Set master branch to "main"

git remote add github https://github.com/melly3o6/FitCom_TypeScript_Expo.git &&
git remote add gitlab https://git.bbcag.ch/inf-bl/zh/2023/team-c/zkampm/javascript/fitcom.git

git remote -v # displays all remotes
git add .
git commit -m "init"
git push --set-upstream github main && git push --set-upstream gitlab main

# OR 

# Clone remote from gitlab
git clone https://git.bbcag.ch/inf-bl/zh/2023/team-c/zkampm/javascript/fitcom.git
git remote rename origin gitlab

# Clone remote from github
git clone https://github.com/melly3o6/FitCom_TypeScript_Expo.git
git remote rename origin github

# Pull remote repositories
# WSL:
git pull github main && git pull gitlab main

# Powershell:
git pull github main; git pull gitlab main
```

#### Branches

````sh
git branch -M main # Main is now master branch, on main there is always a running version. 

git branch dev # Creates branch named "dev"
git checkout dev # Switches to branch dev

git branch documentation # Branch for documentation
git branch deployment # Branch for deployment
```