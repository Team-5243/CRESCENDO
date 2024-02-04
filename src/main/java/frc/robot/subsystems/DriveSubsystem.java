// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.playingwithfusion.CANVenom;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
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
  public Pose2d estimatedPose;

  public DriveSubsystem() {
    // Create CANVenoms
    fl = new CANVenom(Constants.FL);
    fr = new CANVenom(Constants.FR);
    bl = new CANVenom(Constants.BL);
    br = new CANVenom(Constants.BR);

    // Create Pose
    estimatedPose = new Pose2d();
    
    // Set CANVenom to Follow Mode
    bl.follow(fl);
    br.follow(fr);

    // Create Differential Drive with leading CANVenoms
    diffDrive = new DifferentialDrive(fl, fr);

    // Set Break or Coast Mode
    setBreakMode();

    // Reset Encoders
    resetEncoders();
  }
  

  public Command driveMethodCommand() {
    return runOnce(
        () -> {
          /* one-time action goes here */
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


  // Gyro
  public AHRS getGyro(){
    return gyro;
  }

  
  // Pose Estimation
  public Pose2d getPose(){
    return estimatedPose;
  }

  
  // Displacement (Get Position might not return what we expect)
  public double getLeftDisplacement(){
    return fl.getPosition() / (Constants.wheelCircumference * Constants.drivetrainGearRatio);
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

  public void resetEncoders(){
    fl.resetPosition();
    bl.resetPosition();
    fr.resetPosition();
    br.resetPosition();
  }


  @Override
  public void periodic() {
    // SmartDashboard
    Double[] positions = getPosition();
    Double[] speeds = getSpeed();
    Double[] temperatures = getTemperature();

    // Front Left
    SmartDashboard.putNumber("FL Position", positions[0]);
    SmartDashboard.putNumber("FL Speed", speeds[0]);
    SmartDashboard.putNumber("FL Temperature", temperatures[0]);

    // Front Right
    SmartDashboard.putNumber("FR Position", positions[1]);
    SmartDashboard.putNumber("FR Speed", speeds[1]);
    SmartDashboard.putNumber("FR Temperature", temperatures[1]);
    
    // Back Left
    SmartDashboard.putNumber("BL Position", positions[2]);
    SmartDashboard.putNumber("BL Speed", speeds[2]);
    SmartDashboard.putNumber("BL Temperature", temperatures[2]);

    // Back Right
    SmartDashboard.putNumber("BR Position", positions[3]);
    SmartDashboard.putNumber("BR Speed", speeds[3]);
    SmartDashboard.putNumber("BR Temperature", temperatures[3]);

    // Pose Estimation (GET SPEED RETURNS RPM NOT VELOCITY, CONVERT TO VELOCITY)
    double leftVelocity = fl.getSpeed()/(Constants.wheelCircumference * Constants.drivetrainGearRatio);
    double rightVelocity = fr.getSpeed()/(Constants.wheelCircumference * Constants.drivetrainGearRatio);
    Pose2d poseChange = new Pose2d(new Translation2d(-(leftVelocity + rightVelocity) / 2d, 0d), new Rotation2d((leftVelocity - rightVelocity) / (2d * Constants.wheelSeperationDistance)));
    estimatedPose.minus(poseChange);
  }
}