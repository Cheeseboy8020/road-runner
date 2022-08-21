package com.acmerobotics.roadrunner.planning

import com.acmerobotics.roadrunner.geometry.Vector2d

object Field {
    val cellSize = 0.5

    val cells = mutableListOf<Cell>()

    fun generateField(): MutableList<Cell> {
        for (x in 0..(72/cellSize).toInt())
            for (y in 0 ..(72/cellSize).toInt())
                this.cells.add(Cell(Vector2d(x*cellSize, y*cellSize), 0.0))
        return this.cells
    }
}