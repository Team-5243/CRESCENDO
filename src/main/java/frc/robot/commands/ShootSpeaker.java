// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.RollerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj.Timer;

public class ShootSpeaker extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final RollerSubsystem roller;
  private final ShooterSubsystem shooter;
  private Timer time = new Timer();

  public ShootSpeaker(RollerSubsystem roller, ShooterSubsystem shooter) {
    this.roller = roller;
    this.shooter = shooter;
    addRequirements(roller, shooter);
  }


@Override
  public void initialize() {
    time.restart();
  }


  @Override
  public void execute() {
    if (time.get() < 2){
        shooter.setSpeed(Constants.outtakeSpeed);
    }

    else if (time.get() < 2.5) {
        roller.setSpeed(Constants.rollerOutputNote);
    }

    else if (time.get() > 3) {
        roller.stop();
        shooter.stop();
    }
  }


  @Override
  public void end(boolean interrupted) {}


  @Override
  public boolean isFinished() {
    return (time.get() > 3);
  }
}