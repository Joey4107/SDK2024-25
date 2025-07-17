package org.firstinspires.ftc.teamcode.Handlers;

import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Event;
import org.firstinspires.ftc.robotcore.external.State;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Subsystem.S_Lift;
public class Lift extends SubsystemBase {
    private LiftStates desiredState, currentState = LiftStates.IDLE;
    private S_Lift lift = S_Lift.getInstance();
    private static Lift m_Instance;

    private Lift() {}

    public static Lift getInstance() {
        if(m_Instance == null) {
            m_Instance = new Lift();
        }
        return m_Instance;
    }

    public void setDesiredState(State state) {
        if(this.desiredState != state) {
            desiredState = (LiftStates) state;
            handleStateTransition();
        }
    }

    public void handleStateTransition() {
            switch(desiredState) {
                case IDLE:
                    lift.stop();
                    break;
                case BROKEN:
                    lift.stop();
                    break;
                case HIGHBASKET:
                    lift.setSetpoint(Constants.LiftConstants.HIGHBASKET_POSITION);
                    break;
                case WALLPICKUP:
                    lift.setSetpoint(Constants.LiftConstants.WALLPICKUP_POSITION);
                    break;
                case CHAMBER:
                    lift.setSetpoint(Constants.LiftConstants.CHAMBER_POSITION);
                    break;

                default:
                break;
            }

            currentState = desiredState;
        }
        public void update() {
            switch(currentState) {
                case IDLE:
                    break;
                case BROKEN:
                    break;
                case HIGHBASKET:
                    lift.moveToSetpoint();
                    break;
                case WALLPICKUP:
                    lift.moveToSetpoint();
                    break;
                case CHAMBER:
                    lift.moveToSetpoint();
                    break;

                default:
                    break;
            }

            if(!lift.checkSubsystem()) {
                setDesiredState(LiftStates.BROKEN);
            }
        }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        update();
    }

    /**
     * @return The current state of the subsystem
     */
    public LiftStates getState() {
        return currentState;
    }

    public enum LiftStates implements State {
        IDLE,
        BROKEN,
        HIGHBASKET,
        WALLPICKUP,
        CHAMBER,
        ;

        @Override
        public void onEnter(Event event) {
        }
        @Override
        public void onExit(Event event) {
        }
    }
}
