# Formatting Conventions

## Back Ticks

`Code` and sometimes `other text` is surrounded (*like this ->*) by `back-ticks`.

**IMPORTANT:** When copying the code for use, do not copy the back ticks.


# Laptops
Laptop   | Nickname      |Win   | Account  | User Folder   | root
---      |---            |---   |---       |---            |---
Laptop-1 | SHORT CIRCUIT |      | MS: DK   | frct          | \Repose\vscode-workspace-2023
Laptop-2 | STATIC SHOCK  |      | MS: DK   |               |
Laptop-3 | LOOSE SCREWS  |      | MS       |               |
Laptop-4 | GLITCH        |11 Pro| local    | Team 1502     | \repos\vscode-workspace-2023
Laptop-5 | CRASH         |11 Pro| MS: DK   | frct          | \repos\vscode-workspace-2023
Laptop-6 | GREMLINS      |11 Pro| MS: DK   | frcte         | \repos\vscode-workspace-2023

MS: Microsoft Account (Team 1502 - frcteam1502@gmail.com)
MS: DK -- Some laptops are sharing a desktop via OneDrive (intentional ?!?)
* Laptop-5 has desktop and documents (including repos) in OneDrive somehow
* Laptop-6 has desktop and documents (including repos) in OneDrive somehow

**NOTE:** Saving your repos on OneDrive could result in really difficult errors to diagonose and fix, e.g., "corrupted" files


# WPI Lib Preparation

# GitHub Integration
sync laptop w. github, e.g., login and save credentials

```
git config --global user.email frcteam1502@gmail.com
git config --global user.name "Team 1502"
```
(default `user.name` is over-written using the 'VSCode Personalization' scheme below)

## Helpful git alias
Helpful when using git in terminal windows
```
git config --global alias.la 'log --all --decorate --graph --oneline'
```
To see a nice tree of commits, branches, remotes, etc:
type `git la`


Do not change the default (Edge) browser.
The laptops are (or may) be synced to GitHub through the browser
Microsoft built/owns VSCode, GitHub and Edge; 
remember these are **shared**.

ERROR: when trying to checkin with web browser (MS User Account: frcteam1502)
http://localhost:50310/?code=702118c2b5b2cc7ddb0e&state=94953bf8e1774ee080c7be2df8b2b645

## Verify
1. open "Windows Credential Manager"

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
## Standard VS Code
Unknown what-all is in WPI-VSCode, but if you want/need to use Standard VSCode
To set environment(like `JAVA_HOME`) from WPI lib:
1. execute `. C:\Users\Public\wpilib\2023\frccode\frcvars2023.ps1` from PowerShell
2. Confirm `$env:JAVA_HOME` has a valid path (e.g. C:\Users\Public\wpilib\2023\jdk)
3. execute `.\gradlew.bat build` to confirm Java build works

# Nerd Fonts
Download and install `CaskaydiaCove` from https://www.nerdfonts.com/font-downloads
1. Download and unzip the font files, extract fonts to a folder
2. Click "Windows Key", search for "Font", open Font Settings
3. Drag (just) font files from unzipped font folder into Font Settings drop area

VSCode settings.json:
```
  "terminal.integrated.fontFamily": "CaskaydiaCove NF Mono"
```

aliases: CaskaydiaCove NFM, CaskaydiaCove Nerd Font Mono ??

# TODO
* [ ] Make a script to do the install for year 20XX
