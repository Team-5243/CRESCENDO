// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.RollerSubsystem;

public class IntakeNote extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final RollerSubsystem roller;
  private Timer time;

  public IntakeNote(RollerSubsystem roller) {
    this.roller = roller;
    addRequirements(roller);
    time = new Timer();
  }


  @Override
  public void initialize() {
    time.restart();
  }


  @Override
  public void execute() {
    System.out.print(time.get());
    if (time.get() < .6 ){
        roller.setSpeed(Constants.rollerIntakeNote);
    }
  }


  @Override
  public void end(boolean interrupted) {
    roller.stop();
  }


  @Override
  public boolean isFinished() {
    return (time.get() < .6);
  }
}