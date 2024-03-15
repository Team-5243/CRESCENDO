package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class OuttakeAndIntakeSubsystem extends SubsystemBase {

    // Intake
    CANSparkMax rollerMotor;
    CANSparkMax armMotor;
    RelativeEncoder armEncoder;
    SparkPIDController armPIDController;


    // Outtake
    CANSparkMax leftOuttakeMotor;
    CANSparkMax rightOuttakeMotor;

    DigitalInput intakeLimitSwitch;

    DigitalInput throughBoreInputOuttakeLeft;
    DutyCycleEncoder throughBoreEncoderOuttakeLeft;

    DigitalInput throughBoreInputOuttakeRight;
    DutyCycleEncoder throughBoreEncoderOuttakeRight;


    public OuttakeAndIntakeSubsystem() {
        // Intake
        rollerMotor = new CANSparkMax(Constants.IntakeMotorRoller,  MotorType.kBrushed);
        armMotor = new CANSparkMax(Constants.IntakeMotorArm, MotorType.kBrushless);
        armEncoder = armMotor.getEncoder();

        intakeLimitSwitch = new DigitalInput(Constants.IntakeLimitSwitch);

        // Outtake
        leftOuttakeMotor = new CANSparkMax(Constants.OuttakeMotorLeft, MotorType.kBrushed);
        rightOuttakeMotor = new CANSparkMax(Constants.OuttakeMotorRight,  MotorType.kBrushed);

        // Left Outtake Bore
        throughBoreInputOuttakeLeft = new DigitalInput(Constants.OuttakeBoreLeft);
        throughBoreEncoderOuttakeLeft = new DutyCycleEncoder(throughBoreInputOuttakeLeft);

        // Right Outtake Bore
        throughBoreInputOuttakeRight = new DigitalInput(Constants.OuttakeBoreRight);
        throughBoreEncoderOuttakeRight = new DutyCycleEncoder(throughBoreInputOuttakeRight);
    }


    // Resets
    public void resetEncoders(){
      // throughBoreEncoderOuttakeLeft.reset();
      // throughBoreEncoderOuttakeRight.reset();
      armEncoder.setPosition(0);
    }

    public void resetMotors(){
        rollerMotor.clearFaults();
        leftOuttakeMotor.clearFaults();
        rightOuttakeMotor.clearFaults();
    }


    // PID Tuning for Arm
    public void setArmPID(){
      armEncoder.setPosition(0);
      armPIDController= armMotor.getPIDController();
      armPIDController.setP(Constants.armKp);
      armPIDController.setI(Constants.armKi);
      armPIDController.setD(Constants.armKd);
      armPIDController.setIZone(Constants.armTolerance);
      armPIDController.setOutputRange(Constants.armPMin, Constants.armPMax);
    }


    // Accessor Methods
    public Double[] getOuttakeRPM(){
        return new Double[] {throughBoreEncoderOuttakeLeft.getDistance(), throughBoreEncoderOuttakeRight.getDistance()};
    }

    public double getIntakeSpeed(){
        return rollerMotor.get();
    }

    public double getArmSpeed(){
        return armMotor.get();
    }

    public boolean intakeHasRing(){
      return intakeLimitSwitch.get();
    }


    // Set Intake Speeds
    public void setRollerSpeed(double speed){
        rollerMotor.set(speed);
    }

    public void setArmSpeed(double speed){
        armMotor.set(speed);
    }

     public void setArmPIDSpeed(double speed){
        //armPIDcontroller.set;
    }


    // Set Outtake Speeds
    public void setOuttakeSpeed(double speed){
      leftOuttakeMotor.set(-speed);
      rightOuttakeMotor.set(speed);
    }

    public void setLeftOuttakeSpeed(double speed){
      leftOuttakeMotor.set(-speed);
    }

    public void setRightOuttakeSpeed(double speed){
      rightOuttakeMotor.set(speed);
    }


    // Find Position Difference from Current Arm Position to Desired Position
    public double getArmPositionDifference(double desiredPosition){
      return (desiredPosition - armEncoder.getPosition());
    }


    // Change Intake Speed
    public void intakeRing(){
      setRollerSpeed(Constants.redlineRollerInPercent);
    }


    // Move Arm
    public void moveArmToIntake(){
      setOuttakeSpeed(Constants.redlineIdlePercent);
      armPIDController.setReference(Constants.armIntakePosition, CANSparkMax.ControlType.kPosition);
    }

    public void moveArmToAmp(){
      setOuttakeSpeed(Constants.redlineIdlePercent);
      armPIDController.setReference(Constants.armAMPPosition, CANSparkMax.ControlType.kPosition);
    }

    public void moveArmToOuttake(){
      setOuttakeSpeed(Constants.redlineOuttakePercent);
      armPIDController.setReference(Constants.armOuttakePosition, CANSparkMax.ControlType.kPosition);    
    }

    public void armStandStill(){
      setOuttakeSpeed(0);
      armPIDController.setReference(armEncoder.getPosition(), CANSparkMax.ControlType.kPosition);    
    }


    // Conditionals
    public boolean atLaunchRPM(){
      Double[] speeds = getOuttakeRPM();
      if (Math.abs(speeds[0]) >= Constants.redlineLaunchRPM && Math.abs(speeds[1]) >= Constants.redlineLaunchRPM){
          return true;
      }
      return false;
    }

    public boolean armAtAMP(){
      return (Math.abs(Constants.armAMPPosition)-Math.abs(armEncoder.getPosition()) < Constants.armTolerance);
    }

    public boolean armAtOuttake(){
      return (Math.abs(Constants.armOuttakePosition)-Math.abs(armEncoder.getPosition()) < Constants.armTolerance);
    }

    public boolean armAtIntake(){
      return (Math.abs(Constants.armIntakePosition)-Math.abs(armEncoder.getPosition()) < Constants.armTolerance);
    }

    public boolean canShootOuttake(){
      return (armAtOuttake() && atLaunchRPM() && intakeHasRing());
    }

    public boolean canShootAMP(){
      return (armAtAMP() && intakeHasRing());
    }

    public boolean canShoot(){
      return (canShootOuttake() || canShootAMP());
    }


    // Shooting
    public boolean shoot(){
      if (canShootAMP()){
        setRollerSpeed(Constants.redlineRollerShootPercent);
        return true;
      }

      else if (canShootOuttake()){
        setRollerSpeed(Constants.redlineRollerOutPercent);
        return true;
      }

      return false;
    }


    @Override
    public void periodic() {
      // SmartDashboard
      SmartDashboard.putBoolean("At Intake", armAtIntake());
      SmartDashboard.putBoolean("At Amp", armAtAMP());
      SmartDashboard.putBoolean("At Outtake", armAtOuttake());
      SmartDashboard.putBoolean("Can Shoot", canShoot());
      SmartDashboard.putNumber("Arm Angle", armEncoder.getPosition());
      SmartDashboard.putNumber("Error", armEncoder.getPosition() - Constants.armAMPPosition);
    }
}
