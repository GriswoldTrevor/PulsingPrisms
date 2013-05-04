package com.tgriswold.pulsingprisms;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Color;

public class GLCube {
	private final int _nrOfVertices = 8;

	private FloatBuffer _vertexBuffer;

	private FloatBuffer _normalBuffer;

	private ShortBuffer frontFaceIndices;
	private ShortBuffer rightFaceIndices;
	private ShortBuffer leftFaceIndices;
	private ShortBuffer backFaceIndices;
	private ShortBuffer topFaceIndices;
	private ShortBuffer bottomFaceIndices;
	
	//My Vars
	private float r;
	private float g;
	private float b;
	private float a;
	
	private float darkR;
	private float darkG;
	private float darkB;
	
	private float darkerR;
	private float darkerG;
	private float darkerB;
	
	private float zInc;
	private float currentZShift;
	private float maxZShift;
	private float zShiftDir;

	public GLCube() {
		this.init(0f, 0f, 0f, 1f, 1f, 1f);
		calcColors(255, 0, 0, 255);
		
		zInc = 0.01f;
		currentZShift = 0f;
		maxZShift = 0.75f;
		zShiftDir = 1f;
	}
	public GLCube(float centerX, float centerY, float centerZ) {
		this.init(centerX, centerY, centerZ, 1f, 1f, 1f);
		calcColors(255, 0, 0, 255);
		
		zInc = 0.01f;
		currentZShift = 0f;
		maxZShift = 0.75f;
		zShiftDir = 1f;
	}
	public GLCube(float centerX, float centerY, float centerZ, float width, float height, float depth) {
		this.init(centerX, centerY, centerZ, width, height, depth);
		calcColors(255, 0, 0, 255);
		
		zInc = 0.01f;
		currentZShift = 0f;
		maxZShift = 0.75f;
		zShiftDir = 1f;
	}
	public GLCube(float centerX, float centerY, float centerZ, float width, float height, float depth, int r, int g, int b, int a) {
		this.init(centerX, centerY, centerZ, width, height, depth);
		calcColors(r, g, b, a);
		
		zInc = 0.01f;
		currentZShift = 0f;
		maxZShift = 0.75f;
		zShiftDir = 1f;
	}
	public GLCube(float centerX, float centerY, float centerZ, float width, float height, float depth, int r, int g, int b, int a, float zInc) {
		this.init(centerX, centerY, centerZ, width, height, depth);
		this.zInc = zInc;
		calcColors(r, g, b, a);
		
		currentZShift = 0f;
		maxZShift = 0.75f;
		zShiftDir = 1f;
	}
	
	private void calcColors(int r, int g, int b, int a){
		float[] hsvAry = new float[3];
		
		this.r = (float) (r/255.0);
		this.g = (float) (g/255.0);
		this.b = (float) (b/255.0);
		this.a = (float) (a/255.0);
		
		Color.RGBToHSV(r, g, b, hsvAry);
		float h = hsvAry[0];
		float s = hsvAry[1];
		float v = hsvAry[2];
		
		int darkColor = Color.HSVToColor(new float[] {h,s,Math.max(0f, v-0.04f)});
		darkR = (float) (Color.red(darkColor)/255.0);
		darkG = (float) (Color.green(darkColor)/255.0);
		darkB = (float) (Color.blue(darkColor)/255.0);
		
		int darkerColor = Color.HSVToColor(new float[] {h,s,Math.max(0f, v-0.14f)});
		darkerR = (float) (Color.red(darkerColor)/255.0);
		darkerG = (float) (Color.green(darkerColor)/255.0);
		darkerB = (float) (Color.blue(darkerColor)/255.0);
	}

