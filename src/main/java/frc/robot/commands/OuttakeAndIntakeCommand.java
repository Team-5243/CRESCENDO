package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.OuttakeAndIntakeSubsystem;


public class OuttakeAndIntakeCommand extends Command {
    
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final OuttakeAndIntakeSubsystem m_outtakeAndIntakeSubsystem;

  public OuttakeAndIntakeCommand(OuttakeAndIntakeSubsystem subsystem) {
    m_outtakeAndIntakeSubsystem = subsystem;
    addRequirements(subsystem);
  }


  @Override
  public void initialize() {
      // Reset Encoders
      m_outtakeAndIntakeSubsystem.resetEncoders();

      // Reset Motor
      m_outtakeAndIntakeSubsystem.resetMotors();

      // Set PID
      m_outtakeAndIntakeSubsystem.setArmPID();
  }


  @Override
  public void execute() {

    // Trigger Outtake (Pressed )
    if (Constants.secondStick.getRawButton(1)){
        m_outtakeAndIntakeSubsystem.setOuttakeSpeed(Constants.redlineOuttakePercent);
    }
    else {
        m_outtakeAndIntakeSubsystem.setOuttakeSpeed(Constants.redlineIdlePercent);
    }


    // Button Controls for Rollers
    if (Constants.secondStick.getRawButton(4)){
        m_outtakeAndIntakeSubsystem.intakeRing();
    } 
    
    else if (Constants.secondStick.getRawButton(6)) {
        m_outtakeAndIntakeSubsystem.loadOuttake();
    } 

    else {
      m_outtakeAndIntakeSubsystem.setRollerSpeed(0);
    }


    // Button Controls for Arm
    if (Constants.secondStick.getRawButton(8)){
        m_outtakeAndIntakeSubsystem.moveArmToIntake();
    } 
    
    if (Constants.secondStick.getRawButton(10)) {
        m_outtakeAndIntakeSubsystem.moveArmToOuttake();
    } 

    if (Constants.secondStick.getRawButton(12)){
      m_outtakeAndIntakeSubsystem.moveArmToAmp();
    }
  }
  
  @Override
  public void end(boolean interrupted) {}


  @Override
  public boolean isFinished() {
    return false;
  }
}
