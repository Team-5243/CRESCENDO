package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class OuttakeAndIntakeSubsystem extends SubsystemBase {

    // Intake
    DigitalInput throughBoreInputLeft;
    DutyCycleEncoder throughBoreEncoderLeft;
    DigitalInput throughBoreInputRight;
    DutyCycleEncoder throughBoreEncoderRight;
    CANSparkMax intakeMotor;
    CANSparkMax armMotor;


    // Outtake
    CANSparkMax leftOuttakeMotor;
    CANSparkMax rightOuttakeMotor;

    public OuttakeAndIntakeSubsystem() {
        // Intake
        // throughBoreInput = new DigitalInput(0);
        // throughBoreEncoder = new DutyCycleEncoder(throughBoreInput);
        intakeMotor = new CANSparkMax(Constants.In1,  MotorType.kBrushless);
        armMotor = new CANSparkMax(Constants.In2,  MotorType.kBrushless);

        // Outtake
        throughBoreInputLeft = new DigitalInput(0);
        throughBoreEncoderLeft = new DutyCycleEncoder(throughBoreInput);

        throughBoreInputRight = new DigitalInput(0);
        throughBoreEncoderRight = new DutyCycleEncoder(throughBoreInput);
        leftOuttakeMotor = new CANSparkMax(Constants.Out1, MotorType.kBrushless);
        rightOuttakeMotor = new CANSparkMax(Constants.Out2,  MotorType.kBrushless);

        // Reset Encoders
        resetEncoders();

        // Reset Motor
        resetMotors();
    }

    // Encoders
    public double getDistance(){
      return throughBoreEncoder.getDistance();
    }

    public void resetEncoders(){
      throughBoreEncoder.reset();
    }

    // Motors Helper Functions
    public void resetMotors(){
        intakeMotor.clearFaults();
        armMotor.clearFaults();
        leftOuttakeMotor.clearFaults();
        rightOuttakeMotor.clearFaults();
    }


    // Accessor Methods
    public double getIntakeSpeed(){
        return intakeMotor.get();
    }

    public double getArmSpeed(){
        return armMotor.get();
    }

    public Double[] getOuttakeSpeed(){
      return new Double[] {leftOuttakeMotor.get(), rightOuttakeMotor.get()};
    }


    // Set Speeds
    public void setIntakeSpeed(double speed){
        intakeMotor.set(speed);
    }

    public void setArmSpeed(double speed){
        armMotor.set(speed);
    }

    public void setOuttakeSpeed(double speed){
      leftOuttakeMotor.set(speed);
      rightOuttakeMotor.set(-speed);
    }


    // Change Intake Speed
    public void intakeRing(){
      setIntakeSpeed(1);
    }

    public void pushRingToOuttake(){
      setIntakeSpeed(-0.3);
    }


    // Move Arm
    public void moveArmToOuttake(){
      // find angle difference and where the arm currently is, move so the angle lines up with the set angle for the intake
      while(throughBoreEncoder.getAbsolutePosition() < 30){
        setArmSpeed(-.1);
      }
    }

    public void moveArmToIntake(){
      // find angle difference and where the arm currently is, move so the angle lines up with the set angle for the intake
      while(throughBoreEncoder.getAbsolutePosition() < 30){
        setArmSpeed(-.1);
      }
    }

    public void shootRing(){
        pushRingToOuttake();
    }


    // Outtake Launch Conditionals
    public boolean atLaunchRPM(){
      Double[] speeds = getOuttakeSpeed();
      if (Math.abs(speeds[0]) >= Constants.neoLaunchRPM && Math.abs(speeds[1]) >= Constants.neoLaunchRPM){
          return true;
      }
      return false;
    }

    public boolean canShoot(){
      // return (throughBoreEncoder.getAbsolutePosition() == Constants.ArmOuttakeAngle) ? true : false;
      return (atLaunchRPM() && true);
    }


    @Override
    public void periodic() {
      // SmartDashboard
      SmartDashboard.putBoolean("Ready to Shoot", canShoot());
      SmartDashboard.putNumber("Intake Bore Encoder", getDistance());
    }
}
