package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.OuttakeSubsystem;


public class OuttakeCommand extends Command {
    
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final OuttakeSubsystem m_outtakeSubsystem;

  public OuttakeCommand(OuttakeSubsystem subsystem) {
    m_outtakeSubsystem = subsystem;
    addRequirements(subsystem);
  }


  @Override
  public void initialize() {
  }


  @Override
  public void execute() {
    m_outtakeSubsystem.setSpeed(0.6);
    if (Constants.mainStick.getTrigger() == true){
        m_outtakeSubsystem.shoot();
    }
  }


  @Override
  public void end(boolean interrupted) {}


  @Override
  public boolean isFinished() {
    return false;
  }
}
