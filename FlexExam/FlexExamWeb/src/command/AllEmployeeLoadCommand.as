package command{
    
    
    import business.AllEmployeeLoadDelegate;
    
    import cairngormevents.AllEmployeeLoadEvent;
    
    import com.adobe.cairngorm.commands.ICommand;
    import com.adobe.cairngorm.control.CairngormEvent;
    
    import model.ExamModelLocator;
    
    import mx.collections.ArrayCollection;
    import mx.controls.Alert;
    import mx.managers.PopUpManager;
    import mx.rpc.IResponder;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;
    
    import pm.EmployeeSelectorPm;
    
    import ui.EmployeeSelector;
    import ui.EmployeeSelectorWrap;

    public class AllEmployeeLoadCommand implements ICommand, IResponder {
        private var pm:EmployeeSelectorPm;
        private var propName:String;
        public function AllEmployeeLoadCommand()
        {
        }

        public function execute(event:CairngormEvent):void {
            var select:AllEmployeeLoadEvent = event as AllEmployeeLoadEvent;
            this.pm = select.getPM();
            this.propName = select.getPropName();
            
            
            var delegate:AllEmployeeLoadDelegate = new AllEmployeeLoadDelegate(this);
            delegate.loadEmployee();
        }
        
        public function result(data:Object):void {
            var array:ArrayCollection = (data as ResultEvent).result as ArrayCollection;
            pm[this.propName] = array;
            
//            var employeeSelector:EmployeeSelector = EmployeeSelectorWrap.getEmployeeSelector();
//            PopUpManager.addPopUp(employeeSelector, this.parentPop, true);
//            PopUpManager.centerPopUp(employeeSelector);
        }
        
        public function fault(info:Object):void {
            var fault:FaultEvent = info as FaultEvent;
            Alert.show("fail due to " + fault.message);
        }
        
    }
}