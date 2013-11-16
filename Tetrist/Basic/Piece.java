package Basic;

class Piece
{
    final int id;
    protected int rotation;
    protected final int face[][][];
    int abcissae[];
    int ordinates[];
    int spawn_x;
    int spawn_y;

    Piece(int n, int x, int y)
    {
        this(n);
        spawn_x = x;
        spawn_y = y;
        reset();
    }

    protected Piece(int n)
    {
        id = n;
        rotation = 1;
        abcissae = new int[4];
        ordinates = new int[4];
        face = load_face(id);
    }

    public void reset()
    {
        rotation = 1;
        int center_x = face[rotation][0][0];
        for (int i = 0; i < abcissae.length; i++)
        {
            int i_x = face[rotation][i][0];
            abcissae[i] = spawn_x + i_x - center_x;
        }

        int center_y = face[rotation][0][1];
        for (int i = 0; i < ordinates.length; i++)
        {
            int i_y = face[rotation][i][1];
            ordinates[i] = spawn_y + i_y - center_y;
        }

    }

    public void fall()
    {
        move(ordinates, -1);
    }

    public void left()
    {
        move(abcissae, -1);
    }

    public void right()
    {
        move(abcissae, 1);
    }

    protected void move(int[] axis, int shift)
    {
        for (int i = 0; i < axis.length; i++)
            axis[i] += shift;
    }

    void rotate()
    {
        if (id != 5)
        {
            rotation++;
            if (id == 0 || id == 4 || id == 6)
                rotation = rotation % 2;
            else
                rotation = rotation % 4;

            for (int i = 1; i < 4; i++)
            {
                int center_x = face[rotation][0][0];
                int center_y = face[rotation][0][1];
                int i_x = face[rotation][i][0];
                int i_y = face[rotation][i][1];
                abcissae[i] = abcissae[0] + i_x - center_x;
                ordinates[i] = ordinates[0] + i_y - center_y;
            }
        }
    }

    public void needed_space_fall(int[] x, int[] y)
    {
        needed_space(x, y, 0);
    }

    public void needed_space_left(int[] x, int[] y)
    {
        needed_space(x, y, 1);
    }

    public void needed_space_right(int[] x, int[] y)
    {
        needed_space(x, y, 2);
    }

    protected void needed_space(int[] x, int[] y, int move)
    {
        /* move:
         * 0: fall, 1: left; 2: right */
        int shift = (move == 0) ? 0 : ((move == 1) ? -1 : 1);
        for (int i = 0; i < abcissae.length; i++)
            x[i] = abcissae[i] + shift;

        shift = (move == 0) ? -1 : 0;
        for (int i = 0; i < ordinates.length; i++)
            y[i] = ordinates[i] + shift;
    }

    public void needed_space_rotation(int[] x, int[] y)
    {
        rotate();
        for (int i = 0; i < abcissae.length; i++)
        {
            x[i] = abcissae[i];
            y[i] = ordinates[i];
        }
        rotate();
        rotate();
        rotate();
    }

    protected static int[][][] load_face(int p)
    {
        int[][][] face = new int[4][4][2];
        /* Centre de rotation. */
        for (int i = 0; i < face.length; i++)
        {
            face[i][0][0] = 0;
            face[i][0][1] = 1;
        }

        face[0][1][0] = p != 0 ? 1 : 0;
        face[0][1][1] = p >= 4 ? 1 : (3 - p);

        face[0][2][0] = p == 4 ? 1 : 0 ;
        face[0][2][1] = ((p % 2) == 0) ? 2 : 0;

        face[0][3][0] = p > 4 ? 1 : 0;
        face[0][3][1] = (p == 1 || p == 3) ? 2 : 0;

        /* Rotations. */
        for (int i = 1; i < face.length; i++)
            for (int j = 0; j < face[i].length; j++)
            {
                int h = i - 1;
                face[i][j][0] = -face[h][j][1];
                face[i][j][1] = face[h][j][0];
            }

        return face;
    }
}
