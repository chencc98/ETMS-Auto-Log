package command{
    
    
    import business.ProjectDetailDelegate;
    
    import cairngormevents.ProjectDetailPopUpEvent;
    
    import com.adobe.cairngorm.commands.ICommand;
    import com.adobe.cairngorm.control.CairngormEvent;
    
    import entity.MainPanelProject;
    import entity.ProjectStatus;
    
    import model.ExamModelLocator;
    
    import mx.collections.ArrayCollection;
    import mx.controls.Alert;
    import mx.managers.PopUpManager;
    import mx.rpc.IResponder;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;
    
    import pm.ProjectDetailPm;
    
    import ui.ProjectDetail;
    import ui.ProjectDetailWrap;

    public class OpenProjectDetailCommand implements ICommand, IResponder{
        private var project:MainPanelProject;
        private var pm:ProjectDetailPm;
        private var proName:String;
        public function OpenProjectDetailCommand(){
        }

        public function execute(event:CairngormEvent):void{
            var detailEvent:ProjectDetailPopUpEvent = event as ProjectDetailPopUpEvent;
            this.project = detailEvent.getProjectInfo();
            this.pm = detailEvent.getPM();
            this.proName = detailEvent.getPropName();
            
            var delegate:ProjectDetailDelegate = new ProjectDetailDelegate(this );
            delegate.loadEmployee(project.id);
            
        }
        
        public function result(data:Object):void{
            //Alert.show("ok");
            
            
            
            var arraylist:ArrayCollection = (data as ResultEvent).result as ArrayCollection;
            this.pm[this.proName] = arraylist;
            
            
            
        }
        
        public function fault(info:Object):void{
            var fault:FaultEvent = info as FaultEvent;
            Alert.show("fail to load employees due to " + fault.message);
        }
        

        

        
    }
}