package cairngormevents{
    import com.adobe.cairngorm.control.CairngormEvent;
    
    import flash.events.Event;
    
    import pm.MainPanelPm;

    public class GetMainPanelProjectEvent extends CairngormEvent {
        public static const GET_MAINPANEL_PROJECT:String = "GET_MAINPANEL_PROJECT";
        private var pm:MainPanelPm;
        private var proName:String;
        public function GetMainPanelProjectEvent(type:String, mp:MainPanelPm, pro:String) {
            
            super(type);
            this.pm = mp;
            this.proName = pro;
        }
        
        override public function clone():Event{
            return new GetMainPanelProjectEvent(type, pm, proName);
        }
        
        public function get getPM():MainPanelPm{
            return this.pm;
        }
        public function get getProName():String{
            return this.proName;
        }
        
    }
}