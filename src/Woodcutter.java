import org.osbot.rs07.api.Objects;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.Interactable;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.utility.ConditionalSleep;

@ScriptManifest(name = "pip", author = "lul", version = 1.0, info = "", logo = "")
public final class Woodcutter extends Script {


    private enum State {
        CHOP, DROP, WAIT
    }

    @Override
    public final void onStart() {
        log("This is a test script");
    }


    private State getState(){

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

        switch(getState()) {
            Entity tree = getObjects().closest("Oak");
            case CHOP:
                if(tree != null && tree.interact("Chop down")) {
                    new Sleep(() -> myPlayer().isAnimating() || !tree.exists(), 5000).sleep();
                }
                break;
            case DROP:
                if(inventory.isFull()){
                    inventory.dropAll();
                    doAntiBan();
                    sleep(random(300,700));
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

    private void doAntiBan() throws InterruptedException {
        switch (random(1, 30)) {
            case 1:
                getTabs().open(Tab.SKILLS);
                getSkills().hoverSkill(Skill.WOODCUTTING);
                sleep(random(100, 6000));
                log("Antiban");
                break;

            case 2:
                this.camera.movePitch(random(150,360));
                this.camera.moveYaw(random(100, 250));
                log("Camera antiban");
                break;

            case 3:
                this.camera.movePitch(random(22, 67));
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

            case 6:
                this.mouse.move(random(150, 275), random(75, 200));
                log("Mouse preset antiban");
                break;

        }
        sleep(random(700, 1800));
        getTabs().open(Tab.INVENTORY);
    }

}
