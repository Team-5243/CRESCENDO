// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.playingwithfusion.CANVenom;
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


  // Encoders
  public Double[] getPosition(){
    return new Double[]{fl.getPosition(), fr.getPosition(), bl.getPosition(), br.getPosition()};
  }

  public Double[] getSpeed(){
    return new Double[]{fl.getSpeed(), fr.getSpeed(), bl.getSpeed(), br.getSpeed()};
  }

  public void resetEncoders(){
    fl.resetPosition();
    bl.resetPosition();
    fr.resetPosition();
    br.resetPosition();
  }


  // Example Boolean
  public boolean exampleCondition(){
    return false;
  }

  @Override
  public void periodic() {
    // SmartDashboard
    Double[] positions = getPosition();
    SmartDashboard.putNumber("FL Positions", positions[0]);
    SmartDashboard.putNumber("FR Positions", positions[1]);
    SmartDashboard.putNumber("BL Positions", positions[2]);
    SmartDashboard.putNumber("BR Positions", positions[3]);

    Double[] speeds = getSpeed();
    SmartDashboard.putNumber("FL Speed", speeds[0]);
    SmartDashboard.putNumber("FR Speed", speeds[1]);
    SmartDashboard.putNumber("BL Speed", speeds[2]);
    SmartDashboard.putNumber("BR Speed", speeds[3]);

  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}