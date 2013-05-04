package com.tgriswold.pulsingprisms;

import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import net.rbgrn.android.glwallpaperservice.*;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.opengl.GLU;
import android.preference.PreferenceManager;
import android.graphics.Color;

public class MyRenderer implements GLWallpaperService.Renderer {
	private static Random random = new Random();
	
	private Context context;
	
	//Cubes
	private GLCube aCube;
	private GLCube bCube;
	private GLCube cCube;
	private GLCube dCube;
	private GLCube eCube;
	private GLCube fCube;
	private GLCube gCube;
	private GLCube hCube;
	private GLCube iCube;
	private GLCube jCube;
	private GLCube kCube;
	private GLCube lCube;
	private GLCube mCube;
	private GLCube nCube;
	
	/* Preferences Start Here*/
	//Color Settings
	private float backgroundR;
	private float backgroundG;
	private float backgroundB;
	private float backgroundA;
	private float centerX;
	private float centerY;
	private float centerZ;
	
	//Block Settings
	private float blockSpeed;
	private int color1R;
	private int color1G;
	private int color1B;
	private int color1A;
	private int color2R;
	private int color2G;
	private int color2B;
	private int color2A;
	private int color3R;
	private int color3G;
	private int color3B;
	private int color3A;
	
	//Camera Settings
	private float cameraDistance;
	private float cameraRotateX;
	private float cameraRotateY;
	private float cameraRotateZ;
	/* Preferences End Here*/
	
	public MyRenderer(Context context){
		super();
		
		this.context = context;
		init();
	}
	
	private void init() {
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		Resources resources = context.getResources();
		
		//World Settings
		int worldColor = sharedPref.getInt("worldColor", 0xFF333333);
		backgroundR = (float) (Color.red(worldColor) / 255.0);
		backgroundG = (float) (Color.green(worldColor) / 255.0);
		backgroundB = (float) (Color.blue(worldColor) / 255.0);
		backgroundA = (float) (Color.red(worldColor) / 255.0);
		
		centerX = 0.2f * sharedPref.getInt("worldCenterX", Integer.parseInt(resources.getString(R.string.worldCenterX_default)));
		centerY = 0.2f * sharedPref.getInt("worldCenterY", Integer.parseInt(resources.getString(R.string.worldCenterY_default)));
		centerZ = 0.2f * sharedPref.getInt("worldCenterZ", Integer.parseInt(resources.getString(R.string.worldCenterZ_default)));
		
		//Block Settings
		blockSpeed = 0.0001f * sharedPref.getInt("blockSpeed", Integer.parseInt(resources.getString(R.string.blockSpeed_default)));
		
		int color1 = sharedPref.getInt("blockColor1", 0xFFFFFFFF);
		color1R = Color.red(color1);
		color1G = Color.green(color1);
		color1B = Color.blue(color1);
		color1A = Color.red(color1);
		
		int color2 = sharedPref.getInt("blockColor2", 0xFF007FD2);
		color2R = Color.red(color2);
		color2G = Color.green(color2);
		color2B = Color.blue(color2);
		color2A = Color.red(color2);
		
		int color3 = sharedPref.getInt("blockColor3", 0xFFFE7443);
		color3R = Color.red(color3);
		color3G = Color.green(color3);
		color3B = Color.blue(color3);
		color3A = Color.red(color3);
		
		//Camera Settings
		cameraDistance = -0.2f * sharedPref.getInt("cameraDistance", Integer.parseInt(resources.getString(R.string.cameraDistance_default)));
		cameraRotateX = 1f * sharedPref.getInt("cameraRotateX", Integer.parseInt(resources.getString(R.string.cameraRotateX_default)));
		cameraRotateY = 1f * sharedPref.getInt("cameraRotateY", Integer.parseInt(resources.getString(R.string.cameraRotateY_default)));
		cameraRotateZ = 1f * sharedPref.getInt("cameraRotateZ", Integer.parseInt(resources.getString(R.string.cameraRotateZ_default)));
		
		//Initialize Cubes
		aCube = new GLCube(	centerX - 1.00f, centerY +-1.40f, centerZ - 1.00f,
			   	1.00f, 1.00f, 10.00f,
			   	color1R, color1G, color1B, color1A,
			   	blockSpeed*(random.nextInt()%5 + 1));
		bCube = new GLCube(	centerX + 0.00f, centerY - 1.40f, centerZ - 0.50f,
			   	1.00f, 1.00f, 10.00f,
			   	color2R, color2G, color2B, color2A,
			   	blockSpeed*(random.nextInt()%5 + 1));
		cCube = new GLCube(	centerX + 1.00f, centerY - 1.65f, centerZ + 0.00f,
			   	0.50f, 1.00f, 10.00f,
			   	color3R, color3G, color3B, color3A,
			   	blockSpeed*(random.nextInt()%5 + 1));
		dCube = new GLCube(	centerX - 1.50f, centerY + 0.10f, centerZ - 1.00f,
	   			0.50f, 0.50f, 10.00f,
	   			color1R, color1G, color1B, color1A,
   				blockSpeed*(random.nextInt()%5 + 1));
		eCube = new GLCube(	centerX - 1.00f, centerY - 0.40f, centerZ - 0.50f,
				0.50f, 1.25f, 10.00f,
				color2R, color2G, color2B, color2A,
				blockSpeed*(random.nextInt()%5 + 1));
		fCube = new GLCube(	centerX - 0.50f, centerY - 0.40f, centerZ + 0.00f,
			   	0.50f, 1.00f, 10.00f,
			   	color3R, color3G, color3B, color3A,
			   	blockSpeed*(random.nextInt()%5 + 1));
		gCube = new GLCube(	centerX + 0.00f, centerY - 0.40f, centerZ + 0.25f,
				0.50f, 0.50f, 10.00f,
				color1R, color1G, color1B, color1A,
   				blockSpeed*(random.nextInt()%5 + 1));
		hCube = new GLCube(	centerX + 0.50f, centerY - 0.40f, centerZ + 0.75f,
				0.50f, 0.50f, 10.00f,
				color3R, color3G, color3B, color3A,
			   	blockSpeed*(random.nextInt()%5 + 1));
		iCube = new GLCube(	centerX + 1.00f, centerY - 0.65f, centerZ + 0.50f,
				0.50f, 2.00f, 10.00f,
				color1R, color1G, color1B, color1A,
			   	blockSpeed*(random.nextInt()%5 + 1));
		jCube = new GLCube(	centerX + 1.50f, centerY - 0.40f, centerZ + 0.75f,
				0.25f, 0.50f, 10.00f,
				color2R, color2G, color2B, color2A,
			   	blockSpeed*(random.nextInt()%5 + 1));
		kCube = new GLCube(	centerX - 0.50f, centerY + 0.60f, centerZ + 0.25f,
				0.50f, 0.50f, 10.00f,
				color1R, color1G, color1B, color1A,
   				blockSpeed*(random.nextInt()%5 + 1));
		lCube = new GLCube(	centerX + 0.00f, centerY + 0.10f, centerZ + 1.25f,
				1.00f, 1.00f, 10.00f,
				color2R, color2G, color2B, color2A,
				blockSpeed*(random.nextInt()%5 + 1));
		mCube = new GLCube(	centerX - 1.50f, centerY + 1.10f, centerZ - 0.25f,
				1.25f, 0.50f, 10.00f,
				color3R, color3G, color3B, color3A,
   				blockSpeed*(random.nextInt()%5 + 1));
		nCube = new GLCube(	centerX - 0.2f, centerY + 1.10f, centerZ + 0.75f,
				1.00f, 0.25f, 10.00f,
				color3R, color3G, color3B, color3A,
   				blockSpeed*(random.nextInt()%5 + 1));
	}

