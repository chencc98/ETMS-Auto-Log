package cairngormevents{
    import com.adobe.cairngorm.control.CairngormEvent;
    
    import flash.events.Event;

    public class SimpleEvent extends CairngormEvent{
        public static const PROJECT_INIT:String = "PROJECT_INIT";
        //public static const ALL_EMPLOYEE_LOAD = "ALL_EMPLOYEE_LOAD";
        public function SimpleEvent(type:String){
            super(type);
        }
        
        override public function clone():Event{
            return new SimpleEvent(type);
        }
        
    }
}