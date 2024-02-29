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

    // If Trigger Pressed
    // Shoot Ring
    if (Constants.secondStick.getTrigger()){
        m_outtakeAndIntakeSubsystem.shootRing();
    }

    // If Side Button Pressed
    // Set Outtake Speed to 1
    if (Constants.secondStick.getRawButton(1)){
        m_outtakeAndIntakeSubsystem.setOuttakeSpeed(0.6);
    }
    else {
        m_outtakeAndIntakeSubsystem.setOuttakeSpeed(0);
    }

    // If Button #3 (Left Bottom) Pressed
    // Spin Intake to Take in Ring
    if (Constants.secondStick.getRawButton(2)){
        m_outtakeAndIntakeSubsystem.intakeRing();
    }

    // // If Button #4 (Right Bottom) Pressed
    // // Align Arm with Intake
    // if (Constants.secondStick.getRawButton(3)){
    //     m_outtakeAndIntakeSubsystem.moveArmToIntake();
    // }

    // // If Button #6 (Right Top) Pressed
    // // Align Arm with Outtake
    // if(Constants.secondStick.getRawButton(5)){
    //     m_outtakeAndIntakeSubsystem.moveArmToOuttake();
    // }
  }


  @Override
  public void end(boolean interrupted) {}


  @Override
  public boolean isFinished() {
    return false;
  }
}
