package command{
    
    
    import com.adobe.cairngorm.commands.ICommand;
    import com.adobe.cairngorm.control.CairngormEvent;
    import flash.display.DisplayObjectContainer;
    import mx.collections.ArrayCollection;
    import mx.controls.Alert;
    import mx.managers.PopUpManager;
    import mx.managers.SystemManager;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;
    import mx.rpc.IResponder;
    
    
    import business.ProjectDetailDelegate;
    import cairngormevents.ProjectDetailPopUpEvent;
    import entity.MainPanelProject;
    import model.ExamModelLocator;
    import pm.ProjectDetailPm;
    import ui.ProjectDetail;
    import ui.ProjectDetailWrap;

    public class OpenProjectDetailCommand implements ICommand, IResponder{
        private var project:MainPanelProject;
        private var parent:DisplayObjectContainer;
        public function OpenProjectDetailCommand(){
        }

        public function execute(event:CairngormEvent):void{
            var detailEvent:ProjectDetailPopUpEvent = event as ProjectDetailPopUpEvent;
            this.project = detailEvent.getProjectInfo();
            this.parent = detailEvent.getPopUpParent();
            
            var delegate:ProjectDetailDelegate = new ProjectDetailDelegate(this );
            delegate.loadEmployee(project.id);
            
        }
        
        public function result(data:Object):void{
            //Alert.show("ok");
            var mpm:ProjectDetailPm = ExamModelLocator.getInstance().projectDetailPm;
            mpm.projectDetail = this.project;
            var arraylist:ArrayCollection = (data as ResultEvent).result as ArrayCollection;
            mpm.employeeList = arraylist;
            
            var projectDetail:ProjectDetail = ProjectDetailWrap.getProjectDetailUI();
            PopUpManager.addPopUp(projectDetail, this.parent, true);
            PopUpManager.centerPopUp(projectDetail);
            
        }
        
        public function fault(info:Object):void{
            var fault:FaultEvent = info as FaultEvent;
            Alert.show("fail to load employees due to " + fault.message);
        }
        

        

        
    }
}