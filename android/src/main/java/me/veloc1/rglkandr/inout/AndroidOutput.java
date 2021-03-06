package me.veloc1.rglkandr.inout;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.view.SurfaceHolder;
import me.veloc1.rglk.game.World;
import me.veloc1.rglk.game.objects.BaseObject;
import me.veloc1.rglk.game.objects.Player;
import me.veloc1.rglk.output.GameOutput;

public class AndroidOutput implements GameOutput {

    private static final int OFFSET    = 6;
    private static final int TILE_SIZE = 64;
    private int           mUiOffset;
    private Handler       mHandler;
    private SurfaceHolder mSurfaceHolder;
    private Paint         mPaint;
    private int           mWidth;
    private int           mHeight;
    private int           mOffsetX;
    private int           mOffsetY;

    public AndroidOutput(Activity activity) {
        if (activity instanceof SurfaceProvider) {
            mHandler = new Handler(activity.getMainLooper());
            mSurfaceHolder = ((SurfaceProvider) activity).getSurfaceHolder();
        } else {
            throw new IllegalArgumentException("Activity should implements SurfaceProvider");
        }

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(3);

        mWidth = mSurfaceHolder.getSurfaceFrame().width();
        mHeight = mSurfaceHolder.getSurfaceFrame().height();
        mOffsetX = 0;
        mOffsetY = 0;

        mUiOffset = 48;
    }

    @Override
    public void clear() {
    }

    @Override
    public void draw(final World world) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mWidth = mSurfaceHolder.getSurfaceFrame().width();
                mHeight = mSurfaceHolder.getSurfaceFrame().height();

                Canvas canvas = mSurfaceHolder.lockCanvas();
                // clear
                canvas.drawColor(Color.BLACK);
                if (world.isGameOver()) {
                    drawGameOver(canvas);
                } else {
                    drawWorld(canvas, world);
                }
                mSurfaceHolder.unlockCanvasAndPost(canvas);
            }
        });
    }

    private void drawGameOver(Canvas canvas) {
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(48);
        String gameover = "Game Over!";
        Rect   bounds   = new Rect();
        mPaint.getTextBounds(gameover, 0, gameover.length(), bounds);
        canvas.drawText(gameover, mWidth / 2 - bounds.width() / 2, mHeight / 2 - bounds.height() / 2, mPaint);
    }

    private void drawWorld(Canvas canvas, World world) {
        Player player = null;
        for (BaseObject baseObject : world.getObjects()) {
            if (baseObject.isPlayer) {
                player = (Player) baseObject;
                if (baseObject.x > mOffsetX + mWidth / TILE_SIZE - OFFSET) {
                    mOffsetX = baseObject.x - mWidth / TILE_SIZE + OFFSET;
                } else if (baseObject.x < mOffsetX + OFFSET) {
                    mOffsetX = baseObject.x - OFFSET;
                }
                if (baseObject.y >= mOffsetY + mHeight / TILE_SIZE - OFFSET) {
                    mOffsetY = baseObject.y - mHeight / TILE_SIZE + OFFSET;
                } else if (baseObject.y <= mOffsetY + OFFSET) {
                    mOffsetY = baseObject.y - OFFSET;
                }
            }
        }
        if (mOffsetX < 0) {
            mOffsetX = 0;
        }
        if (mOffsetY < 0) {
            mOffsetY = 0;
        }

        mPaint.setTextSize(24);
        mPaint.setColor(Color.WHITE);

        canvas.drawText("HP: " + player.hp, 0, mUiOffset, mPaint);

        for (int i = mOffsetY; i <= mOffsetY + mHeight / TILE_SIZE; i++) {
            for (int j = mOffsetX; j <= mOffsetX + mWidth / TILE_SIZE; j++) {
                char character = world.getCharAtPosition(j, i);
                switch (character) {
                    case '.':
                        mPaint.setColor(Color.WHITE);
                        break;
                    case '#':
                        mPaint.setColor(Color.DKGRAY);
                        break;
                    case '@':
                        mPaint.setColor(Color.GREEN);
                        break;
                    case 'm':
                        mPaint.setColor(Color.RED);
                        break;
                }
                int x = j * TILE_SIZE - mOffsetX * TILE_SIZE;
                int y = i * TILE_SIZE - mOffsetY * TILE_SIZE + mUiOffset;

                canvas.drawRect(x, y, x + TILE_SIZE, y + TILE_SIZE, mPaint);
            }

        }
    }

    public interface SurfaceProvider {

        public SurfaceHolder getSurfaceHolder();

    }
}
