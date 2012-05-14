package entity {
    import entity.EmployeeStatus;
    public class Employee {
        public var employeeId:String;
        public var employeeName:String;

        public var employeeStatus:String;

        public function Employee() {
            employeeId = "";
            employeeName = "";
            employeeStatus = EmployeeStatus.DEFAULT;
        }

        public function get toXMLFormat():String{
            return "<employee>"
                    + "<id>" + employeeId + "</id>"
                    + "<name>" + employeeName + "</name>"
                    + "<status>" + employeeStatus + "</status>"
                    + "</employee>";
        }



    }
}