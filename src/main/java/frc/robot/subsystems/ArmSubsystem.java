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


public class ArmSubsystem extends SubsystemBase {

    CANSparkMax motor;
    RelativeEncoder encoder;
    SparkPIDController armPIDController;

    DigitalInput throughBoreInputArm;
    DutyCycleEncoder throughBoreEncoder;

    public ArmSubsystem() {

        // Create Objects
        motor = new CANSparkMax(Constants.IntakeMotorArm, MotorType.kBrushless);
        encoder = motor.getEncoder();
        throughBoreInputArm = new DigitalInput(Constants.ArmBore);
        throughBoreEncoder = new DutyCycleEncoder(throughBoreInputArm);

        // Resets
        resetEncoders();
        resetMotors();

    }


    // Resets
    public void resetEncoders(){
      throughBoreEncoder.reset();
      encoder.setPosition(0);
    }

    public void resetMotors(){
        motor.clearFaults();
    }


    // PID Tuning for Arm
    public void setArmPID(){
      encoder.setPosition(0);
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


    // Conditionals
    // public boolean atAMP(){
    //   return (Math.abs(Constants.armAMPPosition-encoder.getPosition()) < Constants.armToleranceDisplay);
    // }

    // public boolean atOuttake(){
    //   return (Math.abs(Constants.armOuttakePosition-encoder.getPosition()) < Constants.armToleranceDisplay);
    // }

    // public boolean atIntake(){
    //   return (Math.abs(Constants.armIntakePosition-encoder.getPosition()) < Constants.armToleranceDisplay);
    // }


    @Override
    public void periodic() {

    }
}
