package entity {
    import entity.EmployeeStatus;
    public class Employee {
        public var id:String;
        public var name:String;

        public var status:String;

        public function Employee() {
            id = "";
            name = "";
            status = EmployeeStatus.DEFAULT;
        }

        public function get toXMLFormat():String{
            return "<employee>"
                    + "<id>" + id + "</id>"
                    + "<name>" + name + "</name>"
                    + "<status>" + status + "</status>"
                    + "</employee>";
        }



    }
}