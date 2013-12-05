import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    TestPoint.class,
    TestGrid.class,
    TestPiece.class,
    TestGame.class,
    TestGridIA.class,
    TestPredict.class,
})


public class TestAll { }
