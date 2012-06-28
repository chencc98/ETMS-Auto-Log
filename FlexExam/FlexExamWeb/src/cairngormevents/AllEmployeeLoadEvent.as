package cairngormevents{
    import com.adobe.cairngorm.control.CairngormEvent;
    
    import flash.events.Event;
    
    import pm.EmployeeSelectorPm;
    

    public class AllEmployeeLoadEvent extends CairngormEvent {
        public static const ALL_EMPLOYEE_LOAD:String = "ALL_EMPLOYEE_LOAD";
        private var employeePm:EmployeeSelectorPm;
        private var proName:String;
        public function AllEmployeeLoadEvent(type:String, pa:EmployeeSelectorPm, prop:String){
            super(type);
            this.employeePm = pa;
            this.proName = prop;
        }
        
        public function getPM():EmployeeSelectorPm{
            return this.employeePm;
        }
        
        public function getPropName():String{
            return this.proName;
        }
        
        override public function clone():Event{
            return new AllEmployeeLoadEvent(AllEmployeeLoadEvent.ALL_EMPLOYEE_LOAD, 
                    employeePm, proName);
        }
    }
}