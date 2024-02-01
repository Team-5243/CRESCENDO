// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.playingwithfusion.CANVenom;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.DriveCommand;


public class DriveSubsystem extends SubsystemBase {

  // Define Motor Controllers
  public CANVenom fl;
  public CANVenom fr;
  public CANVenom bl;
  public CANVenom br;

  public DifferentialDrive diffDrive;

  public DriveSubsystem() {
    fl = new CANVenom(Constants.FL);
    fr = new CANVenom(Constants.FR);
    bl = new CANVenom(Constants.BL);
    br = new CANVenom(Constants.BR);

    fl.setBrakeCoastMode(CANVenom.BrakeCoastMode.Brake);
    fr.setBrakeCoastMode(CANVenom.BrakeCoastMode.Brake);
    bl.setBrakeCoastMode(CANVenom.BrakeCoastMode.Brake);
    br.setBrakeCoastMode(CANVenom.BrakeCoastMode.Brake);
    
    bl.follow(fl);
    br.follow(fr);

    diffDrive = new DifferentialDrive(fl, fr);

    fl.resetPosition();
    bl.resetPosition();
    fr.resetPosition();
    br.resetPosition();
  }
  

  public Command driveMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
  }


  // Arcade Drive Using Joysticks
  public void arcadeDrive(){
    diffDrive.arcadeDrive(0, -1);
  } 

  public void tankDrive(){
    diffDrive.tankDrive(Constants.mainStick.getY(), Constants.secondStick.getY());
  } 


  // Drive Commands
  public void driveForward() {
    diffDrive.arcadeDrive(-.102, -0.5);
  }


  public void stopDrive(){
    diffDrive.arcadeDrive(0, 0);
  }

  public Double[] getPos(){
    return new Double[]{fl.getPosition(), fr.getPosition(), bl.getPosition(), br.getPosition()};
  }


  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    setDefaultCommand(new DriveCommand(this));
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}