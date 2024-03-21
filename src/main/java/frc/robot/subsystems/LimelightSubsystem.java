package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.LimelightHelpers;

public class LimelightSubsystem extends SubsystemBase {
    
    public double ty;
    public double xSpeed;
    public double forwardSpeed;
    public boolean visionMode;

    public LimelightSubsystem() {
      ty = 0;
      xSpeed = 0;
      forwardSpeed = 0;
      visionMode = false;
    }

    /**
     * This will reset the speed variables of this class.
     */
    public void resetSpeeds() {
        this.xSpeed = 0;
        this.forwardSpeed = 0;
    }

    /**
     * This will turn the robot towards the speaker.
     * @param table the network table of limelight one. Limelight one is the back limelight.
     * @return Return true if there is a change in speed variables otherwise it will return false.
     */
    public boolean alignWithSpeaker(NetworkTable table) {
        // table.getEntry("ledMode").setNumber(3);
        double tx = table.getEntry("tx").getDouble(0.0);
        double ty = table.getEntry("ty").getDouble(0.0)==0.0 ? this.ty : table.getEntry("ty").getDouble(0.0);
        this.ty = ty;
        System.out.println(this.ty);
        double val = tx / Constants.dRVP;
        double results = 0;
        if (LimelightHelpers.getLatestResults(Constants.limelight1).targetingResults.targets_Fiducials.length > 0)
          results = LimelightHelpers.getLatestResults(Constants.limelight1).targetingResults.targets_Fiducials[0].fiducialID;
        
        if (Math.abs(tx) > Constants.limelightTolerance) {
          if (Math.abs(val) > Constants.maxRVP) {
            val = Math.signum(val) * Constants.maxRVP;
          }
          if (Math.abs(val) < Constants.minRVP) {
            val = Math.signum(val) * Constants.minRVP;
          }
          
          //System.out.println(-9.5 > ty && ty > -10.5 ? 0 : Math.signum(10 - ty) * 0.3);
        }
        SmartDashboard.putNumber("id", results);
        if (results == Constants.redSpeakerID || results == Constants.blueSpeakerID) {
        //   m_driveSubsystem.diffDrive.arcadeDrive(s 
            this.xSpeed = val; // Math.max(0.25, Math.min(SmartDashboard.getNumber("maxRVP", 0.0), tx / SmartDashboard.getNumber("dRVP", 1))),
            this.forwardSpeed = Constants.maxSpeakerTY > ty && ty > Constants.minSpeakerTY ? 0 : Math.signum(10 - ty) * 0.3;
            return true;
        }
        return false;
    }

    /**
     * This will turn the robot towards the amp.
     * @param table the network table of limelight two. Limelight one is the front limelight.
     * @return Return true if there is a change in speed variables otherwise it will return false.
     */
    public boolean alignWithAmp(NetworkTable table) {
        double tx = table.getEntry("tx").getDouble(0.0);
        double val = tx / Constants.dRVP;
        double results = 0;
        if (LimelightHelpers.getLatestResults(Constants.limelight2).targetingResults.targets_Fiducials.length > 0)
          results = LimelightHelpers.getLatestResults(Constants.limelight2).targetingResults.targets_Fiducials[0].fiducialID;
        
        if (Math.abs(tx) > Constants.limelightTolerance) {
          if (Math.abs(val) > Constants.maxRVP) {
            val = Math.signum(val) * Constants.maxRVP;
          }
          if (Math.abs(val) < Constants.minRVP) {
            val = Math.signum(val) * Constants.minRVP;
          }
          if (results == Constants.redAmpID || results == Constants.blueAmpID)
          {
            this.xSpeed = val;
            return true;
          }
        }
        return false;
    }

    /**
     * Will set a limelights LEDs to a specified state
     * @param limelight the name of limelight that needs to be changed
     * @param ledMode the mode of the LED it needs to be changed to. Possible values are "off", "on", and "blinking".
     * @return true if valid input, false if not.
     */
    public boolean setLedMode(String limelight, String ledMode) {
        if (ledMode == "off") {
            LimelightHelpers.setLEDMode_ForceOff(limelight);
        } else if (ledMode == "on") {
            LimelightHelpers.setLEDMode_ForceOn(limelight);
        } else if (ledMode == "blinking") {
            LimelightHelpers.setLEDMode_ForceBlink(limelight);
        } else {
            return false;
        }
        return true;
    }

    /**
     * Sets the vision mode of all limelights.
     * @param mode true for april tag detection, false for driver view.
     */
    public void setVisionMode(boolean mode) {
        if (mode) {
            LimelightHelpers.setPipelineIndex(Constants.limelight1, Constants.visionPipelineID);
            LimelightHelpers.setPipelineIndex(Constants.limelight2, Constants.visionPipelineID);
        } else {
            LimelightHelpers.setPipelineIndex(Constants.limelight1, Constants.drivePipelineID);
            LimelightHelpers.setPipelineIndex(Constants.limelight2, Constants.drivePipelineID);
        }
        visionMode = mode;
    }

    @Override
    public void periodic() {
        // NetworkTable table = NetworkTableInstance.getDefault().getTable(Constants.limelight1);
        // NetworkTableEntry tx = table.getEntry("tx");
        // NetworkTableEntry ty = table.getEntry("ty");
        // NetworkTableEntry ta = table.getEntry("ta");

        // //read values periodically
        // double x = tx.getDouble(0.0);
        // double y = ty.getDouble(0.0);
        // double area = ta.getDouble(0.0);

        // //post to smart dashboard periodically
        // SmartDashboard.putNumber("LimelightX", x);
        // SmartDashboard.putNumber("LimelightY", y);
        // SmartDashboard.putNumber("LimelightArea", area);
    }
}
