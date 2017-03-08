import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.Interactable;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;

public final class Fletcher extends Script {

    private enum State {
        BANK, FLETCH, WAIT, WITHDRAW
    }

    @Override
    public final void onStart() {
        log("Fletcher");
    }

    private State getState(){

        int bowId = 0;
        int bowString = 0;
        int completedbow = 0;

        if (getInventory().isEmpty() || getInventory().contains(completedbow)){
            return State.BANK;
        }

        if(getBank().isOpen()){
            return State.WITHDRAW;
        }

        if(getInventory().contains(bowId, bowString)){
            return State.FLETCH;
        }

        return State.WAIT;

    }

    public final int onLoop() throws InterruptedException {

        int bowId = 0;
        int bowString = 0;

        switch (getState()){
            case BANK:
                Entity bank = getObjects().closest("Bank booth");
                if(bank != null && bank.exists()){
                if(bank.interact("Bank")){
                    new Sleep(() -> getBank().isOpen(), 5000).sleep();
                }
            }
            break;
            case WITHDRAW:
                if (getBank().isOpen()){
                    if(!getInventory().contains(bowId, bowString)){
                        if (getBank().contains(bowId, bowString)) {
                            getBank().withdraw(bowId, 14);
                            getBank().withdraw(bowString, 14);
                            getBank().close();
                        }
                    }
                }
            break;
            case FLETCH:
                if(getInventory().contains(bowId, bowString)){
                    getInventory().interact("Use", bowId, bowString);


                }
        }
        return random(200, 300);
    }

    @Override
    public final void onExit() {
        log("Script exiting");
    }
}
