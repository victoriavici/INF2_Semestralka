package sk.uniza.fri.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import sk.uniza.fri.MojaHra;


public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Semestralna praca";
        config.width = 1000;
        config.height = 500;
        config.resizable = false;
        new LwjglApplication(new MojaHra(), config);
    }
}
