package pe.fernan.kmp.tmdb.ui.grid

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class AdaptiveWithCountColumns(private val minSize: Dp, val onCountColumns: (Int) -> Unit) :
    GridCells {
    init {
        require(minSize > 0.dp) { "Provided min size $minSize should be larger than zero." }
    }

    private fun calculateCellsCrossAxisSizeImpl(
        gridSize: Int,
        slotCount: Int,
        spacing: Int
    ): List<Int> {
        val gridSizeWithoutSpacing = gridSize - spacing * (slotCount - 1)
        val slotSize = gridSizeWithoutSpacing / slotCount
        val remainingPixels = gridSizeWithoutSpacing % slotCount
        return List(slotCount) {
            slotSize + if (it < remainingPixels) 1 else 0
        }
    }

    override fun Density.calculateCrossAxisCellSizes(
        availableSize: Int,
        spacing: Int
    ): List<Int> {
        val count = maxOf((availableSize + spacing) / (minSize.roundToPx() + spacing), 1)
        onCountColumns(count)
        return calculateCellsCrossAxisSizeImpl(availableSize, count, spacing)
    }

    override fun hashCode(): Int {
        return minSize.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return other is GridCells.Adaptive
    }
}