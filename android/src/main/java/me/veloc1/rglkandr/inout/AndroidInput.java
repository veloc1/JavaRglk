package me.veloc1.rglkandr.inout;

import android.app.Activity;
import android.util.Log;
import me.veloc1.rglk.input.GameInput;

public class AndroidInput implements GameInput, InputListener {

    private static final int NO_COMMAND = -1000;
    private int mLastCommand;

    public AndroidInput(Activity activity) {
        if (!(activity instanceof InputListenerProvider)) {
            throw new IllegalArgumentException("Activity must implements InputListenerProvider");
        } else {
            ((InputListenerProvider) activity).setInputListener(this);
        }
        mLastCommand = NO_COMMAND;
    }

    @Override
    public synchronized int update() {
        while (mLastCommand == NO_COMMAND) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int command = mLastCommand;
        mLastCommand = NO_COMMAND;
        return command;
    }

    @Override
    public synchronized void onCommandPressed(int command) {
        mLastCommand = command;
        notifyAll();
    }

    public interface InputListenerProvider {
        public void setInputListener(InputListener listener);
    }
}
