package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.LimelightHelpers;

public class Limelight {
    double distance;
   
    public void findDistance(){
        Target target = getTarget();
        distance = target.tx;
        SmartDashboard.putNumber("Distance", distance);
    }
    //
    //
    //
    //Ignore comments I don't think they are correct
    //
    //
    //
//Limelight wont be centored so do math later when robot built, im sick and have bad brain fog get off my back for english
    public static class Target {
        public double tx;
        public double ty;
        public double tv;
        public double ta;
        public double ts;

        Target(double x, double y, double v, double a, double s) {
            tx = x;
            ty = y;
            tv = v;
            ta = a;
            ts = s;
        }
  }

  public static Target getTarget() {
    double tx = getTableEntry("tx").getDouble(0.0);
    double ty = getTableEntry("ty").getDouble(0.0);
    double ta = getTableEntry("ta").getDouble(0.0);
    double tv = getTableEntry("tv").getDouble(0.0);
    double ts = getTableEntry("ts").getDouble(0.0);
    return new Target(tx, ty, tv, ta, ts);
  }

  private static NetworkTableEntry getTableEntry(String entry) {
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry(entry);
  }

  // April Tag Things


  public void setPipeline(int pipe){
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(pipe);
  }

  public String whatPipeline(int pipe){
    String result= "Only 0 and 1 are valid pipelines!";
    if(pipe == 0) result= "April Tag Pipeline";
    else if (pipe == 1) result= "RetroReflector Pipeline";
    return result;
  }

  /**
   * Outputs the primary in-view April Tag seen by the robot
   * PIPELINE MUST BE SET TO 0
   * 
   * @return The April Tag data registered by the robot
   */
  public static double getAprilTagData(){
    return LimelightHelpers.getFiducialID("limelight");
  }

  /**
   * (tx, ty, ta)
   * 
   * @return The X and Y coordinates of the fidual marker being tracked and the area of the camera the marker takes
  */
  public static double[] getTagPos(){
    double[] tagPos = new double[3];
    tagPos[0] = LimelightHelpers.getTX("limelight");
    tagPos[1] = LimelightHelpers.getTY("limelight");
    tagPos[2] = LimelightHelpers.getTA("limelight");
    return tagPos;
  }

  /**
   * Pipeline must be set to 0 and April Tag being tracked
   * 
   * @return The location of the April Tag in 3D space relative to the Camera
   */
  public static double[] getAprilTagCoord(){
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("targetpose_cameraspace").getDoubleArray(new double[6]);
  }

  /**
   * Pose relative to the robot
   * @return (x, y, z, qw, qx, qy, qz)
   */
  public static double[] getTargetPose(){
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("targetPose").getDoubleArray(new double[7]);
  }

  /**
   * Pitch of target in degrees (positive up)
   * Yaw of target in degrees (positive right)
   * Percent of bounding box in screen (0-100)
   * Skew of target in degrees (counter-clockwise positive)
   * 
   * @return (targetPitch, targetYaw, targetArea, targetSkew)
   */
  public static double[] getTargetData(){
    double[] ret = new double[4];
    ret[0] = NetworkTableInstance.getDefault().getTable("limelight").getEntry("targetPitch").getDouble(0.0);
    ret[1] = NetworkTableInstance.getDefault().getTable("limelight").getEntry("targetYaw").getDouble(0.0);
    ret[2] = NetworkTableInstance.getDefault().getTable("limelight").getEntry("targetArea").getDouble(0.0);
    ret[3] = NetworkTableInstance.getDefault().getTable("limelight").getEntry("targetSkew").getDouble(0.0);
    return ret;
  }

  public static void DisplayDiagnostics() {
    if (frc.robot.Constants.SystemMap.LimelighteSubsystem.DiagnosticLevel == 0) return;
    
    SmartDashboard.putNumber("tx", LimelightHelpers.getTX("limelight"));
    SmartDashboard.putNumber("ty", LimelightHelpers.getTY("limelight"));
    SmartDashboard.putNumber("ta", LimelightHelpers.getTA("limelight"));
    SmartDashboard.putBoolean("tv", LimelightHelpers.getTV("limelight"));
    SmartDashboard.putNumber("tagID", LimelightHelpers.getFiducialID("limelight"));
  }
 
}