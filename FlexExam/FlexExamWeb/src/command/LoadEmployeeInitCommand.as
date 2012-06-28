package command{
    
    
    
    
    import business.MainPanelInitDelegate;
    
    import cairngormevents.GetMainPanelProjectEvent;
    
    import com.adobe.cairngorm.commands.ICommand;
    import com.adobe.cairngorm.control.CairngormEvent;
    
    import mx.collections.ArrayCollection;
    import mx.controls.Alert;
    import mx.rpc.IResponder;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;
    
    import pm.MainPanelPm;

    public class LoadEmployeeInitCommand implements ICommand, IResponder{
        private var pm:MainPanelPm ;
        private var proName:String;
        public function LoadEmployeeInitCommand(){
        }

        public function execute(event:CairngormEvent):void{
            //Alert.show("execute the command");
            var ev:GetMainPanelProjectEvent = event as GetMainPanelProjectEvent;
            this.pm = ev.getPM;
            this.proName = ev.getProName;
            var delegate:MainPanelInitDelegate = new MainPanelInitDelegate(this);
            delegate.loadEmployee();
        }
        
        public function result(data:Object):void{
            //Alert.show("ok");
            //var mpm:MainPanelPm = ExamModelLocator.getInstance().mainPanelPm;
            var arraylist:ArrayCollection = (data as ResultEvent).result as ArrayCollection;
            this.pm[this.proName] = arraylist;
        }
        
        public function fault(info:Object):void{
            var fault:FaultEvent = info as FaultEvent;
            Alert.show("fail due to " + fault.message);
        }
        
    }
}