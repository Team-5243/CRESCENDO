package frc.robot.commands;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.cscore.HttpCamera.HttpCameraKind;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LimelightSubsystem;

public class Limelight extends Command {
    public DriveSubsystem m_driveSubsystem;
    private LimelightSubsystem m_limelightSubsystem;

    public Limelight(LimelightSubsystem limelight) {
        m_limelightSubsystem = limelight;
        addRequirements(limelight);
    }

    @Override
    public void initialize() {
        CameraServer.startAutomaticCapture(new HttpCamera(Constants.limelight1, "http://10.52.43.11:5800/", HttpCameraKind.kMJPGStreamer));
    }

    @Override
    public void execute() {

    }
}
