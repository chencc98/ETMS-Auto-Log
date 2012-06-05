package control{
    import com.adobe.cairngorm.control.FrontController;
    import cairngormevents.SimpleEvent;
    import cairngormevents.ProjectDetailPopUpEvent;
    import cairngormevents.ProjectEditEvent;
    import cairngormevents.AllEmployeeLoadEvent;
    import cairngormevents.AddEmployeeEvent;
    import command.LoadEmployeeInitCommand;
    import command.OpenProjectDetailCommand;
    import command.ProjectEditCommand;
    import command.AllEmployeeLoadCommand;
    import command.AddEmployeeCommand;

    public class ExamController extends FrontController{
        public function ExamController(){
            
            
            addCommand(SimpleEvent.PROJECT_INIT,LoadEmployeeInitCommand);
            addCommand(ProjectDetailPopUpEvent.PROJECT_DETAIL, OpenProjectDetailCommand);
            addCommand(ProjectEditEvent.PROJECT_EDIT,ProjectEditCommand);
            addCommand(AllEmployeeLoadEvent.ALL_EMPLOYEE_LOAD,AllEmployeeLoadCommand);
            addCommand(AddEmployeeEvent.ADD_EMPLOYEE_IN_PROJECT, AddEmployeeCommand);
        }
        
    }
}