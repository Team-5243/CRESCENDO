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

  }


  @Override
  public void execute() {

    // If Side Button Pressed
    // Set Outtake Speed to 1 & Set Idle Speed
    if (Constants.secondStick.getRawButton(1)){
        m_outtakeAndIntakeSubsystem.setOuttakeSpeed(1);
    }
    else {
        m_outtakeAndIntakeSubsystem.setOuttakeSpeed(Constants.redlineIdlePercent);
    }

    

    // Button Controls for Arm (Left Buttons)
    if (Constants.secondStick.getRawButton(5)){
        m_outtakeAndIntakeSubsystem.moveArmToIntake();
    } 
    
    else if (Constants.secondStick.getRawButton(3)) {
        m_outtakeAndIntakeSubsystem.moveArmToOuttake();
    } 

    else {
      m_outtakeAndIntakeSubsystem.setArmSpeed(0);
    }

        // Button Controls for Arm (Right Buttons)
    if (Constants.secondStick.getRawButton(4)){
        m_outtakeAndIntakeSubsystem.intakeRing();
    } 
    
    else if (Constants.secondStick.getRawButton(6)) {
        m_outtakeAndIntakeSubsystem.loadOuttake();
    } 

    else {
      m_outtakeAndIntakeSubsystem.setRollerSpeed(0);
    }

    if (Constants.secondStick.getRawButton(2)){
      m_outtakeAndIntakeSubsystem.holdArmAtAmp();
    }

    if (Constants.secondStick.getRawButton(12)){
      m_outtakeAndIntakeSubsystem.getArmPosition();
    }
  }
  
  @Override
  public void end(boolean interrupted) {}


  @Override
  public boolean isFinished() {
    return false;
  }
}
