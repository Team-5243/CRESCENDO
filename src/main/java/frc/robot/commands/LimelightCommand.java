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

public class LimelightCommand extends Command {
    public DriveSubsystem m_driveSubsystem;
    private LimelightSubsystem m_limelightSubsystem;

    public LimelightCommand(LimelightSubsystem subsystem, DriveSubsystem driveSubsystem) {
        m_driveSubsystem = driveSubsystem;
        m_limelightSubsystem = subsystem;
        
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(driveSubsystem, subsystem);
    }

    @Override
    public void initialize() {
        CameraServer.startAutomaticCapture(new HttpCamera(Constants.limelight1, "http://10.52.43.11:5800/", HttpCameraKind.kMJPGStreamer));
        CameraServer.startAutomaticCapture(new HttpCamera(Constants.limelight2, "http://10.52.43.12:5800/", HttpCameraKind.kMJPGStreamer));
    }

    @Override
    public void execute() {
        NetworkTable table = NetworkTableInstance.getDefault().getTable(Constants.limelight1);
        m_limelightSubsystem.resetSpeeds();
        if (Constants.mainStick.getRawButton(3)) {
            m_limelightSubsystem.alignWithSpeaker(table);
        } //else {
        // table.getEntry("ledMode").setNumber(0);
    //   }
        if (Constants.mainStick.getRawButton(5)) {
            m_limelightSubsystem.alignWithAmp(table);
        }
        m_driveSubsystem.arcadeDrive(m_limelightSubsystem.xSpeed, m_limelightSubsystem.forwardSpeed);

      // System.out.println(LimelightHelpers.getBotPose(""));
      
    //   if (Constants.mainStick.getRawButton(1)) {
    //     // System.out.println("Yay!");
    //     table.getEntry("ledMode").setNumber(3);
    //   } else {
    //     table.getEntry("ledMode").setNumber(0);
    //   }
    }

    
}
