package pe.fernan.kmp.tmdb.ui.grid

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class FixedSizeWithCountColumns(val size: Dp, val onCountColumns: (Int) -> Unit) : GridCells {
    init {
        require(size > 0.dp) { "Provided size $size should be larger than zero." }
    }

    override fun Density.calculateCrossAxisCellSizes(
        availableSize: Int,
        spacing: Int
    ): List<Int> {
        val cellSize = size.roundToPx()
        return if (cellSize + spacing < availableSize + spacing) {
            val cellCount = (availableSize + spacing) / (cellSize + spacing)
            onCountColumns(cellCount)
            List(cellCount) { cellSize }
        } else {
            onCountColumns(1)
            List(1) { availableSize }
        }
    }

    override fun hashCode(): Int {
        return size.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return other is GridCells.FixedSize
    }
}
