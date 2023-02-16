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

**NOTE:** Saving your repos on OneDrive could result in really difficult errors to diagnose and fix, e.g., "corrupted" files


# WPI Lib Preparation
For more information on using WPI Lib for Team 1502, see [WPI Lib](/docs/WpiLib.md)


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

# VSCode

## Build
If using WPI-flavored VsCode:
* in the Explorer (ctrl+shift+E), hover over the "Java Projects" section, click on the wrench and screwdriver icon (Rebuild All)
* or type `ctrl+shift+p` to bring up commands and search for "wpi", pick "Build"

For more information on using WPI Lib for Team 1502, see [WPI Lib](/docs/WpiLib.md/#Build)

## Deploy
If using WPI-flavored VsCode:
* use `shift+F5` to build and deploy
* or type `ctrl+shift+P` to bring up commands and search for "wpi", pick "Deploy"

For more information on using WPI Lib for Team 1502, see [WPI Lib](/docs/WpiLib.md/#Deploy)

# VSCode Personalization
For shared laptops, it is desirable that the code author gets credit for their `commits`.
One way to do this (without muiltiple user accounts and/or personal folders) is to set an environment
variable so that `git` assigns the commits to the specific person.

For details on how to set up personalization, see [VSCode Personalization](/docs/Source-Control.md/#VSCode-Personalization)


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
