package com.acmerobotics.roadrunner.planning

import com.acmerobotics.roadrunner.geometry.Vector2d

data class Cell(val center: Vector2d, val cost: Double) {
    override fun toString() = center.toString()
}