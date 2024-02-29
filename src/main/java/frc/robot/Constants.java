package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public final class Constants {

  // CANVenom CAN IDS
  public static int FL = 4;
  public static int FR = 1;
  public static int BL = 3;
  public static int BR = 2;

  // CANSparkMax
  public static int Out1 = 1;
  public static int Out2 = 2;
  public static int In1 = 3;
  public static int In2 = 4;
  public static int redlineTheoretialMaxRPM = 21000;
  public static double redlineLaunchPercent = 90;
  public static double redlineLaunchRPM = redlineTheoretialMaxRPM * (redlineLaunchPercent/100); 

  // Joysticks
  public static Joystick mainStick = new Joystick(0);
  public static Joystick secondStick = new Joystick(1);

  // Arm 
  // set 12345 to the angle that the arm should be at when aligned with outtake/intake
  public static double armOuttakeAngle = 12345;
  public static double armIntakeAngle = 12345;

  // PID
  public static double kd = 0;
  public static double kf = 0;
  public static double ki = 0;
  public static double kp = 0;

  // Drive (Meters)
  public static double wheelCircumference = 0.478779;
  public static double drivetrainGearRatio = 8.46;
  public static double trackWidth = 12;
  
  public static double maxSpeed = 0;
  public static double maxAcceleration = 0;
  public static double mass = 0;

  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }
}