package suits;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import tests.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({

        GetStatsTest.class,
        TotalRequestsTest.class,
        HashPasswordTest.class,
        MultiHashPasswordTest.class,
        ShutdownTest.class

})
public class JumpCloudExerciseSuit {

}
