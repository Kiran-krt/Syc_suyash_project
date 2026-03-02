package com.syc.dashboard.framework.common.utils

import com.syc.dashboard.query.timesheet.dto.DayDetailsDto
import org.apache.kafka.common.requests.FetchMetadata.log
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.format.ResolverStyle
import java.time.format.TextStyle
import java.time.temporal.TemporalAdjusters
import java.util.*

object DateUtils {

    fun getLastFridayByFormat(pattern: String = MMddyyyy_PATTERN): String {
        val dt = LocalDate.now()
        return dt.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY)).format(getFormatter(pattern))
    }

    private fun getNextWeekOfComingSaturday(): LocalDate {
        val dt = LocalDate.now()
        return dt.with(TemporalAdjusters.next(DayOfWeek.SATURDAY))
    }

    fun getTimesheetEmptyWeeklyDetails(timesheetId: String, timesheetRowId: String): List<DayDetailsDto> {
        val weeklyDetails: MutableList<DayDetailsDto> = mutableListOf()

        val day = getNextWeekOfComingSaturday()

        for (i in 0..6) { // iterating over week, so iterating 7 times
            weeklyDetails.add(
                DayDetailsDto(
                    timesheetId = timesheetId,
                    timesheetRowId = timesheetRowId,
                    day = day.plusDays(i.toLong()).format(MMddyyyy_formatter),
                ),
            )
        }
        return weeklyDetails
    }

    fun getTimesheetEmptyWeeklyDetailsByStartDate(
        weekStartingDate: String,
        timesheetId: String,
        timesheetRowId: String,
    ): List<DayDetailsDto> {
        val weeklyDetails: MutableList<DayDetailsDto> = mutableListOf()

        val day = LocalDate.parse(weekStartingDate, MMddyyyy_formatter)

        for (i in 0..6) { // iterating over week, so iterating 7 times
            weeklyDetails.add(
                DayDetailsDto(
                    timesheetId = timesheetId,
                    timesheetRowId = timesheetRowId,
                    day = day.plusDays(i.toLong()).format(MMddyyyy_formatter),
                ),
            )
        }
        return weeklyDetails
    }

    fun getDateMMddyyyy(date: Date = Date()) =
        date.toInstant().atZone(ZoneId.systemDefault()).format(MMddyyyy_formatter)

    fun getDayOfWeek(date: String, pattern: String = MMddyyyy_PATTERN) =
        LocalDate.parse(date, getFormatter(pattern)).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault())

    // =============== formatter
    private const val MMddyyyy_PATTERN = "MM/dd/yyyy"
    private const val MMddyyyy_PATTERN_STRICT = "MM/dd/uuuu"
    private const val ddMMYYYY_PATTERN = "dd/MM/YYYY"

    private val MMddyyyy_formatter = DateTimeFormatter.ofPattern(MMddyyyy_PATTERN)
    private val MMddyyyy_formatter_strict = DateTimeFormatter.ofPattern(MMddyyyy_PATTERN_STRICT).withResolverStyle(ResolverStyle.STRICT)
    private val ddMMYYYY_formatter = DateTimeFormatter.ofPattern(ddMMYYYY_PATTERN)

    private val dateFormatMapping = mutableMapOf<String, DateTimeFormatter>()
    const val yyyyMMDDTHHmmssSSSZ_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

    init {
        dateFormatMapping[MMddyyyy_PATTERN] = MMddyyyy_formatter
        dateFormatMapping["MM/DD/yyyy"] = MMddyyyy_formatter
        dateFormatMapping[MMddyyyy_PATTERN_STRICT] = MMddyyyy_formatter_strict
        dateFormatMapping[ddMMYYYY_PATTERN] = ddMMYYYY_formatter
        dateFormatMapping["DD/MM/YYYY"] = ddMMYYYY_formatter
    }

    private fun getFormatter(pattern: String = "MM/dd/yyyy"): DateTimeFormatter {
        return dateFormatMapping[pattern]!!
    }

    fun isValidDateByFormat(date: String, pattern: String = MMddyyyy_PATTERN_STRICT): Boolean {
        return try {
            LocalDate.parse(date, getFormatter(pattern))
            true
        } catch (ex: DateTimeParseException) {
            log.warn("Invalid Date Format $date")
            false
        }
    }

    fun isValidExpenseDate(date: String): Boolean {
        return try {
            LocalDate.parse(date, getFormatter())
            true
        } catch (ex: DateTimeParseException) {
            log.warn("Expense date is invalid")
            false
        }
    }

    fun getCurrentYear(): Int {
        return LocalDate.now().year
    }

    fun getCurrentMonth(): Int {
        return LocalDate.now().monthValue
    }

    fun getCurrentDay(): Int {
        return LocalDate.now().dayOfMonth
    }

    fun getDayOfMonth(
        year: Int,
        month: Int,
        day: Int,
        timezone: String = "UTC",
        pattern: String = yyyyMMDDTHHmmssSSSZ_PATTERN,
    ): String {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone(timezone))
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month - 1)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val dateFormat = SimpleDateFormat(pattern)
        dateFormat.timeZone = TimeZone.getTimeZone(timezone)
        return dateFormat.format(calendar.time)
    }
}
