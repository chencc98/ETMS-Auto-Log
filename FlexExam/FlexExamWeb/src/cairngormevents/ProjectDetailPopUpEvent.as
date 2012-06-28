package cairngormevents{
    import com.adobe.cairngorm.control.CairngormEvent;
    
    import entity.MainPanelProject;
    
    import flash.events.Event;
    
    import pm.ProjectDetailPm;

    public class ProjectDetailPopUpEvent extends CairngormEvent{
        public static const PROJECT_DETAIL:String = "PROJECT_DETAIL";
        private var project:MainPanelProject;
        private var pm:ProjectDetailPm;
        private var proName:String;
        public function ProjectDetailPopUpEvent(type:String, project:MainPanelProject, 
                mm:ProjectDetailPm, prop:String)
        {
            super(type);
            this.project = project;
            this.pm = mm;
            this.proName = prop;
        }
        
        public function getProjectInfo():MainPanelProject{
            return this.project;
        }
        public function getPM():ProjectDetailPm{
            return this.pm;
        }
        public function getPropName():String{
            return this.proName;
        }
        
        override public function clone():Event{
            return new ProjectDetailPopUpEvent(ProjectDetailPopUpEvent.PROJECT_DETAIL, 
                    project, pm, proName);
        }
        
    }
}