package cairngormevents{
    import com.adobe.cairngorm.control.CairngormEvent;
    import flash.display.DisplayObjectContainer;
    import flash.events.Event;
    
    import entity.MainPanelProject;

    public class ProjectDetailPopUpEvent extends CairngormEvent{
        public static const PROJECT_DETAIL:String = "PROJECT_DETAIL";
        private var project:MainPanelProject;
        private var popupParent:DisplayObjectContainer;
        public function ProjectDetailPopUpEvent(type:String, project:MainPanelProject, 
                parent:DisplayObjectContainer)
        {
            super(type);
            this.project = project;
            this.popupParent = parent;
        }
        
        public function getProjectInfo():MainPanelProject{
            return this.project;
        }
        public function getPopUpParent():DisplayObjectContainer{
            return this.popupParent;
        }
        
        override public function clone():Event{
            return new ProjectDetailPopUpEvent(ProjectDetailPopUpEvent.PROJECT_DETAIL, 
                    project, popupParent);
        }
        
    }
}