# Formatting Conventions

## Back Ticks

`Code` and sometimes `other text` is surrounded (*like this ->*) by `back-ticks`.

**IMPORTANT:** When copying the code for use, do not copy the back ticks.


# Laptops
* Laptop-1 - SHORT CIRCUIT
* Laptop-2 - STATIC SHOCK
* Laptop-3 - LOOSE SCREWS
* Laptop-4 - GLITCH
* Laptop-5 - CRASH
* Laptop-6 - GREMLINS

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

# TODO
* [ ] Make a script to do the install for year 20XX
