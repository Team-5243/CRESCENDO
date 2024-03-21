// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LimelightSubsystem;

public class PoseEstimation extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveSubsystem drive;
  private final LimelightSubsystem limelight;
  private Timer time;

  public DifferentialDrivePoseEstimator drivePoseEstimator;
  public DifferentialDriveKinematics driveKinematics;
  public Field2d field;


  public PoseEstimation(DriveSubsystem drive, LimelightSubsystem limelight) {
    this.drive = drive;
    this.limelight = limelight;
    addRequirements(drive, limelight);
    this.time = new Timer();
  }


  @Override
  public void initialize() {
    // Create Drive Kinematics
    driveKinematics = new DifferentialDriveKinematics(Constants.driveTrackWidth);

    // Create Diff Drive Pose Estimator
    drivePoseEstimator = new DifferentialDrivePoseEstimator(driveKinematics, new Rotation2d(drive.getGyro().getAngle()), 
      drive.getMetersLeft(), drive.getMetersRight(), new Pose2d());

    field = new Field2d();
    SmartDashboard.putData(field);
  }


  @Override
  public void execute() {
    // Pose Estimation
    drivePoseEstimator.update(new Rotation2d(drive.getGyro().getAngle()), drive.getMetersLeft(), drive.getMetersRight());
    // drivePoseEstimator.addVisionMeasurement(null, 0);
    field.setRobotPose(drivePoseEstimator.getEstimatedPosition());
  }


  @Override
  public void end(boolean interrupted) {

  }


  @Override
  public boolean isFinished() {
    return false;
  }
}