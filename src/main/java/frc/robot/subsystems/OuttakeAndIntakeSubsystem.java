package frc.robot.subsystems;

import com.revrobotics.REVLibError;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class OuttakeAndIntakeSubsystem extends SubsystemBase {

    // Intake
    CANSparkMax intakeMotor;
    CANSparkMax armMotor;


    // Outtake
    CANSparkMax leftOuttakeMotor;
    CANSparkMax rightOuttakeMotor;

    DigitalInput throughBoreInputLeft;
    DutyCycleEncoder throughBoreEncoderLeft;
    DigitalInput throughBoreInputRight;
    DutyCycleEncoder throughBoreEncoderRight;

    public OuttakeAndIntakeSubsystem() {
        // Intake
        intakeMotor = new CANSparkMax(Constants.In1,  MotorType.kBrushed);
        armMotor = new CANSparkMax(Constants.In2,  MotorType.kBrushed);

        // Outtake
        throughBoreInputLeft = new DigitalInput(0);
        throughBoreEncoderLeft = new DutyCycleEncoder(throughBoreInputLeft);
        throughBoreInputRight = new DigitalInput(1);
        throughBoreEncoderRight = new DutyCycleEncoder(throughBoreInputRight);
        leftOuttakeMotor = new CANSparkMax(Constants.OutLeft, MotorType.kBrushed);
        rightOuttakeMotor = new CANSparkMax(Constants.OutRight,  MotorType.kBrushed);

        // Reset Encoders
        resetEncoders();

        // Reset Motor
        resetMotors();
    }

    // Encoders
    public Double[] getBore(){
      return new Double[] {throughBoreEncoderLeft.getDistance(), throughBoreEncoderRight.getDistance()};
    }

    public void resetEncoders(){
      throughBoreEncoderLeft.reset();
      throughBoreEncoderRight.reset();
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
      rightOuttakeMotor.set(speed);
    }


    // Change Intake Speed
    public void intakeRing(){
      setIntakeSpeed(1);
    }

    public void pushRingToOuttake(){
      setIntakeSpeed(-0.3);
    }


    // Move Arm
    // public void moveArmToOuttake(){
    //   // find angle difference and where the arm currently is, move so the angle lines up with the set angle for the intake
    //   while(throughBoreEncoder.getAbsolutePosition() < 30){
    //     setArmSpeed(-.1);
    //   }
    // }

    // public void moveArmToIntake(){
    //   // find angle difference and where the arm currently is, move so the angle lines up with the set angle for the intake
    //   while(throughBoreEncoder.getAbsolutePosition() < 30){
    //     setArmSpeed(-.1);
    //   }
    // }

    public void shootRing(){
        pushRingToOuttake();
    }


    // Outtake Launch Conditionals
    public boolean atLaunchRPM(){
      Double[] speeds = getOuttakeSpeed();
      if (Math.abs(speeds[0]) >= Constants.redlineLaunchRPM && Math.abs(speeds[1]) >= Constants.redlineLaunchRPM){
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
      SmartDashboard.putNumber("Average Outtake RPM", ((getOuttakeSpeed()[0]+getOuttakeSpeed()[1])/2));
      SmartDashboard.putNumber("Intake Bore Encoder Left", getBore()[0]);
      SmartDashboard.putNumber("Intake Bore Encoder Right", getBore()[1]);
    }
}
