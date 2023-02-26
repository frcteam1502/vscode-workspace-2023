# Pneumatics
Pneumatic Hub (REV-11-1852)

## Compressor
There are two types of compressors supported by WPI Library.

Pneumatics Module Type
*  CTREPCM
*  REVPH

Storage Tanks (e.g., 125psi)

## Double Solenoid
The double solenoid is for running 2 channels of high voltage Digital Output on the pneumatics
module. For instance, to use two channels to move the piston forward and reverse.


The double solenoid has 3 states, called Value, which is one of: kOff, kForward, kReverse.
e.g.,:
```java
DoubleSolenoid.set(Value.kForward);
Value value = DoubleSolenoid.get(); // value == Value.kFormward
```

# Library

## WPI
[Pneumatics APIs](https://docs.wpilib.org/en/stable/docs/software/hardware-apis/pneumatics/pneumatics.html)

```java
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
```


# History
* 2023 "Charged Up"