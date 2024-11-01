package com.example.tetrisjet.ui.theme.presentation

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tetrisjet.ui.theme.game.Board
import com.example.tetrisjet.ui.theme.game.BoardSize
import com.example.tetrisjet.ui.theme.game.TetrisGameBlock

fun DrawScope.drawHero(hero: TetrisGameBlock, blockSize: Float) {
    hero.coordinates.forEach {
        drawPeace(it, blockSize, hero.color, alpha = 0.1F)
    }
}

fun DrawScope.drawProjection(hero: TetrisGameBlock, blockSize: Float) {
    hero.coordinates.forEach {
        drawPeace(it, blockSize, hero.color)
    }
}


fun DrawScope.drawBoard(board: Board, blockSize: Float) {
    board.forEachIndexed { y, block ->
        block.forEachIndexed { x, color ->
            if (color == Color.Unspecified) {
                val actualCenter = Offset(
                    x * blockSize + blockSize / 2F,
                    y * blockSize + blockSize / 2F
                )
                drawCircle(
                    color = Color(0xFF30353A),
                    radius = 1.dp.toPx(),
                    center = actualCenter
                )
            } else {
                drawPeace(x to y, blockSize, color)
            }
        }
    }
}


fun DrawScope.drawPeace(
    location: Pair<Int, Int>,
    blockSize: Float,
    blockColor: Color,
    alpha: Float = 1F,
    stroke: Dp = 1.dp
) {
    val actualLocation = Offset(
        location.first * blockSize,
        location.second * blockSize
    )
    val boarderWidth = blockSize / 8
    drawTriangle (
        Color.White.copy(alpha),
        actualLocation,
        actualLocation + Offset(blockSize, 0F),
        actualLocation + Offset(0F, blockSize)
    )
    drawTriangle(
        Color.Black.copy(alpha),
        actualLocation + Offset(blockSize, 0F),
        actualLocation + Offset(blockSize, blockSize),
        actualLocation + Offset(0F, blockSize)
    )
    drawRect(
        color = blockColor.copy(alpha),
        size = Size(blockSize, blockSize),
        style = Stroke(width = stroke.toPx()),
        topLeft = actualLocation
    )
}

fun DrawScope.drawTriangle(color: Color, point1: Offset, point2: Offset, point3: Offset) {
    val path = Path().apply {
        moveTo(point1.x, point1.y)
        lineTo(point2.x, point2.y)
        lineTo(point3.x, point3.y)
        close()
    }
    drawPath(path, color)
}


fun DrawScope.drawBoarderBackground(boardSize: BoardSize) {
    val ratio = boardSize.second / boardSize.first
    drawRect(
        color = Color.Black,
        topLeft = Offset.Zero - Offset(2.dp.toPx(), 2.dp.toPx()),
        size = Size(
            size.height / ratio + 4.dp.toPx(),
            size.height + 4.dp.toPx()
        ),
        style = Stroke(width = 2.dp.toPx())
    )
}