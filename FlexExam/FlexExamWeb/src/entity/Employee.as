package entity
{
	import entity.EmployeeStatus;
	public class Employee
	{
		public var employeeid:String;
		public var employeename:String;
		
		public var employeestatus:String;
		
		public function Employee()
		{
			employeeid = "";
			employeename = "";
			employeestatus = EmployeeStatus.DEFAULT;
		}
		
		public function Employee(id:String, name:String, status:String){
			employeeid = id;
			employeename = name;
			employeestatus = status;
		}
		
		

	}
}