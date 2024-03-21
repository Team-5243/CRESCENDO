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

    public Limelight(LimelightSubsystem limelight, DriveSubsystem drive) {
        m_driveSubsystem = drive;
        m_limelightSubsystem = limelight;
        addRequirements(drive, limelight);
    }

    @Override
    public void initialize() {
        CameraServer.startAutomaticCapture(new HttpCamera(Constants.limelight1, "http://10.52.43.11:5800/", HttpCameraKind.kMJPGStreamer));
    }

    @Override
    public void execute() {
        NetworkTable table = NetworkTableInstance.getDefault().getTable(Constants.limelight1);
        m_limelightSubsystem.resetSpeeds();
        if (Constants.mainStick.getRawButton(3)) {
            m_limelightSubsystem.alignWithSpeaker(table);
            // m_driveSubsystem.arcadeDrive(m_limelightSubsystem.xSpeed, m_limelightSubsystem.forwardSpeed);
        } 
        
        else if (Constants.mainStick.getRawButton(5)) {
            // m_limelightSubsystem.alignWithAmp(table);
        }
    }
}
