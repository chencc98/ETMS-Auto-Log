package cairngormevents{
    import com.adobe.cairngorm.control.CairngormEvent;
    
    import flash.events.Event;

    public class SimpleEvent extends CairngormEvent{
        public static const PROJECT_INIT:String = "PROJECT_INIT";
        public function SimpleEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false){
            super(type, bubbles, cancelable);
        }
        
        override public function clone():Event{
            return new SimpleEvent(type, bubbles, cancelable);
        }
        
    }
}