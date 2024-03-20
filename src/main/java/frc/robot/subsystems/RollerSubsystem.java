package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
