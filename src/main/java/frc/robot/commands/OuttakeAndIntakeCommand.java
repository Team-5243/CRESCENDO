package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.OuttakeAndIntakeSubsystem;
import java.lang.*;


public class OuttakeAndIntakeCommand extends Command {
    
  // Variables
  // NONE

  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final OuttakeAndIntakeSubsystem m_outtakeAndIntakeSubsystem;

  public OuttakeAndIntakeCommand(OuttakeAndIntakeSubsystem subsystem) {
    m_outtakeAndIntakeSubsystem = subsystem;
    addRequirements(subsystem);
  }


  @Override
  public void initialize() {
      // Variables
      // NONE

      // Reset Encoders
      m_outtakeAndIntakeSubsystem.resetEncoders();

      // Reset Motor
      m_outtakeAndIntakeSubsystem.resetMotors();

      // Set PID
      m_outtakeAndIntakeSubsystem.setArmPID();
    }


  @Override
  public void execute() {
    
    // (Trigger - Main Stick) --> Intake Ring
    if (Constants.mainStick.getRawButton(1)){
        m_outtakeAndIntakeSubsystem.setRollerSpeed(Constants.redlineRollerInPercent);
    } 
    
    // (Button 3 - Main Stick) --> Unjam Intake
    // else if (Constants.mainStick.getRawButton(3)){
    //     m_outtakeAndIntakeSubsystem.setRollerSpeed(-Constants.redlineRollerInPercent);
    // } 
    
    // (Button 3 - Main Stick) --> Shoot AMP/Unjam Intake
    else if (Constants.secondStick.getRawButton(4)) {
        m_outtakeAndIntakeSubsystem.shootAMP();
    }

    // (Trigger - Second Stick) --> Shoot Speaker
    else if (Constants.secondStick.getRawButton(1)) {
        m_outtakeAndIntakeSubsystem.shootOuttake();
    }

    // (No Roller Related Button) --> Disable Motor
    else {
        m_outtakeAndIntakeSubsystem.setRollerSpeed(0);
    }


    // (Side - Second Stick) --> Ramp Outtake
    if (Constants.secondStick.getRawButton(2)){
        m_outtakeAndIntakeSubsystem.setOuttakeSpeed(-Constants.outtakeMaxSpeed);
    } 
    
    // (No Outtake Related Button) --> Disable Motor
    else {
        m_outtakeAndIntakeSubsystem.setOuttakeSpeed(0);
    }


    // (Button 5 - Second Stick) --> Move Arm to Intake
    if (Constants.secondStick.getRawButton(5)){
        m_outtakeAndIntakeSubsystem.moveArmToIntake();
    } 
    
    // (Button 3 - Second Stick) --> Move Arm to AMP
    else if (Constants.secondStick.getRawButton(3)){
        m_outtakeAndIntakeSubsystem.moveArmToAmp();
    }

    // (Button 6 - Second Stick) --> Move Arm to Outtake
    else if (Constants.secondStick.getRawButton(6)) {
        m_outtakeAndIntakeSubsystem.moveArmToOuttake();
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
