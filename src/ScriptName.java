import org.osbot.Lo;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.Interactable;
import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import java.awt.*;

@ScriptManifest(name = "pip", author = "lul", version = 1.0, info = "", logo = "")
public final class ScriptName extends Script {


    private enum State {
        CHOP, DROP, WAIT
    }

    @Override
    public final void onStart() {
        log("This is a test script");
    }


    private State getState(){
        Entity check = objects.closest("Oak Tree");

        if(inventory.isFull()){
            return State.DROP;
        }

        if(check != null){
            return State.CHOP;
        }

        return State.WAIT;

    }

    @Override
    public final int onLoop() throws InterruptedException {

        Interactable tree = null;
        tree = objects.closest("Oak Tree");
        int Log = 3511;

        switch(getState()) {
            case CHOP:
                tree = objects.closest("Oak Tree");
                if(tree != null){
                    tree.interact("Chop Down");
                }
                sleep(random(1000));
                break;

            case DROP:
                if(inventory.isFull()){
                    inventory.drop(Log);
                }
                break;

            case WAIT:
                sleep(random(3000, 7000));
                break;
        }
        return 0;
    }



    @Override
    public final void onExit() {
        log("Script exiting");
    }





}
