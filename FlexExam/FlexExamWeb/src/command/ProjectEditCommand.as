package command{
    
    
    import business.ProjectEditDelegate;
    
    import cairngormevents.ProjectEditEvent;
    import cairngormevents.SimpleEvent;
    
    import com.adobe.cairngorm.commands.ICommand;
    import com.adobe.cairngorm.control.CairngormEvent;
    import com.adobe.cairngorm.control.CairngormEventDispatcher;
    
    import mx.controls.Alert;
    import mx.rpc.IResponder;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;

    public class ProjectEditCommand implements ICommand, IResponder{
        private var feedback:Function;
        public function ProjectEditCommand()
        {
        }

        public function execute(event:CairngormEvent):void {
            var projectEditEvent:ProjectEditEvent = event as ProjectEditEvent;
            this.feedback = projectEditEvent.getFeedback();
            var delegate:ProjectEditDelegate = new ProjectEditDelegate(this);
            delegate.editProject(projectEditEvent.projectID, projectEditEvent.progressValue,
                    projectEditEvent.newEmployeesList);
        }
        
        public function result(data:Object):void {
            var text:String = (data as ResultEvent).result as String;
            Alert.show(text,"",4,null,feedback);
            
        }
        
        public function fault(info:Object):void{
            var fault:FaultEvent = info as FaultEvent;
            Alert.show("fail due to " + fault.message,"",4,null,feedback);
            
        }
        
        private function triggerRefresh():void{
            var trigger:SimpleEvent = new SimpleEvent(SimpleEvent.PROJECT_INIT);
            CairngormEventDispatcher.getInstance().dispatchEvent(trigger);
            
        }
        
    }
}