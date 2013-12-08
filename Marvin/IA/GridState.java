package IA;

import java.lang.Math;
import java.util.Arrays;
import java.lang.Comparable;
import java.util.Comparator;

public class GridState implements Comparable<GridState>, Comparator<GridState>
{
    private final int holes;
    private final int blocks;
    private final int highest_block;

    private final int[] blocks_array;
    private final int[] highest_blocks_array;
    private final int[] holes_array;

    private final float highest_blocks_mean;
    private final float holes_mean;
    private final float blocks_mean;

    private final float highest_blocks_std;
    private final float holes_std;
    private final float blocks_std;

    public GridState(GridIA grid)
    {
        holes = grid.holes();
        blocks = grid.blocks();
        highest_block = grid.highest_block();

        blocks_array = grid.blocks_array();
        highest_blocks_array = grid.highest_blocks_array();
        holes_array = grid.holes_array();

        highest_blocks_mean = mean_value(highest_blocks_array);
        holes_mean = mean_value(holes_array);
        blocks_mean = mean_value(blocks_array);

        highest_blocks_std = standard_deviation(highest_blocks_array, highest_blocks_mean);
        holes_std = standard_deviation(holes_array, holes_mean);
        blocks_std = standard_deviation(blocks_array, blocks_mean);
    }

    private float[] int_array_to_float(int[] array)
    {
        float[] result = new float[array.length];

        for (int i = 0; i < result.length; i++)
            result[i] = (float) array[i];

        return result;
    }

    private float standard_deviation(int[] values)
    {
        return standard_deviation(values, mean_value(values));
    }

    private float standard_deviation(int[] values, float mean)
    {
        float sum = 0;

        for (int value : values)
            sum += Math.pow(value - mean, 2);

        return sum / values.length;
    }

    private float mean_value(int[] values)
    {
        float sum = 0;

        for (int value : values)
            sum += value;

        return sum / values.length;
    }

    public int compareTo(GridState o)
    {
        int result = 0;

        if (! equals(o))
        {
            result = -1;
            int blocks_diff = Math.abs(blocks - o.blocks);
            int highest_diff = Math.abs(highest_block - o.highest_block);
            int holes_diff = Math.abs(holes - o.holes);

            if (blocks < o.blocks)
            {
                result = blocks_lower_than_compared(o);
            }
            else if (blocks_diff == 0)
            {
                result = blocks_equals_compared(o);
            }
            else
            {
                // result = - blocks_lower_than_compared(o);
                // if (highest_block < o.highest_block && holes_diff < 3)
                //     result = 1;
            }
        }

        return result;
    }

    public boolean equals(Object obj)
    {
        boolean result = false;

        if (obj instanceof GridState)
        {
            GridState state = (GridState) obj;

            result = state.holes == holes
                && state.blocks == blocks
                && state.highest_block == highest_block
                && state.highest_blocks_array.length == highest_blocks_array.length
                && state.holes_array.length == holes_array.length
                && state.blocks_array.length == blocks_array.length
            ;
        }

        return result;
    }

    public int compare(GridState state_1, GridState state_2)
    {
        return state_1.compareTo(state_2);
    }

    public int blocks_lower_than_compared(GridState o)
    {
        int result = -1;
        int blocks_diff = Math.abs(blocks - o.blocks);
        int highest_diff = Math.abs(highest_block - o.highest_block);
        int holes_diff = Math.abs(holes - o.holes);

        if (holes < o.holes)
            result = 1;
        else if (holes_diff < 3)
            result = 1;

        return result;
    }

    public int blocks_equals_compared(GridState o)
    {
        int result = -1;
        int blocks_diff = Math.abs(blocks - o.blocks);
        int highest_diff = Math.abs(highest_block - o.highest_block);
        int holes_diff = Math.abs(holes - o.holes);

        if (holes < o.holes)
        {
                result = 1;
        }
        else if (holes == o.holes)
        {
            if (highest_block < o.highest_block)
                result = 1;
            else if (highest_block == o.highest_block)
            {
                if (highest_blocks_mean < o.highest_blocks_mean)
                {
                    result = 1;
                }
                else if (highest_blocks_mean == o.highest_blocks_mean)
                {
                    if (blocks_std < o.blocks_std)
                        result = 1;
                    else if (blocks_std == o.blocks_std)
                    {
                        if (holes_std < o.holes_std)
                            result = 1;
                        else if (holes_std == o.holes_std)
                            result = 0;
                    }
                }
            }
        }

        return result;
    }
}
