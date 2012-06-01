package command{
    
    
    import business.MainPanelInitDelegate;
    
    import com.adobe.cairngorm.commands.ICommand;
    import com.adobe.cairngorm.control.CairngormEvent;
    
    import model.ExamModelLocator;
    
    import mx.collections.ArrayCollection;
    import mx.controls.Alert;
    import mx.rpc.IResponder;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;
    
    import pm.MainPanelPm;

    public class LoadEmployeeInitCommand implements ICommand, IResponder{
        public function LoadEmployeeInitCommand(){
        }

        public function execute(event:CairngormEvent):void{
            //Alert.show("execute the command");
            var delegate:MainPanelInitDelegate = new MainPanelInitDelegate(this);
            delegate.loadEmployee();
        }
        
        public function result(data:Object):void{
            //Alert.show("ok");
            var mpm:MainPanelPm = ExamModelLocator.getInstance().mainPanelPm;
            var arraylist:ArrayCollection = (data as ResultEvent).result as ArrayCollection;
            mpm.employeeList = arraylist;
        }
        
        public function fault(info:Object):void{
            var fault:FaultEvent = info as FaultEvent;
            Alert.show("fail due to " + fault.message);
        }
        
    }
}