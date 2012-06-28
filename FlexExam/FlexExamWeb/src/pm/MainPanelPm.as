package pm{
    import mx.collections.ArrayCollection;
    import mx.collections.XMLListCollection;
    import com.adobe.cairngorm.control.CairngormEventDispatcher;
    import cairngormevents.GetMainPanelProjectEvent;
    
    
    public class MainPanelPm{
        [Bindable]
        public var employeeList:ArrayCollection;
        public function MainPanelPm() {
            this.employeeList = new ArrayCollection();
        }
        
        public function loadAllProjects():void{
            var init:GetMainPanelProjectEvent = new GetMainPanelProjectEvent(
                            GetMainPanelProjectEvent.GET_MAINPANEL_PROJECT, 
                            this,
                            "employeeList");
            CairngormEventDispatcher.getInstance().dispatchEvent(init);
        }

    }
}