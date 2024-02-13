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
    if (Constants.secondStick.getTrigger() == true){
        m_outtakeAndIntakeSubsystem.intakeRing();
    }
    m_outtakeAndIntakeSubsystem.setOuttakeSpeed(0.6);
    if (Constants.secondStick.getRawButton(2) && m_outtakeAndIntakeSubsystem.canShoot()){
        m_outtakeAndIntakeSubsystem.shootRing();
    }
  }


  @Override
  public void end(boolean interrupted) {}


  @Override
  public boolean isFinished() {
    return false;
  }
}
