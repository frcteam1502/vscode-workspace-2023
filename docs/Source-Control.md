# Source Control

# GitHub Integration
sync laptop w. github, e.g., login and save credentials

```
git config --global user.email frcteam1502@gmail.com
git config --global user.name "Team 1502"
```
(default `user.name` is over-written using the 'VSCode Personalization' scheme below)


# VSCode Personalization
For shared laptops, it is desirable that the code author gets credit for their `commits`.
One way to do this (without muiltiple user accounts and/or personal folders) is to set an environment
variable so that `git` assigns the commits to the specific person.

1. Make a copy of the "WPILib VS Code" shortcut
2. Below the 'W' logo on the shortcut, click on the text in order to edit it
    a. rename the shortcut something like "NAME VSCode"
    b. arrange the new shortcut(s) to make it easy for the developer to find their personal shortcut
2. Right-click on the copy and select `Properties`
2. Bracket the original `Target` with a CMD executable statement
    a. `C:\Windows\System32\cmd.exe /c "SET GIT_AUTHOR_NAME=Your Name && START /D^"` (previous target) (previous exe)`"`
    b. for example `C:\Windows\System32\cmd.exe /c "SET GIT_AUTHOR_NAME=Your Name && START /D^"C:\Users\Public\wpilib\2023\vscode^" Code.exe"`
3. (Optional) set "Run:" to `minimized`
3. (Optional) You need to click "Change Icon..." if the shortcut changes to a black square
    a. The (2023) WPiLib icon can be found at %SystemDrive%\Users\Public\wpilib\2023\frccode\wpilib-256.ico
3. Click "Apply" to save the changes
4. Execute the shortcut (double-click)
3. Verify the shortcut
    a. Verify VS Code starts
    b. In the terminal, execute `$env:GIT_AUTHOR_NAME`and verify it displays *Your Name*
    c. Make a commit and then check the git log to verify *Your Name* is assinged to the commit
        i. In the VS Code terminal, `git log` will display the log

For Example, to personalize a shortcut for "Hung Nguyen" in 2023, the shortcut would be:
```cmd
C:\Windows\System32\cmd.exe /c "SET GIT_AUTHOR_NAME=Hung Nguyen && START /D^"C:\Users\Public\wpilib\2023\vscode^" Code.exe"
```

# git

## Helpful git alias
Helpful when using git in terminal windows
```
git config --global alias.la 'log --all --decorate --graph --oneline'
```
To see a nice tree of commits, branches, remotes, etc:
type `git la`


## merging a a single sub-folder or "cherry-pick" files

This is an example of merging latest 'docs' from dev into main, but not moving any src\main\java
```
git checkout main
git merge --no-ff --no-commit dev
git reset -- src*
git checkout -- src*
git clean -n
git clean -fd
git commit -a -m "add (just) latest docs"
```

For more information, [see this gist](https://gist.github.com/rotimi-best/dc51f09a373815434dd3a7d578d4648a)