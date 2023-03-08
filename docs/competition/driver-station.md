# Code Hygiene
Normal development/test pipeline will be highly compressed by deadline attrition, hardware availibility for testing.

So as soon as possible make a "match" or "competition" branch with the first working subsystem, e.g., drive

Under the supervision of code-integrator and team lead and the additional subsystems




# Driver Station Laptop
Do not use Driver Station Laptop as a development machine
* Do not want gradle daemons running while you are driving
* Driver Station Laptop will not be available: on-field, in-queue, practice field, under-going repairs

Do have Driver Station Laptop all up-to-date
* consider finding a way to Deploy *official* competition code
* make sure it has git/github configured so it can pull files; commit and push in dire emergenies

THERE WILL NOT BE TIME TO MAKE MAJOR CHANGES ON THE FLY

# Coding Laptop
Use a development laptop to hotfix the official code and deploy it.
Have anticipated mods, ready, or easy to apply.
* Bring an extra xbox controller (or two) for use with the coding/testing laptop.

# Diagnostics and Testing
* Basic connection, e.g., does the CAN ID exist? Is it wired in, connected.
* Basic sensor, e.g., encoder, does the position move
* basic move, send speed command, e.g., joystick
* basic move and diagnostic, e.g., go to position, check encoder, power, break or coast
## Bot specific
* run diagnostics with bot parameters, e.g., drive fwd, rev, etc