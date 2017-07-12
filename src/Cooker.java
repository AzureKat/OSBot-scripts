import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;


@ScriptManifest(name = "cooker", author = "x7599", version = 1.0, info = "yolo", logo = " ")
public class Cooker extends Script {

    int rLobster = 377;
    int cLobster = 379;
    Area cookingarea = new Area(3275, 3179, 3272, 3180);

    private enum State {
        COOK, WALKTOBANK, WAIT, WITHDRAW, DEPOSIT, WALKTOAREA, QUIT
    }

    @Override
    public final void onStart(){
        log("Starting..");
    }

    private State getState(){

        if(Banks.AL_KHARID.contains(myPosition()) && getInventory().isEmpty()){
            return State.WITHDRAW;
        }
        if(Banks.AL_KHARID.contains(myPosition()) && getInventory().contains(rLobster)){
            return State.WALKTOAREA;
        }
        if(getInventory().contains(rLobster) && cookingarea.contains(myPosition())){
            return State.COOK;
        }
        if(!getInventory().contains(rLobster) && cookingarea.contains(myPosition())){
            return State.WALKTOBANK;
        }
        if(getInventory().contains(cLobster) && Banks.AL_KHARID.contains(myPosition())){
            return State.DEPOSIT;
        }
        return State.WAIT;
    }


    public final int onLoop() throws InterruptedException{


        switch (getState()) {
            case WITHDRAW:
                if(Banks.AL_KHARID.contains(myPosition()) && !getInventory().contains(rLobster)){
                    Entity bank = getObjects().closest("Bank booth");
                    if(getBank().isOpen()){
                        getBank().withdrawAll(rLobster);
                        sleep(random(300, 950));
                    }
                    if(bank != null && bank.exists() && !getBank().isOpen()){
                        bank.interact("Bank");
                        Sleep.sleepUntil(() -> getBank().isOpen(), 2500);
                        sleep(random(300, 950));
                    }
                }

            case WALKTOAREA:
                if(getBank().isOpen() && getInventory().contains(rLobster)){
                    getWalking().webWalk(cookingarea);
                    Sleep.sleepUntil(() -> cookingarea.contains(myPosition()), 2500);
                }

            case COOK:
                if(cookingarea.contains(myPosition()) && getInventory().contains(rLobster) && !myPlayer().isAnimating()){
                    RS2Widget lobOption = getWidgets().get(307, 3);
                    if(getWidgets().getWidgetContainingText("How many would you like to") == null){
                        getInventory().interact("Use", rLobster);
                        sleep(random(300, 850));
                        getObjects().closest("Range").interact("Use");
                        Sleep.sleepUntil(() -> getWidgets().getWidgetContainingText("How many would you like to") != null, 2500);
                        getWidgets().getWidgetContainingText("Raw lobster").interact("Cook All");
                        getMouse().moveOutsideScreen();
                        Sleep.sleepUntil(() -> !getInventory().contains(rLobster), 2500);
                    }
                    if(getWidgets().getWidgetContainingText("How many would you like to") != null){
                        getWidgets().getWidgetContainingText("Raw lobster").interact("Cook All");
                        getMouse().moveOutsideScreen();
                        Sleep.sleepUntil(() -> !getInventory().contains(rLobster), 2500);
                    }
                }

            case WALKTOBANK:
                if(!getInventory().contains(rLobster) && cookingarea.contains(myPosition())){
                    sleep(random(300, 700));
                    getWalking().webWalk(Banks.AL_KHARID);
                }

            case DEPOSIT:
                if(!getInventory().contains(rLobster) && Banks.AL_KHARID.contains(myPosition()) && getBank().contains(rLobster)){
                    Entity bank = getObjects().closest("Bank booth");
                    if(bank != null && bank.exists()){
                        bank.interact("Bank");
                        Sleep.sleepUntil(() -> getBank().isOpen(), 2500);
                        sleep(random(300, 700));
                        getBank().depositAll();
                        Sleep.sleepUntil(() -> getInventory().isEmpty(), 2500);
                    }
                }

            case QUIT:
                if(!getBank().contains(rLobster)){
                    if(getBank().isOpen()){
                        getBank().close();
                        Sleep.sleepUntil(() -> !getBank().isOpen(), 2500);
                        random(300, 700);
                        getLogoutTab().logOut();
                    }
                }
        }
        return random(200, 300);
    }

    public final void onExit(){

        log("Exiting..");
    }

}
