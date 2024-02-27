See https://connorsabine.com/2024/02/26/frc-robotics/ for in-depth installation guide.

Install:

1. Install Current Year WPILib (https://docs.wpilib.org/en/stable/docs/zero-to-robot/step-2/wpilib-setup.html)
2. Install Current Years Game Tools (https://docs.wpilib.org/en/stable/docs/zero-to-robot/step-2/frc-game-tools.html)
3. Flash Rio to Current Year (using roboRIO imaging tool installed along with game tools)


Creating a New WPILib Project:

1. Click on the WPILib Command Pallet icon on the top right corner of the VS Code window
2. Select WPILib: Create a new project
3. Select template
4. Select JAVA
5. Select Command Robot
6. Set local base folder
7. Set a project name
8. Set team number (5243)
9. Generate project


CANVenom Install:

1. Click on the WPILib Command Pallet icon on the top right corner of the VS Code window.
2. Select WPILib: Manage Vendor Libraries
3. Then select Install new library (online) end enter the following URL:
- https://www.playingwithfusion.com/frc/playingwithfusion(THE CURRENT YEAR).json
4. Press enter

If everything works, a folder named vendordeps will be created in the root directory of the project.


Setting CANVenom IDS:
- Connect to RIO
- Deploy Code
- Open Driver Station
- Enable
- go to roboRIO online page
- add port 5812 to address (Ex. 172.11.22.2:5812)
- set ids from playing with fusion online page

