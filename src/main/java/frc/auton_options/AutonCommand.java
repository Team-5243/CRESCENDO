// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

// package frc.robot.commands;

// import com.kauailabs.navx.frc.AHRS;

// import edu.wpi.first.wpilibj.Timer;
// import edu.wpi.first.wpilibj2.command.Command;
// import frc.robot.Constants;
// import frc.robot.subsystems.DriveSubsystem;
// import frc.robot.subsystems.OuttakeAndIntakeSubsystem;

// public class AutonCommand extends Command {
//   // public DriveSubsystem m_subsystem;
//   // public OuttakeAndIntakeSubsystem m_outtakeSubsystem;
//   // public double step = 0;
//   // public boolean start = true;
//   // public Timer time = new Timer();
//   // public int kP = 1;
//   // public AHRS gyro;
//   // public double gyroStartYaw;


//   public AutonCommand(DriveSubsystem subsystem, OuttakeAndIntakeSubsystem outtake) {
//     // m_subsystem = subsystem;
//     // m_outtakeSubsystem = outtake;
//     // addRequirements(subsystem);
//     // gyro = m_subsystem.getGyro();
//     // time.start();
//     // Use addRequirements() here to declare subsystem dependencies.
//   }

//   // Called when the command is initially scheduled.
//   @Override
//   public void initialize() {
//     // time.reset();
//     // gyro.reset();
//     // gyroStartYaw = gyro.getYaw() ;
//   }

//   // Called every time the scheduler runs while the command is scheduled.
//   @Override
//   public void execute() {
//     // time.start();
//     // if (start) {
//     //     time.restart();
//     //     gyro.reset();
//     //     start = false;
//     // }
//     // if (step == 0) {
//     //     if (time.get() < 3.0) {
//     //         m_subsystem.driveForward();
//     //     }
//     //     else {
//     //         step = 1;
//     //         start = true;
//     //         m_subsystem.stopDrive();
//     //     }
//     // } else if (step == 1) {
//     //   if (m_subsystem.turnToHeading(90)) {
//     //     step = 2;
//     //     start = true;
//     //     m_subsystem.stopDrive();
//     //   }
//     // } else if (step == 2) {
//     //   m_outtakeSubsystem.moveArmToAmp();
//     //   if (m_outtakeSubsystem.armAtAMP()) {
//     //     step = 3;
//     //     start = true;
//     //   }
//     // } else if (step == 3) {
//     //   if (time.get() < 3.0) {
//     //     m_outtakeSubsystem.shootAMP();
//     //   } else {
//     //     step = 4;
//     //     start = true;
//     //   }
//     // }
//     //  else {
//     //     m_subsystem.stopDrive();
//     //     m_outtakeSubsystem.setRollerSpeed(0);
//     // }
//   }

//   // Called once the command ends or is interrupted.
//   @Override
//   public void end(boolean interrupted) {}

//   // Returns true when the command should end.
//   @Override
//   public boolean isFinished() {
//     return false;
//   }
// }