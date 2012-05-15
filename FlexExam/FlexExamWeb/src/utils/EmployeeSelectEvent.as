package utils {
    import entity.Employee;
    
    import flash.events.Event;

    

    public class EmployeeSelectEvent extends Event{
        public static const EMPLOY_SELECT_EVENT:String = "EmployeeSelectEvent";
        private var employee:Employee;

        public function EmployeeSelectEvent(type:String, emp:Employee){
            super(type);
            employee = emp;
        }

        public function get EmployeeInside():Employee{
            return employee;
        }
        
        override public function clone():Event{
            return new EmployeeSelectEvent(type, employee);
        }
}
}