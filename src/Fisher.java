import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

@ScriptManifest(author = "pekka", info = "xd", name = "something great", logo = "", version = 1.0)
public final class Fisher extends Script {

    int feather = 314;
    int rod = 309;

    private enum State {
        FISH, DROP, WAIT
    }

    private State getState(){

        if(getInventory().isEmptyExcept(feather, rod)){
            return State.FISH;
        }

        if(getInventory().isFull()){
            return State.DROP;
        }

        return State.WAIT;
    }

    public final int onLoop() throws InterruptedException {

        switch (getState()){
            case FISH:
                Entity fish = getObjects().closest("Fishing spot");
                if(fish != null && fish.exists()){
                    new Sleep(() -> myPlayer().isAnimating() || !fish.exists(), 5000).sleep();
                }
                break;

            case DROP:
                if(getInventory().isFull()){
                    sleep(random(200, 500));
                    getInventory().drop("Trout", "Salmon");
                    sleep(random(300, 700));
                }
                break;

            case WAIT:
                sleep(random(3000, 7000));
        }
        return random(200, 300);
    }

    @Override
    public final void onStart(){
        log("some good shit");
    }

    @Override
    public final void onExit(){
        log("Script dying..");
    }

}
