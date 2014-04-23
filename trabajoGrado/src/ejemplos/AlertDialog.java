package ejemplos;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import static javax.management.Query.lt;

/**
 *
 * @author N550J
 */
public class AlertDialog extends Stage {

    private final int WIDTH_DEFAULT = 300;

    public static final int ICON_INFO = 0;
    public static final int ICON_ERROR = 1;    

    public AlertDialog(Stage owner, String msg, int type) {
        setResizable(false);
        initModality(Modality.APPLICATION_MODAL);
        initStyle(StageStyle.DECORATED);
      BorderPane borderPane = new BorderPane();
      BorderPane borderPanebox = new BorderPane();
        Label label = new Label(msg);
        label.setWrapText(true);
        label.setGraphicTextGap(20);
        label.setGraphic(new ImageView(getImage(type)));

        Button button = new Button("OK");
      
        button.setOnAction(new EventHandler(){
                        public void handle(ActionEvent arg0) {
                AlertDialog.this.close();
            }

//            @Override
            public void handle(Event t) {
              AlertDialog.this.close();
            }
        });

        borderPanebox.getStylesheets().add(getClass().getResource("/estilos/estiloAlerta.css").toExternalForm()); 
        borderPane.getStylesheets().add(getClass().getResource("/estilos/estiloAlerta.css").toExternalForm());        
        borderPane.setTop(label);
        
        HBox hbox2 = new HBox();
        hbox2.setAlignment(Pos.CENTER);
        hbox2.getChildren().add(button);
        borderPane.setBottom(hbox2);
        borderPanebox.setCenter(borderPane);
        
        // calculate width of string
        final Text text = new Text(msg);
        text.snapshot(null, null);
        // + 20 because there is padding 10 left and right
        int width = (int) text.getLayoutBounds().getWidth() + 40;

//        if (width & lt; WIDTH_DEFAULT)
//        
//        
//            width = WIDTH_DEFAULT;
          
        int height = 100;

        final Scene scene = new Scene(borderPanebox, width, height);
        scene.setFill(Color.TRANSPARENT);
        setScene(scene);

        // make sure this stage is centered on top of its owner
        setX(owner.getX() + (owner.getWidth() / 2 - width / 2));
        setY(owner.getY() + (owner.getHeight() / 2 - height / 2));
    }

    private Image getImage(int type) {
        if (type == ICON_ERROR)
            return new Image(getClass().getResourceAsStream("/imagenes/error.png"));
        else                        
            return new Image(getClass().getResourceAsStream("/imagenes/info.png"));
    }

}