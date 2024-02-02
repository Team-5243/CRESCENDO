// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.AutonCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.DriveSubsystem;


public class RobotContainer {
  private final DriveSubsystem m_driveSubsystem = new DriveSubsystem();
  private final DriveCommand m_driveCommand = new DriveCommand(m_driveSubsystem);

  public RobotContainer() {
    m_driveSubsystem.setDefaultCommand(m_driveCommand);
  }

  private void configureBindings() {
    
  }

  public Command getAutonomousCommand() {
    return AutonCommand.exampleAuto(m_driveSubsystem);
  }
}