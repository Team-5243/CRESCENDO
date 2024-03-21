// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.playingwithfusion.CANVenom;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;   


public class DriveSubsystem extends SubsystemBase {

  // Define Motor Controllers
  public CANVenom fl;
  public CANVenom fr;
  public CANVenom bl;
  public CANVenom br;
  public DifferentialDrive diffDriveFront;
  public DifferentialDrive diffDriveBack;
  public AHRS gyro;
  
  DigitalInput throughBoreInputRight;
  DutyCycleEncoder throughBoreEncoderRight;

  DigitalInput throughBoreInputLeft;
  DutyCycleEncoder throughBoreEncoderLeft;

  public DifferentialDrivePoseEstimator drivePoseEstimator;
  public DifferentialDriveKinematics driveKinematics;
  public Field2d field;


  public DriveSubsystem() {
    // Create CANVenoms
    fl = new CANVenom(Constants.FL);
    fr = new CANVenom(Constants.FR);
    bl = new CANVenom(Constants.BL);
    br = new CANVenom(Constants.BR);
  

    // Create Differential Drive with leading CANVenoms
    diffDriveFront = new DifferentialDrive(fl, fr);
    diffDriveBack = new DifferentialDrive(bl, br);

    // Set Break or Coast Mode
    setBreakMode();

    // Create Objects
    throughBoreInputLeft = new DigitalInput(Constants.DriveBoreLeft);
    throughBoreEncoderLeft = new DutyCycleEncoder(throughBoreInputLeft);
    // Encoder left = new Encoder(0, 1);

    throughBoreInputRight = new DigitalInput(Constants.DriveBoreRight);
    throughBoreEncoderRight = new DutyCycleEncoder(throughBoreInputRight);

    // Resets
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


  // Arcade Drive Using Joysticks (Double Diff Drive)
  public void arcadeDrive(double speed, double rotation){
    diffDriveFront.arcadeDrive(speed, rotation);
    diffDriveBack.arcadeDrive(speed, rotation);
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
    throughBoreEncoderRight.reset();
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


  @Override
  public void periodic() {
    // Pose Estimation
    drivePoseEstimator.update(new Rotation2d(gyro.getAngle()), getMeters(fl), getMeters(fr));
    // drivePoseEstimator.addVisionMeasurement(null, 0);
    field.setRobotPose(drivePoseEstimator.getEstimatedPosition());

    SmartDashboard.putNumber("Left", throughBoreEncoderLeft.get());
    SmartDashboard.putNumber("Right", throughBoreEncoderRight.get());

  };
}