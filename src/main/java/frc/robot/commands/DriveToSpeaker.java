// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LimelightSubsystem;


public class DriveToSpeaker extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveSubsystem m_driveSubsystem;
  private final LimelightSubsystem m_limelightSubsystem;
  private double checkTurn = 1;
  private double checkForward = 1;

  public DriveToSpeaker(DriveSubsystem drive, LimelightSubsystem limelight) {
    m_driveSubsystem = drive;
    m_limelightSubsystem = limelight;
    addRequirements(drive, limelight);
  }


  @Override
  public void initialize() {
    m_limelightSubsystem.setLedMode(Constants.limelight1, "on");
  }


  @Override
  public void execute() {
    m_limelightSubsystem.resetSpeeds();
    m_limelightSubsystem.alignWithSpeaker(NetworkTableInstance.getDefault().getTable(Constants.limelight1));
    checkForward = m_limelightSubsystem.forwardSpeed;
    checkTurn = m_limelightSubsystem.xSpeed;
    m_driveSubsystem.arcadeDrive(m_limelightSubsystem.xSpeed, m_limelightSubsystem.forwardSpeed);
  }


  @Override
  public void end(boolean interrupted) {
    m_limelightSubsystem.setLedMode(Constants.limelight1, "off");
  }


  @Override
  public boolean isFinished() {
    return checkTurn == 0 && checkForward == 0;
  }
}