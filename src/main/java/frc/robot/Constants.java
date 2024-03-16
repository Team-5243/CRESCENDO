package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public final class Constants {

  // Joysticks
  public static Joystick mainStick = new Joystick(0);
  public static Joystick secondStick = new Joystick(1);


  // Redline Settings
  public static int redlineTheoretialMaxRPM = 21000;
  public static double redlineLaunchPercent = 90;
  public static double redlineLaunchRPM = redlineTheoretialMaxRPM * (redlineLaunchPercent/100); 

  public static double redlineIdlePercent = 0;
  public static double redlineRollerInPercent = 0.5;
  public static double redlineRollerOutPercent = -0.4;
  public static double redlineRollerShootPercent = -0.6;
  public static double redlineOuttakePercent = 1;

  
  // Intake/Outtake Other
  public static long waitAfterShoot = 500;
  public static double armToleranceDisplay = 2.5;


  // Intake/Outtake CAN
  public static int OuttakeMotorLeft = 30;
  public static int OuttakeMotorRight = 20;
  public static int IntakeMotorRoller = 44;
  public static int IntakeMotorArm = 50;


  // Intake/Outtake DIO
  public static int OuttakeBoreLeft = 1;
  public static int OuttakeBoreRight = 2;
  public static int IntakeLimitSwitch = 4;

  // Arm 
  public static double armSpeed = 0.6;
  public static double armGearRatio = 16;


  // SET TO ZERO ON INSIDE BOT AT OUTTAKE POSITION
  // SET TO INTAKE ON START
  public static double armIntakePosition = -45.5;
  public static double armAMPPosition = -19;
  public static double armOuttakePosition = 0;


  // Arm PID
  public static double armKp = 0.1;
  public static double armKi = 0.01;
  public static double armKd = 0.05;
  public static double armTolerance = 0.05;
  public static double armPMin = -0.8;
  public static double armPMax = 0.8;


  // Drive CAN
  public static int FL = 4;
  public static int FR = 1;
  public static int BL = 3;
  public static int BR = 2;


  // Drive PID
  public static double driveKp = 0;
  public static double driveKi = 0;
  public static double driveKd = 0;
  public static double driveKf = 0;
  public static double driveMaxV = 0;
  public static double driveMaxA = 0;


  // Drive
  public static double driveMaxSpeed = 0;
  public static double driveMaxAcceleration = 0;

  
  // Chassis (Meters)
  public static double driveWheelCircumference = 0.478779;
  public static double driveGearRatio = 8.46;
  public static double driveTrackWidth = 12;
  public static double mass = 0;

  public static int tolerance = 3;

  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }
}