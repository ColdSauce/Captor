package at.maui.cardar.ui.ar;

import static at.maui.cardar.ui.ar.GLUtils.checkGLError;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.google.vrtoolkit.cardboard.CardboardView;
import com.google.vrtoolkit.cardboard.EyeTransform;
import com.google.vrtoolkit.cardboard.HeadTransform;
import com.google.vrtoolkit.cardboard.Viewport;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;

import at.maui.cardar.R;
import timber.log.Timber;

/**
 * Created by maui on 02.07.2014.
 */
public class Renderer implements CardboardView.StereoRenderer {

    // We keep the light always position just above the user.
    private final float[] mLightPosInWorldSpace = new float[] {0.0f, 2.0f, 0.0f, 1.0f};
    private final float[] mLightPosInEyeSpace = new float[4];

    private static final int COORDS_PER_VERTEX = 3;

    private static final float CAMERA_Z = 0.01f;
    private static final float TIME_DELTA = 0.3f;

    private static final float YAW_LIMIT = 0.12f;
    private static final float PITCH_LIMIT = 0.12f;

    private final WorldLayoutData DATA = new WorldLayoutData();

    private Context mContext;

    private int[] mGlPrograms;

    private FloatBuffer mFloorVertices;
    private FloatBuffer mFloorColors;
    private FloatBuffer mFloorNormals;

    private FloatBuffer mWallVertices;
    private FloatBuffer mWallColors;
    private FloatBuffer mWallNormals;

    private FloatBuffer mCubeVertices;
    private FloatBuffer mCubeColors;
    private FloatBuffer mCubeFoundColors;
    private FloatBuffer mCubeNormals;

    // Head Position
    private float[] mHeadView;

    private float[] mView;
    private float[] mModelCube;
    private float[] mCamera;
    private float[] mModelFloor;
    private float[] mModelWall;
    private float[] mModelViewProjection;
    private float[] mModelViewProjectionWall;
    private float[] mModelView;
    private float[] mModelViewWall;

    private int[] mTextures;

    private int mPositionParam;
    private int mTextureCoordParam;
    private int mTextureParam;

    private int mNormalParam;
    private int mColorParam;
    private int mModelViewProjectionParam;
    private int mLightPosParam;
    private int mModelViewParam;
    private int mModelParam;
    private int mIsFloorParam;

    private FloatBuffer pVertex;
    private FloatBuffer pTexCoord;

    private Camera mRealCamera;
    private SurfaceTexture mRealCameraTexture;

    private float mObjectDistance = 12f;
    private float mFloorDepth = 60f;

