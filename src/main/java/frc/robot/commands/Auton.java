// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.RollerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LimelightSubsystem;


public class Auton extends Command {
  public RollerSubsystem roller;
  public ArmSubsystem arm;
  public ShooterSubsystem shooter;
  public DriveSubsystem drive;
  public LimelightSubsystem limelight;
  private Timer time;


  public Auton(DriveSubsystem drive, ArmSubsystem arm, RollerSubsystem roller, ShooterSubsystem shooter, LimelightSubsystem limelight) {
    this.drive = drive;
    this.arm = arm;
    this.roller = roller;
    this.shooter = shooter;
    this.limelight = limelight;
    addRequirements(drive, arm, roller, shooter, limelight);
    time = new Timer();
    time.restart();
  }

  @Override
  public void initialize() {
    time.restart();
  }

  @Override
  public void execute() {
    new SequentialCommandGroup(
      // Shoot
      new ArmToShooter(arm),
      new ShootSpeaker(roller, shooter),

      // Arm to Floor
      // Drive to Middle Note
      // Spin Roller
      // Arm to Shooter
      // Drive Back to Speaker
      // Align With Speaker
      // Shoot

      new ParallelCommandGroup(
        new ArmToGround(arm),
        new DriveToNote(drive, roller, 8, true)),
      new ParallelCommandGroup(
        new ArmToShooter(arm),
        new DriveBackwards(drive, 5)),
      new ShootSpeaker(roller, shooter)

      // Arm to Floor
      // Turn 30* to Align with Note
      // Drive to Note
      // Spin Roller
      // Arm to Shooter
      // Drive Back to Speaker
      // Align With Speaker
      // Shoot

      // new ParallelCommandGroup(
      //   new ArmToGround(arm),
      //   new DriveToNote(drive, roller, 8, false)),
      //   new TurnToHeading(drive, -90),
      //   new DriveToNote(drive, roller, 7, true),
      //   new TurnToHeading(drive, 55),
      //   new ParallelCommandGroup(
      //     new DriveToSpeaker(drive, limelight),
      //     new ArmToShooter(arm)),
      //   new ShootSpeaker(roller, shooter)
      ).schedule();

  }

  @Override
  public void end(boolean interrupted) {

  }

  @Override
  public boolean isFinished() {
    return false;
  }
} 