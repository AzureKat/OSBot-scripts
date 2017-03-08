import org.osbot.rs07.api.Objects;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.Interactable;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.utility.ConditionalSleep;

@ScriptManifest(name = "pip", author = "lul", version = 1.0, info = "", logo = "")
public final class ScriptName extends Script {


    private enum State {
        CHOP, DROP, WAIT
    }

    @Override
    public final void onStart() {
        Entity tree = null;
        tree = objects.closest("Oak");
        log("This is a test script");
    }


    private State getState(){

        Entity tree = null;
        tree = objects.closest("Oak");

        if(inventory.isFull()){
            return State.DROP;
        }

        if(tree != null && !myPlayer().isAnimating() && !myPlayer().isMoving()){
            return State.CHOP;
        }

        return State.WAIT;

    }

    @Override
    public final int onLoop() throws InterruptedException {

        Interactable oakTree = null;
        oakTree = objects.closest("Oak");
        int Log = 1521;

        switch(getState()) {
            case CHOP:
                Entity pippeli = objects.closest("Oak");
                if (pippeli != null && !myPlayer().isAnimating() && !myPlayer().isMoving()){
                    pippeli = objects.closest("Oak");
                    pippeli.interact("Chop down");
                    sleep(random(700,900));
                }
                break;

            case DROP:
                if(inventory.isFull()){
                    inventory.dropAll(Log);
                }
                break;

            case WAIT:
                sleep(random(3000, 7000));
                break;
        }
        return random(200, 300);
    }

    @Override
    public final void onExit() {
        log("Script exiting");
    }

}
