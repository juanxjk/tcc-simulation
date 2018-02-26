package jat.examples.jat3D.Mouse;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Group;
import javax.media.j3d.Node;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;

import com.sun.j3d.utils.behaviors.mouse.MouseBehaviorCallback;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class MouseCallback implements MouseBehaviorCallback{

	private SimpleUniverse universe;
	private BoundingSphere bounds;
	public TransformGroup scene;
	
	public void init() {
		universe = new SimpleUniverse();
		bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
	
		Node myScene = createScene();
		BranchGroup mouseGroup = defineMouseBehaviour(myScene);
		universe.addBranchGraph(mouseGroup);
		universe.getViewingPlatform().setNominalViewingTransform();
	}

	public Node createScene() {
		Group g = new Group();

		scene = new TransformGroup();
		scene.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		scene.addChild(new ColorCube(0.3));

		g.addChild(scene);
		return g;
	}

	private BranchGroup defineMouseBehaviour(Node scene) {
		BranchGroup bg = new BranchGroup();

		TransformGroup objTransform = new TransformGroup();
		objTransform.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		objTransform.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		objTransform.addChild(scene);
		bg.addChild(objTransform);
				
		MouseRotate mouseRotate = new MouseRotate();
		mouseRotate.setTransformGroup(objTransform);
		mouseRotate.setSchedulingBounds(bounds);
		mouseRotate.setupCallback(this);
		bg.addChild(mouseRotate);
		
		return bg;
	}

	public void transformChanged(int arg0, Transform3D arg1) {
		System.out.println("Callback from mouse");
	}

	
	public static void main(String[] args) {
		(new MouseCallback()).init();
		System.out.println("Drag mouse to see Callback from mouse");
	}


}