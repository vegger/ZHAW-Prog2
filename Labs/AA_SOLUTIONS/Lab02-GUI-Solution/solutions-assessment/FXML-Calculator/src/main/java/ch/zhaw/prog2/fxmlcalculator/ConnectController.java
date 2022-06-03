package ch.zhaw.prog2.fxmlcalculator;

import javafx.scene.Parent;

public interface ConnectController {
	void setValueHandler(ValueHandler valueHandler);
	void setParentSceneGraph(Parent sceneGraph);
}
