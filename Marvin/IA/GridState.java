package IA;

import java.lang.Math;
import java.util.Arrays;
import java.lang.Comparable;
import java.util.Comparator;

/**
 * Classe de l'état d'une grille d'IA.
 */
public class GridState implements Comparable<GridState>, Comparator<GridState>
{
	/**
	 * Nombre de trous.
	 */
    private final int holes;
    
    /**
	 * Nombre de blocs.
	 */
    private final int blocks;
    
    /**
     * Bloc le plus haut.
     */
    private final int highest_block;
    
    /**
     * Colonne la plus basse.
     */
    private final int smallest_size;

	/**
	 * Tableau de blocs.
	 */
    private final int[] blocks_array;
    
    /**
     * Tableau des plus hauts blocs.
     */
    private final int[] highest_blocks_array;
    
    /**
     * Tableau du nombre de trous.
     */
    private final int[] holes_array;

    private final float highest_blocks_mean;
    private final float holes_mean;
    private final float blocks_mean;

    private final float highest_blocks_std;
    private final float holes_std;
    private final float blocks_std;

	/**
	 * Constructeur de l'état d'une grille d'IA.
	 * 
	 * @param grid
	 * 			Grille d'IA.
	 */
    public GridState(GridIA grid)
    {
        holes = grid.holes();
        blocks = grid.blocks();
        highest_block = grid.highest_block();
        smallest_size = grid.smallest_column_size();

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

	/**
	 * Transforme un tableau d'entiers en tableau de floattants.
	 * 
	 * @param array
	 * 			Tableau d'entiers.
	 * @return (float)array.
	 */
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

	/**
	 * Compare l'état d'une grille d'IA à celle courante.
	 * 
	 * @param GridState
	 * 			Grille à comparer.
	 * @return La différence de blocs ou de plus haut bloc.
	 */
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
                result = - blocks_lower_than_compared(o);
                // if (highest_block < o.highest_block && holes_diff < 3)
                //     result = 1;
            }
        }

        return result;
    }

	/**
	 * Teste l'égalité entre deux états de grilles d'IA.
	 * 
	 * @param obj
	 * 			Un état de grille d'IA.
	 * @return <b>true</b> si les deux états sont égaux, <b>false</b> sinon.
	 */
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

	/**
	 * Compare deux états de grilles d'IA.
	 * 
	 * @param state_1
	 * 			Premier état.
	 * @param state_2
	 * 			Deuxième état.
	 * @return La différence de blocs ou de plus haut bloc.
	 */
    public int compare(GridState state_1, GridState state_2)
    {
        return state_1.compareTo(state_2);
    }

	/**
	 * Vérifie si les le nombre de trous est moins élevé d'un état à un autre.
	 * 
	 * @param o
	 * 			Etat de grille d'IA à comparer.
	 * @return 1 si l'état comparé a plus de trous, -1 sinon.
	 */
    public int blocks_lower_than_compared(GridState o)
    {
        int result = -1;
        int blocks_diff = Math.abs(blocks - o.blocks);
        int highest_diff = Math.abs(highest_block - o.highest_block);
        int holes_diff = Math.abs(holes - o.holes);

        if (holes <= o.holes)
            result = 1;
        // else if (highest_block < 6 && holes_diff < 3)
        //     result = 1;

        return result;
    }

	/**
	 * Vérifie si le nombre de blocs, le nombre de trous,</br>
	 * le plus bas des blocs et le plus haut sont les mêmes.
	 * 
	 * @param o
	 * 			Etat de grille d'IA à comparer.
	 * @return <b>1</b> si plus petit, <b>0</b> si égaux, <b>-1</b> sinon.
	 */
    public int blocks_equals_compared(GridState o)
    {
        int result = -1;
        int blocks_diff = Math.abs(blocks - o.blocks);
        int highest_diff = Math.abs(highest_block - o.highest_block);
        int holes_diff = Math.abs(holes - o.holes);

        if (holes < o.holes)
            result = 1;
        else if (holes == o.holes)
        {
            if (highest_block < o.highest_block)
                result = 1;
            else if (highest_block == o.highest_block)
            {
                int smallest_comparisons = compare_tabs_lower(highest_blocks_array, o.highest_blocks_array);
                if (smallest_comparisons == 1)
                    result = 1;
                else if (smallest_comparisons == 0)
                {
                    if (highest_blocks_mean < o.highest_blocks_mean)
                        result = 1;
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
        }

        return result;
    }

	/**
	 * Comparaison de deux tableaux.
	 * 
	 * @param local
	 * 			Tableau local.
	 * @param compared
	 * 			Tableau à comparer.
	 * @return <b>1</b> si le local est plus petit, <b>0</b> si ils sont égaux, <b>-1</b> sinon.
	 */
    private int compare_tabs_lower(int[] local, int[] compared)
    {
        int result = 0;
        int length = local.length;
        int[] local_copy = Arrays.copyOf(local, length);
        int[] compared_copy = Arrays.copyOf(compared, length);

        Arrays.sort(local_copy);
        Arrays.sort(compared_copy);

        for (int i = 0; result == 0 && i < length; i++)
        // for (int i = length -1; result == 0 && i >= 0; i--)
        {
            if (local_copy[i] < compared_copy[i])
                result = 1;
            else if (local_copy[i] > compared_copy[i])
                result = -1;
        }

        return result;
    }
}
