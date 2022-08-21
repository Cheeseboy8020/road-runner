package com.acmerobotics.roadrunner.planning

import com.acmerobotics.roadrunner.geometry.Vector2d
import com.fasterxml.jackson.databind.JsonNode
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

class FieldDStar {
    val iTable = mutableMapOf<Triple<Int, Int, Double>, Double>()
    fun constructInterpolationTable(costNum: Double, costMax: Double, cellCosts: Array<Double>) {
        var cIndex = 0
        while(cIndex< costNum){
            val c = cellCosts[cIndex]
            var bIndex = 0
            while(bIndex < costNum){
                val b = cellCosts[bIndex]
                var f = 1.0
                while(f <= costMax){
                    if(f<b){
                        if(c<=f)
                            iTable[Triple(cIndex, bIndex, f)] = (c* sqrt(c))
                        else{
                            val y = min(f/sqrt(c.pow(2) - b.pow(2)), 1.0)
                            iTable[Triple(cIndex, bIndex, f)] = (c * sqrt(1+y.pow(2))) + (f*(1-y))
                        }
                    }
                    else{
                        if(c<=b)
                            iTable[Triple(cIndex, bIndex, f)] = c.pow(2)
                        else {
                            val x = 1 - min(b / sqrt(c.pow(2) - b.pow(2)), 1.0)
                            iTable[Triple(cIndex, bIndex, f)] = (c * sqrt(1 + ((1 - x).pow(2))) + (b * x))
                        }
                    }
                    f++
                }
                bIndex++
            }
            cIndex++
        }
    }
    fun computeCost(node: Vector2d, nodeA: Vector2d, nodeB: Vector2d): Double {
        val maxCost = 1.0
        val v: Double
        val node1: Vector2d
        val node2: Vector2d
        if (node.distTo(nodeA) == Field.cellSize * sqrt(2.0)) {
            node1 = nodeB
            node2 = nodeA
        }
        else {
            node1 = nodeA
            node2 = nodeB
        }
        //TODO implement obstacles here Lines 28 to 30
        val cIndex = 0
        val bIndex = 0
        val c = 0.0
        val b = 0.0
        v = if (min(c, b) == Double.POSITIVE_INFINITY) {
            Double.POSITIVE_INFINITY
        } else if(g(node1) <= g(node2)) {
            min(c, b) + g(node1)
        } else {
            val f = g(node1) - g(node2)
            if(f > min(c, b)){
                iTable[Triple(cIndex, bIndex, maxCost)]!! + g(node2)
            } else{
                iTable[Triple(cIndex, bIndex, f)]!! + g(node2)
            }
        }
        return v
    }

    //TODO implement g
    fun g(node: Vector2d): Double {
        return 0.0
    }
}