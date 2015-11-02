package com.muzammilpeer.commandapp.executer;

import android.content.Context;

import com.muzammilpeer.commandapp.util.Log4a;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Created by muzammilpeer on 11/1/15.
 */
public class ShellExecuter {

    private static ShellExecuter mInstance = new  ShellExecuter();

    private Process mProcess;
    private  int PROCESS_ID;

    private Context mContext;

    private boolean isStopped = false;

    BufferedReader mReader;

    BufferedWriter mWriter;

    private ShellExecuter() {

    }

    public static void writeLine(OutputStream os, PrintWriter logWriter, String value) throws IOException
    {
        String line = value + "\n";
        os.write( line.getBytes() );
        if( logWriter != null )
        {
            Log4a.e("writeLine ", value);
        }
    }
    public static String readString(InputStream is, PrintWriter logWriter, boolean block) throws IOException
    {
        if( !block && is.available() == 0 )
        {
            //Caller doesn't want to wait for data and there isn't any available right now
            return null;
        }
        byte firstByte = (byte)is.read(); //wait till something becomes available
        int available = is.available();
        byte[] characters = new byte[available + 1];
        characters[0] = firstByte;
        is.read( characters, 1, available );
        String string = new String( characters );
        if( logWriter != null )
        {
            logWriter.println( string );
        }
        return string;
    }

    public static ShellExecuter getInstance(Context context)
    {
        mInstance.mContext = context;
        return mInstance;
    }

    public String Executer(String command) {

        try {
            mProcess = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    mProcess.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                Log4a.e("Progress = ",line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


//        try {
//            mProcess = Runtime.getRuntime().exec(command);
//            InputStream stderr = mProcess.getErrorStream();
//            InputStreamReader isr = new InputStreamReader(stderr);
//            BufferedReader br = new BufferedReader(isr);
//            String line = null;
//            Log4a.e("Progress = ","<ERROR>");
//            while ( (line = br.readLine()) != null)
//                Log4a.e("Progress = ",line);
//            Log4a.e("Progress = ", "</ERROR>");
//            int exitVal = mProcess.waitFor();
//            Log4a.e("Process exitValue: " , exitVal + "");
//
//        }catch (Exception e)
//        {
//            Log4a.printException(e);
//        }
//
        StringBuffer output = new StringBuffer();
//        try {
//            mProcess = Runtime.getRuntime().exec(command);
//
//            OutputStream os = mProcess.getOutputStream();
//            InputStream is = mProcess.getInputStream();
//            writeLine( os, null, "ps | grep PID" );
//            writeLine( os, null, "exit" );
//
//        } catch (IOException e) {
//            Log4a.printException(e);
//        }
//        try {
//            PROCESS_ID =  mProcess.waitFor();
//        }catch (InterruptedException e)
//        {
//            Log4a.printException(e);
//            if (mProcess != null)
//            {
//                mProcess.destroy();
//
//            }
//        }

        String response = output.toString();
        return response;

    }

    public void stopExecutor() {
        if (mProcess != null)
        {
            try {
                mWriter = new BufferedWriter(
                        new OutputStreamWriter(mProcess.getOutputStream()));
                mWriter.write("q");
                mWriter.close();
            }catch (IOException e)
            {
                Log4a.printException(e);
            }

//            Log4a.e("killing ID ", PROCESS_ID + "");
//            mProcess.destroy();
//            mProcess = null;
//            android.os.Process.killProcess(PROCESS_ID);
        }

    }

    public int getPROCESS_ID() {
        return PROCESS_ID;
    }

    public void setPROCESS_ID(int PROCESS_ID) {
        this.PROCESS_ID = PROCESS_ID;
    }
}

