package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class RollerSubsystem extends SubsystemBase {

    CANSparkMax motor;

    public RollerSubsystem() {

        // Create Objects
        motor = new CANSparkMax(Constants.IntakeMotorRoller,  MotorType.kBrushless);

        // Resets
        resetMotors();

    }


    // Resets
    public void resetMotors(){
        motor.clearFaults();
    }


    // Accessor Methods
    public double getSpeed(){
        return motor.get();
    }

    // Set Speeds
    public void setSpeed(double speed){
        motor.set(speed);
    }

    public void stop(){
        motor.set(0);
    }


    @Override
    public void periodic() {

    }
}
