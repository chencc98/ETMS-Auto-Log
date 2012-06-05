package cairngormevents{
    import com.adobe.cairngorm.control.CairngormEvent;
    import flash.events.Event;
    
    import entity.Employee;

    public class AddEmployeeEvent extends CairngormEvent{
        public static const ADD_EMPLOYEE_IN_PROJECT:String = "ADD_EMPLOYEE_IN_PROJECT";
        private var employee:Employee;
        public function AddEmployeeEvent(type:String, em:Employee) {
            super(type );
            this.employee = em;
        }
        
        public function getEmployee():Employee{
            return this.employee;
        }
        
        override public function clone():Event{
            return new AddEmployeeEvent(AddEmployeeEvent.ADD_EMPLOYEE_IN_PROJECT,employee );
        }
        
    }
}