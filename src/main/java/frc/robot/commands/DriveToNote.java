// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.RollerSubsystem;


public class DriveToNote extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveSubsystem drive;
  private final RollerSubsystem roller;
  private double distance;
  private double left;
  private double right;
  private boolean intake;

  public DriveToNote(DriveSubsystem drive, RollerSubsystem roller, double distance, boolean intake) {
    this.drive = drive;
    this.roller = roller;
    this.intake = intake;
    this.distance = distance;
    addRequirements(drive);
  }


  @Override
  public void initialize() {
    drive.resetEncoders();
    left = drive.getMetersLeft();
    right = drive.getMetersRight();
  }


  @Override
  public void execute() {
    if (intake) {
        roller.setSpeed(Constants.rollerIntakeNote);
    }
    drive.arcadeDrive(0, -0.5);
  }


  @Override
  public void end(boolean interrupted) {
    roller.stop();
  }


  @Override
  public boolean isFinished() {
    return (drive.getMetersLeft() > left + (distance*0.0254)) || (drive.getMetersRight() > right + (distance*0.0254));
  }
}