package pj.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import pj.pj;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.width = 1920;
		config.width = 960;
		config.width = 1280;
		config.height = 1080;
		config.height = 540;
		config.height = 720;
		config.resizable = false;
		new LwjglApplication(new pj(), config);
		
	}
}
