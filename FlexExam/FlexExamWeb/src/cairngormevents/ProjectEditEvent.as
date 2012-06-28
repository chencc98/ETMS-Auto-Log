package cairngormevents{
    import com.adobe.cairngorm.control.CairngormEvent;
    
    import flash.events.Event;

    public class ProjectEditEvent extends CairngormEvent{
        public static const PROJECT_EDIT:String = "PROJECT_EDIT";
        
        private var projectid:String;
        private var progress:String;
        private var newEmployeeList:String;
        private var feedback:Function;
        public function ProjectEditEvent(type:String, ID:String, progress:String,
                list:String, fb:Function)
        {
            super(type);
            this.projectid = ID;
            this.progress = progress;
            this.newEmployeeList = list;
            this.feedback = fb;
        }
        
        public function get projectID():String{
            return this.projectid;
        }
        public function get progressValue():String{
            return this.progress;
        }
        public function get newEmployeesList():String{
            return this.newEmployeeList;
        }
        public function getFeedback():Function{
            return this.feedback;
        }
        
        override public function clone():Event{
            return new ProjectEditEvent(type, this.projectid, this.progress, this.newEmployeeList,
                    feedback);
        }
        
    }
}