	public void onDrawFrame(GL10 gl) {
		gl.glClearColor(backgroundR, backgroundG, backgroundB, backgroundA);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		aCube.draw(gl);
		bCube.draw(gl);
		cCube.draw(gl);
		dCube.draw(gl);
		eCube.draw(gl);
		fCube.draw(gl);
		gCube.draw(gl);
		hCube.draw(gl);
		iCube.draw(gl);
		jCube.draw(gl);
		kCube.draw(gl);
		lCube.draw(gl);
		mCube.draw(gl);
		nCube.draw(gl);
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		init();
		
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, 60f, (float)width/(float)height, 1f, 100f);

		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();

		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glTranslatef(0, 0, cameraDistance);
		gl.glRotatef(cameraRotateX, 1, 0, 0);
		gl.glRotatef(cameraRotateY, 0, 1, 0);
		gl.glRotatef(cameraRotateZ, 0, 0, 1);
		
		gl.glEnable(GL10.GL_LIGHTING);
		gl.glEnable(GL10.GL_RESCALE_NORMAL);
		gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
		//Set the color of light bouncing off of surfaces to respect the surface color
		gl.glEnable(GL10.GL_COLOR_MATERIAL);

		// Turn on a global ambient light
		float[] ambientLightRGB = {1f, 1f, 1f, 1.0f};
		gl.glLightModelfv(GL10.GL_LIGHT_MODEL_AMBIENT, ambientLightRGB, 0);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearDepthf(1f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);

		//Turn on culling, so OpenGL only draws one side of the primitives
		gl.glEnable(GL10.GL_CULL_FACE);
		//Define the front of a primitive to be the side where the listed vertexes are counterclockwise
		gl.glFrontFace(GL10.GL_CCW);
		//Do not draw the backs of primitives
		gl.glCullFace(GL10.GL_BACK);
	}

	/**
	 * Called when the engine is destroyed. Do any necessary clean up because
	 * at this point your renderer instance is now done for.
	 */
	public void release() {

	}
}
