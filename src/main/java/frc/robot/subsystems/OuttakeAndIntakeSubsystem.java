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
    DigitalInput throughBoreInput;
    DutyCycleEncoder throughBoreEncoder;
    CANSparkMax intakeMotor;
    CANSparkMax armMotor;
    DigitalInput alignedWithIntake;


    // Outtake
    CANSparkMax leftOuttakeMotor;
    CANSparkMax rightOuttakeMotor;
    DigitalInput alignedWithOutake;

    public OuttakeAndIntakeSubsystem() {
        // Intake
        throughBoreInput = new DigitalInput(0);
        throughBoreEncoder = new DutyCycleEncoder(throughBoreInput);
        intakeMotor = new CANSparkMax(Constants.In1,  MotorType.kBrushless);
        armMotor = new CANSparkMax(Constants.In2,  MotorType.kBrushless);
        alignedWithIntake = new DigitalInput(1);

        // Outtake
        leftOuttakeMotor = new CANSparkMax(Constants.Out1, MotorType.kBrushless);
        rightOuttakeMotor = new CANSparkMax(Constants.Out2,  MotorType.kBrushless);
        alignedWithOutake = new DigitalInput(2);

        // Reset Encoders
        resetEncoders();

        // Reset Motor
        resetMotor();
    }

    // Encoders
    public double getDistance(){
      return throughBoreEncoder.getDistance();
    }

    public void resetEncoders(){
      throughBoreEncoder.reset();
    }

    // Motors Helper Functions
    public void resetMotor(){
        intakeMotor.clearFaults();
        armMotor.clearFaults();
        leftOuttakeMotor.clearFaults();
        rightOuttakeMotor.clearFaults();
    }


    // Intake
    public double getIntakeSpeed(){
        return intakeMotor.get();
    }

    public double getArmSpeed(){
        return armMotor.get();
    }

    public void setIntakeSpeed(double speed){
        intakeMotor.set(speed);
    }

    public void setArmSpeed(double speed){
        armMotor.set(speed);
    }

    public void intakeRing(){
      setIntakeSpeed(1);
    }

    public void outputRing(){
      setIntakeSpeed(-0.3);
    }

    // Move Arm
    public void moveArmToOuttake(){
      // if limit switch not touched
    }

    public void moveArmToIntake(){
      // if limit switch not touched
    }

    // Outtake
    public Double[] getOuttakeSpeed(){
      return new Double[] {leftOuttakeMotor.get(), rightOuttakeMotor.get()};
    }

    public void setOuttakeSpeed(double speed){
      leftOuttakeMotor.set(speed);
      rightOuttakeMotor.set(-speed);
    }

    public boolean atLaunchRPM(){
      Double[] speeds = getOuttakeSpeed();
      if (Math.abs(speeds[0]) >= Constants.neoLaunchRPM && Math.abs(speeds[1]) >= Constants.neoLaunchRPM){
          return true;
      }
      return false;
    }

    public boolean canShoot(){
      // change true to if the arm is aligned with outtake
      return (atLaunchRPM() && true);
    }

    // Shoot
    public void shootRing(){
      if (canShoot()){
        outputRing();
        setOuttakeSpeed(1);
      }
    }

    @Override
    public void periodic() {
      // SmartDashboard
      SmartDashboard.putBoolean("Ready to Shoot", canShoot());
      SmartDashboard.putNumber("Intake Bore Encoder", getDistance());
    }
}
