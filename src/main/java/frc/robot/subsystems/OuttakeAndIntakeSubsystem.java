package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

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
    PIDController armPIDcontroller;


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
        armPIDcontroller = new PIDController(Constants.armKp, Constants.armKi, Constants.armKd);

        intakeLimitSwitch = new DigitalInput(Constants.IntakeLimitSwitch);

        // Outtake
        leftOuttakeMotor = new CANSparkMax(Constants.OuttakeMotorLeft, MotorType.kBrushed);
        rightOuttakeMotor = new CANSparkMax(Constants.OuttakeMotorRight,  MotorType.kBrushed);

        // Left Outtake Bore
        throughBoreInputOuttakeLeft = new DigitalInput(Constants.OuttakeBoreLeft);
        throughBoreEncoderOuttakeLeft = new DutyCycleEncoder(throughBoreInputOuttakeLeft);

        // Right Outtake Bore
        throughBoreInputOuttakeLeft = new DigitalInput(Constants.OuttakeBoreRight);
        throughBoreEncoderOuttakeLeft = new DutyCycleEncoder(throughBoreInputOuttakeLeft);
    }


    // Resets
    public void resetEncoders(){
      throughBoreEncoderOuttakeLeft.reset();
      throughBoreEncoderOuttakeRight.reset();
      armEncoder.setPosition(0);
    }

    public void resetMotors(){
        rollerMotor.clearFaults();
        leftOuttakeMotor.clearFaults();
        rightOuttakeMotor.clearFaults();
    }

    public void zeroMotor(){
        armEncoder.setPosition(0);
    }


    // PID Tuning for Arm
    // public void setArmPID(){
    //     armMotor.setKP(Constants.armKp);
    //     armMotor.setKI(Constants.armKi);
    //     armMotor.setKD(Constants.armKd);
    //     armMotor.setKF(Constants.armKf);
    //     armMotor.setMaxAcceleration(Constants.armMaxA); VENOM
    //     armMotor.setMaxSpeed(Constants.armMaxV);
    // }

    //  public void setPILimit(double speed){
    //     armMotor.setMaxPILimit());
    // }


    // Reset Setpoint
    public void resetArmSetPos(){    
        armMotor.set(armPIDcontroller.calculate(armEncoder.getPosition(),0));
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
      System.out.println("Moving Arm to Intake");
      // armMotor.setCommand(ControlMode.PositionControl, getArmPositionDifference(Constants.armIntakePosition)*4096);
      armMotor.set(armPIDcontroller.calculate(armEncoder.getPosition(), Constants.armIntakePosition));
    }

    public void moveArmToAmp(){
      System.out.println("Moving Arm to AMP");
      // armMotor.setCommand(ControlMode.PositionControl, getArmPositionDifference(Constants.armAMPPosition)*4096);
      armMotor.set(armPIDcontroller.calculate(armEncoder.getPosition(), -Constants.armAMPPosition));
    }

    public void moveArmToOuttake(){
      System.out.println("Moving Arm to Outtake and Set Outtake Speed");
      setOuttakeSpeed(Constants.redlineOuttakePercent);
      // armMotor.setCommand(ControlMode.PositionControl, getArmPositionDifference(Constants.armOuttakePosition)*4096);
      armMotor.set(armPIDcontroller.calculate(armEncoder.getPosition(), Constants.armOuttakePosition));
    }

    public void armStandStill(){
      System.out.println("Moving Arm to Outtake and Set Outtake Speed");
      setOuttakeSpeed(Constants.redlineOuttakePercent);
      // armMotor.setCommand(ControlMode.PositionControl, getArmPositionDifference(Constants.armOuttakePosition)*4096);
      armMotor.set(armPIDcontroller.calculate(armEncoder.getPosition(), armEncoder.getPosition()));
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
      System.out.println("Pos " + armEncoder.getPosition());
      System.out.println("Speed " + armEncoder.getVelocity());
      System.out.println("Error " + (Constants.armAMPPosition-armEncoder.getPosition()));
      SmartDashboard.putNumber("Error", Constants.armAMPPosition-armEncoder.getPosition());
    }
}
