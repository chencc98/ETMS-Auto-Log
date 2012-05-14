package utils{
    import flash.events.Event;

    public class ProjectRefreshEvent extends Event{
        public static const REFRESH_EVENT:String = "ProjectRefreshEvent";

        public function ProjectRefreshEvent(type:String){
            super(type);
        }

    }
}