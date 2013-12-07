import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    TestComponent.TestPoint.class,
    TestComponent.TestGrid.class,
    TestComponent.TestPiece.class,
    TestComponent.TestGame.class,
    TestIA.TestGridIA.class,
    TestIA.TestPredict.class,
    TestIA.TestGridState.class,
})

public class TestAll { }
