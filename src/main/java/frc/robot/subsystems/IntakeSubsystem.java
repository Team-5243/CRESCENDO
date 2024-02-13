package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class IntakeSubsystem extends SubsystemBase {

    // Define Input & Encoder
    DigitalInput throughBoreInput;
    DutyCycleEncoder throughBoreEncoder;
    CANSparkMax flywheelMotor;
    CANSparkMax alignmentMotor;

    public IntakeSubsystem() {
        // Create Input & Encoder
        throughBoreInput = new DigitalInput(0);
        throughBoreEncoder = new DutyCycleEncoder(throughBoreInput);
        flywheelMotor = new CANSparkMax(Constants.In1,  MotorType.kBrushless);
        alignmentMotor = new CANSparkMax(Constants.In2,  MotorType.kBrushless);

        // Reset Encoders
        resetEncoders();

        // Reset Motor
        resetMotor();
    }

    // Encoders
    public double getDistance(){
      return throughBoreEncoder.getDistance();
    }

    public void resetEncoders(){
      throughBoreEncoder.reset();
    }

    // Motors Helper Functions
    public void resetMotor(){
        flywheelMotor.clearFaults();
        alignmentMotor.clearFaults();
    }

    public double getFlywheelSpeed(){
        return flywheelMotor.get();
    }

    public double getAlignmentSpeed(){
        return alignmentMotor.get();
    }

    // to spin flywheels
    public void setFlywheelSpeed(double speed){
        flywheelMotor.set(speed);
    }

    // to flip intake
    public void setAlignmentSpeed(double speed){
        flywheelMotor.set(speed);
    }
    

    @Override
    public void periodic() {
      // SmartDashboard
      SmartDashboard.putNumber("Intake Bore Encoder", getDistance());
    }
}
