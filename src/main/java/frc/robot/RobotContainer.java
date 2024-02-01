// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;


public class RobotContainer {
  private final DriveSubsystem m_driveSubsystem = new DriveSubsystem();

  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    new Trigger(m_driveSubsystem::exampleCondition)
        .onTrue(new DriveCommand(m_driveSubsystem));

    m_driverController.b().whileTrue(m_driveSubsystem.driveMethodCommand());
  }

  public Command getAutonomousCommand() {
    return Autos.exampleAuto(m_driveSubsystem);
  }
}