    public Renderer(Context ctx) {
        mContext = ctx;

        mCamera = new float[16];
        mView = new float[16];
        mHeadView = new float[16];

        mGlPrograms = new int[2];

        mModelCube = new float[16];
        mModelFloor = new float[16];
        mModelWall = new float[16];

        mModelViewProjection = new float[16];
        mModelViewProjectionWall = new float[16];
        mModelView = new float[16];
        mModelViewWall = new float[16];

        float[] vtmp = { 1.0f, -1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f };
        float[] ttmp = { 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f };
        pVertex = ByteBuffer.allocateDirect(8 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        pVertex.put ( vtmp );
        pVertex.position(0);
        pTexCoord = ByteBuffer.allocateDirect(8*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        pTexCoord.put ( ttmp );
        pTexCoord.position(0);
    }

    @Override
    public void onNewFrame(HeadTransform headTransform) {

        // Build the Model part of the ModelView matrix.
        Matrix.rotateM(mModelCube, 0, TIME_DELTA, 0.5f, 0.5f, 1.0f);

        // Build the camera matrix and apply it to the ModelView.
        Matrix.setLookAtM(mCamera, 0, 0.0f, 0.0f, CAMERA_Z, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);

        headTransform.getHeadView(mHeadView, 0);

        mRealCameraTexture.updateTexImage();

        checkGLError("onReadyToDraw");
    }

    @Override
    public void onDrawEye(EyeTransform eyeTransform) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Apply the eye transformation to the camera.
        Matrix.multiplyMM(mView, 0, eyeTransform.getEyeView(), 0, mCamera, 0);

        /*GLES20.glUseProgram(mGlPrograms[0]);

        mPositionParam = GLES20.glGetAttribLocation(mGlPrograms[0], "vPosition");
        mTextureCoordParam = GLES20.glGetAttribLocation(mGlPrograms[0], "vTexCoord");
        mTextureParam = GLES20.glGetAttribLocation(mGlPrograms[0], "sTexture");

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, mTextures[0]);
        GLES20.glUniform1i(mTextureParam, 0);

        GLES20.glVertexAttribPointer(mPositionParam, 2, GLES20.GL_FLOAT, false, 4*2, pVertex);
        GLES20.glVertexAttribPointer(mTextureCoordParam, 2, GLES20.GL_FLOAT, false, 4*2, pTexCoord );
        GLES20.glEnableVertexAttribArray(mPositionParam);
        GLES20.glEnableVertexAttribArray(mTextureCoordParam);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        GLES20.glFlush();

        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT);*/

        GLES20.glUseProgram(mGlPrograms[1]);

        mTextureParam = GLES20.glGetAttribLocation(mGlPrograms[1], "v_Texture");
        mTextureCoordParam = GLES20.glGetAttribLocation(mGlPrograms[1], "v_TextCoord");
        mModelViewProjectionParam = GLES20.glGetUniformLocation(mGlPrograms[1], "u_MVP");
        mLightPosParam = GLES20.glGetUniformLocation(mGlPrograms[1], "u_LightPos");
        mModelViewParam = GLES20.glGetUniformLocation(mGlPrograms[1], "u_MVMatrix");
        mModelParam = GLES20.glGetUniformLocation(mGlPrograms[1], "u_Model");
        mIsFloorParam = GLES20.glGetUniformLocation(mGlPrograms[1], "u_IsFloor");
        mPositionParam = GLES20.glGetAttribLocation(mGlPrograms[1], "a_Position");
        mNormalParam = GLES20.glGetAttribLocation(mGlPrograms[1], "a_Normal");
        mColorParam = GLES20.glGetAttribLocation(mGlPrograms[1], "a_Color");

        GLES20.glVertexAttribPointer(mTextureCoordParam, 2, GLES20.GL_FLOAT, false, 4*2, pTexCoord );
        GLES20.glEnableVertexAttribArray(mTextureCoordParam);

        GLES20.glEnableVertexAttribArray(mPositionParam);
        GLES20.glEnableVertexAttribArray(mNormalParam);
        GLES20.glEnableVertexAttribArray(mColorParam);
        checkGLError("mColorParam");

        // Set the position of the light
        Matrix.multiplyMV(mLightPosInEyeSpace, 0, mView, 0, mLightPosInWorldSpace, 0);
        GLES20.glUniform3f(mLightPosParam, mLightPosInEyeSpace[0], mLightPosInEyeSpace[1],
                mLightPosInEyeSpace[2]);

        // Build the ModelView and ModelViewProjection matrices
        // for calculating cube position and light.
        Matrix.multiplyMM(mModelView, 0, mView, 0, mModelCube, 0);
        Matrix.multiplyMM(mModelViewProjection, 0, eyeTransform.getPerspective(), 0, mModelView, 0);
        drawCube();


        //Matrix.multiplyMM(mModelView, 0, mView, 0, mModelWall, 0);
        Matrix.setIdentityM(mModelViewWall, 0);
        Matrix.translateM(mModelViewWall, 0, 0, -5f, -78f);
        Matrix.multiplyMM(mModelViewProjectionWall, 0, eyeTransform.getPerspective(), 0,
                mModelViewWall, 0);
        drawWall();

        // Set mModelView for the floor, so we draw floor in the correct location
        Matrix.multiplyMM(mModelView, 0, mView, 0, mModelFloor, 0);
        Matrix.multiplyMM(mModelViewProjection, 0, eyeTransform.getPerspective(), 0,
                mModelView, 0);
        drawFloor(eyeTransform.getPerspective());

    }


    public void drawCube() {
        // This is not the floor!
        GLES20.glUniform1f(mIsFloorParam, 0f);

        // Set the Model in the shader, used to calculate lighting
        GLES20.glUniformMatrix4fv(mModelParam, 1, false, mModelCube, 0);

        // Set the ModelView in the shader, used to calculate lighting
        GLES20.glUniformMatrix4fv(mModelViewParam, 1, false, mModelView, 0);

        // Set the position of the cube
        GLES20.glVertexAttribPointer(mPositionParam, COORDS_PER_VERTEX, GLES20.GL_FLOAT,
                false, 0, mCubeVertices);

        // Set the ModelViewProjection matrix in the shader.
        GLES20.glUniformMatrix4fv(mModelViewProjectionParam, 1, false, mModelViewProjection, 0);

        // Set the normal positions of the cube, again for shading
        GLES20.glVertexAttribPointer(mNormalParam, 3, GLES20.GL_FLOAT,
                false, 0, mCubeNormals);

        if (isLookingAtObject()) {
            GLES20.glVertexAttribPointer(mColorParam, 4, GLES20.GL_FLOAT, false,
                    0, mCubeFoundColors);
        } else {
            GLES20.glVertexAttribPointer(mColorParam, 4, GLES20.GL_FLOAT, false,
                    0, mCubeColors);
        }
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 36);
        checkGLError("Drawing cube");
    }

