// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.OuttakeAndIntakeSubsystem;

public class AutonCommand extends Command {
  public OuttakeAndIntakeSubsystem m_outtakeSubsystem;
  public Timer time = new Timer();


  public AutonCommand(OuttakeAndIntakeSubsystem subsystem) {
    m_outtakeSubsystem = subsystem;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
    time.restart();
  }

  @Override
  public void execute() {
    if (time.get() < 2){
      m_outtakeSubsystem.setOuttakeSpeed(-Constants.outtakeMaxSpeed);
    }

    else if (time.get() < 3.5) {
      m_outtakeSubsystem.setRollerSpeed(Constants.redlineRollerOutPercent);
    }

    else {
      m_outtakeSubsystem.setRollerSpeed(0);
      m_outtakeSubsystem.setOuttakeSpeed(0);

    }
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
} 