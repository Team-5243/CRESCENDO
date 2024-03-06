package frc.robot.subsystems;

import com.playingwithfusion.CANVenom;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class OuttakeAndIntakeSubsystem extends SubsystemBase {

    // Intake
    CANSparkMax intakeMotor;
    CANVenom armMotor;


    // Outtake
    CANSparkMax leftOuttakeMotor;
    CANSparkMax rightOuttakeMotor;

    DigitalInput throughBoreInputArm;
    DutyCycleEncoder throughBoreEncoderArm;

    DigitalInput topLimitSwitch;
    DigitalInput bottomLimitSwitch;

    DigitalInput throughBoreInputOuttake;
    DutyCycleEncoder throughBoreEncoderOuttake;

    public OuttakeAndIntakeSubsystem() {
        // Intake
        intakeMotor = new CANSparkMax(Constants.Roller,  MotorType.kBrushed);
        armMotor = new CANVenom(50);
        throughBoreInputArm = new DigitalInput(0);
        throughBoreEncoderArm = new DutyCycleEncoder(throughBoreInputArm);
        // topLimitSwitch = new DigitalInput(0);
        bottomLimitSwitch = new DigitalInput(8);
        armMotor.setBrakeCoastMode(CANVenom.BrakeCoastMode.Brake);


        // Outtake
        leftOuttakeMotor = new CANSparkMax(Constants.OutLeft, MotorType.kBrushed);
        rightOuttakeMotor = new CANSparkMax(Constants.OutRight,  MotorType.kBrushed);
        throughBoreInputOuttake = new DigitalInput(1);
        throughBoreEncoderOuttake = new DutyCycleEncoder(throughBoreInputOuttake);

        // Reset Encoders
        resetEncoders();

        // Reset Motor
        resetMotors();

        // Set PID
        setArmPID();
    }

    // Encoders
    public double getThroughBoreArm(){
      return throughBoreEncoderArm.getDistance();
    }

    public double getThroughBoreOuttake(){
      return throughBoreEncoderOuttake.getDistance();
    }

    public void resetEncoders(){
      throughBoreEncoderArm.reset();
      throughBoreEncoderOuttake.reset();
    }

    // Motors Helper Functions
    public void resetMotors(){
        intakeMotor.clearFaults();
        leftOuttakeMotor.clearFaults();
        rightOuttakeMotor.clearFaults();
    }

    // PID Tuning for Arm
    public void setArmPID(){
        armMotor.setKP(Constants.armKp);
        armMotor.setKI(Constants.armKi);
        armMotor.setKD(Constants.armKd);
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
       if (speed>0) {
        // if (topLimitSwitch.get()) {
        //   armMotor.set(0);
        // } else  {
        //   armMotor.set(speed);
        // }
      
       } else {
        if (bottomLimitSwitch.get()) {
          armMotor.set(0);
        } else {
          armMotor.set(speed);
        }
       }
    }

    public void setOuttakeSpeed(double speed){
      leftOuttakeMotor.set(-speed);
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
    public void moveArmToOuttake(){
      // find angle difference and where the arm currently is, move so the angle lines up with the set angle for the intake
      if(throughBoreEncoderArm.getAbsolutePosition() < 4){
        setArmSpeed(-.1);
      }
    }

    public void moveArmToIntake(){
      // find angle difference and where the arm currently is, move so the angle lines up with the set angle for the intake
      if(throughBoreEncoderArm.getAbsolutePosition() < 30){
        setArmSpeed(-.1);
      }
    }

    public void moveArmToAmp(){
      // find angle difference and where the arm currently is, move so the angle lines up with the set angle for the intake
      if(throughBoreEncoderArm.getAbsolutePosition() < 30){
        setArmSpeed(-.1);
      }
    }

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
      SmartDashboard.putNumber("Intake Arm Bore", armMotor.getPosition());
      SmartDashboard.putNumber("Outtake Bore", getThroughBoreOuttake());
    }
}
