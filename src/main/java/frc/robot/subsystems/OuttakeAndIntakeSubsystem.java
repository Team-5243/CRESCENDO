package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.IdleMode;

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

    DigitalInput throughBoreInputArm;
    DutyCycleEncoder throughBoreEncoderArm;


    // Outtake
    CANSparkMax leftOuttakeMotor;
    CANSparkMax rightOuttakeMotor;

    DigitalInput intakeLimitSwitch;

    DigitalInput throughBoreInputOuttake;
    DutyCycleEncoder throughBoreEncoderOuttake;


    public OuttakeAndIntakeSubsystem() {
        // Intake
        rollerMotor = new CANSparkMax(Constants.IntakeMotorRoller,  MotorType.kBrushless);
        armMotor = new CANSparkMax(Constants.IntakeMotorArm, MotorType.kBrushless);
        armEncoder = armMotor.getEncoder();

        intakeLimitSwitch = new DigitalInput(Constants.IntakeLimitSwitch);

        // Outtake
        leftOuttakeMotor = new CANSparkMax(Constants.OuttakeMotorLeft, MotorType.kBrushless);
        rightOuttakeMotor = new CANSparkMax(Constants.OuttakeMotorRight,  MotorType.kBrushless);

        leftOuttakeMotor.setIdleMode(IdleMode.kCoast);
        rightOuttakeMotor.setIdleMode(IdleMode.kCoast);

        // Outtake Bore
        throughBoreInputOuttake = new DigitalInput(Constants.OuttakeBore);
        throughBoreEncoderOuttake = new DutyCycleEncoder(throughBoreInputOuttake);

        // Arm Bore
        throughBoreInputArm = new DigitalInput(Constants.ArmBore);
        throughBoreEncoderArm = new DutyCycleEncoder(throughBoreInputArm);
    }


    // Resets
    public void resetEncoders(){
      throughBoreEncoderOuttake.reset();
      throughBoreEncoderArm.reset();
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
    public double getOuttakeRPM(){
        return throughBoreEncoderOuttake.get();
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


    // Change Intake Speed
    public void intakeRing(){
      setRollerSpeed(Constants.redlineRollerInPercent);
    }


    // Move Arm
    public void moveArmToIntake(){
      armPIDController.setReference(Constants.armIntakePosition, CANSparkMax.ControlType.kPosition);
    }

    public void moveArmToAmp(){
      armPIDController.setReference(Constants.armAMPPosition, CANSparkMax.ControlType.kPosition);
    }

    public void moveArmToOuttake(){
      armPIDController.setReference(Constants.armOuttakePosition, CANSparkMax.ControlType.kPosition);    
    }


    // Conditionals
    public boolean atLaunchRPM(){
      if (Math.abs(getOuttakeRPM()) >= Constants.outtakeMinimumLaunchSpeed){
          return true;
      }
      return false;
    }

    public boolean armAtAMP(){
      return (Math.abs(Constants.armAMPPosition-armEncoder.getPosition()) < Constants.armToleranceDisplay);
    }

    public boolean armAtOuttake(){
      return (Math.abs(Constants.armOuttakePosition-armEncoder.getPosition()) < Constants.armToleranceDisplay);
    }

    public boolean armAtIntake(){
      return (Math.abs(Constants.armIntakePosition-armEncoder.getPosition()) < Constants.armToleranceDisplay);
    }

    public boolean canShootOuttake(){
      return (armAtOuttake() && atLaunchRPM());
    }

    public boolean canShootAMP(){
      return (armAtAMP());
    }

    public boolean canShoot(){
      return (canShootOuttake() || canShootAMP());
    }


    // Shooting Outtake
    public void shootOuttake(){
        setRollerSpeed(Constants.redlineRollerOutPercent);
    }


    // Shooting AMP
    public void shootAMP(){
        setRollerSpeed(Constants.redlineRollerShootPercent);
    }


    @Override
    public void periodic() {
      // SmartDashboard
      SmartDashboard.putBoolean("At Intake", armAtIntake());
      SmartDashboard.putBoolean("At Amp", armAtAMP());
      SmartDashboard.putBoolean("At Outtake", armAtOuttake());
      SmartDashboard.putNumber("Arm Position", armEncoder.getPosition());
      SmartDashboard.putBoolean("Shoot", canShoot());
    }
}
