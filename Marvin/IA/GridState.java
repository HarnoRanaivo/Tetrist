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
     * Étendue des hauteurs des colonnes.
     */
    private final int range;

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

    /**
     * Moyenne des plus hauts blocs.
     */
    private final double highest_blocks_mean;

    /**
     * Moyenne du nombre de trous.
     */
    private final double holes_mean;

    /**
     * Moyenne du nombre de blocs.
     */
    private final double blocks_mean;

    /**
     * Médiane des plus hauts blocs.
     */
    private final int highest_blocks_median;

    /**
     * Médiane des trous.
     */
    private final int holes_median;

    /**
     * Médiane des blocs.
     */
    private final int blocks_median;

    /**
     * Ecart-type des plus hauts blocs.
     */
    private final double highest_blocks_std;

    /**
     * Ecart-type du nombre de trous.
     */
    private final double holes_std;

    /**
     * Ecart-type du nombre de blocs.
     */
    private final double blocks_std;

    /**
     * Constructeur de l'état d'une grille d'IA.
     *
     * @param grid
     *          Grille d'IA.
     */
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

    /**
     * Transforme un tableau d'entiers en tableau de doubletants.
     *
     * @param array
     *          Tableau d'entiers.
     * @return (double)array.
     */
    private double[] int_array_to_double(int[] array)
    {
        double[] result = new double[array.length];

        for (int i = 0; i < result.length; i++)
            result[i] = (double) array[i];

        return result;
    }

    /**
     * Calcule d'un écart-type à partir d'un tableau d'entiers.
     *
     * @param values
     *          Tableau d'entiers.
     * @return Ecart-type.
     */
    private double standard_deviation(int[] values)
    {
        return standard_deviation(values, mean_value(values));
    }

    /**
     * Calcule d'un écart-type à partir d'un tableau d'entiers et d'une moyenne.
     *
     * @param values
     *          Tableau d'entiers.
     * @param mean
     *          Moyenne.
     * @return Ecart-type.
     */
    private double standard_deviation(int[] values, double mean)
    {
        double sum = 0;

        for (int value : values)
            sum += Math.pow(value - mean, 2);

        return Math.sqrt(sum / values.length);
    }

    /**
     * Calcule la moyenne d'un tableau d'entiers.
     *
     * @param values
     *          Tableau d'entiers.
     * @return Moyenne.
     */
    private double mean_value(int[] values)
    {
        double sum = 0;

        for (int value : values)
            sum += value;

        return sum / values.length;
    }

    /**
     * Compare l'état d'une grille d'IA à celle courante.
     *
     * @param GridState
     *          Grille à comparer.
     * @return La différence de blocs ou de plus haut bloc.
     */
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

    /**
     * Teste l'égalité entre deux états de grilles d'IA.
     *
     * @param obj
     *          Un état de grille d'IA.
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
     *          Premier état.
     * @param state_2
     *          Deuxième état.
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
     *          Etat de grille d'IA à comparer.
     * @return 1 si l'état comparé a plus de trous, -1 sinon.
     */
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

    /**
     * Vérifie si le nombre de blocs, le nombre de trous,</br>
     * le plus bas des blocs et le plus haut sont les mêmes.
     *
     * @param o
     *          Etat de grille d'IA à comparer.
     * @return <b>1</b> si plus petit, <b>0</b> si égaux, <b>-1</b> sinon.
     */
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

    /**
     * Valeur médiane d'un tableau.
     *
     * @param array
     *          Tableau
     *
     * @return Médiane.
     */
    private int median_value(int[] array)
    {
        int[] copy = Arrays.copyOf(array, array.length);
        Arrays.sort(copy);

        return copy[copy.length/2];
    }

    /**
     * Comparaison de deux tableaux.
     *
     * @param local
     *          Tableau local.
     * @param compared
     *          Tableau à comparer.
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
        {
            if (local_copy[i] < compared_copy[i])
                result = 1;
            else if (local_copy[i] > compared_copy[i])
                result = -1;
        }

        return result;
    }
}
