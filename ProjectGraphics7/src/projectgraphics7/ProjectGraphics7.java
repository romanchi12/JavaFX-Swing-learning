/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectgraphics7;


import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import java.net.URL;
import javafx.application.Application;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.input.PickResult;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;


public class ProjectGraphics7 extends Application {
//    Group root = new Group();
//        Sphere sphere = new Sphere(20);
//        PhongMaterial material2 = new PhongMaterial();
//        Image imageTex2 = new Image(ProjectGraphics7.class.getResource("/projectgraphics7/resources/Desert.jpg").toString(),512,512,false,false);
//        material2.setDiffuseMap(imageTex2);
//        sphere.setMaterial(material2);
//        root.getChildren().add(sphere);
//        PointLight light = new PointLight(Color.WHITE);
//       
//        light.getTransforms().addAll(new Translate(-100, -100, -100));
//        root.getChildren().add(light);
//            
//        PerspectiveCamera camera = new PerspectiveCamera(true);
//        camera.setFieldOfView(100);
//        camera.getTransforms().addAll(new Translate(0, 0, -100));
//        
//        SubScene subscene = new SubScene(root, 400, 400);
//        subscene.setCamera(camera);
//        
//        Group root2 = new Group();
//        Box box = new Box(100,100,100);
//        PhongMaterial material = new PhongMaterial();
//        Image imageTex = new Image(ProjectGraphics7.class.getResource("/projectgraphics7/resources/air.jpg").toString(),256,256,false,false);
//        material.setDiffuseMap(imageTex);
//        box.setMaterial(material);
//        root2.getChildren().add(box);
//        AmbientLight light2 = new AmbientLight(Color.BISQUE);
//        
//        light2.getTransforms().addAll(new Translate(-100, -100, -100));
//        root2.getChildren().add(light2);
//            
//        PerspectiveCamera camera2 = new PerspectiveCamera(true);
//        camera2.setFieldOfView(100);
//        camera2.getTransforms().addAll(new Translate(0, 0, -100), new Rotate(-200, Rotate.X_AXIS), new Rotate(-20, Rotate.Y_AXIS)); 
//        SubScene subscene2 = new SubScene(root2, 400, 400);
//        subscene2.setCamera(camera2);
//     
//        BorderPane pane = new BorderPane();
//        Text text = new Text();
//        text.setFont(new Font(20));
//        text.setText("Mikhalchuk Taras");
//        text.setFill(Color.GREEN);
//        pane.setTop(text);
//        pane.setCenter(subscene);
//        pane.setRight(root2);
//        Scene scene = new Scene(pane, 800, 600);
//        
//        primaryStage.setScene(scene);
//        primaryStage.show();
    Translate translateZ = new Translate(0,-15,-100);
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        Text text = new Text("Taras Mikhalchuk");
        text.setFill(Color.BLUE);
        text.setFont(new Font(24));
        root.setTop(text);
        Group root3d = new Group();
        ObjModelImporter objModel = new ObjModelImporter();
        URL path = ProjectGraphics7.class.getResource("/projectgraphics7/resources/airballoon/Air_Balloon.obj");
        objModel.read(path);
        MeshView[] meshViews = objModel.getImport();
        
        Box simpleBox = new Box(20,20,20);
        PhongMaterial material = new PhongMaterial();
        Image imageTexture = new Image(ProjectGraphics7.class.getResource("/projectgraphics7/resources/sky.jpg").toString(), 256,256,false,false);
        material.setDiffuseMap(imageTexture);
        simpleBox.setMaterial(material);
        simpleBox.setTranslateX(15);
        
        Sphere sphere = new Sphere(10);
        PhongMaterial material2 = new PhongMaterial();
        material2.setDiffuseColor(Color.YELLOW);
        sphere.setMaterial(material2);
        sphere.setTranslateX(-40);
        sphere.setTranslateY(-40);
        root3d.getChildren().addAll(meshViews);
        root3d.getChildren().addAll(simpleBox, sphere);
        SubScene scene3d = new SubScene(root3d, 1000,700, true,null);
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.getTransforms().addAll(translateZ);
        camera.setNearClip(0.1);
        camera.setFarClip(100);
        scene3d.setCamera(camera);
        PointLight light = new PointLight(Color.CORAL);
        light.getTransforms().add(new Translate(-20,-20,-21));
        root3d.getChildren().add(light);
        scene3d.setFill(Color.ROYALBLUE);
        root.setCenter(scene3d);
        Scene scene = new Scene(root, 1024, 768);
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
