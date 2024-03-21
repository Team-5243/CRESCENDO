// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.RollerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.DriveSubsystem;


public class Auton extends Command {
  public RollerSubsystem roller;
  public ArmSubsystem arm;
  public ShooterSubsystem shooter;
  public DriveSubsystem drive;
  private Timer time;


  public Auton(DriveSubsystem drive, ArmSubsystem arm, RollerSubsystem roller, ShooterSubsystem shooter) {
    this.drive = drive;
    this.arm = arm;
    this.roller = roller;
    this.shooter = shooter;
    addRequirements(drive, arm, roller, shooter);
    time = new Timer();
    time.restart();
  }

  @Override
  public void initialize() {
    time.restart();
  }

  @Override
  public void execute() {
    SmartDashboard.putNumber("auton timer", time.get());
    SmartDashboard.putNumber("fpga time", Timer.getFPGATimestamp());
    if (!time.hasElapsed(2)){
      shooter.setSpeed(Constants.outtakeSpeed);
    }

    else if (!time.hasElapsed(3)) {
      roller.setSpeed(Constants.rollerOutputNote);
    }
  }

  @Override
  public void end(boolean interrupted) {
    roller.stop();
    shooter.stop();
  }

  @Override
  public boolean isFinished() {
    return time.hasElapsed(3.2);
  }
} 