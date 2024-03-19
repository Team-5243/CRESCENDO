// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.AutonCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.OuttakeAndIntakeCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.OuttakeAndIntakeSubsystem;


public class RobotContainer {
  private final DriveSubsystem m_driveSubsystem = new DriveSubsystem();
  private final DriveCommand m_driveCommand = new DriveCommand(m_driveSubsystem);
  private final OuttakeAndIntakeSubsystem m_outtakeAndIntakeSubsystem = new OuttakeAndIntakeSubsystem();
  private final OuttakeAndIntakeCommand m_outtakeAndIntakeCommand = new OuttakeAndIntakeCommand(m_outtakeAndIntakeSubsystem);
  private final AutonCommand m_autonCommand = new AutonCommand(m_outtakeAndIntakeSubsystem);


  public RobotContainer() {
    m_driveSubsystem.setDefaultCommand(m_driveCommand);
    m_outtakeAndIntakeSubsystem.setDefaultCommand(m_outtakeAndIntakeCommand);
  }

  public Command getAutonomousCommand() {
    return m_autonCommand;
  }
}