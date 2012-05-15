package utils{
    import flash.events.Event;

    public class ProjectRefreshEvent extends Event{
        public static const REFRESH_EVENT:String = "ProjectRefreshEvent";

        public function ProjectRefreshEvent(type:String, bubbles:Boolean=false){
            super(type,bubbles);
        }
        
        override public function clone():Event{
            return new ProjectRefreshEvent(type, bubbles);
        }

    }
}