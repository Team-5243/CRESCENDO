// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;


public class DriveBackwards extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveSubsystem drive;
  private double distance;
  private double left;
  private double right;
  Timer time = new Timer();

  public DriveBackwards(DriveSubsystem drive, double distance) {
    this.drive = drive;
    this.distance = distance;
    addRequirements(drive);
  }


  @Override
  public void initialize() {
    time.restart();
    drive.resetEncoders();
    left = drive.getMetersLeft();
    right = drive.getMetersRight();
  }


  @Override
  public void execute() {
    drive.arcadeDrive(0, 0.5);
  }


  @Override
  public void end(boolean interrupted) {
  }


  @Override
  public boolean isFinished() {
    return (drive.getGyro().getVelocityX() < .1 && time.hasElapsed(2));
  }
}