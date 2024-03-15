// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.playingwithfusion.CANVenom;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelPositions;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;   


public class DriveSubsystem extends SubsystemBase {

  // Define Motor Controllers
  public CANVenom fl;
  public CANVenom fr;
  public CANVenom bl;
  public CANVenom br;
  public DifferentialDrive diffDrive;
  public AHRS gyro;
  
  public DifferentialDrivePoseEstimator drivePoseEstimator;
  public DifferentialDriveKinematics driveKinematics;
  public Field2d field;


  public DriveSubsystem() {
    // Create CANVenoms
    fl = new CANVenom(Constants.FL);
    fr = new CANVenom(Constants.FR);
    bl = new CANVenom(Constants.BL);
    br = new CANVenom(Constants.BR);

    // Set CANVenom to Follow Mode
    bl.follow(fl);
    br.follow(fr);

    // Create Differential Drive with leading CANVenoms
    diffDrive = new DifferentialDrive(fl, fr);

    // Set Break or Coast Mode
    setBreakMode();

    // Reset Encoders
    resetEncoders();

    // Create Gyro
    gyro = new AHRS();

    // Create Drive Kinematics
    driveKinematics = new DifferentialDriveKinematics(Constants.driveTrackWidth);

    // Create Diff Drive Pose Estimator
    drivePoseEstimator = new DifferentialDrivePoseEstimator(driveKinematics, new Rotation2d(gyro.getAngle()), 
      getMeters(fl), getMeters(fr), new Pose2d());

    field = new Field2d();
    SmartDashboard.putData(field);

  }
  

  public Command driveForwardCommand() {
    return runOnce(
        () -> {
          diffDrive.arcadeDrive(.5, 0);
        });
  }


  // Arcade Drive Using Joysticks
  public void arcadeDrive(double speed, double rotation){
    diffDrive.arcadeDrive(speed, rotation);
  } 


  // Drive Commands
  public void setCoastMode(){
    fl.setBrakeCoastMode(CANVenom.BrakeCoastMode.Coast);
    fr.setBrakeCoastMode(CANVenom.BrakeCoastMode.Coast);
    bl.setBrakeCoastMode(CANVenom.BrakeCoastMode.Coast);
    br.setBrakeCoastMode(CANVenom.BrakeCoastMode.Coast);
  }

  public void setBreakMode(){
    fl.setBrakeCoastMode(CANVenom.BrakeCoastMode.Brake);
    fr.setBrakeCoastMode(CANVenom.BrakeCoastMode.Brake);
    bl.setBrakeCoastMode(CANVenom.BrakeCoastMode.Brake);
    br.setBrakeCoastMode(CANVenom.BrakeCoastMode.Brake);
  }


  // Reset Encoders
  public void resetEncoders(){
    fl.resetPosition();
    bl.resetPosition();
    fr.resetPosition();
    br.resetPosition();
  }


  // Gyro
  public AHRS getGyro(){
    return gyro;
  }


  // Motor Meters Traveled
  public double getMeters(CANVenom motor){
    return motor.getPosition() / (Constants.driveWheelCircumference * Constants.driveGearRatio);

  }


  // Encoders
  public Double[] getPosition(){
    return new Double[]{fl.getPosition(), fr.getPosition(), bl.getPosition(), br.getPosition()};
  }

  public Double[] getSpeed(){
    return new Double[]{fl.getSpeed(), fr.getSpeed(), bl.getSpeed(), br.getSpeed()};
  }

  public Double[] getTemperature(){
    return new Double[]{fl.getTemperature(), fr.getTemperature(), bl.getTemperature(), br.getTemperature()};
  }


  // Get Encoder Data
  public boolean safetyCheck(){
    for (double value : getTemperature()){
      if (value > 100){
        return true;
      }
    }
    return false;
  }


  @Override
  public void periodic() {
    // Pose Estimation
    drivePoseEstimator.update(new Rotation2d(gyro.getAngle()), getMeters(fl), getMeters(fr));
    // drivePoseEstimator.addVisionMeasurement(null, 0);
    field.setRobotPose(drivePoseEstimator.getEstimatedPosition());

    // SmartDashboard Safety Check
    SmartDashboard.putBoolean("Safe?", safetyCheck());
  };
}