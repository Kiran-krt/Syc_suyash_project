package com.syc.dashboard

import com.syc.dashboard.command.admin.api.commands.*
import com.syc.dashboard.command.document.api.commands.DocumentUpdateStatusCommand
import com.syc.dashboard.command.document.api.commands.DocumentUploadCommand
import com.syc.dashboard.command.employee.api.commands.*
import com.syc.dashboard.command.expensereport.api.commands.*
import com.syc.dashboard.command.jobcode.api.commands.*
import com.syc.dashboard.command.notification.email.api.commands.SendEmailNotificationCommand
import com.syc.dashboard.command.notification.inapp.api.commands.InAppNotificationUpdateStatusCommand
import com.syc.dashboard.command.notification.inapp.api.commands.SendInAppNotificationCommand
import com.syc.dashboard.command.notification.mobile.api.commands.RestoreMobileNotificationReadDbCommand
import com.syc.dashboard.command.notification.mobile.api.commands.SendMobileNotificationCommand
import com.syc.dashboard.command.project.api.commands.*
import com.syc.dashboard.command.projectreport.api.commands.*
import com.syc.dashboard.command.settings.api.commands.*
import com.syc.dashboard.command.systemconfig.api.commands.RegisterSystemConfigCommand
import com.syc.dashboard.command.systemconfig.api.commands.SystemConfigUpdateAllFieldsCommand
import com.syc.dashboard.command.systemconfig.api.commands.SystemConfigUpdateLogoCommand
import com.syc.dashboard.command.timesheet.api.commands.*
import com.syc.dashboard.command.tvhgConfig.api.commands.*
import com.syc.dashboard.command.tvhginput.api.commands.*
import com.syc.dashboard.command.vehiclelog.api.commands.RegisterVehicleLogCommand
import com.syc.dashboard.command.vehiclelog.api.commands.VehicleLogUpdateAllFieldsCommand
import com.syc.dashboard.command.vehiclelog.api.commands.VehicleLogUpdateStatusByIdCommand
import com.syc.dashboard.framework.core.commands.CommandHandler
import com.syc.dashboard.framework.core.infrastructure.CommandDispatcher
import com.syc.dashboard.framework.core.infrastructure.QueryDispatcher
import com.syc.dashboard.framework.core.listener.PropertyFilePatternRegisteringListener
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.query.admin.api.queries.FindAdminByEmailQuery
import com.syc.dashboard.query.admin.api.queries.FindAdminByIdQuery
import com.syc.dashboard.query.admin.api.queries.SearchAllUsersByFilterQuery
import com.syc.dashboard.query.document.api.queries.DocumentThumbnailByIdQuery
import com.syc.dashboard.query.document.api.queries.DocumentViewByIdQuery
import com.syc.dashboard.query.document.api.queries.FindDocumentByIdQuery
import com.syc.dashboard.query.employee.api.queries.*
import com.syc.dashboard.query.expensereport.api.queries.*
import com.syc.dashboard.query.jobcode.api.queries.*
import com.syc.dashboard.query.notification.email.api.queries.FindEmailNotificationCountStatusQuery
import com.syc.dashboard.query.notification.email.api.queries.PageableFindEmailNotificationByUserIdAndStatusQuery
import com.syc.dashboard.query.notification.inapp.api.queries.FindInAppNotificationCountStatusQuery
import com.syc.dashboard.query.notification.inapp.api.queries.PageableFindInAppNotificationByUserIdAndStatusQuery
import com.syc.dashboard.query.notification.mobile.api.queries.FindMobileNotificationCountStatusQuery
import com.syc.dashboard.query.notification.mobile.api.queries.PageableFindMobileNotificationByUserIdAndStatusQuery
import com.syc.dashboard.query.project.api.queries.FindAllActiveProjectQuery
import com.syc.dashboard.query.project.api.queries.FindAllProjectQuery
import com.syc.dashboard.query.project.api.queries.FindProjectByIdQuery
import com.syc.dashboard.query.project.api.queries.PageableSearchAllProjectByFilterQuery
import com.syc.dashboard.query.projectreport.api.queries.*
import com.syc.dashboard.query.settings.api.queries.FindAllActiveVehicleInfoQuery
import com.syc.dashboard.query.settings.api.queries.FindExpenseTypeByTenantIdQuery
import com.syc.dashboard.query.settings.api.queries.FindPayrollItemByIdQuery
import com.syc.dashboard.query.settings.api.queries.FindPayrollItemByTenantIdQuery
import com.syc.dashboard.query.settings.api.queries.FindSettingsByTenantIdQuery
import com.syc.dashboard.query.settings.api.queries.FindSettingsMileageRateByTenantIdQuery
import com.syc.dashboard.query.settings.api.queries.FindSettingsYearlyQuarterByTenantIdQuery
import com.syc.dashboard.query.settings.api.queries.FindVehicleInfoByIdQuery
import com.syc.dashboard.query.settings.api.queries.PageableSearchAllVehicleInfoByFilterQuery
import com.syc.dashboard.query.systemconfig.api.query.FindSystemConfigQuery
import com.syc.dashboard.query.systemconfig.api.query.FindUISystemConfigQuery
import com.syc.dashboard.query.timesheet.api.queries.*
import com.syc.dashboard.query.timesheet.api.queries.FindTimesheetByIdQuery
import com.syc.dashboard.query.timesheet.api.queries.FindTimesheetByStatusQuery
import com.syc.dashboard.query.timesheet.api.queries.FindTimesheetForManagerToReviewQuery
import com.syc.dashboard.query.timesheet.api.queries.FindTimesheetSubmittedForApprovalForAdminQuery
import com.syc.dashboard.query.tvhgConfig.api.queries.FindDesignStormByIdQuery
import com.syc.dashboard.query.tvhgConfig.api.queries.FindMdStandardNumberByIdQuery
import com.syc.dashboard.query.tvhgConfig.api.queries.FindTvhgConfigQuery
import com.syc.dashboard.query.tvhgConfig.api.queries.FindUnitsByIdQuery
import com.syc.dashboard.query.tvhginput.api.queries.*
import com.syc.dashboard.query.vehiclelog.api.queries.FindVehicleLogByIdQuery
import com.syc.dashboard.query.vehiclelog.api.queries.FindVehicleLogByTenantIdQuery
import com.syc.dashboard.query.vehiclelog.api.queries.FindVehicleLogByVehicleIdQuery
import com.syc.dashboard.query.vehiclelog.api.queries.FindVehicleLogForEmployeeByIdQuery
import com.syc.dashboard.query.vehiclelog.api.queries.PageableSearchAllVehicleLogByFilterQuery
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.reactive.config.EnableWebFlux

