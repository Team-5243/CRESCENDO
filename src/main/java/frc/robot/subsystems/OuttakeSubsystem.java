package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

public class OuttakeSubsystem extends SubsystemBase {
    
    // Define Input & Encoder
    DigitalInput throughBoreInput;
    DutyCycleEncoder throughBoreEncoder;
    CANSparkMax leftMotor;
    CANSparkMax rightMotor;

    public OuttakeSubsystem() {
        // Create Input & Encoder
        throughBoreInput = new DigitalInput(0);
        throughBoreEncoder = new DutyCycleEncoder(throughBoreInput);
        leftMotor = new CANSparkMax(Constants.Out1, MotorType.kBrushless);
        rightMotor = new CANSparkMax(Constants.Out2,  MotorType.kBrushless);

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

    // Motors Get
    public Double[] getSpeed(){
        return new Double[] {leftMotor.get(), rightMotor.get()};
    }

    // Motors Shoot
    public void resetMotor(){
        leftMotor.clearFaults();
        rightMotor.clearFaults();
    }

    public void speedUp(){
        leftMotor.set(.6);
        rightMotor.set(.6);
    }

    public void launchDisk(){
        Double[] speeds = getSpeed();
        if (Math.abs(speeds[0]) > 0.58 && Math.abs(speeds[1]) > 0.58){
            pushDisk();
        }
    }

    public void pushDisk(){
        
    }


    @Override
    public void periodic() {
      // SmartDashboard
      SmartDashboard.putNumber("Distance by Bore Encoder", getDistance());
    }
}

