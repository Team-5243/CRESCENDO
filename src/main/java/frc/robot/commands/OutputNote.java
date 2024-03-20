// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.RollerSubsystem;

public class OutputNote extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final RollerSubsystem roller;

  public OutputNote(RollerSubsystem roller) {
    this.roller = roller;
    addRequirements(roller);
  }


  @Override
  public void initialize() {

  }


  @Override
  public void execute() {
    roller.setSpeed(Constants.rollerOutputNote);
  }


  @Override
  public void end(boolean interrupted) {}


  @Override
  public boolean isFinished() {
    return false;
  }
}