package ch.zhaw.prog2.fxmlcalculator;

import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class ResultController {
    TextArea result;

    ResultController(TextArea result) {
        this.result = result;
    }

    public void showResult(String text, boolean clearFirst, Color backColor) {
        if(clearFirst) {
            result.setText(text);
        } else {
            result.appendText("\n");
            result.appendText(text);
        }
        result.setBorder(new Border(new BorderStroke(backColor, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
    }
}
