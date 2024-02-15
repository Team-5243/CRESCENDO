package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Constants;


public class VisionSubsystem extends SubsystemBase{
    public NetworkTable table;
    public NetworkTableEntry tx;
    public NetworkTableEntry ty;
    public NetworkTableEntry ta;

    public VisionSubsystem(){
        table = NetworkTableInstance.getDefault().getTable("limelight");
    }

    @Override
    public void periodic(){
        double x = tx.getDouble(0.0);
        double y = ty.getDouble(0.0);
        double a = ta.getDouble(0.0);

        SmartDashboard.putNumber("April Tag X", x);
        SmartDashboard.putNumber("April Tag Y", y);
        SmartDashboard.putNumber("April Tag Area", a);
    }
}
