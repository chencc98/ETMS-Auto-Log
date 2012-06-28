package pm{
    import mx.collections.ArrayCollection;
    import cairngormevents.AllEmployeeLoadEvent;
    import com.adobe.cairngorm.control.CairngormEventDispatcher;
    
    public class EmployeeSelectorPm{
        [Bindable]
        public var employeeList:ArrayCollection;
        public function EmployeeSelectorPm(){
            employeeList = new ArrayCollection();
        }
        
        public function loadAllEmployee():void{
            var select:AllEmployeeLoadEvent = new AllEmployeeLoadEvent(
                        AllEmployeeLoadEvent.ALL_EMPLOYEE_LOAD, this,"employeeList");
            CairngormEventDispatcher.getInstance().dispatchEvent(select);
        }

    }
}