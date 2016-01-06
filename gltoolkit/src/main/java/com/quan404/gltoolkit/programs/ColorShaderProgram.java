package com.quan404.gltoolkit.programs;

import android.content.Context;
import android.opengl.GLES20;

/**
 * Created by quanhua on 05/01/2016.
 */
public class ColorShaderProgram extends ShaderProgram {
    private final int uMatrixLocation;
    private final int aPositionLocation;
    private final int aColorLocation;

    public ColorShaderProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId) {
        super(context, vertexShaderResourceId, fragmentShaderResourceId);

        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);

        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
        aColorLocation = GLES20.glGetAttribLocation(program, A_COLOR);
    }

    public void setUniforms(float[] matrix){
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
    }

    public int getPositionAttributeLocation(){
        return aPositionLocation;
    }

    public int getColorAttributeLocation(){
        return aColorLocation;
    }
}
