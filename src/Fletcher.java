import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

@ScriptManifest(author = "x7599", info = "fish n drop", name = "PowerFisher", logo = "", version = 1.0)
public final class Fisher extends Script {

    private enum State {
        FISH, DROP, WAIT
    }

    @Override
    public final void onStart(){
        log("some good shit");
    }

    private State getState(){

        int feather = 314;
        int rod = 309;

        if(getInventory().contains(feather, rod) && !getInventory().contains("Raw trout", "Raw salmon")){
            return State.FISH;
        }
        if(getInventory().isFull()){
            return State.DROP;
        }
        return State.WAIT;
    }

    public final int onLoop() throws InterruptedException {


        int feather = 314;
        int rod = 309;

        switch (getState()){
            case FISH:
                NPC fishSpot = this.getNpcs().closest("Fishing spot");
                if(fishSpot != null){
                    fishSpot.interact("Lure");
                    sleep(random(3000, 5000));
                    Sleep.sleepUntil(() -> !myPlayer().isAnimating(), 5000);
                    getMouse().moveOutsideScreen();
                }
                break;

            case DROP:
                if(getInventory().isFull()){
                    sleep(random(200, 500));
                    getInventory().dropAllExcept(feather, rod);
                    sleep(random(300, 700));
                }
                break;

            case WAIT:
                sleep(random(3000, 7000));
        }
        return random(200, 300);
    }


    @Override
    public final void onExit(){
        log("Script dying..");
    }

}
