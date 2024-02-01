package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public final class Constants {
  public static int FL = 1;
  public static int FR = 4;
  public static int BL = 2;
  public static int BR = 3;
  public static double SENS = 0.4;
  public static Joystick mainStick = new Joystick(0);
  public static Joystick secondStick = new Joystick(1);

  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }
}