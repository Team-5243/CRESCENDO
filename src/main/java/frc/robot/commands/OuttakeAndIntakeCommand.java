package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.OuttakeAndIntakeSubsystem;
import java.lang.*;


public class OuttakeAndIntakeCommand extends Command {
    
  // Variables
  long timeOfShot;

  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final OuttakeAndIntakeSubsystem m_outtakeAndIntakeSubsystem;

  public OuttakeAndIntakeCommand(OuttakeAndIntakeSubsystem subsystem) {
    m_outtakeAndIntakeSubsystem = subsystem;
    addRequirements(subsystem);
  }


  @Override
  public void initialize() {
      // Variables
      timeOfShot = 0;

      // Reset Encoders
      m_outtakeAndIntakeSubsystem.resetEncoders();

      // Reset Motor
      m_outtakeAndIntakeSubsystem.resetMotors();

      // Set PID
      m_outtakeAndIntakeSubsystem.setArmPID();

      // Set Outtake Idle
      m_outtakeAndIntakeSubsystem.setOuttakeSpeed(Constants.redlineIdlePercent);

  }


  @Override
  public void execute() {

    // Stop All Actions for Constants.waitAfterShoot time after a shot
    if (timeOfShot != 0 && timeOfShot + Constants.waitAfterShoot <= System.currentTimeMillis()){
        return;
    }

    // After waiting the time
    if (timeOfShot > 0){
        m_outtakeAndIntakeSubsystem.moveArmToIntake();
        m_outtakeAndIntakeSubsystem.setOuttakeSpeed(Constants.redlineIdlePercent);
        m_outtakeAndIntakeSubsystem.setRollerSpeed(0);
        timeOfShot = 0;
    }
    
    // Shoot
    if (Constants.secondStick.getRawButton(1)){
        if (m_outtakeAndIntakeSubsystem.shoot()){
          timeOfShot = System.currentTimeMillis();
        }
    }

    // Spin Intake Rollers
    if (Constants.secondStick.getRawButton(2)){
        m_outtakeAndIntakeSubsystem.setRollerSpeed(Constants.redlineRollerInPercent);
    }
    else {
        m_outtakeAndIntakeSubsystem.setRollerSpeed(0);
    }


    // Button Controls for Arm (SET POSITION)
    if (Constants.secondStick.getRawButton(5)){
        m_outtakeAndIntakeSubsystem.moveArmToIntake();
    } 
    
    else if (Constants.secondStick.getRawButton(3)){
        m_outtakeAndIntakeSubsystem.moveArmToAmp();
    }

    else if (Constants.secondStick.getRawButton(4)) {
        m_outtakeAndIntakeSubsystem.moveArmToOuttake();
    } 
    
    else {
        m_outtakeAndIntakeSubsystem.armStandStill();
    }
  }
  
  @Override
  public void end(boolean interrupted) {
    
  }


  @Override
  public boolean isFinished() {
    return false;
  }
}
