package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class ArmSubsystem extends SubsystemBase {

    CANSparkMax motor;
    RelativeEncoder encoder;
    SparkPIDController armPIDController;

    public ArmSubsystem() {

      // Create Objects
      motor = new CANSparkMax(Constants.IntakeMotorArm, MotorType.kBrushless);
      encoder = motor.getEncoder();

      // Resets
      resetEncoders();
      resetMotors();

      // Set PID
      setArmPID();

    }


    // Resets
    public void resetEncoders(){
      encoder.setPosition(0);
    }

    public void resetMotors(){
      motor.clearFaults();
    }


    // PID Tuning for Arm
    public void setArmPID(){
      armPIDController= motor.getPIDController();
      armPIDController.setP(Constants.armKp);
      armPIDController.setI(Constants.armKi);
      armPIDController.setD(Constants.armKd);
      armPIDController.setIZone(Constants.armTolerance);
      armPIDController.setOutputRange(Constants.armPMin, Constants.armPMax);
    }


    // Accessor Methods
    public double getSpeed(){
      return motor.get();
    }


    // Move Arm
    public void setPosition(double position){
      armPIDController.setReference(position, CANSparkMax.ControlType.kPosition);
    }

    @Override
    public void periodic() {

    }
}
