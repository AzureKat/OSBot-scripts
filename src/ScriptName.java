import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import java.awt.*;

@ScriptManifest(name = "pip", author = "lul", version = 1.0, info = "", logo = "")
public final class ScriptName extends Script {

    @Override
    public final int onLoop() throws InterruptedException {

        testClass testclass = new testClass(this);
        return 0;
    }

    @Override
    public final void onStart() {
        log("This is a test script");
    }

    @Override
    public final void onExit() {
        log("Script exiting");
    }

    @Override
    public final void onMessage(final Message message) {
        log("A message has appeared" + message.getMessage());
    }

    @Override
    public void onPaint(final Graphics2D g) {
        g.drawString("Some text", 10, 10);
    }


    final class testClass {

        private final MethodProvider methods;

        private testClass(final MethodProvider methods) {
            this.methods = methods;
        }

        public final void someMethod(){
            methods.getInventory();
        }

    }
}