package control{
    import com.adobe.cairngorm.control.FrontController;
    import cairngormevents.SimpleEvent;
    import command.LoadEmployeeInitCommand;

    public class ExamController extends FrontController{
        public function ExamController(){
            
            
            addCommand(SimpleEvent.PROJECT_INIT,LoadEmployeeInitCommand);
        }
        
    }
}