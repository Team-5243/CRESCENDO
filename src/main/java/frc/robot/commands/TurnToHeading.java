// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.RollerSubsystem;


public class TurnToHeading extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveSubsystem drive;
  private double heading;
  private double tolerance = 3;
  private double yaw;

  public TurnToHeading(DriveSubsystem drive, double heading) {
    this.drive = drive;
    this.heading = heading;
    addRequirements(drive);
  }


  @Override
  public void initialize() {
    drive.getGyro().reset();
  }


  @Override
  public void execute() {
    yaw = drive.getGyro().getYaw();
    if (yaw < heading - tolerance || yaw > heading + tolerance) {
        drive.arcadeDrive(((heading - yaw) / Math.abs(heading - yaw) / 2), 0);
    }
  }


  @Override
  public void end(boolean interrupted) {
  }


  @Override
  public boolean isFinished() {
    return !(yaw < heading - tolerance || yaw > heading + tolerance);
  }
}