	private void init(float centerX, float centerY, float centerZ, float width, float height, float depth) {
		// 3 is the number of coordinates to each vertex.
		_vertexBuffer = BufferFactory.createFloatBuffer(_nrOfVertices * 3);

		//We have defined 108 floats for the normals.
		//108 = 3 coordinates in a vertex
		//    * 3 vertexes in a triangle
		//    * 2 triangle in a face
		//    * 6 faces in a cube
		_normalBuffer = BufferFactory.createFloatBuffer(108);

		// Each face has 6 indices, because it is made of two different triangles
		frontFaceIndices =  BufferFactory.createShortBuffer(6);
		rightFaceIndices =  BufferFactory.createShortBuffer(6);
		leftFaceIndices =   BufferFactory.createShortBuffer(6);
		backFaceIndices =   BufferFactory.createShortBuffer(6);
		topFaceIndices =    BufferFactory.createShortBuffer(6);
		bottomFaceIndices = BufferFactory.createShortBuffer(6);

		// Coordinates for the vertexes of the cube.
		float[] vertexCoords = {
				(1f*width)+centerX,  (1f*height)+centerY,  (0f*depth)+centerZ, // Vertex 0
				(0f*width)+centerX,  (1f*height)+centerY,  (0f*depth)+centerZ, // Vertex 1
				(0f*width)+centerX,  (0f*height)+centerY,  (0f*depth)+centerZ, // Vertex 2
				(1f*width)+centerX,  (0f*height)+centerY,  (0f*depth)+centerZ, // Vertex 3
				(1f*width)+centerX,  (0f*height)+centerY, (-1f*depth)+centerZ, // Vertex 4
				(1f*width)+centerX,  (1f*height)+centerY, (-1f*depth)+centerZ, // Vertex 5
				(0f*width)+centerX,  (1f*height)+centerY, (-1f*depth)+centerZ, // Vertex 6
				(0f*width)+centerX,  (0f*height)+centerY, (-1f*depth)+centerZ, // Vertex 7
		};

		short[] frontFaceIndicesArray = {0, 1, 2,   0, 2, 3};
		short[] rightFaceIndicesArray = {3, 4, 0,   4, 5, 0};
		short[] leftFaceIndicesArray =  {7, 2, 6,   6, 2, 1};
		short[] backFaceIndicesArray =  {4, 7, 5,   7, 6, 5};
		short[] topFaceIndicesArray =   {1, 0, 6,   6, 0, 5};
		short[] bottomFaceIndicesArray= {2, 7, 3,   7, 4, 3};

		//Coordinates for Normal Vector. Used for Lighting calculations
		float[] normalCoords = {
				 0f, 0f, 1f,   0f, 0f, 1f,   0f, 0f, 1f,   0f, 0f, 1f,   0f, 0f, 1f,   0f, 0f, 1f,   //Front Face
				 1f, 0f, 0f,   1f, 0f, 0f,   1f, 0f, 0f,   1f, 0f, 0f,   1f, 0f, 0f,   1f, 0f, 0f,   //Right Face
				-1f, 0f, 0f,  -1f, 0f, 0f,  -1f, 0f, 0f,  -1f, 0f, 0f,  -1f, 0f, 0f,  -1f, 0f, 0f,   //Left Face
				 0f, 0f,-1f,   0f, 0f,-1f,   0f, 0f,-1f,   0f, 0f,-1f,   0f, 0f,-1f,   0f, 0f,-1f,   //Back Face
				 0f, 1f, 0f,   0f, 1f, 0f,   0f, 1f, 0f,   0f, 1f, 0f,   0f, 1f, 0f,   0f, 1f, 0f,   //Top Face
				 0f,-1f, 0f,   0f,-1f, 0f,   0f,-1f, 0f,   0f,-1f, 0f,   0f,-1f, 0f,   0f,-1f, 0f    //Bottom Face
		};

		_vertexBuffer.put(vertexCoords);

		_normalBuffer.put(normalCoords);

		frontFaceIndices.put(frontFaceIndicesArray);
		rightFaceIndices.put(rightFaceIndicesArray);
		leftFaceIndices.put(leftFaceIndicesArray);
		backFaceIndices.put(backFaceIndicesArray);
		topFaceIndices.put(topFaceIndicesArray);
		bottomFaceIndices.put(bottomFaceIndicesArray);

		_vertexBuffer.position(0);
		_normalBuffer.position(0);
		frontFaceIndices.position(0);
		rightFaceIndices.position(0);
		leftFaceIndices.position(0);
		backFaceIndices.position(0);
		topFaceIndices.position(0);
		bottomFaceIndices.position(0);
	}

	public void draw(GL10 gl) {
		// 3 coordinates in each vertex
		// 0 is the space between each vertex. They are densely packed in the array, so the value is 0
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, _vertexBuffer);

		// 0 is the space between each vertex. They are densely packed in the array, so the value is 0
		gl.glNormalPointer(GL10.GL_FLOAT, 0, _normalBuffer);

		// Draw the faces of the cube.
		// Each face has 6 indices, because it is made of two different triangles.
		if ((currentZShift <= -maxZShift) || (currentZShift >= maxZShift)) {
			zShiftDir = -zShiftDir;
		}
		currentZShift = currentZShift + zInc*zShiftDir;
		
		gl.glTranslatef(0f, 0f, currentZShift);
		//Draw front face
		gl.glColor4f(darkR, darkG, darkB, a);
		gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_SHORT, frontFaceIndices);

		//Draw right face
		gl.glColor4f(r, g, b, a);
		gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_SHORT, rightFaceIndices);

		//Draw left face
		gl.glColor4f(r, g, b, a);
		gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_SHORT, leftFaceIndices);

		//Draw back face
		gl.glColor4f(darkR, darkG, darkB, a);
		gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_SHORT, backFaceIndices);

		//Draw top face
		gl.glColor4f(darkerR, darkerG, darkerB, a);
		gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_SHORT, topFaceIndices);

		//Draw bottom face
		gl.glColor4f(darkerR, darkerG, darkerB, a);
		gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_SHORT, bottomFaceIndices);
		
		gl.glTranslatef(0f, 0f, -currentZShift);
	}
}
