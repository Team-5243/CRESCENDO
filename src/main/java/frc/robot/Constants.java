package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public final class Constants {

  // Joysticks
  public static Joystick mainStick = new Joystick(0);
  public static Joystick secondStick = new Joystick(1);


  // Intake Settings
  public static double rollerIntakeNote = -0.7;
  public static double rollerOutputNote = 0.6;
  public static double rollerShootNote = 0.75;


  // Outtake NEO Settings
  public static double outtakeSpeed = .65;
  

  // Intake/Outtake Other
  public static long outtakeShootSpeed = 2300;


  // Intake/Outtake CAN
  public static int OuttakeMotorLeft = 30;
  public static int OuttakeMotorRight = 20;
  public static int IntakeMotorRoller = 44;
  public static int IntakeMotorArm = 50;


  // Intake/Outtake DIO
  public static int DriveBoreLeft = 0;
  public static int DriveBoreRight = 2;
  public static int IntakeLimitSwitch = 4;


  // Arm 
  public static double armSpeed = 0.6;
  public static double armGearRatio = 16;


  // Arm Positions
  public static double armGroundPosition = -45.5;
  public static double armAMPPosition = -19.5;
  public static double armShooterPosition = 0;


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


  // Drive
  public static double driveMaxSpeed = 0;
  public static double driveMaxAcceleration = 0;

  
  // Chassis (Meters)
  public static double driveWheelCircumference = 0.478779;
  public static double driveGearRatio = 8.46;
  public static double driveTrackWidth = 12;
  public static double mass = 0;


  // Limelight:
  public static final double maxRVP = 0.55;
  public static final double minRVP = 0.5;
  public static final double dRVP = 50;
  public static final double limelightTolerance = 2;
  public static final double maxSpeakerTY = 19;
  public static final double minSpeakerTY = 17;
  public static final String limelight1 = "limelight";
  public static final String limelight2 = "limelight-two";
  public static final int redSpeakerID = 4;
  public static final int blueSpeakerID = 8;
  public static final int redAmpID = 5;
  public static final int blueAmpID = 6;
  public static final int drivePipelineID = 0;
  public static final int visionPipelineID = 1;

  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }
}