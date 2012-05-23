package utils {
    import entity.Employee;
    
    import flash.events.Event;

    

    public class PopUpCloseEvent extends Event{
        public static const POPUP_CLOSE_EVENT:String = "PopUpCloseEvent";
        

        public function PopUpCloseEvent(type:String){
            super(type);
            
        }

        
        
        override public function clone():Event{
            return new PopUpCloseEvent(type);
        }
    }
}