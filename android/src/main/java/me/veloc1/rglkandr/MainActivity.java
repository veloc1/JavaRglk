package me.veloc1.rglkandr;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import me.veloc1.rglk.game.Commands;
import me.veloc1.rglkandr.inout.AndroidInput;
import me.veloc1.rglkandr.inout.AndroidOutput;
import me.veloc1.rglkandr.inout.InputListener;

public class MainActivity extends Activity implements AndroidInput.InputListenerProvider, AndroidOutput.SurfaceProvider {

    GameThread mGameThread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGameThread = new GameThread(this);

        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGameThread.start();
                findViewById(R.id.start).setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void setInputListener(final InputListener inputListener) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.left:
                        inputListener.onCommandPressed(Commands.LEFT);
                        break;
                    case R.id.right:
                        inputListener.onCommandPressed(Commands.RIGHT);
                        break;
                    case R.id.up:
                        inputListener.onCommandPressed(Commands.UP);
                        break;
                    case R.id.down:
                        inputListener.onCommandPressed(Commands.DOWN);
                        break;
                }

            }
        };

        findViewById(R.id.left).setOnClickListener(listener);
        findViewById(R.id.right).setOnClickListener(listener);
        findViewById(R.id.up).setOnClickListener(listener);
        findViewById(R.id.down).setOnClickListener(listener);
    }

    @Override
    public SurfaceHolder getSurfaceHolder() {
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surface);
        return surfaceView.getHolder();
    }
}