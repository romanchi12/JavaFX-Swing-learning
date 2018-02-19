/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectgraphics8;

import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import com.interactivemesh.jfx.importer.tds.TdsModelImporter;
import java.net.URL;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.AmbientLight;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.PickResult;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class ProjectGraphics8 extends Application{  
    double anchorX = 0;
    double anchorY = 0;
    Rotate rotationX = new Rotate(0);
    Rotate rotationY = new Rotate(0);
    Translate translateZ = new Translate(0, 0, 0);
    double rotationXOld = 0;
    double rotationYOld = 0;
    
    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        root.setDepthTest(DepthTest.ENABLE);
        ObjModelImporter ah64obj = new ObjModelImporter();
        try{
            URL path = this.getClass().getResource("/projectgraphics8/resources/sc/Scar-L.obj");
            ah64obj.read(path);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.GRAY);
        MeshView[] meshViews = ah64obj.getImport();   
        for(MeshView meshView:meshViews){
            meshView.setMaterial(material);
        }
        root.getChildren().addAll(meshViews);
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setFarClip(100);
        camera.setNearClip(0.01);
        SubScene scene3d = new SubScene(root, 1000,700);
        scene3d.setFill(Color.BLANCHEDALMOND);
        PointLight pointLight = new PointLight(Color.GREY);
        pointLight.getTransforms().add(new Translate(100,100,-10));
        root.getChildren().add(pointLight);
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(scene3d);
        Text text = new Text("Taras Mikhalchuk");
        text.setFill(Color.RED);
        borderPane.setTop(text);
        Scene scene = new Scene(borderPane, 1024, 768,true, null);
        Translate translate = new Translate(0,0,-2);
        Rotate rotation = new Rotate(0,0,0,0,Rotate.Y_AXIS);
        camera.getTransforms().addAll(rotation, translate);
        scene3d.setCamera(camera);
        PointLight light = new PointLight(Color.ANTIQUEWHITE);
        light.setTranslateZ(-20);
        light.setTranslateX(0);
        AmbientLight ambLight = new AmbientLight(Color.color(0.9, 0.9, 0.9));
        root.getChildren().addAll(light, ambLight);
        scene3d.setOnMousePressed(event->{
            PickResult pickResult = event.getPickResult();
            try{
                text.setText(pickResult.getIntersectedNode().toString());
            }catch(Exception ex){
                text.setText("nothing clicked");
            }
            anchorX=event.getSceneX();
            anchorY=event.getSceneY();
            rotationXOld = rotationX.getAngle();
            rotationYOld = rotationY.getAngle();        
        });
        scene3d.setOnMouseDragged(event->{
            double rotateFactorX = anchorX - event.getSceneX();
            double rotateFactorY = anchorY - event.getSceneY();
            rotationX.setAngle(rotationXOld + rotateFactorX/5.0);
            rotationY.setAngle(rotationYOld + rotateFactorY/5.0);
            camera.getTransforms().clear();
            camera.getTransforms().addAll(rotationX, rotationY, translateZ);
        });
        scene3d.setOnScroll(event->{
            if(event.getDeltaY()<0){
                translateZ.setZ(translateZ.getZ()-1);
            }else{
                translateZ.setZ(translateZ.getZ()+1);
            }
            camera.getTransforms().clear();
            camera.getTransforms().addAll(rotationX, rotationY, translateZ);
        });
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
