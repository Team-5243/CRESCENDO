// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;


public class DriveToNote extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveSubsystem m_driveSubsystem;

  public DriveToNote(DriveSubsystem drive) {
    m_driveSubsystem = drive;
    addRequirements(drive);
  }


  @Override
  public void initialize() {

  }


  @Override
  public void execute() {
    m_driveSubsystem.arcadeDrive(Constants.mainStick.getX(), Constants.mainStick.getY());
  }


  @Override
  public void end(boolean interrupted) {}


  @Override
  public boolean isFinished() {
    return false;
  }
}