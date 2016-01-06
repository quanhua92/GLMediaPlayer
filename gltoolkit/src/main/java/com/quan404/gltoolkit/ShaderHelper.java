package com.quan404.gltoolkit;

import android.opengl.GLES20;
import android.util.Log;

/**
 * Created by quanhua on 04/01/2016.
 */
public class ShaderHelper {
    private static final String TAG = "ShaderHelper";

    public static int compileVertexShader(String code){
        return compileShader(GLES20.GL_VERTEX_SHADER, code);
    }

    public static int compileFragmentShader(String code){
        return compileShader(GLES20.GL_FRAGMENT_SHADER, code);
    }

    private static int compileShader(int type, String code){
        final int shaderObjectId = GLES20.glCreateShader(type);

        if (shaderObjectId == 0){
            if (LoggerConfig.ON){
                Log.w(TAG, "Could not create new shader");
            }
            return 0;
        }

        GLES20.glShaderSource(shaderObjectId, code);
        GLES20.glCompileShader(shaderObjectId);

        final int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shaderObjectId, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

        if (LoggerConfig.ON){
            Log.v(TAG, "Results of compiling source: " + "\n" + code + "\n" + GLES20.glGetShaderInfoLog(shaderObjectId));
        }

        if (compileStatus[0] == 0){
            // if it failed, delete the shader object
            GLES20.glDeleteShader(shaderObjectId);

            if (LoggerConfig.ON){
                Log.w(TAG, "Compilation of shader failed");
            }
            return 0;
        }
        return shaderObjectId;
    }

    public static int linkProgram(int vertexShaderId, int fragmentShaderId){
        final int programObjectId = GLES20.glCreateProgram();

        if(programObjectId == 0){
            if (LoggerConfig.ON){
                Log.w(TAG, "Could not create new program");
            }
            return 0;
        }

        GLES20.glAttachShader(programObjectId, vertexShaderId);
        GLES20.glAttachShader(programObjectId, fragmentShaderId);

        GLES20.glLinkProgram(programObjectId);

        final int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(programObjectId, GLES20.GL_LINK_STATUS, linkStatus, 0);
        if (LoggerConfig.ON){
            Log.v(TAG, "Results of linking program: " + "\n" + GLES20.glGetProgramInfoLog(programObjectId));
        }

        if (linkStatus[0] == 0){
            // if it failed, delete the shader object
            GLES20.glDeleteProgram(programObjectId);

            if (LoggerConfig.ON){
                Log.w(TAG, "Linking of program failed");
            }
            return 0;
        }
        return programObjectId;
    }

    public static boolean validateProgram(int programObjectId) {
        GLES20.glValidateProgram(programObjectId);

        final int[] validateStatus = new int[1];
        GLES20.glGetProgramiv(programObjectId, GLES20.GL_VALIDATE_STATUS, validateStatus, 0);
        if (LoggerConfig.ON){
            Log.v(TAG, "Results of validating program: " + validateStatus[0] + "\nLog: " + GLES20.glGetProgramInfoLog(programObjectId));
        }
        return validateStatus[0] != 0;
    }

    public static int buildProgram(String vertexShaderSource, String fragmentShaderSource){
        int program;

        // compile the shaders
        int vertexShader = compileVertexShader(vertexShaderSource);
        int fragmentShader = compileFragmentShader(fragmentShaderSource);

        // link them into a shader program
        program = linkProgram(vertexShader, fragmentShader);

        if (LoggerConfig.ON){
            validateProgram(program);
        }

        return program;
    }
}
