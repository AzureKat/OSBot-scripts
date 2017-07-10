import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

@ScriptManifest(name = "big boy cutter", author = "x7599", version = 1.0, info = "eater", logo = " ")
public class Cutter extends Script {

    private long startTime = System.currentTimeMillis();
    final long runTime = System.currentTimeMillis() - startTime;
    private String status = "Starting..";

    int axe = 1351;

    private enum State {
        CUT, DROP, WAIT
    }

    @Override
    public final void onStart() {

        log("Starting..");
    }

    private State getState() {

        if (!getInventory().isFull()) {
            return State.CUT;
        }
        if (getInventory().isFull()) {
            return State.DROP;
        }
        return State.WAIT;
    }

    public final int onLoop() throws InterruptedException {
        switch (getState()) {
            case CUT:
                if (!myPlayer().isAnimating()) {
                    Entity oakTree = getObjects().closest("Oak");
                    if (oakTree != null && oakTree.interact("Chop down")){
                        sleep(random(500, 1500));
                        getMouse().moveOutsideScreen();
                        new Sleep(() -> myPlayer().isAnimating() || !oakTree.exists(), 6500).sleep();
                    }
                    if(getDialogues().inDialogue() || getDialogues().isPendingContinuation()){
                        getDialogues().clickContinue();
                        Sleep.sleepUntil(() -> !getDialogues().inDialogue(), 2500);
                    }
                    if(oakTree == null){
                        Sleep.sleepUntil(() -> oakTree.exists(), 7000);
                    }
                }

            case DROP:
                if (getInventory().isFull()) {
                    sleep(random(300, 700));
                    getInventory().dropAllExcept(axe);
                    sleep(random(700, 1500));
                }

            case WAIT:
                sleep(random(3000, 7000));
        }

        return random(200, 300);
    }

    public final void onExit() {
        log("Exiting..");
    }

    @Override
    public void onPaint(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Dialog", 1, 15));
        Point mP = getMouse().getPosition();
        g.drawLine(mP.x - 5, mP.y + 5, mP.x + 5, mP.y - 5);
        g.drawLine(mP.x + 5, mP.y + 5, mP.x - 5, mP.y - 5);
        g.drawString("" + formatTime(runTime), 298, 409);
        g.drawString("" + status, 298, 423);
    }

    public final String formatTime(final long ms){
        long s = ms / 1000, m = s / 60, h = m / 60, d = h / 24;
        s %= 60; m %= 60; h %= 24;

        return d > 0 ? String.format("%02d:%02d:%02d:%02d", d, h, m, s) :
                h > 0 ? String.format("%02d:%02d:%02d", h, m, s) :
                        String.format("%02d:%02d", m, s);
    }
}

