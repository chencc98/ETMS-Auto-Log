package utils{
    import flash.events.Event;

    public class ProjectRefreshEvent extends Event{
        public static const REFRESH_EVENT:String = "ProjectRefreshEvent";
        public static const JUST_CLOSE:String = "JUST_CLOSE";
        public static const DO_REFRESH:String = "DO_REFRESH";
        
        private var whatDo:String;

        public function ProjectRefreshEvent(type:String, bubbles:Boolean=false,
                what:String=ProjectRefreshEvent.DO_REFRESH)
        {
            super(type,bubbles);
            whatDo = what;
        }
        
        override public function clone():Event{
            return new ProjectRefreshEvent(type, bubbles, whatDo);
        }
        
        public function get whatToDo():String{
            return whatDo;
        }

    }
}