// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;


public class DriveCommand extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveSubsystem m_driveSubsystem;

  public DriveCommand(DriveSubsystem subsystem) {
    m_driveSubsystem = subsystem;
    addRequirements(subsystem);
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}


  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_driveSubsystem.arcadeDrive();
    Double[] positions = m_driveSubsystem.getPos();
    SmartDashboard.putNumber("FL Pos", positions[0]);
    SmartDashboard.putNumber("FR Pos", positions[1]);
    SmartDashboard.putNumber("BL Pos", positions[2]);
    SmartDashboard.putNumber("BR Pos", positions[3]);
  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}


  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}