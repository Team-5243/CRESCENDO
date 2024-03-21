// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.playingwithfusion.CANVenom;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
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
  Encoder left;
  Encoder right;


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
    left = new Encoder(Constants.DriveBoreLeft, Constants.DriveBoreLeft+1);
    right = new Encoder(Constants.DriveBoreRight, Constants.DriveBoreRight+1);

    // Resets
    resetEncoders();

    // Create Gyro
    gyro = new AHRS();
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
    right.reset();
    left.reset();
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
  public double getMetersLeft(){
    return left.get() * (Constants.driveWheelCircumference);
  }

  public double getMetersRight(){
    return right.get() * (Constants.driveWheelCircumference);
  }


  @Override
  public void periodic() {
    SmartDashboard.putNumber("Left", left.get());
    SmartDashboard.putNumber("Right", right.get());
  };
}