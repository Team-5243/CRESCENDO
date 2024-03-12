package frc.robot.subsystems;

import com.playingwithfusion.CANVenom;
import com.playingwithfusion.CANVenom.BrakeCoastMode;
import com.playingwithfusion.CANVenom.ControlMode;
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
    CANSparkMax rollerMotor;
    CANVenom armMotor;


    // Outtake
    CANSparkMax leftOuttakeMotor;
    CANSparkMax rightOuttakeMotor;

    DigitalInput throughBoreInputArm;
    DutyCycleEncoder throughBoreEncoderArm;

    DigitalInput intakeLimitSwitch;
    DigitalInput outtakeLimitSwitch;

    DigitalInput topLimitSwitch;
    DigitalInput bottomLimitSwitch;

    DigitalInput throughBoreInputOuttake;
    DutyCycleEncoder throughBoreEncoderOuttake;

    public OuttakeAndIntakeSubsystem() {
        // Intake
        rollerMotor = new CANSparkMax(Constants.Roller,  MotorType.kBrushed);
        armMotor = new CANVenom(50);
        armMotor.setBrakeCoastMode(CANVenom.BrakeCoastMode.Brake);

        throughBoreInputArm = new DigitalInput(0);
        throughBoreEncoderArm = new DutyCycleEncoder(throughBoreInputArm);
        intakeLimitSwitch = new DigitalInput(1);


        // Outtake
        leftOuttakeMotor = new CANSparkMax(Constants.OutLeft, MotorType.kBrushed);
        rightOuttakeMotor = new CANSparkMax(Constants.OutRight,  MotorType.kBrushed);
        throughBoreInputOuttake = new DigitalInput(2);
        throughBoreEncoderOuttake = new DutyCycleEncoder(throughBoreInputOuttake);
        outtakeLimitSwitch = new DigitalInput(3);
    }


    // Resets
    public void resetEncoders(){
      throughBoreEncoderArm.reset();
      throughBoreEncoderOuttake.reset();
    }

    public void resetMotors(){
        armMotor.clearLatchedFaults();
        rollerMotor.clearFaults();
        leftOuttakeMotor.clearFaults();
        rightOuttakeMotor.clearFaults();
    }

    public void zeroMotor(){
      armMotor.resetPosition();
    }

    // PID Tuning for Arm
    public void setArmPID(){
        armMotor.setKP(Constants.armKp);
        armMotor.setKI(Constants.armKi);
        armMotor.setKD(Constants.armKd);
        armMotor.setKF(Constants.armKf);
        armMotor.setMaxAcceleration(Constants.armMaxA);
        armMotor.setMaxSpeed(Constants.armMaxV);
    }


    // Accessor Methods
    public double getThroughBoreArm(){
      return throughBoreEncoderArm.getDistance();
    }

    public double getThroughBoreOuttake(){
      return throughBoreEncoderOuttake.getDistance();
    }

    public double getIntakeSpeed(){
        return rollerMotor.get();
    }

    public double getArmSpeed(){
        return armMotor.get();
    }

    public Double[] getOuttakeSpeed(){
      return new Double[] {leftOuttakeMotor.get(), rightOuttakeMotor.get()};
    }

    public boolean intakeHasRing(){
      return intakeLimitSwitch.get();
    }

    public boolean outtakeHasRing(){
      return outtakeLimitSwitch.get();
    }


    // Mutator Methods
    public void setRollerSpeed(double speed){
        rollerMotor.set(speed);
    }

    public void setArmSpeed(double speed){
        armMotor.set(speed);
    }

    public void setOuttakeSpeed(double speed){
      leftOuttakeMotor.set(-speed);
      rightOuttakeMotor.set(speed);
    }


    // Helper
    public double getArmPositionDifference(double desiredPosition){
      return (desiredPosition - armMotor.getPosition());
    }


    // Change Intake Speed
    public void intakeRing(){
      setRollerSpeed(Constants.redlineRollerInPercent);
    }


    // Move Arm
    public void moveArmToIntake(){
      System.out.println("Moving Arm to Intake");
      armMotor.setCommand(ControlMode.PositionControl, getArmPositionDifference(Constants.armIntakePosition));
    }

    public void moveArmToAmp(){
      System.out.println("Moving Arm to AMP");
      armMotor.setCommand(ControlMode.PositionControl, getArmPositionDifference(Constants.armAMPPosition));
    }

    public void moveArmToOuttake(){
      System.out.println("Moving Arm to Outtake and Set Outtake Speed");
      setOuttakeSpeed(Constants.redlineOuttakePercent);
      armMotor.setCommand(ControlMode.PositionControl, getArmPositionDifference(Constants.armOuttakePosition));
    }


    // Conditionals
    public boolean atLaunchRPM(){
      Double[] speeds = getOuttakeSpeed();
      if (Math.abs(speeds[0]) >= Constants.redlineLaunchRPM && Math.abs(speeds[1]) >= Constants.redlineLaunchRPM){
          return true;
      }
      return false;
    }

    public boolean armAtAMP(){
      return (armMotor.getPosition()-Constants.armAMPPosition < Constants.armTolerance);
    }

    public boolean armAtOuttake(){
      return (armMotor.getPosition()-Constants.armOuttakePosition < Constants.armTolerance);
    }

    public boolean armAtIntake(){
      return (armMotor.getPosition()-Constants.armIntakePosition < Constants.armTolerance);
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
      SmartDashboard.putNumber("Arm Angle", armMotor.getPosition());
    }
}
