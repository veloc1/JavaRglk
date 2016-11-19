package me.veloc1.rglkandr;

import android.app.Activity;
import dagger.Module;
import dagger.Provides;
import me.veloc1.rglk.input.GameInput;
import me.veloc1.rglk.output.GameOutput;
import me.veloc1.rglkandr.inout.AndroidInput;
import me.veloc1.rglkandr.inout.AndroidOutput;

@Module
public class GameModule {

    private Activity mActivity;

    public GameModule(Activity activity){
        mActivity = activity;
    }

    @Provides
    GameInput provideGameInput() {
        return new AndroidInput(mActivity);
    }

    @Provides
    GameOutput provideGameOutput() {
        return new AndroidOutput(mActivity);
    }

}
