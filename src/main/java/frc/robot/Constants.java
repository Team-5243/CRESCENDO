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

  public static double redlineIdlePercent = 0.27;
  public static double redlineRollerInPercent = 0.5;
  public static double redlineRollerOutPercent = 0.6;
  public static double redlineOuttakePercent = 1;


  // Intake/Outtake CAN
  public static int OutLeft = 30;
  public static int OutRight = 20;
  public static int Roller = 44;
  public static int Arm = 50;


  // Arm 
  public static double armSpeed = 0.6;
  public static double armGearRatio = 8;

  public static double armAMPPosition = -2.6888;
  public static double armOuttakePosition = 0;
  public static double armIntakePosition = -5.4916;


  // Arm PID
  public static double armKp = 1.5;
  public static double armKi = 0;
  public static double armKd = 0;
  public static double armKf = 0.184;
  public static double armMaxV = 5500;
  public static double armMaxA = 20000;


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


  // Drive (Meters)
  public static double driveMaxSpeed = 0;
  public static double driveMaxAcceleration = 0;

  
  // Chassis
  public static double driveWheelCircumference = 0.478779;
  public static double driveGearRatio = 8.46;
  public static double driveTrackWidth = 12;
  public static double mass = 0;

  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }
}