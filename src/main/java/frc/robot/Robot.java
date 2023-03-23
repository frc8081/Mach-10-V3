// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

            // import org.photonvision.PhotonCamera;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoSink;
import edu.wpi.first.cscore.VideoSource.ConnectionStrategy;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;



/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  UsbCamera camera1;
  UsbCamera camera2;
  VideoSink server;

  // Compressor pcmCompressor = new Compressor(PneumaticsModuleType.CTREPCM);

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
    m_robotContainer.pivotOne.home_first_pivot_encoder();
    m_robotContainer.pivotTwo.home_second_pivot_encoder();
    m_robotContainer.extend.home_extend_encoder();
    m_robotContainer.intake.home_intake_encoder();
    m_robotContainer.gyro.CalibrateGyro();



    camera1 = CameraServer.startAutomaticCapture(0);
    camera2 = CameraServer.startAutomaticCapture(1);
    server = CameraServer.getServer();
    

    camera1.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
    camera2.setConnectionStrategy(ConnectionStrategy.kKeepOpen);

                //PhotonCamera camera = new PhotonCamera("Unknown");
    // pcmCompressor.enableDigital();
    // pcmCompressor.disable();
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    m_robotContainer.comp.PressureControl();
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
    m_robotContainer.extend.coast_mode();
    m_robotContainer.pivotOne.coast_mode();
    m_robotContainer.pivotTwo.coast_mode();
    m_robotContainer.intake.coast_mode();
  }

  @Override
  public void disabledPeriodic() {
    m_robotContainer.extend.coast_mode();
    m_robotContainer.pivotOne.coast_mode();
    m_robotContainer.pivotTwo.coast_mode();
    m_robotContainer.intake.coast_mode();
  }

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {

    m_robotContainer.extend.brake_mode();
    m_robotContainer.pivotOne.brake_mode();
    m_robotContainer.pivotTwo.brake_mode();
    m_robotContainer.intake.brake_mode();
  


    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    m_robotContainer.extend.brake_mode();
    m_robotContainer.pivotOne.brake_mode();
    m_robotContainer.pivotTwo.brake_mode();
    m_robotContainer.intake.brake_mode();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.

    m_robotContainer.extend.brake_mode();
    m_robotContainer.pivotOne.brake_mode();
    m_robotContainer.pivotTwo.brake_mode();
    m_robotContainer.intake.brake_mode();
    m_robotContainer.drive.coast_mode();

    m_robotContainer.hopper.Hopperout();
    // m_robotContainer.intake.coast_mode();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    // boolean enabled = pcmCompressor.isEnabled();
    // boolean pressureSwitch = pcmCompressor.getPressureSwitchValue();
    // double current = pcmCompressor.getCurrent();
    // System.out.println(current);
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
