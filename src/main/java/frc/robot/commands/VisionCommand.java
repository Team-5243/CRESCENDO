package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class VisionCommand extends Command {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final VisionSubsystem m_visionSubsystem;

    public VisionCommand(VisionSubsystem subsystem){
        m_visionSubsystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void initialize(){
    }

    @Override
    public void execute(){
    }
}
