package com.quan404.gltoolkit.objects;

import android.opengl.GLES20;

import com.quan404.gltoolkit.Constants;
import com.quan404.gltoolkit.data.VertexArray;
import com.quan404.gltoolkit.programs.VideoShaderProgram;

import java.nio.FloatBuffer;

/**
 * Created by quanhua on 06/01/2016.
 */
public class Video {
    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT) * Constants.BYTES_PER_FLOAT;

    private VertexArray vertexArray;

    private static final float[] VERTEX_DATA = {
            // X, Y, Z, U, V
            -1.0f, -1.0f, 0, 0.f, 0.f,
            1.0f, -1.0f, 0, 1f, 0.f,
            -1.0f,  1.0f, 0, 0.f, 1.f,
            1.0f,  1.0f, 0, 1f, 1.f,
    };

    public Video() {
        this.vertexArray = new VertexArray(VERTEX_DATA);
    }

    public Video(float[] vertex){
        this.vertexArray = new VertexArray(vertex);
    }

    public void bindData(VideoShaderProgram videoShaderProgram){
        vertexArray.setVertexAttribPointer(
                0,
                videoShaderProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT,
                STRIDE
        );

        vertexArray.setVertexAttribPointer(
                POSITION_COMPONENT_COUNT,
                videoShaderProgram.getTextureCoordinatesAttributeLocation(),
                TEXTURE_COORDINATES_COMPONENT_COUNT,
                STRIDE
        );
    }

    public void draw(){
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
    }

    public void setVertexArray(float[] vertex){
        this.vertexArray = new VertexArray(vertex);
    }
}
