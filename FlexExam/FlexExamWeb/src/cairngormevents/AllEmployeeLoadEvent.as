package cairngormevents{
    import com.adobe.cairngorm.control.CairngormEvent;
    import flash.display.DisplayObjectContainer;
    import flash.events.Event;
    

    public class AllEmployeeLoadEvent extends CairngormEvent {
        public static const ALL_EMPLOYEE_LOAD:String = "ALL_EMPLOYEE_LOAD";
        private var parentPop:DisplayObjectContainer;
        public function AllEmployeeLoadEvent(type:String, pa:DisplayObjectContainer){
            super(type);
            this.parentPop = pa;
        }
        
        public function getParent():DisplayObjectContainer{
            return this.parentPop;
        }
        
        override public function clone():Event{
            return new AllEmployeeLoadEvent(AllEmployeeLoadEvent.ALL_EMPLOYEE_LOAD, parentPop);
        }
    }
}