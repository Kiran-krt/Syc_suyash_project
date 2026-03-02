package com.syc.dashboard.framework.common.utils

// xlsx-js-style formatter is used to render on UI -> https://github.com/gitbrent/xlsx-js-style
object ExcelUtils {

    fun formatCell(
        cellValue: Any,
        dataType: ExcelCell.DataTypeEnum = ExcelCell.DataTypeEnum.STRING,
        comment: String? = null,
        fillFgColor: String = "ffd3d3d3",
        fillPattern: ExcelCell.FillTypeEnum = ExcelCell.FillTypeEnum.NONE,
        fontName: String = "",
        fontColor: String = "000000",
        fontSize: Int = 11,
        fontBold: Boolean = false,
        wrapText: Boolean = false,
    ): ExcelCell =
        ExcelCell(
            v = cellValue,
            dataType = dataType,
            comment = comment,
            fillFgColor = fillFgColor,
            fillPattern = fillPattern,
            fontName = fontName,
            fontColor = fontColor,
            fontSize = fontSize,
            fontBold = fontBold,
            wrapText = wrapText,
        )
}

// for data values check xlsx-js-style formatter
data class ExcelCell(
    val v: Any,
    val t: String = "s",
    private val dataType: DataTypeEnum = DataTypeEnum.STRING,
    private val comment: String?,
    private val fillPattern: FillTypeEnum,
    private val fillFgColor: String,
    private val fontName: String,
    private val fontColor: String,
    private val fontSize: Int,
    private val fontBold: Boolean,
    private val wrapText: Boolean,
) {
    val s: S = S(
        fill = Fill(
            fgColor = Color(color = fillFgColor),
            patternType = fillPattern.value,
        ),
        font = Font(
            name = fontName,
            color = Color(color = fontColor),
            sz = fontSize,
            bold = fontBold,
        ),
        alignment = Alignment(wrapText = wrapText, dataType = dataType),
    )
    val c: MutableList<Comment>? =
        if (comment != null) {
            mutableListOf(Comment(comment = comment))
        } else {
            null
        }

    data class S(
        val fill: Fill,
        val font: Font,
        val alignment: Alignment,
    )

    data class Fill(
        val fgColor: Color,
        val patternType: String,
    )

    data class Alignment(
        val wrapText: Boolean = false,
        private val dataType: DataTypeEnum = DataTypeEnum.STRING,
    ) {
        val horizontal: String = when (dataType) {
            DataTypeEnum.STRING -> "left"
            DataTypeEnum.NUMBER -> "right"
        }
    }

    data class Font(
        val name: String = "",
        val bold: Boolean,
        val color: Color,
        val sz: Int,
    )

    data class Color(
        private val color: String,
    ) {
        val rgb: String = color
    }

    data class Comment(
        private val comment: String?,
    ) {
        val t: String? = comment
    }

    enum class DataTypeEnum {
        STRING, NUMBER
    }

    enum class FillTypeEnum(val value: String) {
        SOLID(value = "solid"), NONE(value = "none")
    }
}