@ConfigurationPropertiesScan(value = ["com.syc.dashboard.*"])
@ComponentScan("com.syc.dashboard.*")
@EnableWebFlux
@SpringBootApplication
class SycDashboardApplication @Autowired constructor(
    private val commandDispatcher: CommandDispatcher,
    private val queryDispatcher: QueryDispatcher,

    // command handler
    @Qualifier("adminCommandHandler")
    private val adminCommandHandler: CommandHandler,

    @Qualifier("employeeCommandHandler")
    private val employeeCommandHandler: CommandHandler,

    @Qualifier("timesheetCommandHandler")
    private val timesheetCommandHandler: CommandHandler,

    @Qualifier("jobCodeCommandHandler")
    private val jobCodeCommandHandler: CommandHandler,

    @Qualifier("settingsCommandHandler")
    private val settingsCommandHandler: CommandHandler,

    @Qualifier("inAppNotificationCommandHandler")
    private val inAppNotificationCommandHandler: CommandHandler,

    @Qualifier("emailNotificationCommandHandler")
    private val emailNotificationCommandHandler: CommandHandler,

    @Qualifier("mobileNotificationCommandHandler")
    private val mobileNotificationCommandHandler: CommandHandler,

    @Qualifier("expenseReportCommandHandler")
    private val expenseReportCommandHandler: CommandHandler,

    @Qualifier("documentCommandHandler")
    private val documentCommandHandler: CommandHandler,

    @Qualifier("projectCommandHandler")
    private val projectCommandHandler: CommandHandler,

    @Qualifier("projectReportCommandHandler")
    private val projectReportCommandHandler: CommandHandler,

    @Qualifier("systemConfigCommandHandler")
    private val systemConfigCommandHandler: CommandHandler,

    @Qualifier("tvhgInputCommandHandler")
    private val tvhgInputCommandHandler: CommandHandler,

    @Qualifier("tvhgConfigCommandHandler")
    private val tvhgConfigCommandHandler: CommandHandler,

    @Qualifier("vehicleLogCommandHandler")
    private val vehicleLogCommandHandler: CommandHandler,

    // query handler
    @Qualifier("adminQueryHandler")
    private val adminQueryHandler: QueryHandler,

    @Qualifier("employeeQueryHandler")
    private val employeeQueryHandler: QueryHandler,

    @Qualifier("timesheetQueryHandler")
    private val timesheetQueryHandler: QueryHandler,

    @Qualifier("jobCodeQueryHandler")
    private val jobCodeQueryHandler: QueryHandler,

    @Qualifier("settingsQueryHandler")
    private val settingsQueryHandler: QueryHandler,

    @Qualifier("inAppNotificationQueryHandler")
    private val inAppNotificationQueryHandler: QueryHandler,

    @Qualifier("emailNotificationQueryHandler")
    private val emailNotificationQueryHandler: QueryHandler,

    @Qualifier("mobileNotificationQueryHandler")
    private val mobileNotificationQueryHandler: QueryHandler,

    @Qualifier("expenseReportQueryHandler")
    private val expenseReportQueryHandler: QueryHandler,

    @Qualifier("documentQueryHandler")
    private val documentQueryHandler: QueryHandler,

    @Qualifier("projectQueryHandler")
    private val projectQueryHandler: QueryHandler,

    @Qualifier("projectReportQueryHandler")
    private val projectReportQueryHandler: QueryHandler,

    @Qualifier("systemConfigQueryHandler")
    private val systemConfigQueryHandler: QueryHandler,

    @Qualifier("tvhgInputQueryHandler")
    private val tvhgInputQueryHandler: QueryHandler,

    @Qualifier("tvhgConfigQueryHandler")
    private val tvhgConfigQueryHandler: QueryHandler,

    @Qualifier("vehicleLogQueryHandler")
    private val vehicleLogQueryHandler: QueryHandler,
) {

    @PostConstruct
    fun registerHandlers() {
        // ---------- [START] COMMAND ----------
        // admin
        commandDispatcher.registerHandler(RestoreAdminReadDbCommand::class.java, adminCommandHandler::handle)
        commandDispatcher.registerHandler(RegisterAdminCommand::class.java, adminCommandHandler::handle)
        commandDispatcher.registerHandler(AdminUpdatePasswordCommand::class.java, adminCommandHandler::handle)
        commandDispatcher.registerHandler(AdminUpdateFullNameCommand::class.java, adminCommandHandler::handle)
        commandDispatcher.registerHandler(AdminLogInCommand::class.java, adminCommandHandler::handle)
        commandDispatcher.registerHandler(AdminLogOutCommand::class.java, adminCommandHandler::handle)
        commandDispatcher.registerHandler(AdminProfileUpdateCommand::class.java, adminCommandHandler::handle)
        commandDispatcher.registerHandler(AdminUpdateEmailCommand::class.java, adminCommandHandler::handle)
        commandDispatcher.registerHandler(AdminUpdateStatusCommand::class.java, adminCommandHandler::handle)
        commandDispatcher.registerHandler(AdminUpdateMobileDeviceInfoCommand::class.java, adminCommandHandler::handle)
        commandDispatcher.registerHandler(AdminForgetPasswordCommand::class.java, adminCommandHandler::handle)

        // employee
        commandDispatcher.registerHandler(RestoreEmployeeReadDbCommand::class.java, employeeCommandHandler::handle)
        commandDispatcher.registerHandler(RegisterEmployeeCommand::class.java, employeeCommandHandler::handle)
        commandDispatcher.registerHandler(EmployeeUpdateAllFieldsCommand::class.java, employeeCommandHandler::handle)
        commandDispatcher.registerHandler(EmployeeResetPasswordCommand::class.java, employeeCommandHandler::handle)
        commandDispatcher.registerHandler(EmployeeUpdatePasswordCommand::class.java, employeeCommandHandler::handle)
        commandDispatcher.registerHandler(EmployeeLogInCommand::class.java, employeeCommandHandler::handle)
        commandDispatcher.registerHandler(EmployeeLogOutCommand::class.java, employeeCommandHandler::handle)
        commandDispatcher.registerHandler(EmployeeUpdateRoleCommand::class.java, employeeCommandHandler::handle)
        commandDispatcher.registerHandler(EmployeeUpdateProfileCommand::class.java, employeeCommandHandler::handle)
        commandDispatcher.registerHandler(EmployeeUpdateEmailCommand::class.java, employeeCommandHandler::handle)
        commandDispatcher.registerHandler(EmployeeUpdateStatusCommand::class.java, employeeCommandHandler::handle)
        commandDispatcher.registerHandler(EmployeeUpdateMobileDeviceInfoCommand::class.java, employeeCommandHandler::handle)
        commandDispatcher.registerHandler(EmployeeForgotPasswordCommand::class.java, employeeCommandHandler::handle)

        // timesheet
        commandDispatcher.registerHandler(RestoreTimesheetReadDbCommand::class.java, timesheetCommandHandler::handle)
        commandDispatcher.registerHandler(RegisterTimesheetCommand::class.java, timesheetCommandHandler::handle)
        commandDispatcher.registerHandler(TimesheetUpdateDayDetailsCommand::class.java, timesheetCommandHandler::handle)
        commandDispatcher.registerHandler(TimesheetUpdateAdminCommentsByIdCommand::class.java, timesheetCommandHandler::handle)
        commandDispatcher.registerHandler(TimesheetUpdateEmployeeCommentsByIdCommand::class.java, timesheetCommandHandler::handle)
        commandDispatcher.registerHandler(TimesheetUpdateManagerCommentsByIdCommand::class.java, timesheetCommandHandler::handle)
        commandDispatcher.registerHandler(TimesheetUpdateStatusCommand::class.java, timesheetCommandHandler::handle)
        commandDispatcher.registerHandler(RegisterTimesheetWithStartDateCommand::class.java, timesheetCommandHandler::handle)
        commandDispatcher.registerHandler(TimesheetUpdateWithTimesheetRowsCommand::class.java, timesheetCommandHandler::handle)
        commandDispatcher.registerHandler(DeleteTimesheetRowCommand::class.java, timesheetCommandHandler::handle)
        commandDispatcher.registerHandler(TimesheetUpdateApproverCommand::class.java, timesheetCommandHandler::handle)
        commandDispatcher.registerHandler(TimesheetUpdateWeekStartingDateCommand::class.java, timesheetCommandHandler::handle)
        commandDispatcher.registerHandler(TimesheetUpdateWeekEndingDateCommand::class.java, timesheetCommandHandler::handle)

        // jobcode
        commandDispatcher.registerHandler(RegisterJobCodeCommand::class.java, jobCodeCommandHandler::handle)
        commandDispatcher.registerHandler(JobCodeUpdateStatusCommand::class.java, jobCodeCommandHandler::handle)
        commandDispatcher.registerHandler(RestoreJobCodeReadDbCommand::class.java, jobCodeCommandHandler::handle)
        commandDispatcher.registerHandler(AddCostCodeCommand::class.java, jobCodeCommandHandler::handle)
        commandDispatcher.registerHandler(UpdateCostCodeCommand::class.java, jobCodeCommandHandler::handle)
        commandDispatcher.registerHandler(UpdateJobCodeCommand::class.java, jobCodeCommandHandler::handle)

        // settings
        commandDispatcher.registerHandler(RegisterSettingsCommand::class.java, settingsCommandHandler::handle)
        commandDispatcher.registerHandler(SettingsUpdateDateFormatCommand::class.java, settingsCommandHandler::handle)
        commandDispatcher.registerHandler(SettingsUpdateTimeZoneCommand::class.java, settingsCommandHandler::handle)
        commandDispatcher.registerHandler(SettingsUpdateTimesheetDelayInHoursCommand::class.java, settingsCommandHandler::handle)
        commandDispatcher.registerHandler(DeleteSettingsCommand::class.java, settingsCommandHandler::handle)
        commandDispatcher.registerHandler(SettingsUpdateStatusByIdCommand::class.java, settingsCommandHandler::handle)
        commandDispatcher.registerHandler(SettingsUpdateMileageRateCommand::class.java, settingsCommandHandler::handle)
        commandDispatcher.registerHandler(AddExpenseTypeCommand::class.java, settingsCommandHandler::handle)
        commandDispatcher.registerHandler(ExpenseTypeUpdateAllFieldsCommand::class.java, settingsCommandHandler::handle)
        commandDispatcher.registerHandler(AddPayrollItemCommand::class.java, settingsCommandHandler::handle)
        commandDispatcher.registerHandler(PayrollItemUpdateAllFieldsCommand::class.java, settingsCommandHandler::handle)
        commandDispatcher.registerHandler(SettingsUpdateYearlyQuarterCommand::class.java, settingsCommandHandler::handle)
        commandDispatcher.registerHandler(RegisterVehicleInfoCommand::class.java, settingsCommandHandler::handle)
        commandDispatcher.registerHandler(VehicleInfoUpdateAllFieldsCommand::class.java, settingsCommandHandler::handle)

        // inapp notification
        commandDispatcher.registerHandler(SendInAppNotificationCommand::class.java, inAppNotificationCommandHandler::handle)
        commandDispatcher.registerHandler(InAppNotificationUpdateStatusCommand::class.java, inAppNotificationCommandHandler::handle)

        // email notification
        commandDispatcher.registerHandler(SendEmailNotificationCommand::class.java, emailNotificationCommandHandler::handle)

        // mobile notification
        commandDispatcher.registerHandler(SendMobileNotificationCommand::class.java, mobileNotificationCommandHandler::handle)
        commandDispatcher.registerHandler(RestoreMobileNotificationReadDbCommand::class.java, mobileNotificationCommandHandler::handle)

        // expense report
        commandDispatcher.registerHandler(AddExpenseReportCommand::class.java, expenseReportCommandHandler::handle)
        commandDispatcher.registerHandler(AddExpenseRowForEmployeeCommand::class.java, expenseReportCommandHandler::handle)
        commandDispatcher.registerHandler(DeleteExpenseReportRowCommand::class.java, expenseReportCommandHandler::handle)
        commandDispatcher.registerHandler(DeleteExpenseReportCommand::class.java, expenseReportCommandHandler::handle)
        commandDispatcher.registerHandler(UpdateExpenseReportStatusCommand::class.java, expenseReportCommandHandler::handle)
        commandDispatcher.registerHandler(ReviewExpenseReportByAdminCommand::class.java, expenseReportCommandHandler::handle)
        commandDispatcher.registerHandler(SubmitExpenseReportByEmployeeCommand::class.java, expenseReportCommandHandler::handle)
        commandDispatcher.registerHandler(ReviewExpenseReportBySupervisorCommand::class.java, expenseReportCommandHandler::handle)
        commandDispatcher.registerHandler(ExpenseReportUpdateAllFieldsCommand::class.java, expenseReportCommandHandler::handle)
        commandDispatcher.registerHandler(AddExpenseRowForSuyashCommand::class.java, expenseReportCommandHandler::handle)
        commandDispatcher.registerHandler(UpdateExpenseRowsForEmployeeCommand::class.java, expenseReportCommandHandler::handle)
        commandDispatcher.registerHandler(UpdateExpenseRowsForSuyashCommand::class.java, expenseReportCommandHandler::handle)

        // document
        commandDispatcher.registerHandler(DocumentUploadCommand::class.java, documentCommandHandler::handle)
        commandDispatcher.registerHandler(DocumentUpdateStatusCommand::class.java, documentCommandHandler::handle)

        // project
        commandDispatcher.registerHandler(RegisterProjectCommand::class.java, projectCommandHandler::handle)
        commandDispatcher.registerHandler(ProjectUpdateStatusCommand::class.java, projectCommandHandler::handle)
        commandDispatcher.registerHandler(ProjectUpdateAllFieldsCommand::class.java, projectCommandHandler::handle)
        commandDispatcher.registerHandler(AddJobCodeCommand::class.java, projectCommandHandler::handle)
        commandDispatcher.registerHandler(UpdateJobCodeByProjectIdCommand::class.java, projectCommandHandler::handle)

        // project report
        commandDispatcher.registerHandler(RegisterProjectReportCommand::class.java, projectReportCommandHandler::handle)
        commandDispatcher.registerHandler(ProjectReportUpdateStatusCommand::class.java, projectReportCommandHandler::handle)
        commandDispatcher.registerHandler(ProjectReportUpdateFieldCommand::class.java, projectReportCommandHandler::handle)
        commandDispatcher.registerHandler(OutfallPhotoUploadCommand::class.java, projectReportCommandHandler::handle)
        commandDispatcher.registerHandler(OutfallPhotoUpdateStatusCommand::class.java, projectReportCommandHandler::handle)
        commandDispatcher.registerHandler(OutfallPhotoUpdateAllFieldsCommand::class.java, projectReportCommandHandler::handle)
        commandDispatcher.registerHandler(AddAppendixCommand::class.java, projectReportCommandHandler::handle)
        commandDispatcher.registerHandler(AppendixUpdateAllFieldsCommand::class.java, projectReportCommandHandler::handle)
        commandDispatcher.registerHandler(AppendixUpdateStatusCommand::class.java, projectReportCommandHandler::handle)

        // system config
        commandDispatcher.registerHandler(RegisterSystemConfigCommand::class.java, systemConfigCommandHandler::handle)
        commandDispatcher.registerHandler(SystemConfigUpdateAllFieldsCommand::class.java, systemConfigCommandHandler::handle)
        commandDispatcher.registerHandler(SystemConfigUpdateLogoCommand::class.java, systemConfigCommandHandler::handle)

        // tvhgInput
        commandDispatcher.registerHandler(RegisterTvhgInputCommand::class.java, tvhgInputCommandHandler::handle)
        commandDispatcher.registerHandler(TvhgInputUpdateStatusCommand::class.java, tvhgInputCommandHandler::handle)
        commandDispatcher.registerHandler(TvhgInputUpdateAllFieldsCommand::class.java, tvhgInputCommandHandler::handle)
        commandDispatcher.registerHandler(AddProjectInformationInTvhgInputCommand::class.java, tvhgInputCommandHandler::handle)
        commandDispatcher.registerHandler(AddStructureInformationCommand::class.java, tvhgInputCommandHandler::handle)
        commandDispatcher.registerHandler(UpdateStructureInformationAllFieldCommand::class.java, tvhgInputCommandHandler::handle)
        commandDispatcher.registerHandler(UpdateProjectInformationAllFieldCommand::class.java, tvhgInputCommandHandler::handle)
        commandDispatcher.registerHandler(AddHydrologicInformationCommand::class.java, tvhgInputCommandHandler::handle)
        commandDispatcher.registerHandler(UpdateHydrologicInformationAllFieldCommand::class.java, tvhgInputCommandHandler::handle)
        commandDispatcher.registerHandler(AddPipeInformationCommand::class.java, tvhgInputCommandHandler::handle)
        commandDispatcher.registerHandler(AddStructureDrawingDataCommand::class.java, tvhgInputCommandHandler::handle)
        commandDispatcher.registerHandler(UpdatePipeInformationAllFieldCommand::class.java, tvhgInputCommandHandler::handle)
        commandDispatcher.registerHandler(UpdateStructureDrawingDataAllFieldsCommand::class.java, tvhgInputCommandHandler::handle)
        commandDispatcher.registerHandler(AddInletControlParameterCommand::class.java, tvhgInputCommandHandler::handle)
        commandDispatcher.registerHandler(AddOutletDrawingInformationCommand::class.java, tvhgInputCommandHandler::handle)
        commandDispatcher.registerHandler(AddPipeDrawingInformationCommand::class.java, tvhgInputCommandHandler::handle)
        commandDispatcher.registerHandler(UpdateFlowPathDrawingInformationAllFieldsCommand::class.java, tvhgInputCommandHandler::handle)
        commandDispatcher.registerHandler(UpdatePipeDrawingInformationAllFieldsCommand::class.java, tvhgInputCommandHandler::handle)
        commandDispatcher.registerHandler(UpdateInletControlParameterAllFieldsCommand::class.java, tvhgInputCommandHandler::handle)
        commandDispatcher.registerHandler(UpdateOutletDrawingInformationAllFieldCommand::class.java, tvhgInputCommandHandler::handle)
        commandDispatcher.registerHandler(AddFlowPathDrawingInformationCommand::class.java, tvhgInputCommandHandler::handle)
        commandDispatcher.registerHandler(UpdateOutletDrawingInformationElevationDataAllFieldsCommand::class.java, tvhgInputCommandHandler::handle)

        // tvhgInput Config
        commandDispatcher.registerHandler(RegisterTvhgConfigCommand::class.java, tvhgConfigCommandHandler::handle)
        commandDispatcher.registerHandler(AddUnitsInTvhgConfigCommand::class.java, tvhgConfigCommandHandler::handle)
        commandDispatcher.registerHandler(AddDesignStormTvhgConfigCommand::class.java, tvhgConfigCommandHandler::handle)
        commandDispatcher.registerHandler(AddStructureTypeInTvhgConfigCommand::class.java, tvhgConfigCommandHandler::handle)
        commandDispatcher.registerHandler(AddInletControlDataTvhgConfigCommand::class.java, tvhgConfigCommandHandler::handle)
        commandDispatcher.registerHandler(AddOutletStructureTypeTvhgConfigCommand::class.java, tvhgConfigCommandHandler::handle)
        commandDispatcher.registerHandler(AddPipeMaterialTvhgConfigCommand::class.java, tvhgConfigCommandHandler::handle)
        commandDispatcher.registerHandler(AddPipeTypeTvhgConfigCommand::class.java, tvhgConfigCommandHandler::handle)
        commandDispatcher.registerHandler(UpdateDesignStormAllFieldsCommand::class.java, tvhgConfigCommandHandler::handle)
        commandDispatcher.registerHandler(UpdateInletControlDataAllFieldsCommand::class.java, tvhgConfigCommandHandler::handle)
        commandDispatcher.registerHandler(UpdatePipeMaterialAllFieldsCommand::class.java, tvhgConfigCommandHandler::handle)
        commandDispatcher.registerHandler(UpdatePipeTypeAllFieldsCommand::class.java, tvhgConfigCommandHandler::handle)
        commandDispatcher.registerHandler(UpdateStructureTypeAllFieldsCommand::class.java, tvhgConfigCommandHandler::handle)
        commandDispatcher.registerHandler(UpdateUnitsAllFieldsCommand::class.java, tvhgConfigCommandHandler::handle)
        commandDispatcher.registerHandler(UpdateOutletStructureTypeAllFieldsCommand::class.java, tvhgConfigCommandHandler::handle)
        commandDispatcher.registerHandler(AddMdStandardNumberTvhgConfigCommand::class.java, tvhgConfigCommandHandler::handle)
        commandDispatcher.registerHandler(UpdateMdStandardNumberAllFieldsCommand::class.java, tvhgConfigCommandHandler::handle)

        // vehicle log
        commandDispatcher.registerHandler(RegisterVehicleLogCommand::class.java, vehicleLogCommandHandler::handle)
        commandDispatcher.registerHandler(VehicleLogUpdateAllFieldsCommand::class.java, vehicleLogCommandHandler::handle)
        commandDispatcher.registerHandler(VehicleLogUpdateStatusByIdCommand::class.java, vehicleLogCommandHandler::handle)

        // ---------- [END] COMMAND ----------

        // ========== [START] QUERY ==========
        // admin
        queryDispatcher.registerHandler(FindAdminByIdQuery::class.java, adminQueryHandler::handle)
        queryDispatcher.registerHandler(FindAdminByEmailQuery::class.java, adminQueryHandler::handle)
        queryDispatcher.registerHandler(SearchAllUsersByFilterQuery::class.java, adminQueryHandler::handle)

        // employee
        queryDispatcher.registerHandler(FindEmployeeByEmailQuery::class.java, employeeQueryHandler::handle)
        queryDispatcher.registerHandler(FindEmployeeByIdQuery::class.java, employeeQueryHandler::handle)
        queryDispatcher.registerHandler(SearchEmployeeByFilterQuery::class.java, employeeQueryHandler::handle)
        queryDispatcher.registerHandler(FindEmployeeByStatusQuery::class.java, employeeQueryHandler::handle)
        queryDispatcher.registerHandler(SearchEmployeeByIdQuery::class.java, employeeQueryHandler::handle)
        queryDispatcher.registerHandler(SearchEmployeeByStatusQuery::class.java, employeeQueryHandler::handle)
        queryDispatcher.registerHandler(SearchEmployeeByIdAndStatusQuery::class.java, employeeQueryHandler::handle)
        queryDispatcher.registerHandler(SearchAdminAndManagerByFilterQuery::class.java, employeeQueryHandler::handle)
        queryDispatcher.registerHandler(EmployeeSearchByFilterQuery::class.java, employeeQueryHandler::handle)
        queryDispatcher.registerHandler(FindEmployeeByUserIdQuery::class.java, employeeQueryHandler::handle)
        queryDispatcher.registerHandler(FindEmployeeCountByFilterQuery::class.java, employeeQueryHandler::handle)
        queryDispatcher.registerHandler(SearchAllSupervisorListQuery::class.java, employeeQueryHandler::handle)
        queryDispatcher.registerHandler(SearchAllActiveSupervisorListQuery::class.java, employeeQueryHandler::handle)
        queryDispatcher.registerHandler(SearchAllEmployeeBySupervisorIdQuery::class.java, employeeQueryHandler::handle)
        queryDispatcher.registerHandler(FindEmployeeVacationsByIdQuery::class.java, employeeQueryHandler::handle)

        // timesheet
        queryDispatcher.registerHandler(FindTimesheetByIdQuery::class.java, timesheetQueryHandler::handle)
        queryDispatcher.registerHandler(FindTimesheetByStatusQuery::class.java, timesheetQueryHandler::handle)
        queryDispatcher.registerHandler(FindTimesheetByStatusListQuery::class.java, timesheetQueryHandler::handle)
        queryDispatcher.registerHandler(FindTimesheetSubmittedForApprovalForAdminQuery::class.java, timesheetQueryHandler::handle)
        queryDispatcher.registerHandler(FindTimesheetForManagerToReviewQuery::class.java, timesheetQueryHandler::handle)
        queryDispatcher.registerHandler(FindTimesheetForEmployeeToReviewQuery::class.java, timesheetQueryHandler::handle)
        queryDispatcher.registerHandler(SearchTimesheetForEmployeeQuery::class.java, timesheetQueryHandler::handle)
        queryDispatcher.registerHandler(SearchTimesheetForManagerQuery::class.java, timesheetQueryHandler::handle)
        queryDispatcher.registerHandler(TimesheetForRejectedByManagerByEmployeeIdQuery::class.java, timesheetQueryHandler::handle)
        queryDispatcher.registerHandler(FindTimesheetByStatusAndWeekEndingDateQuery::class.java, timesheetQueryHandler::handle)
        queryDispatcher.registerHandler(FindTimesheetRowByIdQuery::class.java, timesheetQueryHandler::handle)
        queryDispatcher.registerHandler(FindApprovedTimesheetForEmployeeQuery::class.java, timesheetQueryHandler::handle)
        queryDispatcher.registerHandler(FindApprovedTimesheetForManagerQuery::class.java, timesheetQueryHandler::handle)
        queryDispatcher.registerHandler(FindApprovedTimesheetForAdminQuery::class.java, timesheetQueryHandler::handle)
        queryDispatcher.registerHandler(FindTimesheetCountForEmployeeByStatusQuery::class.java, timesheetQueryHandler::handle)
        queryDispatcher.registerHandler(FindTimesheetCountForManagerByStatusQuery::class.java, timesheetQueryHandler::handle)
        queryDispatcher.registerHandler(FindTimesheetCountForAdminByStatusQuery::class.java, timesheetQueryHandler::handle)
        queryDispatcher.registerHandler(ExportTimesheetToExelByIdQuery::class.java, timesheetQueryHandler::handle)
        queryDispatcher.registerHandler(SearchTimesheetListForEmployeeQuery::class.java, timesheetQueryHandler::handle)
        queryDispatcher.registerHandler(FindTimesheetByStatusListAndBeforeTodaysDateQuery::class.java, timesheetQueryHandler::handle)
        queryDispatcher.registerHandler(FindTimesheetCountForSupervisorByStatusQuery::class.java, timesheetQueryHandler::handle)
        queryDispatcher.registerHandler(SearchTimesheetRowsQuery::class.java, timesheetQueryHandler::handle)
        queryDispatcher.registerHandler(FindTimesheetRowByIdExportToQuickBookQuery::class.java, timesheetQueryHandler::handle)
        queryDispatcher.registerHandler(SearchTimesheetRowsByFilterExportToExcelQuery::class.java, timesheetQueryHandler::handle)

        // jobcode
        queryDispatcher.registerHandler(FindJobCodeByIdQuery::class.java, jobCodeQueryHandler::handle)
        queryDispatcher.registerHandler(FindJobCodeByCodeQuery::class.java, jobCodeQueryHandler::handle)
        queryDispatcher.registerHandler(SearchJobCodeByFilterQuery::class.java, jobCodeQueryHandler::handle)
        queryDispatcher.registerHandler(FindAllJobCodeQuery::class.java, jobCodeQueryHandler::handle)
        queryDispatcher.registerHandler(PageableFindCostCodeByJobCodeIdQuery::class.java, jobCodeQueryHandler::handle)
        queryDispatcher.registerHandler(PageableFindAllActiveStatusCostCodeByIdQuery::class.java, jobCodeQueryHandler::handle)
        queryDispatcher.registerHandler(FindCostCodeAllActiveStatusByIdQuery::class.java, jobCodeQueryHandler::handle)
        queryDispatcher.registerHandler(FindCostCodeByJobCodeIdQuery::class.java, jobCodeQueryHandler::handle)
        queryDispatcher.registerHandler(FindCostCodByJobCodeIdIdAndIdQuery::class.java, jobCodeQueryHandler::handle)
        queryDispatcher.registerHandler(FindAllActiveStatusJobCodeByIdQuery::class.java, jobCodeQueryHandler::handle)
        queryDispatcher.registerHandler(FindCostCodeByCodeQuery::class.java, jobCodeQueryHandler::handle)
        queryDispatcher.registerHandler(SearchJobCodeByCodeQuery::class.java, jobCodeQueryHandler::handle)
        queryDispatcher.registerHandler(SearchCostCodeByFilterQuery::class.java, jobCodeQueryHandler::handle)
        queryDispatcher.registerHandler(FindAllJobCodeByWatcherQuery::class.java, jobCodeQueryHandler::handle)
        queryDispatcher.registerHandler(SearchJobCodeByProjectIdQuery::class.java, jobCodeQueryHandler::handle)

        // settings
        queryDispatcher.registerHandler(FindSettingsByTenantIdQuery::class.java, settingsQueryHandler::handle)
        queryDispatcher.registerHandler(FindExpenseTypeByTenantIdQuery::class.java, settingsQueryHandler::handle)
        queryDispatcher.registerHandler(FindSettingsMileageRateByTenantIdQuery::class.java, settingsQueryHandler::handle)
        queryDispatcher.registerHandler(FindPayrollItemByTenantIdQuery::class.java, settingsQueryHandler::handle)
        queryDispatcher.registerHandler(FindPayrollItemByIdQuery::class.java, settingsQueryHandler::handle)
        queryDispatcher.registerHandler(FindSettingsYearlyQuarterByTenantIdQuery::class.java, settingsQueryHandler::handle)
        queryDispatcher.registerHandler(FindVehicleInfoByIdQuery::class.java, settingsQueryHandler::handle)
        queryDispatcher.registerHandler(PageableSearchAllVehicleInfoByFilterQuery::class.java, settingsQueryHandler::handle)
        queryDispatcher.registerHandler(FindAllActiveVehicleInfoQuery::class.java, settingsQueryHandler::handle)

        // inapp notification
        queryDispatcher.registerHandler(PageableFindInAppNotificationByUserIdAndStatusQuery::class.java, inAppNotificationQueryHandler::handle)
        queryDispatcher.registerHandler(FindInAppNotificationCountStatusQuery::class.java, inAppNotificationQueryHandler::handle)

        // email notification
        queryDispatcher.registerHandler(PageableFindEmailNotificationByUserIdAndStatusQuery::class.java, emailNotificationQueryHandler::handle)
        queryDispatcher.registerHandler(FindEmailNotificationCountStatusQuery::class.java, emailNotificationQueryHandler::handle)

        // email notification
        queryDispatcher.registerHandler(PageableFindMobileNotificationByUserIdAndStatusQuery::class.java, mobileNotificationQueryHandler::handle)
        queryDispatcher.registerHandler(FindMobileNotificationCountStatusQuery::class.java, mobileNotificationQueryHandler::handle)

        // expense report
        queryDispatcher.registerHandler(FindExpenseReportByIdQuery::class.java, expenseReportQueryHandler::handle)
        queryDispatcher.registerHandler(PageableSearchExpenseReportByFilterQuery::class.java, expenseReportQueryHandler::handle)
        queryDispatcher.registerHandler(FindExpenseReportCountForAdminByStatusQuery::class.java, expenseReportQueryHandler::handle)
        queryDispatcher.registerHandler(FindExpenseReportCountForEmployeeByStatusQuery::class.java, expenseReportQueryHandler::handle)
        queryDispatcher.registerHandler(FindExpenseReportCountForSupervisorByStatusQuery::class.java, expenseReportQueryHandler::handle)
        queryDispatcher.registerHandler(FindExpenseRowsForSuyashByIdQuery::class.java, expenseReportQueryHandler::handle)
        queryDispatcher.registerHandler(FindExpenseRowsForEmployeeByIdQuery::class.java, expenseReportQueryHandler::handle)
        queryDispatcher.registerHandler(ExportExpenseReportToExcelByIdQuery::class.java, expenseReportQueryHandler::handle)
        queryDispatcher.registerHandler(ExportExpenseReportDataToPdfByIdQuery::class.java, expenseReportQueryHandler::handle)
        queryDispatcher.registerHandler(SearchExpenseReportForAdminByJobCodeQuery::class.java, expenseReportQueryHandler::handle)
        queryDispatcher.registerHandler(SearchExpenseReportByJobCodeAndPeriodFromQuery::class.java, expenseReportQueryHandler::handle)
        queryDispatcher.registerHandler(ExpenseReportSearchRowExportToExcelQuery::class.java, expenseReportQueryHandler::handle)
        queryDispatcher.registerHandler(FindJobCodeByPeriodFromQuery::class.java, expenseReportQueryHandler::handle)
        queryDispatcher.registerHandler(FindExpenseReportCountForManagerByStatusQuery::class.java, expenseReportQueryHandler::handle)
        queryDispatcher.registerHandler(FindExpenseReportByTenantIdQuery::class.java, expenseReportQueryHandler::handle)

        // document
        queryDispatcher.registerHandler(DocumentViewByIdQuery::class.java, documentQueryHandler::handle)
        queryDispatcher.registerHandler(DocumentThumbnailByIdQuery::class.java, documentQueryHandler::handle)
        queryDispatcher.registerHandler(FindDocumentByIdQuery::class.java, documentQueryHandler::handle)

        // project
        queryDispatcher.registerHandler(FindProjectByIdQuery::class.java, projectQueryHandler::handle)
        queryDispatcher.registerHandler(FindAllProjectQuery::class.java, projectQueryHandler::handle)
        queryDispatcher.registerHandler(PageableSearchAllProjectByFilterQuery::class.java, projectQueryHandler::handle)
        queryDispatcher.registerHandler(FindAllActiveProjectQuery::class.java, projectQueryHandler::handle)

        // project report
        queryDispatcher.registerHandler(FindProjectReportByIdQuery::class.java, projectReportQueryHandler::handle)
        queryDispatcher.registerHandler(PageableSearchProjectReportByFilterQuery::class.java, projectReportQueryHandler::handle)
        queryDispatcher.registerHandler(FindAllProjectReportQuery::class.java, projectReportQueryHandler::handle)
        queryDispatcher.registerHandler(FindOutfallPhotoByIdQuery::class.java, projectReportQueryHandler::handle)
        queryDispatcher.registerHandler(FindAppendixByIdQuery::class.java, projectReportQueryHandler::handle)

        // system config
        queryDispatcher.registerHandler(FindSystemConfigQuery::class.java, systemConfigQueryHandler::handle)
        queryDispatcher.registerHandler(FindUISystemConfigQuery::class.java, systemConfigQueryHandler::handle)

        // tvhg input
        queryDispatcher.registerHandler(FindTvhgInputByIdQuery::class.java, tvhgInputQueryHandler::handle)
        queryDispatcher.registerHandler(FindTvhgInputByTenantIdQuery::class.java, tvhgInputQueryHandler::handle)
        queryDispatcher.registerHandler(PageableSearchAllTvhgInputQuery::class.java, tvhgInputQueryHandler::handle)
        queryDispatcher.registerHandler(FindStructureDrawingDataByIdQuery::class.java, tvhgInputQueryHandler::handle)
        queryDispatcher.registerHandler(FindProjectInformationByIdQuery::class.java, tvhgInputQueryHandler::handle)
        queryDispatcher.registerHandler(FindHydrologicInformationByIdQuery::class.java, tvhgInputQueryHandler::handle)
        queryDispatcher.registerHandler(FindPipeDrawingInformationByIdQuery::class.java, tvhgInputQueryHandler::handle)

        // tvhg config
        queryDispatcher.registerHandler(FindUnitsByIdQuery::class.java, tvhgConfigQueryHandler::handle)
        queryDispatcher.registerHandler(FindTvhgConfigQuery::class.java, tvhgConfigQueryHandler::handle)
        queryDispatcher.registerHandler(FindDesignStormByIdQuery::class.java, tvhgConfigQueryHandler::handle)
        queryDispatcher.registerHandler(FindMdStandardNumberByIdQuery::class.java, tvhgConfigQueryHandler::handle)

        // vehicle log
        queryDispatcher.registerHandler(FindVehicleLogByTenantIdQuery::class.java, vehicleLogQueryHandler::handle)
        queryDispatcher.registerHandler(PageableSearchAllVehicleLogByFilterQuery::class.java, vehicleLogQueryHandler::handle)
        queryDispatcher.registerHandler(FindVehicleLogByVehicleIdQuery::class.java, vehicleLogQueryHandler::handle)
        queryDispatcher.registerHandler(FindVehicleLogByIdQuery::class.java, vehicleLogQueryHandler::handle)
        queryDispatcher.registerHandler(FindVehicleLogForEmployeeByIdQuery::class.java, vehicleLogQueryHandler::handle)

        // ========== [END] QUERY ==========
    }
}

fun main(args: Array<String>) {
    val springApplication = SpringApplication(SycDashboardApplication::class.java)
    springApplication.addListeners(PropertyFilePatternRegisteringListener())
    springApplication.run(*args)
}
