package ch.zhaw.prog2.step14a;

import java.util.HashMap;

import javafx.scene.Parent;

/**
 * Interface with one method to set the list of actual available
 * screens with a key (name) of the screen
 * @author bles
 *
 */
public interface ControlledScreens {
	public void setScreenList(HashMap<String, Parent> screens);
}
