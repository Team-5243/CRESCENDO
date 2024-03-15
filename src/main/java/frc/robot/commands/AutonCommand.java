// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;
import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public final class AutonCommand {
  public static Command mainAuton(DriveSubsystem subsystem) {
    return Commands.sequence(subsystem.driveForwardCommand(), new DriveCommand(subsystem));
  }

  private AutonCommand() {
    
  }
}