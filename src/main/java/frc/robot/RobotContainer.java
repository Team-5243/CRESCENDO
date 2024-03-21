// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ArmToAMP;
import frc.robot.commands.ArmToGround;
import frc.robot.commands.ArmToShooter;
import frc.robot.commands.Auton;
import frc.robot.commands.Drive;
import frc.robot.commands.IntakeNote;
import frc.robot.commands.Limelight;
import frc.robot.commands.PoseEstimation;
import frc.robot.commands.ShootAMP;
import frc.robot.commands.ShootSpeaker;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.RollerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.VisionSubsystem;


public class RobotContainer {
  private final DriveSubsystem m_driveSubsystem = new DriveSubsystem();
  private final Drive m_driveCommand = new Drive(m_driveSubsystem);

  private final ArmSubsystem m_ArmSubsystem = new ArmSubsystem();
  private final RollerSubsystem m_RollerSubsystem = new RollerSubsystem();
  private final ShooterSubsystem m_ShooterSubsystem = new ShooterSubsystem();

  private final LimelightSubsystem m_limelightSubsystem = new LimelightSubsystem();

  private final VisionSubsystem m_visionSubsystem = new VisionSubsystem();
  private final Limelight m_limelight = new Limelight(m_limelightSubsystem, m_driveSubsystem);

  private final Auton m_autonCommand = new Auton(m_driveSubsystem, m_ArmSubsystem, m_RollerSubsystem, m_ShooterSubsystem);
  private final PoseEstimation m_poseEstimation = new PoseEstimation(m_driveSubsystem, m_visionSubsystem);


  public RobotContainer() {
    m_driveSubsystem.setDefaultCommand(m_poseEstimation);
    m_limelightSubsystem.setDefaultCommand(m_limelight);

    configureBindings();
  }

  private void configureBindings() {
    
    new JoystickButton(Constants.secondStick, 2)
        .onTrue(new IntakeNote(m_RollerSubsystem));

    new JoystickButton(Constants.secondStick, 1)
        .whileTrue(new ShootSpeaker(m_RollerSubsystem, m_ShooterSubsystem));

    new JoystickButton(Constants.secondStick, 5)
        .whileTrue(new ArmToGround(m_ArmSubsystem));

    new JoystickButton(Constants.secondStick, 3)
        .whileTrue(new ArmToAMP(m_ArmSubsystem));

    new JoystickButton(Constants.secondStick, 4)
        .whileTrue(new ShootAMP(m_RollerSubsystem));

    new JoystickButton(Constants.secondStick, 6)
        .whileTrue(new ArmToShooter(m_ArmSubsystem));

  }

  public Command getAutonomousCommand() {
    return m_autonCommand;
  }
}