    public void drawWall() {

        GLES20.glUniform1f(mIsFloorParam, 0.3f);

        // Set the Model in the shader, used to calculate lighting
        GLES20.glUniformMatrix4fv(mModelParam, 1, false, mModelViewWall, 0);

        // Set the ModelView in the shader, used to calculate lighting
        GLES20.glUniformMatrix4fv(mModelViewParam, 1, false, mModelViewWall, 0);

        // Set the position of the cube
        GLES20.glVertexAttribPointer(mPositionParam, COORDS_PER_VERTEX, GLES20.GL_FLOAT,
                false, 0, mWallVertices);

        // Set the ModelViewProjection matrix in the shader.
        GLES20.glUniformMatrix4fv(mModelViewProjectionParam, 1, false, mModelViewProjectionWall, 0);

        // Set the normal positions of the cube, again for shading
        GLES20.glVertexAttribPointer(mNormalParam, 3, GLES20.GL_FLOAT,
                false, 0, mWallNormals);

        GLES20.glVertexAttribPointer(mColorParam, 4, GLES20.GL_FLOAT, false,
                0, mWallColors);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, mTextures[0]);
        GLES20.glUniform1i(mTextureParam, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);

        checkGLError("drawing wall");
    }

    public void drawFloor(float[] perspective) {

        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glBlendColor(1.f, 1.f, 1.f, 0.7f);

        // This is the floor!
        GLES20.glUniform1f(mIsFloorParam, 1f);

        // Set ModelView, MVP, position, normals, and color
        GLES20.glUniformMatrix4fv(mModelParam, 1, false, mModelFloor, 0);
        GLES20.glUniformMatrix4fv(mModelViewParam, 1, false, mModelView, 0);
        GLES20.glUniformMatrix4fv(mModelViewProjectionParam, 1, false, mModelViewProjection, 0);
        GLES20.glVertexAttribPointer(mPositionParam, COORDS_PER_VERTEX, GLES20.GL_FLOAT,
                false, 0, mFloorVertices);
        GLES20.glVertexAttribPointer(mNormalParam, 3, GLES20.GL_FLOAT, false, 0, mFloorNormals);
        GLES20.glVertexAttribPointer(mColorParam, 4, GLES20.GL_FLOAT, false, 0, mFloorColors);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);

        GLES20.glDisable(GLES20.GL_BLEND);

        checkGLError("drawing floor");
    }

    @Override
    public void onFinishFrame(Viewport viewport) {

    }

    @Override
    public void onSurfaceChanged(int width, int height) {
        Timber.e("onSurfaceChanged(%d, %d)", width, height);
    }

    @Override
    public void onSurfaceCreated(EGLConfig eglConfig) {
        Timber.e("onSurfaceCreated");

        GLES20.glClearColor(0.1f, 0.1f, 0.1f, 0.5f); // Dark background so text shows up well

        // Init Objects here
        initTextures();

        initRealWorldCamera();

        initModels();

        initWorldShader();

        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        // Object first appears directly in front of user
        Matrix.setIdentityM(mModelCube, 0);
        Matrix.translateM(mModelCube, 0, 0, 0, -mObjectDistance);

        Matrix.setIdentityM(mModelFloor, 0);
        Matrix.translateM(mModelFloor, 0, 0, -mFloorDepth, 0); // Floor appears below user

        Matrix.setIdentityM(mModelWall, 0);
        Matrix.translateM(mModelWall, 0, 0, 0, -16f);

        checkGLError("onSurfaceCreated");
    }

    private void initTextures() {
        mTextures = new int[1];
        GLES20.glGenTextures ( 1, mTextures, 0 );
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, mTextures[0]);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
    }

    private void initRealWorldCamera() {
        mRealCamera = Camera.open(0);

        //Set the camera parameters
        Camera.Parameters params = mRealCamera.getParameters();

        int fps = 0;
        for(Integer nfps : params.getSupportedPreviewFrameRates()) {
            if(nfps > fps)
                fps = nfps;
        }
        params.setPreviewFrameRate(fps);

        params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        params.setPreviewSize(1920,1080);
        mRealCamera.setParameters(params);

        mRealCameraTexture = new SurfaceTexture(mTextures[0]);
        try {
            mRealCamera.setPreviewTexture(mRealCameraTexture);
        } catch (IOException t) {
            Timber.e("Cannot set preview texture target!");
        }

        //Start the preview
        mRealCamera.startPreview();
    }

    /**
     * Find a new random position for the object.
     * We'll rotate it around the Y-axis so it's out of sight, and then up or down by a little bit.
     */
    public void hideObject() {
        float[] rotationMatrix = new float[16];
        float[] posVec = new float[4];

        // First rotate in XZ plane, between 90 and 270 deg away, and scale so that we vary
        // the object's distance from the user.
        float angleXZ = (float) Math.random() * 180 + 90;
        Matrix.setRotateM(rotationMatrix, 0, angleXZ, 0f, 1f, 0f);
        float oldObjectDistance = mObjectDistance;
        mObjectDistance = (float) Math.random() * 15 + 5;
        float objectScalingFactor = mObjectDistance / oldObjectDistance;
        Matrix.scaleM(rotationMatrix, 0, objectScalingFactor, objectScalingFactor, objectScalingFactor);
        Matrix.multiplyMV(posVec, 0, rotationMatrix, 0, mModelCube, 12);

        // Now get the up or down angle, between -20 and 20 degrees
        float angleY = (float) Math.random() * 80 - 40; // angle in Y plane, between -40 and 40
        angleY = (float) Math.toRadians(angleY);
        float newY = (float)Math.tan(angleY) * mObjectDistance;

        Matrix.setIdentityM(mModelCube, 0);
        Matrix.translateM(mModelCube, 0, posVec[0], newY, posVec[2]);
    }

    /**
     * Check if user is looking at object by calculating where the object is in eye-space.
     * @return
     */
    public boolean isLookingAtObject() {
        float[] initVec = {0, 0, 0, 1.0f};
        float[] objPositionVec = new float[4];

        // Convert object space to camera space. Use the headView from onNewFrame.
        Matrix.multiplyMM(mModelView, 0, mHeadView, 0, mModelCube, 0);
        Matrix.multiplyMV(objPositionVec, 0, mModelView, 0, initVec, 0);

        float pitch = (float)Math.atan2(objPositionVec[1], -objPositionVec[2]);
        float yaw = (float)Math.atan2(objPositionVec[0], -objPositionVec[2]);

        Timber.i("Object position: X: " + objPositionVec[0]
                + "  Y: " + objPositionVec[1] + " Z: " + objPositionVec[2]);
        Timber.i("Object Pitch: " + pitch +"  Yaw: " + yaw);

        return (Math.abs(pitch) < PITCH_LIMIT) && (Math.abs(yaw) < YAW_LIMIT);
    }

    private void initModels() {
        ByteBuffer bbVertices = ByteBuffer.allocateDirect(DATA.CUBE_COORDS.length * 4);
        bbVertices.order(ByteOrder.nativeOrder());
        mCubeVertices = bbVertices.asFloatBuffer();
        mCubeVertices.put(DATA.CUBE_COORDS);
        mCubeVertices.position(0);

        ByteBuffer bbColors = ByteBuffer.allocateDirect(DATA.CUBE_COLORS.length * 4);
        bbColors.order(ByteOrder.nativeOrder());
        mCubeColors = bbColors.asFloatBuffer();
        mCubeColors.put(DATA.CUBE_COLORS);
        mCubeColors.position(0);

        ByteBuffer bbFoundColors = ByteBuffer.allocateDirect(DATA.CUBE_FOUND_COLORS.length * 4);
        bbFoundColors.order(ByteOrder.nativeOrder());
        mCubeFoundColors = bbFoundColors.asFloatBuffer();
        mCubeFoundColors.put(DATA.CUBE_FOUND_COLORS);
        mCubeFoundColors.position(0);

        ByteBuffer bbNormals = ByteBuffer.allocateDirect(DATA.CUBE_NORMALS.length * 4);
        bbNormals.order(ByteOrder.nativeOrder());
        mCubeNormals = bbNormals.asFloatBuffer();
        mCubeNormals.put(DATA.CUBE_NORMALS);
        mCubeNormals.position(0);

        // make a floor
        ByteBuffer bbFloorVertices = ByteBuffer.allocateDirect(DATA.FLOOR_COORDS.length * 4);
        bbFloorVertices.order(ByteOrder.nativeOrder());
        mFloorVertices = bbFloorVertices.asFloatBuffer();
        mFloorVertices.put(DATA.FLOOR_COORDS);
        mFloorVertices.position(0);

        ByteBuffer bbFloorNormals = ByteBuffer.allocateDirect(DATA.FLOOR_NORMALS.length * 4);
        bbFloorNormals.order(ByteOrder.nativeOrder());
        mFloorNormals = bbFloorNormals.asFloatBuffer();
        mFloorNormals.put(DATA.FLOOR_NORMALS);
        mFloorNormals.position(0);

        ByteBuffer bbFloorColors = ByteBuffer.allocateDirect(DATA.FLOOR_COLORS.length * 4);
        bbFloorColors.order(ByteOrder.nativeOrder());
        mFloorColors = bbFloorColors.asFloatBuffer();
        mFloorColors.put(DATA.FLOOR_COLORS);
        mFloorColors.position(0);

        // make wall
        ByteBuffer bbWallVertices = ByteBuffer.allocateDirect(DATA.WALL_COORDS.length * 4);
        bbWallVertices.order(ByteOrder.nativeOrder());
        mWallVertices = bbWallVertices.asFloatBuffer();
        mWallVertices.put(DATA.WALL_COORDS);
        mWallVertices.position(0);

        ByteBuffer bbWallNormals = ByteBuffer.allocateDirect(DATA.WALL_NORMALS.length * 4);
        bbWallNormals.order(ByteOrder.nativeOrder());
        mWallNormals = bbWallNormals.asFloatBuffer();
        mWallNormals.put(DATA.WALL_NORMALS);
        mWallNormals.position(0);

        ByteBuffer bbWallColors = ByteBuffer.allocateDirect(DATA.WALL_COLORS.length * 4);
        bbWallColors.order(ByteOrder.nativeOrder());
        mWallColors = bbWallColors.asFloatBuffer();
        mWallColors.put(DATA.WALL_COLORS);
        mWallColors.position(0);
    }

    private int initWorldShader() {
        int vertexShader = GLUtils.loadGLShader(mContext, GLES20.GL_VERTEX_SHADER, R.raw.light_vertex);
        int gridShader = GLUtils.loadGLShader(mContext, GLES20.GL_FRAGMENT_SHADER, R.raw.grid_fragment);

        mGlPrograms[1] = GLES20.glCreateProgram();
        GLES20.glAttachShader(mGlPrograms[1], vertexShader);
        GLES20.glAttachShader(mGlPrograms[1], gridShader);
        GLES20.glLinkProgram(mGlPrograms[1]);

        return mGlPrograms[1];
    }

    @Override
    public void onRendererShutdown() {
        Timber.e("onRendererShutdown");

        GLES20.glDeleteTextures ( 1, mTextures, 0 );

        mRealCamera.stopPreview();
        mRealCamera.setPreviewCallbackWithBuffer(null);
        mRealCamera.release();
    }
}
