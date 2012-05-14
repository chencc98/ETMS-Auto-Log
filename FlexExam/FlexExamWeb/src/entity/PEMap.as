package entity{
    public class PEMap{
        public var projectId:String;
        public var employeeId:String;

        public function PEMap(){
            projectId = "";
            employeeId = "";
        }

        public function PEMap(pid:String, eid:String){
            projectId = pid;
            employeeId = eid;
        }

    }
}