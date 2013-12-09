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
    private final int smallest_size;
    private final int range;

    private final int[] blocks_array;
    private final int[] highest_blocks_array;
    private final int[] holes_array;

    private final double highest_blocks_mean;
    private final double holes_mean;
    private final double blocks_mean;

    private final int highest_blocks_median;
    private final int holes_median;
    private final int blocks_median;

    private final double highest_blocks_std;
    private final double holes_std;
    private final double blocks_std;

    public GridState(GridIA grid)
    {
        holes = grid.holes();
        blocks = grid.blocks();
        highest_block = grid.highest_block();
        smallest_size = grid.smallest_column_size();
        range = highest_block - smallest_size;

        blocks_array = grid.blocks_array();
        highest_blocks_array = grid.highest_blocks_array();
        holes_array = grid.holes_array();

        highest_blocks_mean = mean_value(highest_blocks_array);
        holes_mean = mean_value(holes_array);
        blocks_mean = mean_value(blocks_array);

        highest_blocks_median = median_value(highest_blocks_array);
        holes_median = median_value(holes_array);
        blocks_median = median_value(blocks_array);

        highest_blocks_std = standard_deviation(highest_blocks_array, highest_blocks_mean);
        holes_std = standard_deviation(holes_array, holes_mean);
        blocks_std = standard_deviation(blocks_array, blocks_mean);
    }

    private double[] int_array_to_double(int[] array)
    {
        double[] result = new double[array.length];

        for (int i = 0; i < result.length; i++)
            result[i] = (double) array[i];

        return result;
    }

    private double standard_deviation(int[] values)
    {
        return standard_deviation(values, mean_value(values));
    }

    private double standard_deviation(int[] values, double mean)
    {
        double sum = 0;

        for (int value : values)
            sum += Math.pow(value - mean, 2);

        return Math.sqrt(sum / values.length);
    }

    private double mean_value(int[] values)
    {
        double sum = 0;

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
            if (blocks < o.blocks)
                result = blocks_lower_than_compared(o);
            else if (blocks == o.blocks)
                result = blocks_equals_compared(o);
            else
                result = (-1) * blocks_lower_than_compared(o);
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
        int holes_diff = Math.abs(holes - o.holes);

        if (holes <= o.holes)
            result = 1;
        else if (highest_block > 8 && holes_diff < 6);
            result = 1;

        return result;
    }

    public int blocks_equals_compared(GridState o)
    {
        int result = -1;
        int highest_diff = Math.abs(highest_block - o.highest_block);
        int holes_diff = Math.abs(holes - o.holes);

        if (holes < o.holes)
        {
            if (highest_block <= o.highest_block + 1)
                result = 1;
        }
        else if (holes == o.holes)
        {
            if (highest_block < o.highest_block)
                result = 1;
            else if (highest_block == o.highest_block)
            {
                if (highest_blocks_median < o.highest_blocks_median)
                    result = 1;
                else if (range < o.range)
                    result = 1;
                else if (range == o.range)
                {
                    if (highest_blocks_std < o.highest_blocks_std)
                        result = 1;
                }
            }
        }

        return result;
    }

    private int median_value(int[] array)
    {
        int[] copy = Arrays.copyOf(array, array.length);
        Arrays.sort(copy);

        return copy[copy.length/2];
    }

    private int compare_tabs_lower(int[] local, int[] compared)
    {
        int result = 0;
        int length = local.length;
        int[] local_copy = Arrays.copyOf(local, length);
        int[] compared_copy = Arrays.copyOf(compared, length);

        Arrays.sort(local_copy);
        Arrays.sort(compared_copy);

        for (int i = 0; result == 0 && i < length; i++)
        {
            if (local_copy[i] < compared_copy[i])
                result = 1;
            else if (local_copy[i] > compared_copy[i])
                result = -1;
        }

        return result;
    }
}
