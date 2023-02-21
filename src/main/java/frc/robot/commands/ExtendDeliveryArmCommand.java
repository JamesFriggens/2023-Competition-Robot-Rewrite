// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DeliveryArmSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ExtendDeliveryArmCommand extends CommandBase{
    
  private final DeliveryArmSubsystem m_deliveryArmSubsystem;

  private double endPosition;
  private boolean isLower;

  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})


  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ExtendDeliveryArmCommand(double endPosition) {
    m_deliveryArmSubsystem = RobotContainer.m_deliverySubsystem;    
    this.endPosition = endPosition;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_deliveryArmSubsystem);
  }

  @Override
  public void initialize() {
    m_deliveryArmSubsystem.Release();

    if (endPosition > m_deliveryArmSubsystem.GetDeliveryArmPosition()){
        isLower = true;
        m_deliveryArmSubsystem.SetDeliveryArmSpeed(.3);
    }
    else if (endPosition < m_deliveryArmSubsystem.GetDeliveryArmPosition()){
      isLower = false;
      m_deliveryArmSubsystem.SetDeliveryArmSpeed(-0.3);
    }

    else{
      System.out.println("Error in ExtendDeliveryArmCommand initialize()");
    }
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    m_deliveryArmSubsystem.SetDeliveryArmSpeed(0);
    m_deliveryArmSubsystem.Stop();
  }

  @Override
  public boolean isFinished() {
    
    return (isLower && m_deliveryArmSubsystem.GetDeliveryArmPosition() > endPosition) || 
    (!isLower && m_deliveryArmSubsystem.GetDeliveryArmPosition() < endPosition);
  }
}
