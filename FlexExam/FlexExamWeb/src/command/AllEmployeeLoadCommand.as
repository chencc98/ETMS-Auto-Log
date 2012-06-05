package command{
    
    
    import com.adobe.cairngorm.commands.ICommand;
    import com.adobe.cairngorm.control.CairngormEvent;
    import flash.display.DisplayObjectContainer;
    import mx.collections.ArrayCollection;
    import mx.controls.Alert;
    import mx.managers.PopUpManager;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;
    import mx.rpc.IResponder;

    
    
    import business.AllEmployeeLoadDelegate;
    import cairngormevents.AllEmployeeLoadEvent;
    import model.ExamModelLocator;
    import ui.EmployeeSelector;
    import ui.EmployeeSelectorWrap;

    public class AllEmployeeLoadCommand implements ICommand, IResponder {
        private var parentPop:DisplayObjectContainer;
        public function AllEmployeeLoadCommand()
        {
        }

        public function execute(event:CairngormEvent):void {
            var select:AllEmployeeLoadEvent = event as AllEmployeeLoadEvent;
            parentPop = select.getParent();
            
            var delegate:AllEmployeeLoadDelegate = new AllEmployeeLoadDelegate(this);
            delegate.loadEmployee();
        }
        
        public function result(data:Object):void {
            var array:ArrayCollection = (data as ResultEvent).result as ArrayCollection;
            ExamModelLocator.getInstance().employeeSelectorPm.employeeList = array;
            
            var employeeSelector:EmployeeSelector = EmployeeSelectorWrap.getEmployeeSelector();
            PopUpManager.addPopUp(employeeSelector, this.parentPop, true);
            PopUpManager.centerPopUp(employeeSelector);
        }
        
        public function fault(info:Object):void {
            var fault:FaultEvent = info as FaultEvent;
            Alert.show("fail due to " + fault.message);
        }
        
    }
}