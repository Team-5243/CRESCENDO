package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkRelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class ShooterSubsystem extends SubsystemBase {

    CANSparkMax leftMotor;
    CANSparkMax rightMotor;

    SparkRelativeEncoder leftRelativeEncoder;
    SparkRelativeEncoder rightRelativeEncoder;


    public ShooterSubsystem() {

        // Create Objects
        leftMotor = new CANSparkMax(Constants.OuttakeMotorLeft, MotorType.kBrushless);
        rightMotor = new CANSparkMax(Constants.OuttakeMotorRight,  MotorType.kBrushless);

        // Set Motor Idle
        leftMotor.setIdleMode(IdleMode.kCoast);
        rightMotor.setIdleMode(IdleMode.kCoast);

        // Resets
        resetMotors();

    }


    // Resets
    public void resetMotors(){
        leftMotor.clearFaults();
        rightMotor.clearFaults();
    }


    // Set Speeds
    public void setSpeed(double speed){
        leftMotor.set(speed * 1.32);
        rightMotor.set(-speed);
    }

    
    // Get Speed
    public double getSpeed(){
        return (Math.abs(leftMotor.getEncoder().getVelocity())+Math.abs(rightMotor.getEncoder().getVelocity()))/2;
    }
    

    // Stop Motors
    public void stop(){
        rightMotor.set(0);
        leftMotor.set(0);
    }


    @Override
    public void periodic() {

    }
}
