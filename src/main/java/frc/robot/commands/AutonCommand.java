// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.OuttakeAndIntakeSubsystem;

public class AutonCommand extends Command {
  public DriveSubsystem m_driveSubsystem;
  public OuttakeAndIntakeSubsystem m_outtakeSubsystem;
  public double step = 0;
  public boolean start = true;
  public Timer time = new Timer();
  public int kP = 1;
  public AHRS gyro;
  public double gyroStartYaw;


  public AutonCommand(DriveSubsystem subsystem, OuttakeAndIntakeSubsystem outtake) {
    m_driveSubsystem = subsystem;
    m_outtakeSubsystem = outtake;
    addRequirements(subsystem);
    gyro = m_driveSubsystem.getGyro();
    time.start();
  }

  @Override
  public void initialize() {
    time.reset();
    gyro.reset();
    gyroStartYaw = gyro.getYaw();
  }

  @Override
  public void execute() {
    time.start();
    if (start) {
        time.restart();
        gyro.reset();
        start = false;
    }
    if (step == 0) {
        if (time.get() < 3.0) {
            m_driveSubsystem.driveForward();
        }
        else {
            step = 1;
            start = true;
            m_driveSubsystem.stopDrive();
        }
    } else if (step == 1) {
      if (m_driveSubsystem.turnToHeading(90)) {
        step = 2;
        start = true;
        m_driveSubsystem.stopDrive();
      }
    } 
     else {
        m_driveSubsystem.stopDrive();
    }
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}