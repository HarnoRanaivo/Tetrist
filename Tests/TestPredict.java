import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertArrayEquals;

import Component.Point;
import Component.Grid;
import Component.Piece;
import IA.Predict;
import IA.GridIA;

@RunWith(JUnit4.class)
public class TestPredict
{
    private static final int MIDDLE = Grid.DEFAULT_WIDTH / 2;
    private static final int TOP = Grid.DEFAULT_HEIGHT - 1;

    private static final Grid EMPTY_GRID = new Grid();

    @Test
    public void test__possible_columns()
    {
        GridIA g = new GridIA(EMPTY_GRID);
        int[] results = Predict.possible_columns(g, L, g.highest_blocks());
        assertEquals(0, results[0]);
        assertEquals(9, results[1]);
    }

    @Test
    public void test_possible_falls()
    {
        for (char name : Piece.NAMES)
        {
            Point[][][] expected = expect_empty_grid(name);
            Point[][][] actual = Predict.possible_falls(EMPTY_GRID, new Piece(name, MIDDLE, TOP));
            assertArrayEquals("Fail " + name, expected, actual);
        }
    }

    public static Point[][][] expect_empty_grid(char name)
    {
        Point[][][] points = new Point[4][][];
        Point[][] face = Piece.FACES[Piece.name_to_id(name)];

        for (int i = 0; i < 4; i++)
        {
            Piece piece = new Piece(name, 0, 0);
            for (int j = 0; j < i; j++)
                piece.rotate();
            int width = piece.maximum_abcissa() - piece.minimum_abcissa() + 1;
            int rotation = piece.rotation();

            Point[] local = new Point[4];
            for (int j = 0; j < local.length; j++)
            {
                local[j] = new Point(face[rotation][j]);
                Piece local_piece = new Piece(name, 0, 0);
                local_piece.set_coordinates(face[rotation]);
                if (local_piece.minimum_abcissa() < 0)
                    local[j].shift((-1) * local_piece.minimum_abcissa(), 0);
                if (local_piece.minimum_ordinate() < 0)
                    local[j].shift(0, (-1) * local_piece.minimum_ordinate());
            }

            points[i] = new Point[Grid.DEFAULT_WIDTH - width + 1][];
            for (int j = 0; j < points[i].length; j++)
            {
                points[i][j] = new Point[4];
                for (int k = 0; k < 4; k++)
                {
                    points[i][j][k] = new Point(local[k]);
                    points[i][j][k].shift(j, 0);
                }
            }
        }

        return points;
    }
}
