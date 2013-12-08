package IA;

import IA.KeySender;

public class Orders
{
    private int rotations;
    private int direction;
    private int shift;

    public Orders()
    {
        rotations = 0;
        shift = 0;
        direction = KeySender.NOTHING;
    }

    public void set_rotations(int n)
    {
        if (n >= 0 && n < 4)
            rotations = n;
    }

    public void set_direction(int n)
    {
        direction = (n < 0 || n > 4) ? 0 : n;
    }

    public void set_shift(int n)
    {
        shift = n;
    }

    public void set(int r, int d, int s)
    {
        set_rotations(r);
        set_direction(d);
        set_shift(s);
    }

    public int rotations()
    {
        return rotations;
    }

    public int direction()
    {
        return direction;
    }

    public int shift()
    {
        return shift;
    }
}
