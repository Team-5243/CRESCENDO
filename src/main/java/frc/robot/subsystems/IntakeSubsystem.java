package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class IntakeSubsystem extends SubsystemBase {

    // Define Input & Encoder
    DigitalInput throughBoreInput;
    DutyCycleEncoder throughBoreEncoder;

    public IntakeSubsystem() {
        // Create Input & Encoder
        throughBoreInput = new DigitalInput(0);
        throughBoreEncoder = new DutyCycleEncoder(throughBoreInput);

        // Reset Encoders
        resetEncoders();
    }

    // Encoders
    public double getDistance(){
      return throughBoreEncoder.getDistance();
    }

    public void resetEncoders(){
      throughBoreEncoder.reset();
    }

    @Override
    public void periodic() {
      // SmartDashboard
      SmartDashboard.putNumber("Distance by Bore Encoder", getDistance());
    }
}
