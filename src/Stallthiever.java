import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

/**
 * Created by aleksi on 7.3.2017.
 */

@ScriptManifest(name = "Thiever", author = "x7599", version = 1.0, info = "Steals tea from brits", logo = "")
public final class Stallthiever extends Script {

    private enum State {
        STEAL, DROP, WAIT
    }

    @Override
    public final void onStart(){

        log("Starting tea script..");
    }

    private State getState(){
        Entity stall = objects.closest("Tea stall");

        if(inventory.isFull()){
            return State.DROP;
        }

        if (stall != null){
            return State.STEAL;
        }

        return State.WAIT;
    }


    public final int onLoop() throws InterruptedException {

        switch (getState()){
            case STEAL:
                Entity stall = objects.closest("Tea stall");
                if(stall != null){
                    stall.interact("Steal-from");
                }
                log("test shit");
                sleep(2000);
                doAntiBan();
                break;
            case DROP:
                inventory.dropAll();
                doAntiBan();
                sleep(random(2000,3000));
                break;
            case WAIT:
                sleep(random(500,700));
        }
        return random(200,300);
    }

    private void doAntiBan() throws InterruptedException {
        log("shit doesnt work");
        switch (random(1, 6)) {
            case 1:
                getTabs().open(Tab.SKILLS);
                getSkills().hoverSkill(Skill.THIEVING);
                sleep(random(100, 6000));
                log("Antiban");
                break;

            case 2:
                this.camera.movePitch(random(150,360));
                this.camera.moveYaw(random(100, 250));
                log("Camera antiban");
                break;

            case 3:
                this.camera.movePitch(random(175, 355));
                log("Camera antiban2 only pitch");
                break;

            case 4:
                this.camera.moveYaw(random(75, 250));
                log("Camera antiban3 only yaw");
                break;

            case 5:
                this.mouse.moveRandomly();
                log("Mouse antiban");
                break;

        }
        sleep(random(700, 1800));
        getTabs().open(Tab.INVENTORY);
    }

    @Override
    public final void onExit() {
        log("Exiting script");
    }


}
